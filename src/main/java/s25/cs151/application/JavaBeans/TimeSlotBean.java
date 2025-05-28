package s25.cs151.application.JavaBeans;

import javafx.beans.property.SimpleStringProperty;

public class TimeSlotBean {
    private final SimpleStringProperty fromHour;
    private final SimpleStringProperty toHour;

    public TimeSlotBean(String fromHour, String toHour) {
        this.fromHour = new SimpleStringProperty(fromHour);
        this.toHour = new SimpleStringProperty(toHour);
    }

    public String getFromHour() { return fromHour.get(); }
    public String getToHour() { return toHour.get(); }
    public String toString() {
        return getFromHour() + "," + getToHour();
    }
}
