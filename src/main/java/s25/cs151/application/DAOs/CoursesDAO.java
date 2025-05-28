package s25.cs151.application.DAOs;

import s25.cs151.application.DAOInterfaces.CourseDAOInt;
import s25.cs151.application.JavaBeans.CourseDataBean;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CoursesDAO implements CourseDAOInt {

    private final String filename;

    public CoursesDAO(String filename){
        this.filename = filename;
    }

    public void storeCourses(CourseDataBean entry){

        try(FileWriter data = new FileWriter(filename, true);
        ){
            data.write(entry.toString() + "\n");
        }
        catch (IOException e){
            System.out.println("Failed writing to csv file");
        }
    }

    public List<CourseDataBean> getCourses(){

        List<CourseDataBean> CourseDataEntries = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(filename))){
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] entryData = line.split(",");

                CourseDataEntries.add(new CourseDataBean(entryData[0], entryData[1], entryData[2]));
            }
        }catch(IOException e){
            System.out.println("File could not be open to read");
        }

        return CourseDataEntries;
    }

    public void displaySortedCourses() {
        List<CourseDataBean> entries = getCourses();

        // Sort entries by year (descending) and then semester (Spring > Winter > Fall > Summer)
        entries.sort((e1, e2) -> {
            String code1 = e1.getCourseCode().trim();
            String code2 = e2.getCourseCode().trim();

            String prefix1 = code1.replaceAll("\\d", "").toUpperCase();
            String prefix2 = code2.replaceAll("\\d", "").toUpperCase();

            int prefixCompare = prefix2.compareTo(prefix1); // descending
            if (prefixCompare != 0) return prefixCompare;

            // Extract numeric part inline
            String numPart1 = code1.replaceAll("\\D", "");
            String numPart2 = code2.replaceAll("\\D", "");

            int num1 = numPart1.isEmpty() ? 0 : Integer.parseInt(numPart1);
            int num2 = numPart2.isEmpty() ? 0 : Integer.parseInt(numPart2);

            return Integer.compare(num2, num1); // descending
        });

        try(FileWriter data = new FileWriter(filename, false);
        ){

        }
        catch (IOException e){
            System.out.println("Failed writing to csv file");
        }

        for(CourseDataBean x: entries){
            this.storeCourses(x);
        }
    }
}

