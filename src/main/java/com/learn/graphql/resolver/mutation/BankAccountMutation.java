package com.learn.graphql.resolver.mutation;

import com.learn.graphql.model.BankAccount;
import com.learn.graphql.model.CreateBankAccountInput;
import com.learn.graphql.model.Currency;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class BankAccountMutation implements GraphQLMutationResolver {

    private final Clock clock;

    public BankAccount createBankAccount(CreateBankAccountInput input, DataFetchingEnvironment environment) {
        log.info("Creating a bank account for {}", input);
        return BankAccount.builder()
                .id(UUID.randomUUID())
                .currency(Currency.USD)
                .createdOn(LocalDate.now(clock))
                .createdAt(ZonedDateTime.now(clock))
                .build();
    }

}
