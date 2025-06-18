package tukorea_2024_s3_10.eat_fit;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableBatchProcessing
public class EatFitApplication {

	public static void main(String[] args) {
		SpringApplication.run(EatFitApplication.class, args);
	}

}
