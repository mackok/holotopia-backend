package nhl.stenden.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.context.annotation.ComponentScan.Filter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"nhl.stenden"}, useDefaultFilters = false, includeFilters = {@Filter(org.springframework.stereotype.Controller.class)})
public class WebConfig {

}
