package com.learn.graphql;

import com.learn.graphql.model.BankAccount;
import com.learn.graphql.model.Currency;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class BankAccountRepository {

    private final List<BankAccount> bankAccounts = List.of(
            BankAccount.builder()
                    .id(UUID.fromString("bc6898e0-f046-43cb-bcb5-c70646712441"))
                    .currency(Currency.USD)
                    .createdAt(ZonedDateTime.parse("2023-01-20T12:00:00.000Z"))
                    .build(),
            BankAccount.builder()
                    .id(UUID.fromString("bc6898e0-f046-43cb-bcb5-c70646712442"))
                    .currency(Currency.CFH)
                    .createdAt(ZonedDateTime.parse("2023-01-20T12:00:00.000Z"))
                    .build(),
            BankAccount.builder()
                    .id(UUID.fromString("bc6898e0-f046-43cb-bcb5-c70646712443"))
                    .currency(Currency.USD)
                    .createdAt(ZonedDateTime.parse("2023-01-20T12:00:00.000Z"))
                    .build(),
            BankAccount.builder()
                    .id(UUID.fromString("bc6898e0-f046-43cb-bcb5-c70646712444"))
                    .currency(Currency.CFH)
                    .createdAt(ZonedDateTime.parse("2023-01-20T12:00:00.000Z"))
                    .build(),
            BankAccount.builder()
                    .id(UUID.fromString("bc6898e0-f046-43cb-bcb5-c70646712445"))
                    .currency(Currency.USD)
                    .createdAt(ZonedDateTime.parse("2023-01-20T12:00:00.000Z"))
                    .build(),
            BankAccount.builder()
                    .id(UUID.fromString("bc6898e0-f046-43cb-bcb5-c70646712446"))
                    .currency(Currency.CFH)
                    .createdAt(ZonedDateTime.parse("2023-01-20T12:00:00.000Z"))
                    .build(),
            BankAccount.builder()
                    .id(UUID.fromString("bc6898e0-f046-43cb-bcb5-c70646712447"))
                    .currency(Currency.USD)
                    .createdAt(ZonedDateTime.parse("2023-01-20T12:00:00.000Z"))
                    .build(),
            BankAccount.builder()
                    .id(UUID.fromString("bc6898e0-f046-43cb-bcb5-c70646712448"))
                    .currency(Currency.CFH)
                    .createdAt(ZonedDateTime.parse("2023-01-20T12:00:00.000Z"))
                    .build())
            .stream()
            .sorted(Comparator.comparing(BankAccount::getId))
            .collect(Collectors.toList());

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public List<BankAccount> getBankAccountsAfter(UUID id) {
        return bankAccounts.stream()
                .dropWhile(bankAccount -> bankAccount.getId().compareTo(id) != 1)
                .toList();
    }
}
