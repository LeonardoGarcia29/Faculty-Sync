package s25.cs151.application.DAOs;

import s25.cs151.application.DAOInterfaces.ScheduleDAOInt;
import s25.cs151.application.JavaBeans.ScheduleBean;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScheduleDAO_CSV implements ScheduleDAOInt {

    private final String filename;

    public ScheduleDAO_CSV(String filename){
        this.filename = filename;
    }

    public void storeASchedule(ScheduleBean entry) {
        try(FileWriter data = new FileWriter(filename, true);
        ){
            data.write(entry.toString() + "\n");
        }
        catch (IOException e){
            System.out.println("Failed writing to SCHEDULES csv file");
        }
    }

    public void storeSchedules(List<ScheduleBean> entries){
        try(FileWriter data = new FileWriter(filename, false);
        ){
            for (ScheduleBean schedule : entries) {
                data.write(schedule.toString() + "\n");
            }
        }
        catch (IOException e){
            System.out.println("Failed writing to SCHEDULES csv file");
        }

        sortedSchedules();
    }

    public List<ScheduleBean> getSchedules(){
        List<ScheduleBean> scheduleEntries = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(filename))){
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] entryData = line.split(",");
                LocalDate date = LocalDate.parse(entryData[0]);
                scheduleEntries.add(new ScheduleBean(date, entryData[1], entryData[2], entryData[3], entryData[4], entryData[5]));
            }
        }catch(IOException e){
            System.out.println("File could not be open to read");
        }

        return scheduleEntries;
    }

    public void sortedSchedules(){
        List<ScheduleBean> schedules = getSchedules(); // Get all existing schedules

        // Sort by schedule date, then by time slot (optional secondary sort)
        schedules.sort((s1, s2) -> {
            int dateCompare = s1.getScheduleDate().compareTo(s2.getScheduleDate());
            if (dateCompare != 0) {
                return dateCompare;
            } else {
                return s1.getTimeSlot().compareTo(s2.getTimeSlot());
            }
        });

        // Write the sorted list back to the CSV (overwrite existing file)
        try (FileWriter writer = new FileWriter(filename, false)) {
            for (ScheduleBean schedule : schedules) {
                writer.write(schedule.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Failed to write sorted schedules to file");
        }
    }
}
