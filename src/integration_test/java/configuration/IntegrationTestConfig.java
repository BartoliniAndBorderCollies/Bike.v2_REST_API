package configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 * This class provides configuration for integration tests.
 * It is marked with @TestConfiguration to indicate that it should not be picked up by scanning.
 */
@TestConfiguration
public class IntegrationTestConfig {

    /**
     * This method provides a bean of MvcRequestMatcher.Builder.
     * MvcRequestMatcher.Builder is used to build request matchers for Spring MVC.
     *
     * @param introspector The HandlerMappingIntrospector to be used by the MvcRequestMatcher.Builder.
     * @return MvcRequestMatcher.Builder A builder for MvcRequestMatcher.
     */
    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }
}
