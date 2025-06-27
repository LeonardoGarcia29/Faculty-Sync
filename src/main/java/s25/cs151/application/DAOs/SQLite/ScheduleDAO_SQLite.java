package s25.cs151.application.DAOs.SQLite;

import s25.cs151.application.DAOInterfaces.ScheduleDAOInt;
import s25.cs151.application.JavaBeans.ScheduleBean;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO_SQLite implements ScheduleDAOInt {
    Connection connector;
    public ScheduleDAO_SQLite(){
        connector = sqliteConnect.getConnector();

        String tablePrompt = """
                CREATE TABLE IF NOT EXISTS schedules(
                date TEXT NOT NULL,
                time TEXT NOT NULL,
                name TEXT NOT NULL,
                course TEXT NOT NULL,
                reason TEXT NOT NULL,
                comment TEXT NOT NULL)
                """;

        try(Statement smt = connector.createStatement()){
            smt.execute(tablePrompt);
            System.out.println("Successfully created table for schedules db");
        } catch (SQLException e){
            System.out.println("Failed to create table for schedules db");
        }
    }
    public void storeASchedule(ScheduleBean entry){
        String insertPrompt = "INSERT INTO schedules(date, time, name, course, reason, comment) VALUES(?, ?, ?, ?, ?, ?)";

        try(PreparedStatement psmt = connector.prepareStatement(insertPrompt)){
            psmt.setString(1, entry.getScheduleDate().toString());
            psmt.setString(2, entry.getTimeSlot());
            psmt.setString(3, entry.getStudentName());
            psmt.setString(4, entry.getCourse());
            psmt.setString(5, entry.getReason());
            psmt.setString(6, entry.getComment());
            psmt.executeUpdate();
            System.out.println("Successfully inserted data into schedules db");
        }catch (SQLException e){
            System.out.println("Failed to insert data into schedules db");
        }
    }
    public void storeSchedules(List<ScheduleBean> entries){
        for(ScheduleBean entry: entries){
            storeASchedule(entry);
        }
    }
    public List<ScheduleBean> getSchedules(){
        List<ScheduleBean> entries = new ArrayList<>();
        String selectPrompt = "SELECT * FROM schedules ORDER BY date ASC, time ASC";

        try(Statement smt = connector.createStatement();
            ResultSet rs = smt.executeQuery(selectPrompt);){

            while(rs.next()){
                LocalDate date = LocalDate.parse(rs.getString("date"));
                String time = rs.getString("time");
                String name = rs.getString("name");
                String course = rs.getString("course");
                String reason = rs.getString("reason");
                String comment = rs.getString("comment");
                entries.add(new ScheduleBean(date, time, name, course,reason, comment));
            }

            System.out.println("Successfully retrieved data from schedules db");
        }catch (SQLException e){
            System.out.println("Failed to retrieve data from schedules db");
        }

        return entries;
    }
    public void deleteSchedule(ScheduleBean entry){
        String deletePrompt = """
                DELETE FROM schedules WHERE rowid = ( SELECT rowid FROM schedules WHERE
                 date = ? AND
                 time = ? AND
                 name = ? AND
                 course = ? AND
                 reason = ? AND
                 comment = ? LIMIT 1)""";

        try(PreparedStatement psmt = connector.prepareStatement(deletePrompt)){
            psmt.setString(1,  entry.getScheduleDate().toString());
            psmt.setString(2, entry.getTimeSlot());
            psmt.setString(3, entry.getStudentName());
            psmt.setString(4, entry.getCourse());
            psmt.setString(5, entry.getReason());
            psmt.setString(6, entry.getComment());
            psmt.executeUpdate();
            System.out.println("Successfully DELETED from schedules db");
        }catch (SQLException e){
            System.out.println("Failed to DELETE from schedules db");
        }
    }
    public void sortedSchedules(){
        //NOTE: sorting is done in getSchedules()
    };

}
