package com.makestar.chat.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "chat.popularity")
public class PopularityProperties {

    private int popularThreshold;
    private int superThreshold;
    private double dropRate;
    private int maxParticipants;
}