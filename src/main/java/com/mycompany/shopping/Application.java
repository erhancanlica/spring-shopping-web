package com.mycompany.shopping;

import com.mycompany.shopping.config.ApplicationProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties({ApplicationProperties.class})
public class Application {


    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        Environment environment = application.run(args).getEnvironment();
        logApplicationStartup(environment);
    }

    private static void logApplicationStartup(Environment environment) {
        String protocol = Optional.ofNullable(environment.getProperty("server.ssl.key-store")).map(key -> "https").orElse("http");
        String serverPort = environment.getProperty("server.port");
        String contextPath = Optional
                .ofNullable(environment.getProperty("server.servlet.context-path"))
                .filter(StringUtils::isNotBlank)
                .orElse("/");
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info(
                "\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}{}\n\t" +
                        "External: \t{}://{}:{}{}\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                environment.getProperty("spring.application.name"),
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                environment.getActiveProfiles().length == 0 ? environment.getDefaultProfiles() : environment.getActiveProfiles()
        );
    }

}
