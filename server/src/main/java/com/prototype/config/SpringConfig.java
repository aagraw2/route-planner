package main.java.com.prototype.config;

import main.java.com.prototype.model.Grid;
import main.java.com.prototype.service.RouterService;
import main.java.com.prototype.service.RouterServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static main.java.com.prototype.model.Grid.initializeGrid;
@Configuration
public class SpringConfig {
    @Bean
    public Grid Grid() throws Exception {
        return  initializeGrid();
    }

    @Bean
    public RouterService RouterService(){
        return new RouterServiceImpl();
    }
}