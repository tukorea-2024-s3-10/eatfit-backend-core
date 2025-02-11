package tukorea_2024_s3_10.eat_fit.user.util;

import tukorea_2024_s3_10.eat_fit.user.entity.UserGoal;
import tukorea_2024_s3_10.eat_fit.user.entity.UserProfile;

public class UserGoalCalculator {

    public static UserGoal recommendUserGoal(UserProfile userProfile){
        String goal = userProfile.getGoalCategory();
        UserProfile newUserProfile;

        /**
         * 회원가입 키, 몸무게, 목표, 서비스
         * 메인 페이지 UserGoal < 조회 결과를 계속
         * 프로필 설정 몸무게 변경 -> 저장을 눌러 목표를 변경하고 저장을 눌러 -> 백엔드에서 UserGoal 내용을 계산해서 db 업데이트
         *
         */
        switch(goal){
            case "다이어트" -> newUserProfile = diet(userProfile);
            case "운동" -> newUserProfile = exercise(userProfile);
            case "건강" -> newUserProfile = health(userProfile);
            default -> throw new IllegalArgumentException("알 수 없는 목표");
        }

        return new UserGoal();
    }

    private static UserProfile diet(UserProfile userProfile){
        double standardWeight = calculateStandardWeight(userProfile);
        int goalKcal;

        if(standardWeight > userProfile.getWeight()){
            goalKcal = (int)(userProfile.getWeight()*25);
        }else{
            goalKcal = (int)(standardWeight*25);
        }

        //다이어트 탄단지 비율
//        userProfile.set(goalKcal);
//        userProfile.setGoalCarbs((goalKcal * 0.4) / 4);
//        userProfile.setGoalProtein((goalKcal * 0.3) / 4);
//        userProfile.setGoalFats((goalKcal * 0.3) / 9);

        return userProfile;

    }

    private static UserProfile exercise(UserProfile userProfile){
        double standardWeight = calculateStandardWeight(userProfile);
        int goalKcal;

        if(standardWeight > userProfile.getWeight()){
            goalKcal = (int)(userProfile.getWeight()*35);
        }else{
            goalKcal = (int)(userProfile.getWeight()*40);
        }

//        userProfile.setGoalKcal(goalKcal);
//        userProfile.setGoalCarbs((goalKcal * 0.4) / 4);
//        userProfile.setGoalProtein((goalKcal * 0.4) / 4);
//        userProfile.setGoalFats((goalKcal * 0.2) / 9);

        return userProfile;
    }

    private static UserProfile health(UserProfile userProfile){
        double standardWeight = calculateStandardWeight(userProfile);
        int goalKcal;

        goalKcal = (int)(standardWeight*30);
//
//        userProfile.setGoalKcal(goalKcal);
//        userProfile.setGoalCarbs((goalKcal * 0.5) / 4);
//        userProfile.setGoalProtein((goalKcal * 0.3) / 4);
//        userProfile.setGoalFats((goalKcal * 0.2) / 9);

        return userProfile;
    }

    private static double square(double x){
        return x * x;
    }

    private static double calculateStandardWeight(UserProfile userProfile){
        // 표준 체중 계산: 키(m)^2*22 or 21
        if(userProfile.getGender().equals("남성")){
            return square(userProfile.getHeight() / 100.0) * 22;
        }else {
            return square(userProfile.getHeight() / 100.0) * 21;
        }
    }
}
