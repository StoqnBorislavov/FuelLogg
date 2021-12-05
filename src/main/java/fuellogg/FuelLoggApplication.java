package fuellogg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FuelLoggApplication {

    public static void main(String[] args) {
        SpringApplication.run(FuelLoggApplication.class, args);
    }

}
