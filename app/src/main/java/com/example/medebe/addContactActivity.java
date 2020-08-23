package com.example.medebe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class addContactActivity extends AppCompatActivity {

    private EditText name;      //Field where the name is introduced
    private Button add;         //Button to add the contact
    private TextView error;     //Text view where the error message goes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        findViews();

        this.add.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                MeDebe.getInstace().save();

                //Checking if there's a name introduced
                if (name.getText().toString().length() == 0) {
                    error.setText("You need to introduce a name");
                    return;
                } else {
                    //If there's a name we check if is already in the app
                    if (MeDebe.getInstace().addContact(name.getText().toString()) == false) {
                        error.setText("The contact already exists");
                        return;
                    } else {
                        //Moving to the new contact activity
                        error.setText("");
                        for (Contact c: MeDebe.getInstace().getContacts()) {
                            if (c.getName().equals(name.getText().toString())) {
                                MainActivity.setSelectedContact(c);
                                break;
                            }
                        }

                        openContactActivity();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        openMainActivity();
        return;
    }

    /**
     * Method to open the contact activity
     */
    public void openContactActivity() {
        Intent intent = new Intent(this, contactActivity.class);
        MeDebe.getInstace().save();
        startActivity(intent);
    }

    /**
     * Method to open the main activity
     */
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        MeDebe.getInstace().save();
        startActivity(intent);
    }

    /**
     * Method to find all the views of the activity
     */
    public void findViews() {
        this.name = (EditText) findViewById(R.id.addName);
        this.add = (Button) findViewById(R.id.addContactButton);
        this.error = (TextView) findViewById(R.id.errorMsg);
    }
}