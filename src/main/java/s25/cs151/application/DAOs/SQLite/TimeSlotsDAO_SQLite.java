package s25.cs151.application.DAOs.SQLite;

import s25.cs151.application.DAOInterfaces.TimeSlotsDAOInt;
import s25.cs151.application.JavaBeans.TimeSlotBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimeSlotsDAO_SQLite implements TimeSlotsDAOInt {
    Connection connector;

    public TimeSlotsDAO_SQLite(){

        connector = sqliteConnect.getConnector();

        String tablePrompt = """
                CREATE TABLE IF NOT EXISTS time_slots (
                 start_time text NOT NULL,\s
                 end_time text NOT NULL
                );""";

        //Create table
        try(Statement smt = connector.createStatement()){
            smt.execute(tablePrompt);
            System.out.println("Success creating table for time_slots");
        } catch (SQLException e) {
            System.out.println("Failed to create table: time_slots");
        }

    }
    public void storeTimeSlots(TimeSlotBean entry){
        String insertPrompt = "INSERT INTO time_slots(start_time, end_time) VALUES(?,?)";

        try{
            PreparedStatement psmt = connector.prepareStatement(insertPrompt);
            psmt.setString(1, entry.getFromHour());
            psmt.setString(2, entry.getToHour());
            psmt.executeUpdate();
            System.out.println("Successfully inserted entry to time_slots db");
        } catch (SQLException e) {
            System.out.println("Failed to insert entry into time_slots db");
        }
    }
    public List<TimeSlotBean> getTimeSlots(){
        List<TimeSlotBean> entries = new ArrayList<>();
        String selectPrompt = "SELECT * FROM time_slots ORDER BY start_time ASC";

        try(Statement smt = connector.createStatement();
            ResultSet rs = smt.executeQuery(selectPrompt);){

            while(rs.next()){
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                entries.add(new TimeSlotBean(startTime, endTime));
            }
            System.out.println("Successfully retrieved data from time_slots");
        } catch (SQLException e) {
            System.out.println("Failed to retrieved data from time_slots");
        }

        return entries;
    }
    public void sortTimeSlots(){
        //NOTE: the time slots are sorted in getTimeSlots()
    }



}
