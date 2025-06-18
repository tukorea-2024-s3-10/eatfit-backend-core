package tukorea_2024_s3_10.eat_fit.batch;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.DietRecordRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DietReader implements ItemReader<UserDietSummary> {

    private final DietRecordRepository dietRecordRepository;
    private Iterator<UserDietSummary> iterator;

    @PostConstruct
    public void init() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<Object[]> rawResults = dietRecordRepository.findUserDietSummaryByDate(yesterday);

        List<UserDietSummary> summaries = rawResults.stream()
                .map(row -> new UserDietSummary(
                        ((Number) row[0]).longValue(),
                        Arrays.asList(((String) row[1]).split(","))))
                .collect(Collectors.toList());

        this.iterator = summaries.iterator();
    }

    @Override
    public UserDietSummary read() throws Exception {
        return iterator != null && iterator.hasNext() ? iterator.next() : null;
    }
}
