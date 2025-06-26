package s25.cs151.application.JavaBeans;

public class OfficeHoursDataBean {

    private String semester;
    private String year;
    private String selectedDays;

    public OfficeHoursDataBean(){
        selectedDays = "N/A";
        semester = "N/A";
        year = "N/A";
    }
    public OfficeHoursDataBean(String semester, String year, boolean[] days){
        this.semester = semester.trim();
        this.year = year.trim();
        this.selectedDays = "";

        if(days[0]){
            selectedDays = "Monday ";
        }
        if(days[1]){
            selectedDays = selectedDays + "Tuesday ";
        }
        if(days[2]){
            selectedDays = selectedDays + "Wednesday ";
        }
        if(days[3]){
            selectedDays = selectedDays + "Thursday ";
        }
        if(days[4]){
            selectedDays = selectedDays + "Friday";
        }

    }
    public OfficeHoursDataBean(String semester, String year, String selectedDays){
        this.semester = semester.trim();
        this.year = year.trim();
        this.selectedDays = selectedDays.trim();
    }

    public String getSemester(){
        return semester;
    }

    public String getYear() {
        return year;
    }

    public String getDays() {
        return selectedDays;
    }

    public void setSemester(String semester){this.semester = semester;}
    public void setYear(String year){this.year = year;}
    public void setSelectedDays(String selectedDays){this.selectedDays = selectedDays;}

    @Override
    public String toString() {
        return semester + ", " + year + ", " + selectedDays;
    }
}
