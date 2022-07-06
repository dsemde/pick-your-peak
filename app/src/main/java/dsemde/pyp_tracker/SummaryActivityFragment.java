package dsemde.pyp_tracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import static dsemde.pyp_tracker.MainActivityFragment.daily_goal;

public class SummaryActivityFragment extends Fragment {

    static View SummaryFragmentView;

    ImageView editDailyGoal;


    ImageView selectMountain;

    TextView dailyGoal;
    TextView pypMountain;

    private RadioButton radioButton;

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
        pypMountain = SummaryActivityFragment.SummaryFragmentView.findViewById(R.id.pypMountain);

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

        editor_goal.putString("daily goal", Double.toString(daily_goal));
        editor_goal.commit();
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
                        // What happens when you select a mountain as your goal?
                        int selectedId = picked_peak.getCheckedRadioButtonId();
                        radioButton = (RadioButton) picked_peak.findViewById(selectedId);

                        //String mnt_name = (String) radioButton.getText();
                        String mnt_name;

                        switch(selectedId) {
                            case R.id.radioDiamondHead: mnt_name = "DIAMOND HEAD";
                                break;
                            case R.id.radioBurnabyMountain: mnt_name = "BURNABY MOUNTAIN";
                                break;
                            case R.id.radioStawamusChief: mnt_name = "STAWAMUS CHIEF";
                                break;
                            case R.id.radioTableMountain: mnt_name = "TABLE MOUNTAIN";
                                break;
                            case R.id.radioGrouseMountain: mnt_name = "GROUSE MOUNTAIN";
                                break;
                            case R.id.radioCypressBowl: mnt_name = "CYPRESS BOWL";
                                break;
                            case R.id.radioMountOlympus: mnt_name = "MT. OLYMPUS";
                                break;
                            case R.id.radioMountStHelens: mnt_name = "MOUNT ST. HELENS";
                                break;
                            case R.id.radioMountFuji: mnt_name = "MT. FUJI";
                                break;
                            case R.id.radioMountKilimanjaro: mnt_name = "MT. KILIMANJARO";
                                break;
                            case R.id.radioMountEverest: mnt_name = "MT. EVEREST";
                                break;
                            default:
                                mnt_name = "Uh Oh, kaput.";
                        }

                        pypMountain.setText(mnt_name);






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
