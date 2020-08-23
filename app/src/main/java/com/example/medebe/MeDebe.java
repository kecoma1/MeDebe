package com.example.medebe;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class of the app, containes a Set with all the
 * contacts
 *
 * @author Kevin de la Coba Malam
 */
public class MeDebe {

    private Set<Contact> contacts;			//Set with all the contacts
    private File file = null;       		//File to store the data of the app
    private static MeDebe instance = null;	//Instance of the application

    /**
     * Constructor of the class, it justs initializes the Set
     */
    private MeDebe() {
        load();
    }

    /**
     * Method to get the instance of the app
     *
     * @return Instace of the app
     */
    public static MeDebe getInstace() {
        if (instance == null) {
            instance = new MeDebe();
        }

        return instance;
    }

    /**
     * Getter for the set of contacts
     *
     * @return Set of contacts
     */
    public Set<Contact> getContacts() { return this.contacts; }

    /**
     * Method to add a contact
     *
     * @param name Name of the contact
     * @return True if the contact was added, false if not
     */
    public Boolean addContact(String name) {
        if (name.length() > 30) return false;

        return this.contacts.add(new Contact(name));
    }

    /**
     * Method to remove a contact
     *
     * @param name Name of the contact to be removed
     * @return True if the contact is removed, false if not
     */
    public Boolean removeContact(String name) {
        for (Contact c: this.contacts) {
            if (c.getName().equals(name)) {
                this.contacts.remove(c);
                return true;
            }
        }
        return false;
    }

    /**
     * Method to save all the data
     */
    public void save() {
    try {
        Context c = MainActivity.getContext(); //Android code line to get the file dir to store the file
        File file = new File(c.getFilesDir().getPath().toString()+"/medebe.dat");  //From the context we get the directory and we store the info there
            if (!file.exists()) {  //Checking that the file exists, if not we create a new one
                if (!file.createNewFile()) {
                    throw new IOException("Unable to create file");
                }
            }
    if (this.file == null) this.file = file;  //Creating the file

    ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream(this.file));	//Stream to store the data
            save.writeObject(this.contacts);
            save.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to load all the data
     */
	private void load() {
		try {
            Context c = MainActivity.getContext();
            File file = new File(c.getFilesDir().getPath().toString()+"/medebe.dat");
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new IOException("Unable to create file");
                }
            }
            if (this.file == null) this.file = file;

            ObjectInputStream load = new ObjectInputStream(new FileInputStream(this.file));
            this.contacts = (TreeSet<Contact>) load.readObject();
            load.close();
        } catch (Exception e) {
            e.printStackTrace();
            this.contacts = new TreeSet<Contact>();
        }
	}

    /**
     * Method to delete a contact from the app
     *
     * @param selectedContact Contact to be deleted
     */
    public void deleteContact(Contact selectedContact) { this.contacts.remove(selectedContact); }
}
