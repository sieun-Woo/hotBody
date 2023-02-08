package com.sparta.hotbody.mypage.service;

public class CalorieCalculator {

  // MET: 1분간 소비되는 단위 체중당 에너지 소비량
  private static final double MET_RUNNING = 8.0;
  private static final double MET_SWIMMING = 8.0;
  private static final double MET_CYCLING = 8.0;
  private static final double MET_WALKING = 3.0;

  public double calculateCaloriesBurned(double userWeight, int exerciseDurationInMinutes, ExerciseType exerciseType) {
    double met = 0.0;

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
    }

    return (met * 3.5 * userWeight * exerciseDurationInMinutes) / 200;
  }

  public enum ExerciseType {
    RUNNING, SWIMMING, CYCLING, WALKING
  }
}
