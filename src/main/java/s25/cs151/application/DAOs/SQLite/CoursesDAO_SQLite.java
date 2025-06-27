package s25.cs151.application.DAOs.SQLite;

import s25.cs151.application.DAOInterfaces.CourseDAOInt;
import s25.cs151.application.JavaBeans.CourseDataBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursesDAO_SQLite implements CourseDAOInt {
    Connection connector;

    public CoursesDAO_SQLite(){
        connector = sqliteConnect.getConnector();

        String tablePrompt = """
                CREATE TABLE IF NOT EXISTS courses (
                 course_code text NOT NULL,
                 course_name text NOT NULL,
                 section_number text NOT NULL)
                """;

        try(Statement smt = connector.createStatement()){
            smt.execute(tablePrompt);
            System.out.println("Table for course db was successfully created");
        } catch (Exception e) {
            System.out.println("Failed to create table for course db");
        }
    }
    public void storeCourses(CourseDataBean entry){
        String insertPrompt = "INSERT INTO courses(course_code, course_name, section_number) VALUES(?,?,?)";

        try(PreparedStatement psmt = connector.prepareStatement(insertPrompt)){
            psmt.setString(1, entry.getCourseCode());
            psmt.setString(2, entry.getCourseName());
            psmt.setString(3, entry.getCourseSection());
            psmt.executeUpdate();
            System.out.println("Successfully inserted data into courses db");
        } catch (SQLException e) {
            System.out.println("Failed to insert data into courses db");
        }
    }
    public List<CourseDataBean> getCourses(){
        List<CourseDataBean> entries = new ArrayList<>();
        String selectPrompt = "SELECT * FROM courses ORDER BY course_code DESC";

        try(Statement smt = connector.createStatement();
            ResultSet rs = smt.executeQuery(selectPrompt);){

            while(rs.next()){
                String code = rs.getString("course_code");
                String name = rs.getString("course_name");
                String section = rs.getString("section_number");
                entries.add(new CourseDataBean(code, name, section));
            }

            System.out.println("Successfully retrieve data from courses db");
        }catch (SQLException e){
            System.out.println("Failed to retrieve data from courses db");
        }
        return entries;
    }
    public void displaySortedCourses(){
        //NOTE: Sorting is done in getCourses
    }
}
