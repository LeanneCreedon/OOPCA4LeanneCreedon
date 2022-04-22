package com.dkit.gd2.leannecreedon.DAO;

import com.dkit.gd2.leannecreedon.DTO.Patron;
import com.dkit.gd2.leannecreedon.Exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySqlPatronDAO extends MySqlDao implements IPatronInterface
{
    /* SOME FEATURES ARE TEMPORARY! */

    @Override
    public List<Patron> findAllPatrons() throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Patron> patrons = new ArrayList<>();

        try
        {
            // Get a connection to the database
            con = this.getConnection();
            String query = "SELECT * FROM PATRONS";
            ps = con.prepareStatement(query);

            // Use the prepared statement to execute the sql
            rs = ps.executeQuery();
            while(rs.next())
            {
                String fname = rs.getString("first_name");
                String lname = rs.getString("last_name");
                Date dob = rs.getDate("date_of_birth");
                String gender = rs.getString("gender");
                String addressLine1 = rs.getString("address_line1");
                String addressLine2 = rs.getString("address_line2");
                String county = rs.getString("county");
                String eirCode = rs.getString("eir_code");
                String email = rs.getString("email");
                String confirmEmail = rs.getString("confirm_email");
                String mobile = rs.getString("mobile");
                long pin = rs.getLong("pin");
                long libraryCardNum = rs.getLong("library_card_number");
                LocalDate dateOfBirth = Instant.ofEpochMilli(dob.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                Patron p = new Patron(fname, lname, dateOfBirth, gender, addressLine1, addressLine2, county, eirCode, email, confirmEmail, mobile, pin, libraryCardNum);
                patrons.add(p);
            }
        }
        catch(SQLException sqe)
        {
            throw new DaoException("findAllPatrons() " + sqe.getMessage());
        }
        finally
        {
            try
            {
                if(rs != null)
                {
                    rs.close();
                }
                if(ps != null)
                {
                    ps.close();
                }
                if(con != null)
                {
                    freeConnection(con);
                }
            }
            catch(SQLException sqe)
            {
                throw new DaoException("findAllPatrons() " + sqe.getMessage());
            }
        }
        return patrons;
    }

    @Override
    public Patron findPatronByEmail(String email) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Patron p = null;
        try
        {
            // Get a connection to the database
            con = this.getConnection();
            String query = "select * from patrons where email = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, email);

            // Use the prepared statement to execute the sql
            rs = ps.executeQuery();
            while(rs.next())
            {
                String fname = rs.getString("first_name");
                String lname = rs.getString("last_name");
                Date dob = rs.getDate("date_of_birth");
                String gender = rs.getString("gender");
                String addressLine1 = rs.getString("address_line1");
                String addressLine2 = rs.getString("address_line2");
                String county = rs.getString("county");
                String eirCode = rs.getString("eir_code");
                String patronEmail = rs.getString("email");
                String confirmEmail = rs.getString("confirm_email");
                String mobile = rs.getString("mobile");
                long pin = rs.getLong("pin");
                long libraryCardNum = rs.getLong("library_card_number");
                LocalDate dateOfBirth = Instant.ofEpochMilli(dob.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                p = new Patron(fname, lname, dateOfBirth, gender, addressLine1, addressLine2, county, eirCode, patronEmail, confirmEmail, mobile, pin, libraryCardNum);
            }
        }
        catch(SQLException sqe)
        {
            throw new DaoException("findAllPatrons() " + sqe.getMessage());
        }
        finally
        {
            try
            {
                if(rs != null)
                {
                    rs.close();
                }
                if(ps != null)
                {
                    ps.close();
                }
                if(con != null)
                {
                    freeConnection(con);
                }
            }
            catch(SQLException sqe)
            {
                throw new DaoException("findAllPatrons() " + sqe.getMessage());
            }
        }
        return p;
    }

    @Override
    public Patron findPatronByPin(long pinNumber) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Patron p = null;
        try
        {
            // Get a connection to the database
            con = this.getConnection();
            String query = "select * from patrons where pin = ?";
            ps = con.prepareStatement(query);
            ps.setLong(1, pinNumber);

            // Use the prepared statement to execute the sql
            rs = ps.executeQuery();
            while(rs.next())
            {
                String fname = rs.getString("first_name");
                String lname = rs.getString("last_name");
                Date dob = rs.getDate("date_of_birth");
                String gender = rs.getString("gender");
                String addressLine1 = rs.getString("address_line1");
                String addressLine2 = rs.getString("address_line2");
                String county = rs.getString("county");
                String eirCode = rs.getString("eir_code");
                String patronEmail = rs.getString("email");
                String confirmEmail = rs.getString("confirm_email");
                String mobile = rs.getString("mobile");
                long pin = rs.getLong("pin");
                long libraryCardNum = rs.getLong("library_card_number");
                LocalDate dateOfBirth = Instant.ofEpochMilli(dob.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                p = new Patron(fname, lname, dateOfBirth, gender, addressLine1, addressLine2, county, eirCode, patronEmail, confirmEmail, mobile, pin, libraryCardNum);
            }
        }
        catch(SQLException sqe)
        {
            throw new DaoException("findAllPatrons() " + sqe.getMessage());
        }
        finally
        {
            try
            {
                if(rs != null)
                {
                    rs.close();
                }
                if(ps != null)
                {
                    ps.close();
                }
                if(con != null)
                {
                    freeConnection(con);
                }
            }
            catch(SQLException sqe)
            {
                throw new DaoException("findAllPatrons() " + sqe.getMessage());
            }
        }
        return p;
    }

    @Override
    public void deletePatronByPin(long pin) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            // Get a connection to the database
            con = this.getConnection();
            String query = "delete from patrons where pin = ?";
            ps = con.prepareStatement(query);
            ps.setLong(1, pin);

            // Use the prepared statement to execute the sql
            ps.executeUpdate();
        }

        catch(SQLException sqe)
        {
            throw new DaoException("findAllPatrons() " + sqe.getMessage());
        }
        finally
        {
            try
            {
                if(rs != null)
                {
                    rs.close();
                }
                if(ps != null)
                {
                    ps.close();
                }
                if(con != null)
                {
                    freeConnection(con);
                }
            }
            catch(SQLException sqe)
            {
                throw new DaoException("findAllPatrons() " + sqe.getMessage());
            }
        }
    }

    @Override
    public void insertAPatron(Patron patron) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            // Get a connection to the database
            con = this.getConnection();
            String query = "INSERT INTO patrons(first_name, last_name, date_of_birth, gender, address_line1," +
                    " address_line2, county, eir_code, email, confirm_email, mobile, pin, library_card_number) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(query);

            ps.setString(1, patron.getFirstName());
            ps.setString(2, patron.getLastName());
            ps.setDate(3, java.sql.Date.valueOf(patron.getDateOfBirth()));
            ps.setString(4, patron.getGender());
            ps.setString(5, patron.getAddressLine1());
            ps.setString(6, patron.getAddressLine2());
            ps.setString(7, patron.getCounty());
            ps.setString(8, patron.getEirCode());
            ps.setString(9, patron.getEmail());
            ps.setString(10, patron.getConfirmEmail());
            ps.setString(11, patron.getMobile());
            ps.setLong(12, patron.getPin());
            ps.setLong(13, patron.getLibraryCardNumber());

            // Use the prepared statement to execute the sql
            ps.executeUpdate();
        }
        catch(SQLException sqe)
        {
            throw new DaoException("findAllPatrons() " + sqe.getMessage());
        }
        finally
        {
            try
            {
                if(rs != null)
                {
                    rs.close();
                }
                if(ps != null)
                {
                    ps.close();
                }
                if(con != null)
                {
                    freeConnection(con);
                }
            }
            catch(SQLException sqe)
            {
                throw new DaoException("findAllPatrons() " + sqe.getMessage());
            }
        }
    }
}
