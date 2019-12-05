package david.halek.theworkoutassistant.log_workout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import david.halek.theworkoutassistant.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

public class LogWorkoutActivity extends AppCompatActivity implements LogExerciseFragment.OnFragmentInteractionListener {
    TextView txtRoutineName;
    int userId;
    int routineId;
    ArrayList<SetObject> todoList;
    ArrayList<SetObject> completedList;
    LogWorkoutActivity thisActivity;
    LogExerciseFragment logFragment;


    int exerciseSelectedId;
    int exerciseSelectedPosition;

    RecyclerView todoRecyclerView, completedRecyclerView;
    RecyclerView.LayoutManager todoLayoutManager, completedLayoutManager;
    RecyclerView.Adapter todoAdapter, completedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("LogWorkoutActivity", "************ onCreate ************");
        setContentView(R.layout.activity_log_workout);

        thisActivity = this;

        // Get values from other activity (userId and routineId)
        Bundle b = new Bundle();
        b = getIntent().getExtras();
        userId = b.getInt("userId");
        routineId = b.getInt("routineId");

        // Set the routine name
        txtRoutineName = (TextView) findViewById(R.id.txtRoutineName);
        String routine = SetObject.getRoutineName(routineId);
        txtRoutineName.setText(routine); //

        // Setup first recyclerView for exercises remaining
        todoList = SetObject.getSetsList(userId, routineId); // 9, 1

        todoRecyclerView = findViewById(R.id.recyclerExercisesRemaining);
        todoLayoutManager = new LinearLayoutManager(this);
        todoRecyclerView.setLayoutManager(todoLayoutManager);
        todoAdapter = new ExercisesToDoRecyclerAdapter(todoList, this);
        todoRecyclerView.setAdapter(todoAdapter);

        // Setup recyclerView for completed sets
        completedList = new ArrayList<SetObject>();
    }

    void exerciseSelected(int position, int exerciseId) {
        exerciseSelectedId = exerciseId;
        exerciseSelectedPosition = position;

        // Dialog box to get weight and reps
        String exerciseName = SetObject.getExerciseName(exerciseId);


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        logFragment = LogExerciseFragment.newInstance(exerciseName);
        ft.add(R.id.layout_dialog_frag, logFragment);
        ft.commit();
    }

    void logThisExercise(int weight, int reps) {

        // Log the set
        SetObject ob = todoList.get(exerciseSelectedPosition);
        ob.setReps(reps);
        ob.setWeight(weight);
        ob.logSet();

        // Add it to the completed list, set it up if necessary
        completedList.add(ob);
        if (completedList.size() < 2) {
            completedRecyclerView = findViewById(R.id.recyclerExercisesCompleted);
            completedLayoutManager = new LinearLayoutManager(this);
            completedRecyclerView.setLayoutManager(completedLayoutManager);
            completedAdapter = new FinishedExercisesRecyclerAdapter(completedList, this);
            completedRecyclerView.setAdapter(completedAdapter);
        }

        completedAdapter.notifyItemInserted(completedList.size() - 1);

        // Remove it from the to-do list
        todoList.remove(exerciseSelectedPosition);
        todoAdapter.notifyItemRemoved(exerciseSelectedPosition);
        Log.e("LogWorkoutActivity", "logThisExercise: position is: " + exerciseSelectedPosition + " exerciseId is: " + exerciseSelectedId);
//        public SetObject(int userId, int routineId, int exerciseId, String exerciseName, int weight, int reps) {

    }

    public void onFragmentInteraction(int weight, int reps) {
        if (reps > 0) {
            logThisExercise(weight, reps);
        }
        // getSupportFragmentManager().popBackStack();
    }

    public void onDialogPositiveClick(DialogFragment dialog) {
        Log.e("LogWorkoutActivity", "onDialogPOSITIVEClick");

    }

    public void onDialogNegativeClick(DialogFragment dialog) {
        Log.e("LogWorkoutActivity", "onDialogNEGATIVEClick");
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Log.e("LogWorkoutActivity", "************ onBackPressed ************");

//        Fragment f = getSupportFragmentManager().findFragmentById(R.id.layout_dialog_frag);
//        if (f != null) {
//            getSupportFragmentManager().beginTransaction().remove(f).commit();
//        }
//
        if (logFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(logFragment).commit();
//            getSupportFragmentManager().beginTransaction().replace(R.id.layout_dialog_frag, logFragment).commit();
            Log.e("LogWorkoutActivity", "--> onBackPressed: destroying fragment.");
            logFragment = null;
        } else {
            Log.e("LogWorkoutActivity", "--> onBackPressed: exiting out of activity? ...");
            super.onBackPressed();
        }
    }
}


//        super.onBackPressed();
//            startActivity(new Intent(ThisActivity.this, NextActivity.class));
//        getFragmentManager().popBackStack();
//        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);




