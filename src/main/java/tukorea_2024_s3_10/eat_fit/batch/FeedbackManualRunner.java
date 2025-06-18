//package tukorea_2024_s3_10.eat_fit.batch;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import org.springframework.batch.item.Chunk;
//
//@Component
//@RequiredArgsConstructor
//public class FeedbackManualRunner implements CommandLineRunner {
//
//    private final DietReader reader;
//    private final FeedbackSqsWriter writer;
//
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("=== 리더 실행 시작 ===");
//        List<UserDietSummary> items = new ArrayList<>();
//
//        UserDietSummary item;
//        while ((item = reader.read()) != null) {
//            System.out.println("읽은 유저 ID: " + item.userId() + ", 음식: " + item.foods());
//            items.add(item);
//        }
//
//        System.out.println("=== 라이터 실행 시작 ===");
//
//        // ✅ 여기서 Chunk로 래핑
//        Chunk<UserDietSummary> chunk = new Chunk<>(items);
//        writer.write(chunk);
//
//        System.out.println("=== 라이터 실행 완료 ===");
//    }
//}
