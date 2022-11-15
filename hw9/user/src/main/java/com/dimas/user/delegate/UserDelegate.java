package com.dimas.user.delegate;

import com.dimas.cqrs.InsufficientFundsEvent;
import com.dimas.cqrs.NotificationStatus;
import com.dimas.user.api.ApiUser;
import com.dimas.user.api.ApiUserRequest;
import com.dimas.cqrs.AccountCreatedEvent;
import com.dimas.cqrs.BalanceUpdatedEvent;
import com.dimas.user.data.model.UserStatus;
import com.dimas.user.service.AccountClient;
import com.dimas.user.service.NotificationClient;
import com.dimas.user.service.UserMapper;
import com.dimas.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDelegate {

    private final UserService userService;
    private final AccountClient accountClient;
    private final NotificationClient notificationClient;
    private final UserMapper mapper;

    public ApiUser createUserAndWait(ApiUserRequest userRequest) {
        return createUser(userRequest);
//        startPinging
        //here we are waiting for UI when account will be created
//        send AccountCreateCommand and ping userstatus until NORMAL
    }

    public List<ApiUser> getAll() {
        // here we should avoid to fetch balances cause it could be expensive operation, instead we should give brief info and detailed info will be fetched later
        return mapper.mapAsList(userService.getAll());
    }

    public List<ApiUser> getAllWithDetails() {//what todo if errors occured? use NullValue pattern?
        return userService.getAll().parallelStream()
                .map(e -> getUserDetails(e.getId()))
                .collect(Collectors.toList());
    }

    public ApiUser getUserDetails(Long userId) {
        final var apiUser = mapper.map(userService.findById(userId));
        if (apiUser.getStatus() != UserStatus.NORMAL) {
            throw new RuntimeException("User is not ready. Status=" + apiUser.getStatus());
        }
        final var queryResult = accountClient.getAccountByUserId(userId);
        return apiUser.withBalance(queryResult.getBalance());
    }

    public ApiUser getUserDetailsAsync(Long userId) {
        //TODO use completablefutures
        return null;
    }

    public ApiUser createUser(ApiUserRequest userRequest) {
        final var newUser = userService.create(userRequest);
        final var accountId = accountClient.createAccount(newUser.getId(), BigDecimal.ZERO);
        final var updateRequest = ApiUserRequest.builder()
                .accountId(accountId)
                .status(UserStatus.ACCOUNT_PENDING)
                .build();
        return mapper.map(userService.patch(newUser.getId(), updateRequest));
    }

    public void deleteUserById(Long userId) {
        userService.deleteById(userId);
        accountClient.deleteAccount(userId);
    }

    @EventHandler
    public void onAccountCreated(AccountCreatedEvent event) {
        log.info("Received AccountCreatedEvent={}", event);
        final var updateRequest = ApiUserRequest.builder()
                .accountId(event.getAccountId())
                .status(UserStatus.NORMAL)
                .build();
        final var user = userService.patch(event.getUserId(), updateRequest);
        notificationClient.sendNotification(user, "Account was created", NotificationStatus.OK);
        log.info("Account with id={} was created for user with userId={}", user.getAccountId(), user.getId());
    }

    @EventHandler
    public void onBalanceUpdated(BalanceUpdatedEvent event) {
        log.info("Received BalanceUpdatedEvent={}", event);
        notificationClient.sendNotification(userService.findByAccountId(event.getAccountId()), "Balance was successfully updated", NotificationStatus.OK);
    }

    @EventHandler
    public void onInsufficientFunds(InsufficientFundsEvent event) {
        log.info("Received InsufficientFundsEvent={}", event);
        notificationClient.sendNotification(userService.findByAccountId(event.getAccountId()), "Insufficient funds", NotificationStatus.NOK);
    }

}
