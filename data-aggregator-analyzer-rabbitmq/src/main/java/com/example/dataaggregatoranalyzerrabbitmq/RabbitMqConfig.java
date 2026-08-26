package com.example.dataaggregatoranalyzerrabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {


    public static final String METRICS_EXCHANGE =
            "metrics.exchange";

    public static final String METRICS_QUEUE =
            "system-metrics.queue";

    public static final String METRICS_ROUTING_KEY =
            "system.metrics";


    public static final String ALERTS_EXCHANGE =
            "alerts.exchange";

    public static final String ALERTS_QUEUE =
            "alerts.queue";

    public static final String ALERTS_ROUTING_KEY =
            "ram.alert";


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
    public Binding metricsBinding(
            @Qualifier("systemMetricsQueue")
                    Queue systemMetricsQueue,

            @Qualifier("metricsExchange")
                    DirectExchange metricsExchange
    ) {
        return BindingBuilder
                .bind(systemMetricsQueue)
                .to(metricsExchange)
                .with(METRICS_ROUTING_KEY);
    }

    @Bean
    public DirectExchange alertsExchange() {
        return new DirectExchange(
                ALERTS_EXCHANGE,
                true,
                false
        );
    }

    @Bean
    public Queue alertsQueue() {
        return new Queue(
                ALERTS_QUEUE,
                true
        );
    }

    @Bean
    public Binding alertsBinding(
            @Qualifier("alertsQueue")
                    Queue alertsQueue,

            @Qualifier("alertsExchange")
                    DirectExchange alertsExchange
    ) {
        return BindingBuilder
                .bind(alertsQueue)
                .to(alertsExchange)
                .with(ALERTS_ROUTING_KEY);
    }
}