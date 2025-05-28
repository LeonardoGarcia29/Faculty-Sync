package s25.cs151.application.DAOInterfaces;

import s25.cs151.application.JavaBeans.TimeSlotBean;

import java.util.List;

public interface TimeSlotsDAOInt {

    void storeTimeSlots(TimeSlotBean entry);

    List<TimeSlotBean> getTimeSlots();

    public void sortTimeSlots();

}
