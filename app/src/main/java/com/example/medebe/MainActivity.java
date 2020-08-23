package com.example.medebe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MediaController;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String EXTRA_NUMBER = "com.example.medebe";
    private static Contact selectedContact = null;  //Selected contact
    private static Context context;  //Context of the app
    private MeDebe meDebe;           //App data
    private ListView lv1;            //List view
    private Button addContact;       //Button to add a contact
    private ArrayAdapter<String> adapter;   //Array adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting the context of the app
        this.context = getApplicationContext();

        //Getting the app instance
        this.meDebe = MeDebe.getInstace();

        //Finding views
        this.findViews();

        //Setting the behaviour of the list view
        this.lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i = 0;

                //Getting the selected item
                for (Contact c: MeDebe.getInstace().getContacts()) {
                    if (i == position) {
                        setSelectedContact(c);
                        break;
                    }
                    i++;
                }
                openContactActivity();
            }
        });

        //Setting the behavior of the add contact button
        this.addContact.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                MeDebe.getInstace().save();
                openAddContactActivity();
            }
        });

        //Setting all the data in the list view
        this.adapter = new ArrayAdapter<String>(this, R.layout.list_view_contacts, this.contactsToStringArray());
        this.lv1.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.adapter = new ArrayAdapter<String>(this, R.layout.list_view_contacts, this.contactsToStringArray());
        this.lv1.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.meDebe.save();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.meDebe.save();
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    /**
     * Method to find all the views of the activity
     */
    public void findViews() {
        this.lv1 = (ListView) findViewById(R.id.listViewContacts);
        this.addContact = (Button) findViewById(R.id.addContactButton);
    }

    /**
     * Transforms the set of contacts of the app to
     * an array of strings containing all the information of the contact
     *
     * @return Array of strings containing the information about all the
     * contacts of the app
     */
    public String[] contactsToStringArray() {
        int i = 0;
        String[] array = new String[this.meDebe.getContacts().size()];

        //Adding a string with all the information related to the contact
        for (Contact c: this.meDebe.getContacts()) {
            array[i] = c.getName();
            i++;
        }

        return array;
    }

    /**
     * Method to open the contact activity
     */
    public void openContactActivity() {
        Intent intent = new Intent(this, contactActivity.class);
        this.meDebe.save();
        startActivity(intent);
    }

    /**
     * Method to open the contact activity
     */
    public void openAddContactActivity() {
        Intent intent = new Intent(this, addContactActivity.class);
        this.meDebe.save();
        startActivity(intent);
    }
    /**
     * Method to get the context of the app
     *
     * @return Context of the app
     */
    public static Context getContext() { return MainActivity.context; }

    /**
     * Setter for the static attribute of the item
     *
     * @param contact Contact to be set
     */
    public static void setSelectedContact(Contact contact) { MainActivity.selectedContact = contact; }

    /**
     * Getter for the selected contact of the item
     *
     * @return Contact which was selected
     */
    public static Contact getSelectedContact() { return selectedContact; }
}