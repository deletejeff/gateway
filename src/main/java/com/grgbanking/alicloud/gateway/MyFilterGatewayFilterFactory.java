package com.grgbanking.alicloud.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author machao
 */
@Component
public class MyFilterGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {
    public static final Logger logger = LoggerFactory.getLogger(MyFilterGatewayFilterFactory.class);
    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return ((exchange, chain) -> {
            ServerHttpRequest serverHttpRequest = exchange.getRequest()
                    .mutate()
                    .header(config.getName(), config.getValue())
                    .build();
            ServerWebExchange serverWebExchange = exchange.mutate()
                    .request(serverHttpRequest)
                    .build();
            logger.info("MyFilter过滤器获取的参数：{} = {}", config.getName(), config.getValue());
            logger.info("请求的uri是：{}",serverHttpRequest.getURI());
            return chain.filter(serverWebExchange);
        });
    }
}
