package com.learn.graphql.resolver;

import com.learn.graphql.model.BankAccount;
import com.learn.graphql.model.Client;
import com.learn.graphql.util.CorrelationIdPropagationExecutor;
import graphql.execution.DataFetcherResult;
import graphql.kickstart.execution.error.GenericGraphQLError;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class ClientResolver implements GraphQLResolver<BankAccount> {
    private static final Executor executor = CorrelationIdPropagationExecutor.wrap(
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));

// DIRECT CALL
//    public Client client(BankAccount bankAccount) {
//        log.info("Retrieving client for bank account id: {}", bankAccount.getId());
//        return Client.builder()
//                .id(UUID.randomUUID())
//                .firstName("John")
//                .lastName("Doe")
//                .build();
//    }

// ASYNC CALL
//    public CompletableFuture<Client> client(BankAccount bankAccount) {
//        log.info("Retrieving client for bank account id: {}", bankAccount.getId());
//        return CompletableFuture.supplyAsync(
//                () -> {
//                    return Client.builder()
//                            .id(UUID.randomUUID())
//                            .firstName("John")
//                            .lastName("Doe")
//                            .build();
//                },
//                executor
//        );
//    }
//

    // ASYNC CALL WRAPPED IN DATA FETCHER RESULT
    public CompletableFuture<DataFetcherResult<Client>> client(BankAccount bankAccount) {
        log.info("Retrieving client for bank account id: {}", bankAccount.getId());
        return CompletableFuture.supplyAsync(
                () -> {
                    return DataFetcherResult.<Client>newResult()
                            .data(Client.builder()
                                    .id(UUID.randomUUID())
                                    .firstName("John")
                                    .lastName("Doe")
                                    .build())
                            .error(new GenericGraphQLError("an error"))
                            .build();
                },
                executor
        );
    }
}
