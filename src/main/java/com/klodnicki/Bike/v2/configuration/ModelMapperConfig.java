package com.klodnicki.Bike.v2.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This is a configuration class for ModelMapper. ModelMapper is a simple, intelligent mapping library, it automatically
 * maps between objects.
 * The @Configuration annotation indicates that this class declares one or more @Bean methods
 * and may be processed by the Spring container to generate bean definitions and service requests
 * for those beans at runtime.
 */
@Configuration
public class ModelMapperConfig {

    /**
     * This method is annotated with @Bean which indicates that a method produces a bean
     * to be managed by the Spring container. The lifecycle of the bean is managed by Spring.
     * ModelMapper provides a simple API for object-to-object mapping. Its intelligent mapping
     * approach eliminates the need for most property mappings and converts complex types
     * and deep object graphs automatically.
     *
     * @return a new instance of ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
