package s25.cs151.application.Controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import s25.cs151.application.DAOInterfaces.ScheduleDAOInt;
import s25.cs151.application.DAOs.CoursesDAO;
import s25.cs151.application.DAOs.ScheduleDAO;
import s25.cs151.application.DAOs.TimeSlotsDAO;
import s25.cs151.application.JavaBeans.CourseDataBean;
import s25.cs151.application.JavaBeans.ScheduleBean;
import s25.cs151.application.JavaBeans.TimeSlotBean;
import s25.cs151.application.Main;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ScheduleController {

    @FXML
    private ComboBox<String> CourseCombo;

    @FXML
    private DatePicker ScheduleDate;

    @FXML
    private TextField StudentName;

    @FXML
    private ComboBox<String> TimeCombo;

    @FXML
    private TextField commentText;

    @FXML
    private TextField reasonText;

    @FXML
    private TableView<ScheduleBean> scheduleTable;

    @FXML
    private TableColumn<ScheduleBean, String> courseColumn;
    @FXML
    private TableColumn<ScheduleBean, String> nameColumn;
    @FXML
    private TableColumn<ScheduleBean, LocalDate> dateColumn;
    @FXML
    private TableColumn<ScheduleBean, String> timeSlotColumn;
    @FXML
    private TableColumn<ScheduleBean, String> reasonColumn;
    @FXML
    private TableColumn<ScheduleBean, String> commentColumn;

    private ScheduleDAOInt csvManager;
    private ObservableList<ScheduleBean> scheduleData;

    @FXML
    public void initialize() {
        ScheduleDate.setValue(LocalDate.now());

        TimeSlotsDAO timeDAO = new TimeSlotsDAO("permanentData/timeslots.csv");
        List<TimeSlotBean> timeSlots = timeDAO.getTimeSlots();
        ObservableList<String> timeOptions = FXCollections.observableArrayList();
        for (TimeSlotBean slot : timeSlots) {
            timeOptions.add(slot.getFromHour() + " – " + slot.getToHour());
        }
        TimeCombo.setItems(timeOptions);
        if (!timeOptions.isEmpty()) {
            TimeCombo.getSelectionModel().selectFirst();
        }

        CoursesDAO courseDAO = new CoursesDAO("permanentData/courseEntries.csv");
        List<CourseDataBean> courses = courseDAO.getCourses();
        ObservableList<String> courseOptions = FXCollections.observableArrayList();
        for (CourseDataBean course : courses) {
            courseOptions.add(course.getCourseCode() + "-" + course.getCourseSection());
        }
        CourseCombo.setItems(courseOptions);
        if (!courseOptions.isEmpty()) {
            CourseCombo.getSelectionModel().selectFirst();
        }
        csvManager = new ScheduleDAO("permanentData/schedules.csv");

        csvManager.sortedSchedules();

        dateColumn.setCellValueFactory(CellData -> new SimpleObjectProperty<>(CellData.getValue().getScheduleDate()));
        courseColumn.setCellValueFactory(CellData -> new SimpleObjectProperty<>(CellData.getValue().getCourse()));
        nameColumn.setCellValueFactory(CellData -> new SimpleObjectProperty<>(CellData.getValue().getStudentName()));
        timeSlotColumn.setCellValueFactory(CellData -> new SimpleObjectProperty<>(CellData.getValue().getTimeSlot()));
        reasonColumn.setCellValueFactory(CellData -> new SimpleObjectProperty<>(CellData.getValue().getReason()));
        commentColumn.setCellValueFactory(CellData -> new SimpleObjectProperty<>(CellData.getValue().getComment()));

        reasonColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String reason, boolean empty) {
                super.updateItem(reason, empty);
                if (empty || reason == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(reason);
                    Tooltip tooltip = new Tooltip(reason);
                    tooltip.setWrapText(true);
                    tooltip.setMaxWidth(300);
                    setTooltip(tooltip);
                }
            }
        });

        commentColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String comment, boolean empty) {
                super.updateItem(comment, empty);
                if (empty || comment == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(comment);
                    Tooltip tooltip = new Tooltip(comment);
                    tooltip.setWrapText(true);
                    tooltip.setMaxWidth(300);
                    setTooltip(tooltip);
                }
            }
        });

        List<ScheduleBean> scheduleEntries = csvManager.getSchedules();
        scheduleData = FXCollections.observableArrayList(scheduleEntries);
        scheduleTable.setItems(scheduleData);
    }

    @FXML
    public void HomeOp(ActionEvent actionEvent) {
        Main.loadHomeView();
    }

    @FXML
    public void SaveOp(ActionEvent actionEvent) {
        String student = StudentName.getText();
        if (student == null || student.trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error", "Student name is required");
            return;
        }

        String timeSlot = (String) TimeCombo.getValue();
        String course = (String) CourseCombo.getValue();
        String reason = reasonText.getText();
        if(Objects.equals(reason, "")){reason = "N/A";}
        String comment = commentText.getText();
        if(Objects.equals(comment, "")){comment = "N/A";}

        if (timeSlot == null || course == null) {
            System.out.println("Required dropdown fields are missing.");
            return;
        }

        scheduleData.clear();
        scheduleTable.setItems(scheduleData);

        ScheduleBean schedule = new ScheduleBean(
                ScheduleDate.getValue() != null ? ScheduleDate.getValue() : LocalDate.now(),
                timeSlot,
                student,
                course,
                reason,
                comment
        );

        csvManager.storeASchedule(schedule);

        csvManager.sortedSchedules();

        List<ScheduleBean> scheduleEntries = csvManager.getSchedules();
        scheduleData = FXCollections.observableArrayList(scheduleEntries);
        scheduleTable.setItems(scheduleData);

        showAlert(Alert.AlertType.INFORMATION, "Success", "Office hour schedule was submitted!");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
