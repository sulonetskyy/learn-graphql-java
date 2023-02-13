package com.learn.graphql.context.dataloader;

import com.learn.graphql.service.BalanceService;
import com.learn.graphql.util.CorrelationIdPropagationExecutor;
import lombok.RequiredArgsConstructor;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderFactory;
import org.dataloader.DataLoaderRegistry;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class DataLoaderRegistryFactory {
    public static final String BALANCE_DATA_LOADER = "BALANCE_DATA_LOADER";

    private static final Executor executor = CorrelationIdPropagationExecutor.wrap(
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));

    private final BalanceService balanceService;

    public DataLoaderRegistry create(String userId) {
        return DataLoaderRegistry.newRegistry()
                .register(BALANCE_DATA_LOADER, createBalanceDataLoader(userId))
                .build();
    }

    private DataLoader<UUID, BigDecimal> createBalanceDataLoader(String userId) {
        return DataLoaderFactory.newMappedDataLoader(accountIds ->
                CompletableFuture.supplyAsync(
                        () -> balanceService.getBalanceFor(accountIds, userId),
                        executor));
    }

// EXAMPLE OF THE OPTIONS READ FROM THE ENVIRONMENT
//    private DataLoader<UUID, BigDecimal> createBalanceDataLoader(String userId) {
//        return DataLoaderFactory.newMappedDataLoader((accountIds, environment) ->
//                        CompletableFuture.supplyAsync(
//                                () -> {
//                                    // here the context that can be provided from bellow DataLoaderOptions.newOptions()
//                                    String context = environment.getContext();
//                                    return balanceService.getBalanceFor(accountIds, userId);
//                                },
//                                executor),
//                DataLoaderOptions.newOptions().setBatchLoaderContextProvider(
//                        () -> { return "Any object: the context that can be read from the environment.getContext()";})
//        );
//    }
}
