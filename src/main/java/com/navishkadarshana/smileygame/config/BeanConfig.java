package com.navishkadarshana.smileygame.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.navishkadarshana.smileygame.constants.AppConstants.DetailConstants.ASYNC_EXECUTOR;
import static com.navishkadarshana.smileygame.constants.AppConstants.PatternConstants.DATE_TIME_RESPONSE_PATTERN;
import static com.navishkadarshana.smileygame.constants.AppConstants.PatternConstants.TIME_ZONE;

/**
 * @author Navishka Darshana - navishkada@gmail.com
 * @project  simple-game-backend
 * @CreatedBy IntelliJ IDEA
 * @created 26/02/2024 - 23.16
 */

@EnableCaching
@EnableScheduling
@EnableAsync
@Configuration
@Log4j2
public class BeanConfig implements Jackson2ObjectMapperBuilderCustomizer {

    private final Environment environment;

    public BeanConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        return restTemplate;
    }

    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_RESPONSE_PATTERN)
                .withZone(ZoneId.of(TIME_ZONE));
        LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(formatter);

        builder.failOnEmptyBeans(false) // prevent InvalidDefinitionException Error
                .serializerByType(LocalDateTime.class, localDateTimeSerializer);
    }

    @Bean(ASYNC_EXECUTOR)
    public TaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(500);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("Async-");
        return executor;
    }

}
