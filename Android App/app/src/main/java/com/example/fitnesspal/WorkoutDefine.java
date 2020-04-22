package com.example.fitnesspal;

public class WorkoutDefine {
    String workoutDuration, workoutDetails, calBurned, workoutDate, id;

    public WorkoutDefine(String workoutDuration, String workoutDetails, String calBurned, String workoutDate, String id) {
        this.workoutDuration = workoutDuration;
        this.workoutDetails = workoutDetails;
        this.calBurned = calBurned;
        this.workoutDate = workoutDate;
        this.id = id;
    }

    public String getWorkoutDuration() {
        return workoutDuration;
    }

    public void setWorkoutDuration(String workoutDuration) {
        this.workoutDuration = workoutDuration;
    }

    public String getWorkoutDetails() {
        return workoutDetails;
    }

    public void setWorkoutDetails(String workoutDetails) {
        this.workoutDetails = workoutDetails;
    }

    public String getCalBurned() {
        return calBurned;
    }

    public void setCalBurned(String calBurned) {
        this.calBurned = calBurned;
    }

    public String getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(String workoutDate) {
        this.workoutDate = workoutDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}