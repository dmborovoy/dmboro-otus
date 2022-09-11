package com.dimas.service;

import com.dimas.exception.ElementAlreadyExistsException;
import com.dimas.exception.KeycloakException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.*;

import static com.dimas.util.Constant.KEYCLOAK_USER_ID_ATTRIBUTE;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakService {

    private final RealmResource keycloak;

    public UserRepresentation get(String username, String firstName, String lastName, String email) {
        return find(username, firstName, lastName, email).orElseThrow(() -> {
            throw new NoSuchElementException("No user found for username=" + username);
        });
    }

    public UserRepresentation getByEmail(String email) {
        return find(null, null, null, email).orElseThrow(() -> {
            throw new NoSuchElementException("No user found for email=" + email);
        });
    }

    public Optional<UserRepresentation> find(String username, String firstName, String lastName, String email) {
        return keycloak.users().search(username, firstName, lastName, email, 0, 1)
                .stream().findFirst();
    }

    public List<UserRepresentation> findAll(String username, String firstName, String lastName, String email, int maxResults) {
        return keycloak.users().search(username, firstName, lastName, email, 0, maxResults);
    }

    public UserRepresentation get(UUID kkUserId) {
        return getUserResourceById(kkUserId).toRepresentation();
    }

    public UserRepresentation createUser(Long userId, String login, String password) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(login);
        userRepresentation.setFirstName(login);
        userRepresentation.setLastName(login);
        userRepresentation.setEmail(login);
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);
        userRepresentation.setAttributes(Map.of(KEYCLOAK_USER_ID_ATTRIBUTE, List.of(userId.toString())));
        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(password);
        credentials.setTemporary(false);
        userRepresentation.setCredentials(List.of(credentials));
        if (keycloak.users().count(userRepresentation.getUsername()) != 0) {//add our  validation by email
            throw new ElementAlreadyExistsException("Keycloak already contains user for username=" + userRepresentation.getUsername());
        }
        log.info("userRepresentation={}", userRepresentation);
        final var response = keycloak.users().create(userRepresentation);
        if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
            throw new KeycloakException("Error creating user: " + extractErrorMessage(response));
        }
        return get(userRepresentation.getUsername(), null, null, null);//can't we get it via kcId? no we cant cause KC generates it
    }

    private String extractErrorMessage(Response response) {
        final var responseBody = response.readEntity(String.class);
        final var responseReason = response.getStatusInfo().getReasonPhrase();
        return String.format("%s. %s", responseReason, responseBody);
    }

    public void updateUser(UUID kkUserId, UserRepresentation userRepresentation) {
        getUserResourceById(kkUserId).update(userRepresentation);
    }

    public void deleteUser(UUID kkUserId) {
        getUserResourceById(kkUserId).remove();
    }

    private UserResource getUserResourceById(UUID kkUserId) {
        return keycloak
                .users()
                .get(kkUserId.toString());
    }

}
