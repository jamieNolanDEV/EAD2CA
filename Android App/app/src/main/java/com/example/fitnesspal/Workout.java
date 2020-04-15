package com.example.fitnesspal;

public class Workout {

    String id, date, workoutDuration, workoutDetails, caloriesBurned, user, userId;
    public Workout() {
    }

    public Workout(String id, String date, String workoutDuration, String workoutDetails, String caloriesBurned, String user, String userId) {
        this.id = id;
        this.date = date;
        this.workoutDuration = workoutDuration;
        this.workoutDetails = workoutDetails;
        this.caloriesBurned = caloriesBurned;
        this.user = user;
        this.userId = userId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(String caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
