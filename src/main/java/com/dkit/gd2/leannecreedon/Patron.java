package com.dkit.gd2.leannecreedon;

import java.time.LocalDate;

public class Patron
{
    /* ATTRIBUTES */

    // TO LOG IN AFTER ACCOUNT IS CREATED
    // LAST_NAME, FIRST_NAME
    private int libraryCardNumber;
    private int pin;

    // TO CREATE ACCOUNT & GET LIBRARY CARD
    // ONCE ACCOUNT IS CREATED, USER IS SENT "LIBRARY
    // CARD", AKA A LIBRARY NUMBER & PIN.
    // WITH THAT INFO THEY CAN LOG IN TO THE SYSTEM.
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String addressLine1;
    private String addressLine2;
    private String county;
    private String eirCode;
    private String email;
    private String confirmEmail;
    private int mobile;

    /* CONSTRUCTORS */

    // Creating an account
    public Patron(String firstName, String lastName, LocalDate dateOfBirth, String gender,
                  String addressLine1, String addressLine2, String county, String eirCode,
                  String email, String confirmEmail, int mobile)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.county = county;
        this.eirCode = eirCode;
        this.email = email;
        this.confirmEmail = confirmEmail;
        this.mobile = mobile;
    }

    // Logging in
    public Patron(String lastName, String firstName, int libraryCardNumber, int pin)
    {
        this.lastName = lastName;
        this.firstName = firstName;
        this.libraryCardNumber = libraryCardNumber;
        this.pin = pin;
    }

    // GETTERS
    public int getLibraryCardNumber() {
        return libraryCardNumber;
    }

    public int getPin() {
        return pin;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCounty() {
        return county;
    }

    public String getEirCode() {
        return eirCode;
    }

    public String getEmail() {
        return email;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public int getMobile() {
        return mobile;
    }

    // ToString()
    @Override
    public String toString() {
        return "Patron{" +
                "libraryCardNumber=" + libraryCardNumber +
                ", pin=" + pin +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender='" + gender + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", county='" + county + '\'' +
                ", eirCode='" + eirCode + '\'' +
                ", email='" + email + '\'' +
                ", confirmEmail='" + confirmEmail + '\'' +
                ", mobile=" + mobile +
                '}';
    }
}
