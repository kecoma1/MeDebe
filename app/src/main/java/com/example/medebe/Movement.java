package com.example.medebe;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Class that defines a debt (movement)
 *
 * @author Kevin de la Coba Malam
 *
 */
public class Movement implements Serializable {

    private double amount;			//Amount of debt in the movement
    private String time;			//Date of the debt
    private MoveType type;			//If the move is a returning move then true

    /**
     * Constructor of the movement
     *
     * @param amount Amount of debt of the movement
     * @param time Description of the movement
     */
    public Movement(double amount, String time, MoveType type) {
        this.amount = amount;
        this.time = time;
        this.type = type;
    }

    /**
     * Getter for the amount of the movement
     *
     * @return Amount in the movement
     */
    public double getAmount() { return this.amount; }

    /**
     * Getter for the date of the movement
     *
     * @return date of the movement
     */
    public String getDesc() { return this.time; }

    /**
     * Getter of the type of movement
     *
     * @return Type of movement, true if returning
     */
    public MoveType getType() { return this.type; }
}
