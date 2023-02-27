package com.learn.graphql.service;

import com.learn.graphql.model.BankAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class BalanceService {
    public Map<UUID, BigDecimal> getBalanceFor(Map<UUID, BankAccount> accountIds, String userId) {
        log.info("Requesting bank account ids: {} for user id: {}", accountIds, userId);

        // EXAMPLE
        // var ids = accountIds.keySet();
        // base on balance response -> do smthg

        // MOCKED DATA
        return Map.of(
                UUID.fromString("bc6898e0-f046-43cb-bcb5-c70646712441"), new BigDecimal("1"),
                UUID.fromString("bc6898e0-f046-43cb-bcb5-c70646712442"), new BigDecimal("2"),
                UUID.fromString("bc6898e0-f046-43cb-bcb5-c70646712443"), new BigDecimal("3"),
                UUID.fromString("bc6898e0-f046-43cb-bcb5-c70646712444"), new BigDecimal("4"),
                UUID.fromString("bc6898e0-f046-43cb-bcb5-c70646712445"), new BigDecimal("5"),
                UUID.fromString("bc6898e0-f046-43cb-bcb5-c70646712446"), new BigDecimal("6")
        );
    }
}
