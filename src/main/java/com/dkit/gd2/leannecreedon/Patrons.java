package com.dkit.gd2.leannecreedon;

import com.dkit.gd2.leannecreedon.DAO.IPatronInterface;
import com.dkit.gd2.leannecreedon.DAO.MySqlPatronDAO;
import com.dkit.gd2.leannecreedon.DTO.Patron;
import com.dkit.gd2.leannecreedon.Exceptions.DaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Patrons
{
    /* Manager class for patrons */

    private final ArrayList<Patron> patronList;

    public Patrons()
    {
        this.patronList = new ArrayList<>();
        connectToPatronDatabase();
    }

    /* Part2 & Part3 of spec methods */

    public void connectToPatronDatabase()
    {
        IPatronInterface IPatronDAO = new MySqlPatronDAO();

        try
        {
            List<Patron> patrons = IPatronDAO.findAllPatrons();

            //Adding to the arrayList
            patronList.addAll(patrons);

            if(patrons.isEmpty())
            {
                System.out.println(Colours.RED + "There are no patrons" + Colours.RESET);
            }

        }
        catch (DaoException de)
        {
            System.out.println(de.getMessage());
        }
    }

    //Update arraylist when entities are added/deleted
    public void updatePatronList(Patron patron)
    {
        patronList.add(patron);
    }

    public void updatePatronListDelete(String email)
    {
        patronList.removeIf(patron -> patron.getEmail().equalsIgnoreCase(email));
    }

    /* Check patron found methods */

    public boolean checkPatronFound(Patron patronToFind)
    {
        for(Patron patron : patronList)
        {
            if(patron.equals(patronToFind))
            {
                System.out.println(Colours.GREEN + "Patron found: "  + Colours.RESET);
                displayPatron(patron);
                return true;
            }
        }
        System.out.println(Colours.RED + "No patron found" + Colours.RESET);
        return false;
    }

    public boolean checkPatronFoundByEmail(String email)
    {
        for(Patron patron : patronList)
        {
            if(patron.getEmail().equalsIgnoreCase(email))
            {
                System.out.println(Colours.GREEN + "Patron found: "  + Colours.RESET);
                displayPatron(patron);
                return true;
            }
        }
        System.out.println(Colours.RED + "No patron found" + Colours.RESET);
        return false;
    }

    public void checkPatronAdded(Patron patronToAdd)
    {
        for(Patron patron : patronList)
        {
            if(patron == patronToAdd) {
                System.out.println(Colours.GREEN + "Account Successfully Created"  + Colours.RESET);
            }
            else
            {
                System.out.println(Colours.RED + "Error adding account - please try again" + Colours.RESET);
            }
        }
    }

    // login method
    public boolean login(String lNmae, String fName, long lCard, long pin)
    {
        for(Patron patron : patronList)
        {
            if(patron.getLastName().equalsIgnoreCase(lNmae))
            {
                if(patron.getFirstName().equalsIgnoreCase(fName))
                {
                    if(patron.getLibraryCardNumber() == lCard)
                    {
                        if(patron.getPin() == pin)
                        {
                            System.out.println(Colours.GREEN+"\nValid login, you may proceed"+Colours.RESET);
                            System.out.println("\nHello "+Colours.BOLD+ fName +Colours.RESET+", welcome back!");
                            return true;
                        }
                    }
                }
            }
        }
        System.out.println(Colours.RED+"Invalid account details"+Colours.RESET);
        return false;
    }

    public String getCurrentAccountEmail(long lCard, long pin)
    {
        for(Patron patron : patronList)
        {
            if(patron.getLibraryCardNumber() == lCard)
            {
                if(patron.getPin() == pin)
                {
                    return patron.getEmail();
                }
            }
        }
        return null;
    }

    /* Search Methods */

    public boolean searchEmailOnSystem(String email)
    {
        for(Patron patron : patronList)
        {
            if(patron.getEmail().equalsIgnoreCase(email))
            {
                return true;
            }
        }
        return false;
    }

    public boolean searchByLastName(String lName)
    {
        for(Patron patron : patronList)
        {
            if(patron.getLastName().equalsIgnoreCase(lName))
            {
                return true;
            }
        }
        return false;
    }

    public boolean searchByFirstName(String fName)
    {
        for(Patron patron : patronList)
        {
            if(patron.getFirstName().equalsIgnoreCase(fName))
            {
                return true;
            }
        }
        return false;
    }

    public boolean searchByLCardNum(long lCard, String fName, String lName)
    {
        for(Patron patron : patronList)
        {
            if(patron.getLibraryCardNumber() == lCard)
            {
                if(patron.getFirstName().equalsIgnoreCase(fName))
                {
                    if(patron.getLastName().equalsIgnoreCase(lName))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean searchByPinNum(long pin, long lCard)
    {
        for(Patron patron : patronList)
        {
            if(patron.getPin() == pin)
            {
                if(patron.getLibraryCardNumber() == lCard)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /* Display method */

    public void displayPatron(Patron patron)
    {
        System.out.println();
        System.out.printf(Colours.BOLD+"%-"
                        +(patron.getFirstName().length()+9)+"s%-"
                        +(patron.getLastName().length()+5)+"s%-"
                        +"15s%-" // Date of birth
                        +(patron.getGender().length()+5)+"s%-"
                        +(patron.getAddressLine1().length()+5)+"s%-"
                        +(patron.getAddressLine2().length()+9)+"s%-"
                        +(patron.getCounty().length()+5)+"s%-"
                        +(patron.getEirCode().length()+5)+"s%-"
                        +(patron.getEmail().length()+5)+"s%-"
                        +(patron.getMobile().length()+5)+"s%-"
                        +"22s%" // Library card number
                        +"s%n",

                "First_Name", "Last_Name", "Date_Of_Birth",
                "Gender", "AddressLine1", "AddressLine2", "County", "EirCode" , "Email", "Mobile", "Library_Car_Number", "Pin" + Colours.RESET);

        System.out.printf("%-"
                        +(patron.getFirstName().length()+9)+"s%-"
                        +(patron.getLastName().length()+5)+"s%-"
                        +"15s%-" // Date of birth
                        +(patron.getGender().length()+5)+"s%-"
                        +(patron.getAddressLine1().length()+5)+"s%-"
                        +(patron.getAddressLine2().length()+9)+"s%-"
                        +(patron.getCounty().length()+5)+"s%-"
                        +(patron.getEirCode().length()+5)+"s%-"
                        +(patron.getEmail().length()+5)+"s%-"
                        +(patron.getMobile().length()+5)+"s%-"
                        +"22s%" // Library card number
                        +"s%n"

                , patron.getFirstName(), patron.getLastName(), patron.getDateOfBirth(),
                patron.getGender(), patron.getAddressLine1(), patron.getAddressLine2(), patron.getCounty(),
                patron.getEirCode(), patron.getEmail(), patron.getMobile(), patron.getLibraryCardNumber(), patron.getPin());
    }

    /* INPUT VALIDATION METHODS */

    public boolean checkUniqueEmail(String email)
    {
        for(Patron patron : patronList)
        {
            if(email.equalsIgnoreCase(patron.getEmail()))
            {
                System.out.println(Colours.RED + "Email already taken" + Colours.RESET);
                return false;
            }
        }
        return true;
    }

    public boolean checkEmailRegex(String email)
    {
        String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if(!(matcher.matches())) {
            System.out.println(Colours.RED+"Email "+ email +" is invalid"+Colours.RESET);
            return false;
        }
        return true;
    }

    public boolean checkNameRegex(String name)
    {
        String nameRegex = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(nameRegex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public boolean checkMobileRegex(String mobile)
    {
        String mobileRegex = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$";
        Pattern pattern = Pattern.compile(mobileRegex);
        Matcher matcher = pattern.matcher(mobile);
        if(!(matcher.matches())) {
            System.out.println(Colours.RED+"Mobile "+ mobile +" is invalid"+Colours.RESET);
            return false;
        }
        return true;
    }

    public boolean genderConfirmation(String gender)
    {
        if(gender.equalsIgnoreCase("Female")) {
            return true;
        }
        else if(gender.equalsIgnoreCase("Male")) {
            return true;
        }
        else {
            System.out.println(Colours.RED+"Input is invalid"+Colours.RESET);
            return false;
        }
    }

    public boolean countyValidation(String county)
    {
        String countyRegex = "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";
        Pattern pattern = Pattern.compile(countyRegex);
        Matcher matcher = pattern.matcher(county);
        if(!(matcher.matches())) {
            System.out.println(Colours.RED+"County "+ county +" is invalid"+Colours.RESET);
            return false;
        }
        return true;
    }

    // Generating random number for pin & library card number
    // Got method from stack overflow page linked in client app
    public long generateRandomNumber(int n)
    {
        long min = (long) Math.pow(10, n - 1);
        return ThreadLocalRandom.current().nextLong(min, min * 10);
    }
}
