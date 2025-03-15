package tukorea_2024_s3_10.eat_fit.presentation.aws;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlbHealthCheckController {

    @GetMapping("/api/core/health-check")
    public ResponseEntity<Void> healthCheck() {
        return ResponseEntity.ok().build();
    }
}
