package s25.cs151.application.DAOs;

import s25.cs151.application.DAOInterfaces.OfficeHoursDAOInt;
import s25.cs151.application.JavaBeans.OfficeHoursDataBean;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OfficeHoursDAO implements OfficeHoursDAOInt {

    private final String filename;

    public OfficeHoursDAO(String filename){
        this.filename = filename;
    }

    public void storeSemesterOfficeHours(OfficeHoursDataBean entry){

        try(FileWriter data = new FileWriter(filename, true);
        ){
            data.write(entry.toString() + "\n");
        }
        catch (IOException e){
            System.out.println("Failed writing to csv file");
        }
    }

    public List<OfficeHoursDataBean> getSemesterOfficeHours(){

        List<OfficeHoursDataBean> OfficeHoursDataEntries = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(filename))){
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] entryData = line.split(",");

                OfficeHoursDataEntries.add(new OfficeHoursDataBean(entryData[0], entryData[1], entryData[2]));
            }
        }catch(IOException e){
            System.out.println("File could not be open to read");
        }

        return OfficeHoursDataEntries;
    }

    public void displaySortedOfficeHours() {
        List<OfficeHoursDataBean> entries = getSemesterOfficeHours();

        // Sort entries by year (descending) and then semester (Spring > Winter > Fall > Summer)
        entries.sort((e1, e2) -> {
            int yearCompare = Integer.compare(Integer.parseInt(e2.getYear().trim()), Integer.parseInt(e1.getYear().trim()));
            if (yearCompare != 0) return yearCompare;
            return getSemesterOrder(e2.getSemester()) - getSemesterOrder(e1.getSemester());
        });

        try(FileWriter data = new FileWriter(filename, false);
        ){

        }
        catch (IOException e){
            System.out.println("Failed writing to csv file");
        }

        for(OfficeHoursDataBean x: entries){
            this.storeSemesterOfficeHours(x);
        }
    }

    private int getSemesterOrder(String semester) {
        switch (semester) {
            case "Spring": return 1;
            case "Winter": return 2;
            case "Fall": return 3;
            case "Summer": return 4;
            default: return 5;
        }
    }
}

