package david.halek.theworkoutassistant.log_workout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import david.halek.theworkoutassistant.R;

public class ExercisesToDoRecyclerAdapter extends RecyclerView.Adapter<ExercisesToDoRecyclerAdapter.ViewHolder> {
    private static final String ARG_ROUTINE_ID = "routineId";
    private ArrayList<SetObject> exerciseList;
    LogWorkoutActivity myActivity;

    public ExercisesToDoRecyclerAdapter(ArrayList<SetObject> routineList) {
        this.exerciseList = routineList;
    }

    public ExercisesToDoRecyclerAdapter(ArrayList<SetObject> routineList, LogWorkoutActivity myActivity) {
        this.exerciseList = routineList;
        this.myActivity = myActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_exercise_to_do, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SetObject ob = (SetObject) exerciseList.get(position);
        holder.txtExerciseName.setText(ob.getExerciseName());
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtExerciseName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtExerciseName = itemView.findViewById(R.id.txtExerciseName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    logExercise(v);
                }
            });
        }

        public void logExercise(View v) {
            int position = getAdapterPosition();
            int exerciseId = -1;

            SetObject ob = (SetObject)exerciseList.get(position);
            exerciseId = (int)ob.getExerciseId();

            // Using an activity for logging exercises, calling method in parent activity
            myActivity.exerciseSelected(position, exerciseId);
        }
    }
}
