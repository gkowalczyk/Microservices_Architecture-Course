package com.example.cpurammetricscollectorms1rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    private static final String METRICS_EXCHANGE = "metrics.exchange";
    public static final String METRICS_QUEUE = "system-metrics.queue";
    public static final String METRICS_ROUTING_KEY = "system.metrics";


    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public DirectExchange metricsExchange() {
        return new DirectExchange(
                METRICS_EXCHANGE,
                true,
                true
        );
    }

    @Bean
    public Queue systemMetricsQueue() {
        return new Queue(
                METRICS_QUEUE,
                true
        );
    }

    @Bean
    public Binding metricsBinding(Queue systemMetricsQueue,
                                  DirectExchange metricsExchange) {
        return BindingBuilder
                .bind(systemMetricsQueue)
                .to(metricsExchange)
                .with(METRICS_ROUTING_KEY);
    }

}


