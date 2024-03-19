package dsemde.pyp_tracker;

import static dsemde.pyp_tracker.MainActivityFragment.daily_goal;
import static dsemde.pyp_tracker.MainActivityFragment.goalMountainSteps;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class SummaryActivityFragment extends Fragment {

    static View SummaryFragmentView;

    ImageView editDailyGoal;


    ImageView selectMountain;

    TextView dailyGoal;

    String selectedText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        SummaryFragmentView = inflater.inflate(R.layout.fragment_summary, container,false);
        return SummaryFragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        editDailyGoal = SummaryActivityFragment.SummaryFragmentView.findViewById(R.id.dailyGoalButton);
        dailyGoal = SummaryActivityFragment.SummaryFragmentView.findViewById(R.id.dailyGoal);
        selectMountain = SummaryActivityFragment.SummaryFragmentView.findViewById(R.id.selectMountain);

        editDailyGoal();
        chooseMountain();
    }

    public void editDailyGoal(){
        editDailyGoal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View viewInflated = LayoutInflater.from(getActivity()).inflate(R.layout.edit_daily_goal, (ViewGroup) getView().findViewById(android.R.id.content), false);

                // Step input
                final EditText input = viewInflated.findViewById(R.id.daily_input);
                builder.setView(viewInflated);

                // OK Button
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (input.getText().length() != 0) {
                            try {
                                double in = Double.parseDouble(String.valueOf(input.getText()));
                                if (in > 0) {
                                    daily_goal = in;
                                    saveDailyGoal();
                                    setDailyGoal();
                                } else {
                                    dialog.cancel();
                                }
                            } catch (Exception e) {
                                dialog.cancel();
                            }
                        } else{
                            dialog.cancel();
                        }

                    }
                });
                // Cancel Button
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        input.post(new Runnable() {
                            @Override
                            public void run() {
                                InputMethodManager inputMethodManager= (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputMethodManager.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
                            }
                        });
                    }
                });
                input.requestFocus();

                builder.show();

            }
        });
    }

    private void saveDailyGoal(){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_goal = sharedPref.edit();

        editor_goal.putString("daily_goal", Double.toString(daily_goal));

        editor_goal.commit();
    }

    private void saveMountainGoal(){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor mountain_goal = sharedPref.edit();

        mountain_goal.putString("mountain_goal", selectedText);

        mountain_goal.commit();
    }

    public void setDailyGoal(){
        dailyGoal.setText("Personal goal: " + daily_goal + " flights per day");
    }

    public void chooseMountain(){
        selectMountain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View viewInflated = LayoutInflater.from(getActivity()).inflate(R.layout.select_mountain, (ViewGroup) getView().findViewById(android.R.id.content), false);

                // Step input
                final RadioGroup picked_peak = viewInflated.findViewById(R.id.mountain_radio);
                builder.setView(viewInflated);

                // OK Button
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int selectedId = picked_peak.getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton) picked_peak.findViewById(selectedId);

                        selectedText = (String) radioButton.getText();

                        TextView mountainView = (TextView) getView().findViewById(R.id.pypMountain);
                        mountainView.setText(selectedText);


                        // Change mountain goal
                        switch (selectedText) {
                            case "Diamond Head (232 m)":
                                goalMountainSteps = 1160;
                                break;
                            case "Kelowna Knox Mountain (300 m)":
                                goalMountainSteps = 1500;
                                break;
                            case "Burnaby Mountain (370 m)":
                                goalMountainSteps = 1850;
                                break;
                            case "Stawamus Chief (700 m)":
                                goalMountainSteps = 3500;
                                break;
                            case "Mount Boucherie (758 m)":
                                goalMountainSteps = 3790;
                                break;
                            case "Spion Kop (897 m)":
                                goalMountainSteps = 4485;
                                break;
                            case "Table Mountain (1085 m)":
                                goalMountainSteps = 5425;
                                break;
                            case "Grouse Mountain (1231 m)":
                                goalMountainSteps = 6155;
                                break;
                            case "Cypress Bowl (1432 m)":
                                goalMountainSteps = 7160;
                                break;
                            case "Silverstar (1915 m)":
                                goalMountainSteps = 9575;
                                break;
                            case "Mount Olympus (1950 m)":
                                goalMountainSteps = 9750;
                                break;
                            case "Big White (2319 m)":
                                goalMountainSteps = 11595;
                                break;
                            case "Mount St. Helens (2550 m)":
                                goalMountainSteps = 12750;
                                break;
                            case "Mount Fuji (3776 m)":
                                goalMountainSteps = 18880;
                                break;
                            case "Mount Kilimanjaro (5895 m)":
                                goalMountainSteps = 29475;
                                break;
                            case "Mount Everest (8848 m)":
                                goalMountainSteps = 44240;
                                break;
                        }

                        MainActivity.setChallengeProgress();
                        saveMountainGoal();

                    }
                });
                // Cancel Button
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

            }
        });
    }
}
