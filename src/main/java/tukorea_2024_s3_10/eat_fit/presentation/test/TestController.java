package tukorea_2024_s3_10.eat_fit.presentation.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().build();
    }
}
