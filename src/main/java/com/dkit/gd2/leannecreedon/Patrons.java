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


    // PART 2 OF SPEC - METHODS

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

    public void printAllPatrons()
    {
        for(Patron patron : patronList)
        {
            System.out.println(patron);
        }
    }

    public void updatePatronList(Patron patron)
    {
        patronList.add(patron);
    }

    public boolean checkPatronFound(Patron patron)
    {
        if(patron != null)
        {
            System.out.println(Colours.GREEN + "Patron found: "  + Colours.RESET + patron);
            return true;
        }
        else
        {
            System.out.println(Colours.RED + "No patron found" + Colours.RESET);
            return false;
        }
    }

    public boolean checkPatronFoundByPin(Long pin)
    {
        for(Patron patron : patronList)
        {
            if(patron.getPin() == pin)
            {
                System.out.println(Colours.GREEN + "Patron found: "  + Colours.RESET + patron);
                return true;
            }
        }
        System.out.println(Colours.RED + "No patron found" + Colours.RESET);
        return false;
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
        if(matcher.matches())
        {
            System.out.println(Colours.GREEN+"Email "+ email +" is valid"+Colours.RESET);
            return true;
        }
        else
        {
            System.out.println(Colours.RED+"Email "+ email +" is invalid"+Colours.RESET);
            return false;
        }
    }

    public boolean checkMobileRegex(String mobile)
    {
        String mobileRegex = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$";
        Pattern pattern = Pattern.compile(mobileRegex);
        Matcher matcher = pattern.matcher(mobile);
        if(matcher.matches())
        {
            System.out.println(Colours.GREEN+"Mobile "+ mobile +" is valid"+Colours.RESET);
            return true;
        }
        else
        {
            System.out.println(Colours.RED+"Mobile "+ mobile +" is invalid"+Colours.RESET);
            return false;
        }
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

    // Generating random number for pin & library card number
    // Got method from stack overflow page linked in app
    public long generateRandomNumber(int n)
    {
        long min = (long) Math.pow(10, n - 1);
        return ThreadLocalRandom.current().nextLong(min, min * 10);
    }

}
