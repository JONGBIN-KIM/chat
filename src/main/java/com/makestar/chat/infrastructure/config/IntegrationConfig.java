package com.makestar.chat.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class IntegrationConfig {

    @Bean
    public MessageChannel chatMessageChannel() {
        return new QueueChannel();
    }

    @Bean
    public MessageChannel popularChatMessageChannel() {
        return new QueueChannel();
    }

}
