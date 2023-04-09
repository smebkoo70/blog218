package com.example.blog218.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.math.BigInteger;


@Configuration
@AutoConfigureAfter(JacksonAutoConfiguration.class)
public class JacksonConfiguration {
 
    public JacksonConfiguration(ObjectMapper objectMapper) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(new ToStringSerializer(Long.TYPE));
        module.addSerializer(new ToStringSerializer(Long.class));
        module.addSerializer(new ToStringSerializer(BigInteger.class));
        objectMapper.registerModule(module);
    }
 
}