package com.dimas.cqrs;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.*;
import static org.springframework.util.Assert.*;

@Slf4j
@Getter
@Aggregate
public class AccountAggregate {

    @AggregateIdentifier
    private UUID id;
    private Long userId;
    private BigDecimal balance = BigDecimal.ZERO;
    //maybe add here status and if it=DELETED forbid any actions

    protected AccountAggregate() {
        // For Axon instantiation
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand cmd) {
        log.info("Command recieved={}", cmd);
        notNull(cmd.getAccountId(), "AccountId should not be null");
        notNull(cmd.getUserId(), "UserId should not be null");
        notNull(cmd.getInitialBalance(), "Initial balance should not be null");
        isTrue(cmd.getInitialBalance().compareTo(BigDecimal.ZERO) >= 0, "Initial balance cannot be negative");
        apply(new AccountCreatedEvent(cmd.getAccountId(), cmd.getUserId(), cmd.getInitialBalance()));
    }

    @CommandHandler
    public void debitCommand(DebitCommand cmd) {
        log.info("Command recieved={}", cmd);
        notNull(cmd.getAccountId(), "AccountId should not be null");
        notNull(cmd.getAmount(), "Amount should not be null");
        isTrue(cmd.getAmount().compareTo(BigDecimal.ZERO) >= 0, "Amount cannot be negative");
        final var newBalance = this.balance.add(cmd.getAmount());
        apply(new BalanceUpdatedEvent(cmd.getAccountId(), newBalance));
    }

    @CommandHandler
    public void creditCommand(CreditCommand cmd) {
        log.info("Command recieved={}", cmd);
        notNull(cmd.getAccountId(), "AccountId should not be null");
        notNull(cmd.getAmount(), "Amount should not be null");
        isTrue(cmd.getAmount().compareTo(BigDecimal.ZERO) >= 0, "Amount cannot be negative");
        final var newBalance = this.balance.subtract(cmd.getAmount());
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Insufficient funds");
        }
        apply(new BalanceUpdatedEvent(cmd.getAccountId(), newBalance));
    }

    @EventSourcingHandler
    private void handleCreatedEvent(AccountCreatedEvent event) {
        id = event.getAccountId();
        userId = event.getUserId();
        balance = event.getBalance();
    }

    @EventSourcingHandler
    private void handleBalanceUpdatedEvent(BalanceUpdatedEvent event) {
        balance = event.getBalance();
    }

}
