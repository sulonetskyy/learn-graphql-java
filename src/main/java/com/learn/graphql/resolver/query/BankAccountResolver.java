package com.learn.graphql.resolver.query;

import com.learn.graphql.model.BankAccount;
import com.learn.graphql.model.Currency;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class BankAccountResolver implements GraphQLQueryResolver {

    public BankAccount bankAccount(UUID id, DataFetchingEnvironment environment) {
        log.info("Retrieving bank account id: {}", id);
        return BankAccount.builder()
                .id(id)
                .currency(Currency.USD)
                .build();
    }
}
