package com.sp.healthiswealth;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class DetailForm extends AppCompatActivity {

    private EditText healthiswealthName;
    private EditText healthiswealthExercise;
    private RadioGroup healthiswealthReps;
    private Button buttonSave;

    private ExerciseHelper helper = null;
    private String exerciseID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_form);

        healthiswealthName = (EditText) findViewById(R.id.healthiswealth_name);
        healthiswealthExercise = (EditText) findViewById(R.id.healthiswealth_exercise);
        healthiswealthReps = (RadioGroup) findViewById(R.id.healthiswealth_reps);

        buttonSave = (Button) findViewById(R.id.button_save);
        buttonSave.setOnClickListener(onSave);


        helper = new ExerciseHelper(this);
        exerciseID = getIntent().getStringExtra("ID");
        if (exerciseID != null) {
            load();
        }

    }

    private void load() {
        Cursor c = helper.getById(exerciseID);
        c.moveToFirst();
        healthiswealthName.setText(helper.getHealthIsWealthName(c));
        healthiswealthExercise.setText(helper.getHealthIsWealthExercise(c));

        if (helper.getHealthIsWealthReps(c).equals("60")) {
            healthiswealthReps.check(R.id.sixty);
        } else if (helper.getHealthIsWealthReps(c).equals("50")) {
            healthiswealthReps.check(R.id.fifty);
        } else if (helper.getHealthIsWealthReps(c).equals("40")) {
            healthiswealthReps.check(R.id.forty);
        } else if (helper.getHealthIsWealthReps(c).equals("30")) {
            healthiswealthReps.check(R.id.thirty);
        } else if (helper.getHealthIsWealthReps(c).equals("20")) {
            healthiswealthReps.check(R.id.twenty);
        } else {
            healthiswealthReps.check(R.id.ten);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }

    private View.OnClickListener onSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // To read date from EditText
            String nameStr = healthiswealthName.getText().toString();
            String exerStr = healthiswealthExercise.getText().toString();

            String healReps = "";
            //To read selection of restaurantTypes RadioGroup
            switch (healthiswealthReps.getCheckedRadioButtonId()) {
                case R.id.ten:
                    healReps = "10";
                    break;
                case R.id.twenty:
                    healReps = "20";
                    break;
                case R.id.thirty:
                    healReps = "30";
                    break;
                case R.id.forty:
                    healReps = "40";
                    break;
                case R.id.fifty:
                    healReps = "50";
                    break;
                case R.id.sixty:
                    healReps = "60";
                    break;
            }

            if (exerciseID == null) {
                helper.insert(nameStr, exerStr, healReps);
            } else {
                helper.update(exerciseID, nameStr, exerStr, healReps);
            }


            finish();
        }
    };


}
