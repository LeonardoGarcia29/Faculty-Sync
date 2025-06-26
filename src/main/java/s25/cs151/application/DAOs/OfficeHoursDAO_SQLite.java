package s25.cs151.application.DAOs;

import s25.cs151.application.DAOInterfaces.OfficeHoursDAOInt;
import s25.cs151.application.JavaBeans.OfficeHoursDataBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OfficeHoursDAO_SQLite implements OfficeHoursDAOInt {

    Connection connector;
    String filename;

    public OfficeHoursDAO_SQLite(String filename){
        this.filename = filename;

        //Create connection to DB
        try{
            connector = DriverManager.getConnection(filename);
            System.out.println("Connection to db was successful for office_hours_schedule");
        } catch (SQLException e) {
            System.out.println("Connection to office_hours_schedule could not be  done!");
        }

        //Make the table, if not created yet
        String createTable = "CREATE TABLE IF NOT EXISTS office_hours_schedule (\n"
                        + " semester text NOT NULL, \n"
                        + " year text NOT NULL, \n"
                        + " days text NOT NULL\n"
                        + ");";

        try(Statement smt = connector.createStatement()){
            smt.execute(createTable);
            System.out.println("office_hours_schedule table was created");
        } catch (Exception e) {
            System.out.println("office_hours_schedule table could not be created");
        }
    }
    public void closeDBConnection(){
        try{
            connector.close();
        } catch (SQLException e) {
            System.out.println("DB connector could not be close for office_hours_schedule!");
        }
    }
    public void storeSemesterOfficeHours(OfficeHoursDataBean entry){
        String insertPrompt = "INSERT INTO office_hours_schedule(semester, year, days) VALUES(?, ?, ?)";
        try{
            PreparedStatement psmt= connector.prepareStatement(insertPrompt);
            psmt.setString(1, entry.getSemester());
            psmt.setString(2, entry.getYear());
            psmt.setString(3, entry.getDays());
            psmt.executeUpdate();
            System.out.println("Data entry was successful into: office_hours_schedule");
        } catch (SQLException e) {
            System.out.println("Failed to insert data into: office_hours_schedule");
        }
    }
    public List<OfficeHoursDataBean> getSemesterOfficeHours(){

        List<OfficeHoursDataBean> OfficeHoursDataEntries = new ArrayList<>();
        String selectPrompt = "SELECT * FROM office_hours_schedule ORDER BY year DESC, CASE semester "
                + "WHEN 'Spring' THEN 1 "
                + "WHEN 'Winter' THEN 2 "
                + "WHEN 'Fall' THEN 3 "
                + "WHEN 'Summer' THEN 4 "
                + "ELSE 4 END ASC;";

        try(Statement smt = connector.createStatement();
            ResultSet rs = smt.executeQuery(selectPrompt);){

            while(rs.next()){
                OfficeHoursDataBean entry = new OfficeHoursDataBean();
                entry.setSemester(rs.getString(1));//Gets the semester
                entry.setYear(rs.getString(2)); //Gets the year
                entry.setSelectedDays(rs.getString(3)); //Gets the selected days
                OfficeHoursDataEntries.add(entry);
            }

            System.out.println("Data retrieval was successful from: office_hours_schedule");
        } catch (Exception e) {
            System.out.println("Failed to retrieve data from: office_hours_schedule");
        }

        return OfficeHoursDataEntries;
    }
    public void displaySortedOfficeHours(){
        // NOTE: Sorting is done with sql Query in getSemesterOfficeHours
    }
}
