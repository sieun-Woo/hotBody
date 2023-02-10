package com.sparta.hotbody.exerciseRecord.service;


public class CalorieCalculator {

  // MET: 1분간 소비되는 단위 체중당 에너지 소비량
  private static final double MET_RUNNING = 8.0;
  private static final double MET_SWIMMING = 8.0;
  private static final double MET_CYCLING = 8.0;
  private static final double MET_WEIGHT_TRAINING = 6.0;
  private static final double MET_HIKING = 7.5;
  private static final double MET_WALKING = 3.0;
  private static final int KILO = 1000; //단위 변환

  public double calculateCaloriesBurned(double userWeight, int exerciseDurationInMinutes, ExerciseType exerciseType, int reps) {
    double met = 0.0;
    double oxygenIntake = 0; // 산소 흡입량

    switch (exerciseType) {
      case RUNNING:
        met = MET_RUNNING;
        break;
      case SWIMMING:
        met = MET_SWIMMING;
        break;
      case CYCLING:
        met = MET_CYCLING;
        break;
      case WALKING:
        met = MET_WALKING;
        break;
      case WEIGHT_TRAINING:
        met = MET_WEIGHT_TRAINING;
        break;
      case HIKING:
        met = MET_HIKING;
        break;
    }

    oxygenIntake = met * 3.5 * userWeight * exerciseDurationInMinutes;
    return oxygenIntake * 5 * reps / KILO; // oxygenIntake * 5 => 산소 1L당 약 5 Kcal소모
  }

  public enum ExerciseType {
    RUNNING, SWIMMING, CYCLING, WALKING, WEIGHT_TRAINING, HIKING
  }
}
