package tukorea_2024_s3_10.eat_fit.global.user.util;

import tukorea_2024_s3_10.eat_fit.global.user.entity.UserGoal;

public class UserGoalCalculator {

    public static UserGoal recommendUserGoal(UserGoal userGoal){
        String goal = userGoal.getGoal();
        UserGoal newUserGoal;

        /**
         * 회원가입 키, 몸무게, 목표, 서비스
         * 메인 페이지 UserGoal < 조회 결과를 계속
         * 프로필 설정 몸무게 변경 -> 저장을 눌러 목표를 변경하고 저장을 눌러 -> 백엔드에서 UserGoal 내용을 계산해서 db 업데이트
         *
         */
        switch(goal){
            case "다이어트" -> newUserGoal = diet(userGoal);
            case "운동" -> newUserGoal = exercise(userGoal);
            case "건강" -> newUserGoal = health(userGoal);
            default -> throw new IllegalArgumentException("알 수 없는 목표");
        }

        return newUserGoal;
    }

    private static UserGoal diet(UserGoal userGoal){
        double standardWeight = calculateStandardWeight(userGoal);
        int goalKcal;

        if(standardWeight > userGoal.getWeight()){
            goalKcal = (int)(userGoal.getWeight()*25);
        }else{
            goalKcal = (int)(standardWeight*25);
        }

        //다이어트 탄단지 비율
        userGoal.setGoalKcal(goalKcal);
        userGoal.setGoalCarbs((goalKcal * 0.4) / 4);
        userGoal.setGoalProtein((goalKcal * 0.3) / 4);
        userGoal.setGoalFats((goalKcal * 0.3) / 9);

        return userGoal;

    }

    private static UserGoal exercise(UserGoal userGoal){
        double standardWeight = calculateStandardWeight(userGoal);
        int goalKcal;

        if(standardWeight > userGoal.getWeight()){
            goalKcal = (int)(userGoal.getWeight()*35);
        }else{
            goalKcal = (int)(userGoal.getWeight()*40);
        }

        userGoal.setGoalKcal(goalKcal);
        userGoal.setGoalCarbs((goalKcal * 0.4) / 4);
        userGoal.setGoalProtein((goalKcal * 0.4) / 4);
        userGoal.setGoalFats((goalKcal * 0.2) / 9);

        return userGoal;
    }

    private static UserGoal health(UserGoal userGoal){
        double standardWeight = calculateStandardWeight(userGoal);
        int goalKcal;

        goalKcal = (int)(standardWeight*30);

        userGoal.setGoalKcal(goalKcal);
        userGoal.setGoalCarbs((goalKcal * 0.5) / 4);
        userGoal.setGoalProtein((goalKcal * 0.3) / 4);
        userGoal.setGoalFats((goalKcal * 0.2) / 9);

        return userGoal;
    }

    private static double square(double x){
        return x * x;
    }

    private static double calculateStandardWeight(UserGoal userGoal){
        // 표준 체중 계산: 키(m)^2*22 or 21
        if(userGoal.getGender().equals("남성")){
            return square(userGoal.getHeight() / 100.0) * 22;
        }else {
            return square(userGoal.getHeight() / 100.0) * 21;
        }
    }
}
