package com.learn.graphql.context;

import com.learn.graphql.context.dataloader.DataLoaderRegistryFactory;
import graphql.kickstart.execution.context.GraphQLKickstartContext;
import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoaderRegistry;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomGraphQLContextBuilder implements GraphQLServletContextBuilder {

    private final DataLoaderRegistryFactory dataLoaderRegistryFactory;

    @Override
    public GraphQLKickstartContext build(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        var userId = httpServletRequest.getHeader("user_id");

        Map<Object, Object> map = new HashMap<>();
        map.put(HttpServletRequest.class, httpServletRequest);
        map.put(HttpServletResponse.class, httpServletResponse);

        GraphQLKickstartContext context = GraphQLKickstartContext.of(dataLoaderRegistryFactory.create(userId), map);

        return new CustomGraphQLContext(userId, context);
    }

    @Override
    public GraphQLKickstartContext build(Session session, HandshakeRequest handshakeRequest) {
        throw new IllegalStateException("Unsupported");
    }

    @Override
    public GraphQLKickstartContext build() {
        throw new IllegalStateException("Unsupported");
    }
}
