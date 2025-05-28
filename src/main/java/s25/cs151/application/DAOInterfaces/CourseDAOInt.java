package s25.cs151.application.DAOInterfaces;

import s25.cs151.application.JavaBeans.CourseDataBean;

import java.util.List;

public interface CourseDAOInt {

    void storeCourses(CourseDataBean entry);

    List<CourseDataBean> getCourses();

    void displaySortedCourses();
}

