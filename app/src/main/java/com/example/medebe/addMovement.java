package com.example.medebe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class addMovement extends AppCompatActivity {

    private RadioGroup radioGroup;      //Group where the type is chose
    private RadioButton radioButton;    //Button pressed
    private EditText number;            //Quantity introduced
    private TextView error;             //Error message
    private Button button;              //Button to add the movement

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movement);

        findViews();

        //Adding a behavior to the button
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                MoveType type = null;
                double amount = 0;
                MeDebe.getInstace().save();

                if (number.getText().toString().length() == 0) {
                    error.setText("You need to introduce a number");
                    return;
                }

                try {
                    amount = Double.parseDouble(number.getText().toString());
                } catch (Exception e) {
                    error.setText("Wrong number format");
                    return;
                }

                if (radioButton != null) {
                    if (radioButton.getText().toString().equals("Give")) type = MoveType.GIVE;
                    else if (radioButton.getText().toString().equals("Receive")) type = MoveType.RECEIVE;
                } else type = MoveType.GIVE;

                MainActivity.getSelectedContact().addMovement(amount, type);
                openContactActivity();
            }
        });
    }

    @Override
    public void onBackPressed() {

        openContactActivity();
    }

    /**
     * Method to find all the views in the activity
     */
    private void findViews() {
        this.radioGroup = (RadioGroup) findViewById(R.id.radioGroupMovement);
        this.number = (EditText) findViewById(R.id.editTextNumber);
        this.button = (Button) findViewById(R.id.button);
        this.error = (TextView) findViewById(R.id.errorMsgMov);
    }

    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        this.radioButton = findViewById(radioId);

        Toast.makeText(this, "Selected Radio Button: "+ this.radioButton.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to open the contact activity
     */
    public void openContactActivity() {
        Intent intent = new Intent(this, contactActivity.class);
        MeDebe.getInstace().save();
        startActivity(intent);
    }
}