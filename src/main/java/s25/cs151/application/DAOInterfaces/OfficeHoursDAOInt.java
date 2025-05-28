package s25.cs151.application.DAOInterfaces;

import s25.cs151.application.JavaBeans.OfficeHoursDataBean;

import java.util.List;

public interface OfficeHoursDAOInt {

    List<OfficeHoursDataBean> getSemesterOfficeHours();

    void storeSemesterOfficeHours(OfficeHoursDataBean entry);

    void displaySortedOfficeHours();
}
