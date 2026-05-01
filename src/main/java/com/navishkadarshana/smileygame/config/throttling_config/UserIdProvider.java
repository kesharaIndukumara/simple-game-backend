package com.navishkadarshana.smileygame.config.throttling_config;

import com.navishkadarshana.smileygame.utilities.IPAddressHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserIdProvider {
    private final IPAddressHandler ipAddressHandler;

    public UserIdProvider(IPAddressHandler ipAddressHandler) {
        this.ipAddressHandler = ipAddressHandler;
    }

    public Optional<String> getCurrentUserId() {
        return Optional.of(this.ipAddressHandler.getClientIP());
    }
}