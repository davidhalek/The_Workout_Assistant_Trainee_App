package david.halek.theworkoutassistant.select_routine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import david.halek.theworkoutassistant.R;
import david.halek.theworkoutassistant.log_workout.LogWorkoutActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class SelectRoutineActivity extends AppCompatActivity {

    int userId = 9; // Flex Wheeler
    Activity me;

    // Recyclerview
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    private ArrayList<RoutineObject> routineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_routine);

        // Recyclerview
        if (routineList == null) {
            routineList = RoutineObject.getRoutineList();
        }

        recyclerView = findViewById(R.id.recyclerViewRoutineActivity);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(routineList, this);
        recyclerView.setAdapter(adapter);

        // TODO: add configureTabLayout() and include it in layout
//        configureTabLayout();
    }

    public void routineSelected(int routineId) {
        Bundle b = new Bundle();
        b.putInt("userId", userId);
        b.putInt("routineId", routineId);
        Intent intent = new Intent(SelectRoutineActivity.this, LogWorkoutActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }




    public void configureTabLayout() {
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.getTabAt(0).select();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 3:
                        break;
                    case 0:
                        break;
                    case 1:
//                        Intent intent = new Intent(getApplicationContext(), ExerciseActivity.class);
//                        startActivity(intent);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
