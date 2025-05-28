package s25.cs151.application.JavaBeans;
import java.time.LocalDate;

public class ScheduleBean {

    private final String studentName;
    private final LocalDate scheduleDate;
    private final String timeSlot;
    private final String course;
    private final String reason;
    private final String comment;



    public ScheduleBean(LocalDate scheduleDate, String timeSlot, String studentName, String course, String reason, String comment) {
        this.studentName = studentName.trim();
        this.scheduleDate = scheduleDate;
        this.timeSlot = timeSlot.trim();
        this.course = course.trim();
        this.reason = reason.trim();
        this.comment = comment.trim();
    }

    public String getStudentName() { return studentName; }
    public LocalDate getScheduleDate() { return scheduleDate; }
    public String getTimeSlot() { return timeSlot; }
    public String getCourse() { return course; }
    public String getReason() { return reason; }
    public String getComment() { return comment; }

    @Override
    public String toString(){
        return scheduleDate + "," + timeSlot + "," + studentName + "," + course + "," + reason + "," + comment;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        ScheduleBean schedule = (ScheduleBean) obj;
        return this.toString().equals(schedule.toString());
    }
}
