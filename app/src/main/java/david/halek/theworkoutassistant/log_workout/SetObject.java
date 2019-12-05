package david.halek.theworkoutassistant.log_workout;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import david.halek.theworkoutassistant.ConnectionClass;

public class SetObject {
    public int userId = -1;
    public int  routineId = -1;
    public int exerciseId = -1;
    public String exerciseName = "";
    public int weight = 0;
    public int reps = 0;

    //
    // Constructors
    //
    public SetObject() { }

    public SetObject(int userId) {
        this.userId = userId;
    }

    public SetObject(int userId, int routineId, int exerciseId, String exerciseName) {
        this.userId = userId;
        this.routineId = routineId;
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
    }

    public SetObject(int userId, int routineId, int exerciseId, String exerciseName, int weight, int reps) {
        this.userId = userId;
        this.routineId = routineId;
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.weight = weight;
        this.reps = reps;
    }
//
    // Get list
    //

    static ArrayList<SetObject> getSetsList(int userId, int routineId) {
        ArrayList<SetObject> setsList = new ArrayList<>();

        String query = "SELECT * FROM [ExerciseRoutineDetails] WHERE ExerciseRoutineID = ?";
        int sets = 0;

        ConnectionClass connectionClass = new ConnectionClass();
        Connection con = connectionClass.CONN();

        try {
            PreparedStatement preparedQuery = con.prepareStatement(query);
            preparedQuery.setInt(1, routineId);
            Log.e("SetObject", "Prepared: " + preparedQuery.toString());
            ResultSet rs = preparedQuery.executeQuery();

            while (rs.next()) {
                SetObject ob = new SetObject(userId);
                ob.setUserId(userId);
                int exId = rs.getInt(3);  // ExerciseID
                ob.setExerciseId(exId);
                String exercise = ob.getExerciseName(exId);
                ob.setExerciseName(exercise);
                sets = rs.getInt(4); // Sets
                ob.setRoutineId(routineId);

                while (sets-- >= 0) {
                    setsList.add(ob);
//                    Log.e("setsList", "--> Adding: " + exercise);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        Log.e("setsList", "--> Size is " + setsList.size());
        return setsList;
    }

    static String getExerciseName(int exId) {
        String query = "SELECT [ExerciseName] FROM [Exercise] WHERE [ExerciseID] = ?";

        ConnectionClass connectionClass = new ConnectionClass();
        Connection con = connectionClass.CONN();

        try {
            PreparedStatement preparedQuery = con.prepareStatement(query);
            preparedQuery.setInt(1, exId);
            Log.e("SetObject", "getExerciseName prepared: " + preparedQuery.toString());
            ResultSet rs = preparedQuery.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    static String getRoutineName(int id) {
        String query = "SELECT [ExerciseRoutineName] FROM [ExerciseRoutine] WHERE [ExerciseRoutineID] = ?";

        ConnectionClass connectionClass = new ConnectionClass();
        Connection con = connectionClass.CONN();

        try {
            PreparedStatement preparedQuery = con.prepareStatement(query);
            preparedQuery.setInt(1, id);
            Log.e("SetObject", "Prepared: " + preparedQuery.toString());
            ResultSet rs = preparedQuery.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    boolean logSet() {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection con = connectionClass.CONN();

        String query = "INSERT INTO [ExerciseSet] " +
                "( UserID, RoutineID, ExerciseID, Weight, Reps, ExerciseName ) " +
                " VALUES ( ?, ?, ?, ?, ?, ? )";
//                "" + ROUTINE_NAME_COLUMN + ", " + ROUTINE_DESC_COLUMN + " ) " +
//                "VALUES (?, ? )";
        int result = -1;

        try {
            PreparedStatement preparedQuery = con.prepareStatement(query);
            preparedQuery.setInt( 1, userId);
            preparedQuery.setInt( 2, routineId);
            preparedQuery.setInt( 3, exerciseId);
            preparedQuery.setInt( 4, weight);
            preparedQuery.setInt( 5, reps);
            preparedQuery.setString( 6, exerciseName);
            Log.e("PREPARED", preparedQuery.toString());
            result = preparedQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (result > 0 ? true : false);
    }



    //
    // Setters and Getters
    //
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoutineId() {
        return routineId;
    }

    public void setRoutineId(int routineId) {
        this.routineId = routineId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }


    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
