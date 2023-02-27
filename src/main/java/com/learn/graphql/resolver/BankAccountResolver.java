package com.learn.graphql.resolver;

import com.learn.graphql.context.dataloader.DataLoaderRegistryFactory;
import com.learn.graphql.model.BankAccount;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class BankAccountResolver implements GraphQLResolver<BankAccount> {

    @PreAuthorize("hasAnyAuthority('get:bank_account_balance')")
    public CompletableFuture<BigDecimal> balance(BankAccount bankAccount, DataFetchingEnvironment environment) {
        log.info("Retrieving balance for bank account id: {}", bankAccount.getId());

        DataLoader<UUID, BigDecimal> dataLoader = environment
                .getDataLoaderRegistry()
                .getDataLoader(DataLoaderRegistryFactory.BALANCE_DATA_LOADER);

        return dataLoader.load(bankAccount.getId(), bankAccount);
   }

}
