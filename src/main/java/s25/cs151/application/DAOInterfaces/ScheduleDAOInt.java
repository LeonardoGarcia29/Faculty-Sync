package s25.cs151.application.DAOInterfaces;

import s25.cs151.application.JavaBeans.ScheduleBean;

import java.util.List;

public interface ScheduleDAOInt {
    void storeASchedule(ScheduleBean entry);

    void storeSchedules(List<ScheduleBean> entries);

    List<ScheduleBean> getSchedules();

    void sortedSchedules();
}
