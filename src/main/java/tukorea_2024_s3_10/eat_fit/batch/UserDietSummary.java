package tukorea_2024_s3_10.eat_fit.batch;

import java.util.List;

public record UserDietSummary(Long userId, List<String> foods) {}
