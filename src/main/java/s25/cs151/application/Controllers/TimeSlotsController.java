package s25.cs151.application.Controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import s25.cs151.application.DAOInterfaces.TimeSlotsDAOInt;
import s25.cs151.application.DAOs.TimeSlotsDAO_CSV;
import s25.cs151.application.Main;
import s25.cs151.application.JavaBeans.TimeSlotBean;

import java.util.List;

public class TimeSlotsController {
    @FXML
    private TableColumn<TimeSlotBean, String> TimeFromCol;

    @FXML
    private TableColumn<TimeSlotBean, String> TimeToCol;

    @FXML
    private ComboBox<String> toHourCombo;

    @FXML
    private ComboBox<String> fromHourCombo;

    @FXML
    private TableView<TimeSlotBean> TimeSlotTable;

    private TimeSlotsDAOInt csvManager;
    private ObservableList<TimeSlotBean> timeSlotsData;

    @FXML
    public void initialize() {
        // Populate ComboBoxes with 15-minute intervals
        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m += 15) {
                String time = String.format("%02d:%02d", h, m);
                fromHourCombo.getItems().add(time);
                toHourCombo.getItems().add(time);
            }
        }
        csvManager = new TimeSlotsDAO_CSV("permanentData/timeslots.csv");

        csvManager.sortTimeSlots();

        TimeFromCol.setCellValueFactory(CellData -> new SimpleObjectProperty<>(CellData.getValue().getFromHour()));
        TimeToCol.setCellValueFactory(CellData -> new SimpleObjectProperty<>(CellData.getValue().getToHour()));
        List<TimeSlotBean> entries = csvManager.getTimeSlots();
        timeSlotsData = FXCollections.observableArrayList(entries);
        TimeSlotTable.setItems(timeSlotsData);
    }

    @FXML
    void SubmitTimeOp(ActionEvent event) {
        String from = fromHourCombo.getValue();
        String to = toHourCombo.getValue();

        if (from == null || to == null || from.compareTo(to) >= 0) {
            showAlert(Alert.AlertType.INFORMATION, "Form Error", "Invalid Time Slot!");
        }
        else{
            timeSlotsData.clear();
            TimeSlotTable.setItems(timeSlotsData);

            TimeSlotBean entry = new TimeSlotBean(from, to);

            csvManager.storeTimeSlots(entry);
            csvManager.sortTimeSlots();

            List<TimeSlotBean> entries = csvManager.getTimeSlots();
            timeSlotsData = FXCollections.observableArrayList(entries);
            TimeSlotTable.setItems(timeSlotsData);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Time Slot submitted!");
        }

    }
    @FXML
    void HomeOp(ActionEvent event) {
        Main.loadHomeView();
    }
    @FXML
    private void handleViewCourses(ActionEvent event) {
        // Scroll or focus on the table view, or navigate if needed
        TimeSlotTable.requestFocus();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
