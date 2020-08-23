package com.example.medebe;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class that defines a contact
 *
 * @author Kevin de la Coba Malam
 *
 */
public class Contact implements Comparable<Contact>, Serializable {

    private String name = null;					//Name of the contact
    private double totalDebt = 0;				//Debt of the contact
    private ArrayList<Movement> movements;		//Debts of the contact

    /**
     * Constructor of the contact class
     *
     * @param name Name of the contact
     */
    public Contact(String name) {
        this.name = name;
        this.movements = new ArrayList<Movement>();
    }

    /**
     * Getter for the name of the contact
     *
     * @return Name of the contact
     */
    public String getName() { return this.name; }

    /**
     * Setter for the name of the contact
     *
     * @param name Name of the contact
     */
    public void setName(String name) { this.name = name; }

    /**
     * Getter for the list of movements
     *
     * @return List with all the movements
     */
    public ArrayList<Movement> getMovements() { return this.movements; }

    /**
     * Getter for the debt of a contact
     *
     * @return Debt of the contact
     */
    public double getTotalDebt() { return this.totalDebt; }

    /**
     * Method to change the name of a contact
     *
     * @param name Name of the contact to be changed
     * @return True if the name is being changed, false if the name is too long
     */
    public boolean changeName(String name) {
        if (this.name.length() > 30) return false;

        this.name = name;
        MeDebe.getInstace().save();
        return true;
    }

    /**
     * Method to increase the debt of the contact
     *
     * @param amount Amount to be added
     */
    public void addMovement(double amount, MoveType type) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        this.movements.add(new Movement(amount, formatter.format(date), type));
        if (type == MoveType.RECEIVE)
            this.totalDebt -= amount;
        else this.totalDebt += amount;
        MeDebe.getInstace().save();
    }

    @Override
    public int compareTo(Contact arg0) { return this.name.compareTo(arg0.getName()); }
}
