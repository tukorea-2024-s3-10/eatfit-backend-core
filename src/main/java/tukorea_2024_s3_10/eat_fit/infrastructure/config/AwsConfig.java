package tukorea_2024_s3_10.eat_fit.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class AwsConfig {

    @Value("${aws.accessKeyId}")
    private String awsAccessKey;

    @Value("${aws.secretAccessKey}")
    private String awsSecretAccessKey;

    @Bean
    public SqsClient sqsClient() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(awsAccessKey, awsSecretAccessKey);

        return SqsClient.builder()
                .region(Region.AP_NORTHEAST_2) // 원하는 리전
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }
}
