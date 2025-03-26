package tukorea_2024_s3_10.eat_fit.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EcsTestController {
    @GetMapping("/ECS-TEST4")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok().build();
    }
}
