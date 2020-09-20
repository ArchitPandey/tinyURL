package tinyurl.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"tinyurl.config","tinyurl.commons","tinyurl.rest","tinyurl.service","tinyurl.entity","tinyurl.dao"})
public class DispatcherConfig implements WebMvcConfigurer {
	
}
