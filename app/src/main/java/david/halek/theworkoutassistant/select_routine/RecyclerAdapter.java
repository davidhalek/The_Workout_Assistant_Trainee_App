package david.halek.theworkoutassistant.select_routine;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import david.halek.theworkoutassistant.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private static final String ARG_ROUTINE_ID = "routineId";
    private ArrayList<RoutineObject> routineList;
    SelectRoutineActivity myActivity;

    public RecyclerAdapter(ArrayList<RoutineObject> routineList) {
        this.routineList = routineList;
    }

    public RecyclerAdapter(ArrayList<RoutineObject> routineList, SelectRoutineActivity myActivity) {
        this.routineList = routineList;
        this.myActivity = myActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.routine_card_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RoutineObject ob = (RoutineObject)routineList.get(position);
        holder.txtRoutineName.setText(ob.getExerciseRoutineName());
        holder.txtRoutineDesc.setText(ob.getExerciseRoutineDesc());
    }

    @Override
    public int getItemCount() {
        return routineList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtRoutineName;
        public TextView txtRoutineDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRoutineName = itemView.findViewById(R.id.txtRoutineName);
            txtRoutineDesc = itemView.findViewById(R.id.txtRoutineDesc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    openRoutineDetail(v);
                }
            });
        }

        public void openRoutineDetail(View v) {
            int position = getAdapterPosition();
            int id = -1;

            RoutineObject ob = (RoutineObject)routineList.get(position);
            id = (int)ob.getExerciseRoutineId();

            // Using an activity for logging exercises, calling method in parent activity
            myActivity.routineSelected(id);
        }
    }
}