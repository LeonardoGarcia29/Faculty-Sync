package s25.cs151.application.JavaBeans;

public class CourseDataBean {

    private final String courseCode;
    private final String courseName;
    private final String courseSection;

    public CourseDataBean(String courseCode, String courseName, String courseSection) {
        this.courseCode = courseCode.trim();
        this.courseName = courseName.trim();
        this.courseSection = courseSection.trim();
    }

    public String getCourseCode(){
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseSection() {
        return courseSection;
    }

    @Override
    public String toString() {
        return courseCode + "," + courseName + "," + courseSection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseDataBean that = (CourseDataBean) o;

        return courseCode.equalsIgnoreCase(that.courseCode)
                && courseName.equalsIgnoreCase(that.courseName)
                && courseSection.equalsIgnoreCase(that.courseSection);
    }

    @Override
    public int hashCode() {
        int result = courseCode.toLowerCase().hashCode();
        result = 31 * result + courseName.toLowerCase().hashCode();
        result = 31 * result + courseSection.toLowerCase().hashCode();
        return result;
    }

}
