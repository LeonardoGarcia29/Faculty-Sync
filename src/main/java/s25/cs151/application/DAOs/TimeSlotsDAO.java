package s25.cs151.application.DAOs;

import s25.cs151.application.DAOInterfaces.TimeSlotsDAOInt;
import s25.cs151.application.JavaBeans.OfficeHoursDataBean;
import s25.cs151.application.JavaBeans.TimeSlotBean;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class TimeSlotsDAO implements TimeSlotsDAOInt {
    private final String filename;

    public TimeSlotsDAO(String filename){
        this.filename = filename;
    }
    public void storeTimeSlots(TimeSlotBean entry) {
        try (FileWriter data = new FileWriter(filename, true)) {
            data.write(entry.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Failed writing to csv file");
        }
    }

    public List<TimeSlotBean> getTimeSlots() {
        List<TimeSlotBean> timeSlotEntries = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] entryData = line.split(",");
                if (entryData.length == 2) {
                    timeSlotEntries.add(new TimeSlotBean(entryData[0], entryData[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("File(TimeSlots) could not be opened for reading");
        }

        return timeSlotEntries;
    }

    public void sortTimeSlots(){
        List<TimeSlotBean> timeSlots = getTimeSlots();
        Comparator<TimeSlotBean> comparator = (TimeSlot1, TimeSlot2) -> String.CASE_INSENSITIVE_ORDER.compare(TimeSlot1.getFromHour(), TimeSlot2.getFromHour());
        timeSlots.sort(comparator);

        try(FileWriter data = new FileWriter(filename, false);
        ){

        }
        catch (IOException e){
            System.out.println("Failed writing to time slots csv file");
        }

        for(TimeSlotBean x: timeSlots){
            this.storeTimeSlots(x);
        }
    }
}
