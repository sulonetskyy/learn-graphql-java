package com.learn.graphql.resolver.query;

import com.learn.graphql.BankAccountRepository;
import com.learn.graphql.connection.CursorUtil;
import com.learn.graphql.context.CustomGraphQLContext;
import com.learn.graphql.model.BankAccount;
import com.learn.graphql.model.Currency;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.*;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.SelectedField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class BankAccountQueryResolver implements GraphQLQueryResolver {

    private final BankAccountRepository bankAccountRepository;
    private final CursorUtil cursorUtil;

    public BankAccount bankAccount(UUID id, DataFetchingEnvironment environment) {
        log.info("Retrieving bank account id: {}", id);

        CustomGraphQLContext context = environment.getContext();
        log.info("User ID: {}", context.getUserId());

        var requestedFields = environment.getSelectionSet().getFields().stream()
                .map(SelectedField::getName)
                .collect(Collectors.toUnmodifiableSet());
        log.info("Requested fields: {}", requestedFields);

        return BankAccount.builder()
                .id(id)
                .currency(Currency.USD)
                .build();
    }

    public Connection<BankAccount> bankAccounts(int limit, @Nullable String cursor) {
        List<Edge<BankAccount>> edges = getBankAccounts(cursor)
                .stream()
                .map(bankAccount -> new DefaultEdge<>(bankAccount, cursorUtil.createCursorWith(bankAccount.getId())))
                .limit(limit)
                .collect(Collectors.toUnmodifiableList());

        var pageInfo = new DefaultPageInfo(
                cursorUtil.getFirstCursorFrom(edges),
                cursorUtil.getLastCursorFrom(edges),
                cursor != null,
                edges.size() >= limit);

        return new DefaultConnection<>(edges, pageInfo);
    }

    private List<BankAccount> getBankAccounts(@Nullable String cursor) {
        return (cursor == null)
                ? bankAccountRepository.getBankAccounts()
                : bankAccountRepository.getBankAccountsAfter(cursorUtil.decode(cursor));
    }

}
