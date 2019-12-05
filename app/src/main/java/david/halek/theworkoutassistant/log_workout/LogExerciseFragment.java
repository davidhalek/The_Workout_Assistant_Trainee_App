package david.halek.theworkoutassistant.log_workout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.FragmentManager;
import david.halek.theworkoutassistant.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LogExerciseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LogExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogExerciseFragment extends Fragment {
    private static final String ARG_EXERCISE = "exercise";

    private String exerciseName;
    EditText editWeight, editReps;

    private OnFragmentInteractionListener mListener;

    public LogExerciseFragment() { }

    public static LogExerciseFragment newInstance(String exercise) {
        LogExerciseFragment fragment = new LogExerciseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EXERCISE, exercise);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exerciseName = getArguments().getString(ARG_EXERCISE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_log_exercise, container, false);

        TextView text = (TextView) view.findViewById(R.id.txtExerciseName);
        text.setText(exerciseName);

        editReps = (EditText) view.findViewById(R.id.editReps);
        editWeight = (EditText) view.findViewById(R.id.editWeight);

        Button button = (Button) view.findViewById(R.id.btnLogSet);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPush(v);
            }
        });

        Button button2 = (Button) view.findViewById(R.id.btnCancel);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPush(v);
            }
        });

        return view;
    }

    public void buttonPush(View v) {
        // TODO: if it's cancel, cancel.  if it's not, make sure there are reps
        int w, r;

        switch (v.getId()) {
            case (R.id.btnCancel):
                w = r = 0;
                onButtonPressed(w, r);
                break;
            case (R.id.btnLogSet):
                w = getInt(editWeight.getText().toString());
                r = getInt(editReps.getText().toString());
                if (r > 0) {
                    onButtonPressed(w, r);
                } else {
                    Snackbar.make(v, "Reps must be at least 1.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Log.e("LogExerciseFragment", "Reps are not set.");
                }
                break;
        }
    }

//                getActivity().getFragmentManager().beginTransaction().remove(this).commit();

    int getInt(String s) {
        return (s.equals("") ? 0 : Integer.parseInt(s));
    }

    public void onButtonPressed(int weight, int reps) {
        if (mListener != null) {
            mListener.onFragmentInteraction(weight, reps);
        }

        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(int weight, int reps);
    }
}
