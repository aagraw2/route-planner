package main.java.com.prototype;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class App{
    public static void main(String[] args){
        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(App.class)
                .headless(false).run(args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        System.out.println("Router service started successfully.");

    }
}