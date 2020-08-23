package com.example.medebe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class contactActivity extends AppCompatActivity {

    private TextView contactName;   //Name of the contact
    private TextView totalDebt;     //Total debt of the contact
    private ListView listMovements; //Movements of the contact
    private Button addMov;          //Button to add a movement
    private Button delCon;          //Button to delete a contact
    private ArrayAdapter<String> adapter;   //Array adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        findViews();

        setContactName(MainActivity.getSelectedContact().getName());
        setContactTotalDebt(MainActivity.getSelectedContact().getTotalDebt());

        //Setting all the data in the list view
        this.adapter = new ArrayAdapter<String>(this, R.layout.list_view_contacts, this.movementsToString());
        this.listMovements.setAdapter(this.adapter);

        //Setting the behavior of the delete button
        this.delCon.setOnClickListener(new View.OnClickListener() {
           @Override public void onClick(View view) {
                MeDebe.getInstace().deleteContact(MainActivity.getSelectedContact());
                MeDebe.getInstace().save();
                openMainActivity();
           }
        });

        //Setting the behavior of the add button
        this.addMov.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                MeDebe.getInstace().save();
                openAddMovementActivity();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        this.adapter = new ArrayAdapter<String>(this, R.layout.list_view_contacts, this.movementsToString());
        this.listMovements.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        openMainActivity();
        return;
    }

    /**
     * Method to find all the views of the activity
     */
    public void findViews() {
        this.contactName = (TextView) findViewById(R.id.contactName);
        this.totalDebt = (TextView) findViewById(R.id.totalDebtText);
        this.listMovements = (ListView) findViewById(R.id.movementsList);
        this.addMov = (Button) findViewById(R.id.addMovementButton);
        this.delCon = (Button) findViewById(R.id.deleteContactButton);
    }

    /**
     * Method to set the name of the contact
     *
     * @param name Name of the contact
     */
    public void setContactName(String name) { this.contactName.setText(name); }

    /**
     * Method to set all the contacts debt
     *
     * @param debt Debt of the contact
     */
    public void setContactTotalDebt(double debt) { this.totalDebt.setText("Has a debt of "+ debt+"€");
        if (debt >= 0) //Dept > 0 means he owes you
            this.totalDebt.setText("Owes you "+debt+"€");
        else {   //You owe him
            double fixDebt = debt - debt*2;
            this.totalDebt.setText("You owe "+fixDebt+"€");
        }
    }

    /**
     * Method to transform the arrayList of movements to a normal array with info
     *
     * @return Array of strings with all the movements info
     */
    public String[] movementsToString() {
       String[] array = new String[MainActivity.getSelectedContact().getMovements().size()];
        int i = 0;

       for(Movement m: MainActivity.getSelectedContact().getMovements()) {
           if(m.getType() == MoveType.GIVE) array[i] = "You gave ";
           else array[i] = "You received ";
           array[i] = array[i]+m.getAmount()+"€."+" "+m.getDesc();
           i++;
       }

       return array;
    }

    /**
     * Method to open the Main activity
     */
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Method to open the add movement activity
     */
    public void openAddMovementActivity() {
        Intent intent = new Intent(this, addMovement.class);
        startActivity(intent);
    }
}