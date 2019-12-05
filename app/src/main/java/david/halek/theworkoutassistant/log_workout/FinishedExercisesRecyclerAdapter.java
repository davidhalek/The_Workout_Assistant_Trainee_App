package david.halek.theworkoutassistant.log_workout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import david.halek.theworkoutassistant.R;

public class FinishedExercisesRecyclerAdapter extends RecyclerView.Adapter<FinishedExercisesRecyclerAdapter.ViewHolder> {
    private ArrayList<SetObject> exerciseList;

    public FinishedExercisesRecyclerAdapter(ArrayList<SetObject> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public FinishedExercisesRecyclerAdapter(ArrayList<SetObject> exerciseList, LogWorkoutActivity myActivity) {
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_finished_exercise, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SetObject ob = (SetObject) exerciseList.get(position);
        holder.txtExerciseName.setText(ob.getExerciseName());
        holder.txtReps.setText(ob.getReps()+"");
        holder.txtWeight.setText(ob.getWeight()+"");
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtExerciseName;
        public TextView txtWeight;
        public TextView txtReps;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtExerciseName = itemView.findViewById(R.id.txtFinishedExerciseName);
            txtReps = itemView.findViewById(R.id.txtReps);
            txtWeight = itemView.findViewById(R.id.txtWeight);
        }
    }
}
