package com.dimas.profile.delegate;

import com.dimas.common.UserStatus;
import com.dimas.profile.api.ApiUser;
import com.dimas.profile.api.ApiUserRequest;
import com.dimas.profile.service.*;
import com.dimas.profile.api.ApiPermissionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDelegate {

    private final UserService userService;
    private final PermissionService permissionService;
    private final KeycloakService keycloakService;
    private final AccountClient accountClient;
    private final NotificationClient notificationClient;
    private final UserMapper mapper;

    public List<ApiUser> getAll() {
        return mapper.mapAsList(userService.getAll());
    }

    public ApiUser createUser(ApiUserRequest userRequest) {
        final var newUser = userService.register(userRequest);
        final var kc = keycloakService.createUser(newUser.getId(), newUser.getLogin(), userRequest.getPassword());
        final var updateRequest = ApiUserRequest.builder()
                .keycloakId(UUID.fromString(kc.getId()))
                .status(UserStatus.NORMAL)
                .build();
        return mapper.map(userService.patch(newUser.getId(), updateRequest));
    }

    public void deleteUserById(Long userId) {
        userService.deleteById(userId);
    }

    public ApiUser get(Long userId) {
        return mapper.map(userService.findById(userId));
    }

    public ApiUser addPermission(Long userId, ApiPermissionRequest request) {
        final var user = userService.findById(userId);
        final var permission = permissionService.findById(request.getPermissionId());
        user.getPermissions().add(permission);
        return mapper.map(userService.save(user));
    }

    public ApiUser deletePermission(Long userId, ApiPermissionRequest request) {
        final var user = userService.findById(userId);
        final var permission = permissionService.findById(request.getPermissionId());
        user.getPermissions().remove(permission);
        return mapper.map(userService.save(user));
    }
}
