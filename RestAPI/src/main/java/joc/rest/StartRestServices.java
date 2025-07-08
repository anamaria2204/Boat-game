package joc.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication(scanBasePackages = "joc")
public class StartRestServices {
    private static final Logger logger= LogManager.getLogger();

    public static void main(String[] args) {
        SpringApplication.run(StartRestServices.class, args);
    }

    @Bean(name = "props")
    public Properties getBdProperties() {
        Properties props = new Properties();
        try {
            logger.info("Searching bd.config in directory " + (new File(".")).getAbsolutePath());
            props.load(new FileReader("bd.config"));
            logger.info("Configuration file bd.config loaded successfully");
        } catch (IOException e) {
            System.err.println("Configuration file bd.config not found: " + e);
            logger.error("Configuration file bd.config not found: {}", e.getMessage(), e);
        }
        return props;
    }
}
