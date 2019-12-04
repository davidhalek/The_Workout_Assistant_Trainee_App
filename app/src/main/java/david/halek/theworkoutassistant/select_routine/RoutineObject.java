package david.halek.theworkoutassistant.select_routine;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import david.halek.theworkoutassistant.ConnectionClass;

public class RoutineObject {

    public int exerciseRoutineId = -2;
    public String exerciseRoutineName = "";
    public String exerciseRoutineDesc = "";
    public String createDate = "";

    //
    // Constructors
    //

    public RoutineObject(int exerciseRoutineId, String exerciseRoutineName, String exerciseRoutineDesc, String createDate) {
        this.exerciseRoutineId = exerciseRoutineId;
        this.exerciseRoutineName = exerciseRoutineName;
        this.exerciseRoutineDesc = exerciseRoutineDesc;
        this.createDate = createDate;
    }

    public RoutineObject(int exerciseRoutineId, String exerciseRoutineName) {
        this.exerciseRoutineId = exerciseRoutineId;
        this.exerciseRoutineName = exerciseRoutineName;
    }

    public RoutineObject() {
    }

    public RoutineObject(int id) {

        String query = "SELECT * " +
                //"SELECT "+ ROUTINE_ID_COLUMN + ", " + ROUTINE_NAME_COLUMN + ", " + ROUTINE_DESC_COLUMN + ", " + ROUTINE_CREATE_DATE_COLUMN +
                "FROM "+ TABLE_NAME +
                " WHERE " + ROUTINE_ID_COLUMN +" = " + id;
                //"ORDER BY " + ROUTINE_ID_COLUMN ;

        ConnectionClass connectionClass = new ConnectionClass();
        Connection con = connectionClass.CONN();

        try {
            PreparedStatement preparedQuery = con.prepareStatement(query);
            Log.e("RoutineObject", preparedQuery.toString());
            ResultSet rs = preparedQuery.executeQuery();

            while (rs.next()) {
                this.setExerciseRoutineId(rs.getInt(1));
                this.setExerciseRoutineName(rs.getString(2));
                this.setExerciseRoutineDesc(rs.getString(3));
                this.setCreateDate(rs.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //
    // Database access
    //

    public static String TABLE_NAME = " [WorkoutAssistant].[dbo].[ExerciseRoutine] ";
    public static final String ROUTINE_ID_COLUMN = " [ExerciseRoutineID] ";
    public static final String ROUTINE_NAME_COLUMN = " [ExerciseRoutineName] ";
    public static final String ROUTINE_DESC_COLUMN = " [ExerciseRoutineDesc] ";
    public static final String ROUTINE_CREATE_DATE_COLUMN = " [CreateDate] ";

    public static ArrayList<RoutineObject> getRoutineList() {

        ArrayList routineList = new ArrayList();
        String query = "SELECT "+ ROUTINE_ID_COLUMN + ", " + ROUTINE_NAME_COLUMN + ", " + ROUTINE_DESC_COLUMN +
                "FROM "+ TABLE_NAME +
                "ORDER BY " + ROUTINE_ID_COLUMN;

        ConnectionClass connectionClass = new ConnectionClass();
        Connection con = connectionClass.CONN();

        try {
            PreparedStatement preparedQuery = con.prepareStatement(query);
            Log.e("RoutineObject", preparedQuery.toString());
            ResultSet rs = preparedQuery.executeQuery();

            while (rs.next()) {
                RoutineObject ob = new RoutineObject();
                ob.setExerciseRoutineId(rs.getInt(1));
                ob.setExerciseRoutineName(rs.getString(2));
                ob.setExerciseRoutineDesc(rs.getString(3));
                routineList.add(ob);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return routineList;
    }

    public static boolean addExerciseRoutine(String name, String desc) {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection con = connectionClass.CONN();

        String query = "INSERT INTO " + TABLE_NAME +
                "( " + ROUTINE_NAME_COLUMN + ", " + ROUTINE_DESC_COLUMN + " ) " +
                "VALUES (?, ? )";
        int result = -1;

        try {
            PreparedStatement preparedQuery = con.prepareStatement(query);
            preparedQuery.setString( 1, name);
            preparedQuery.setString( 2, desc);
            Log.e("PREPARED", preparedQuery.toString());
            result = preparedQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Log.e("RoutineObject", "Update result for "+name+" is: "+result);
        return (result > 0 ? true : false);
    }

    public static boolean checkIfValidRoutineName(String name) {

        ConnectionClass connectionClass = new ConnectionClass();
        Connection con = connectionClass.CONN();

        String query = "SELECT * " +
                "FROM " + TABLE_NAME +
                "WHERE " + ROUTINE_NAME_COLUMN + " = ?";

        ResultSet rs = null;

        try {
            PreparedStatement preparedQuery = con.prepareStatement(query);
            preparedQuery.setString( 1, name);
            Log.e("RoutineObject", preparedQuery.toString());
            rs = preparedQuery.executeQuery();
            if (!rs.isBeforeFirst() ) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }



    //
    // Setters and Getters
    //

    public int getExerciseRoutineId() {
        return exerciseRoutineId;
    }

    public void setExerciseRoutineId(int exerciseRoutineId) {
        this.exerciseRoutineId = exerciseRoutineId;
    }

    public String getExerciseRoutineName() {
        return exerciseRoutineName;
    }

    public void setExerciseRoutineName(String exerciseRoutineName) {
        this.exerciseRoutineName = exerciseRoutineName;
    }

    public String getExerciseRoutineDesc() {
        return exerciseRoutineDesc;
    }

    public void setExerciseRoutineDesc(String exerciseRoutineDesc) {
        this.exerciseRoutineDesc = exerciseRoutineDesc;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
