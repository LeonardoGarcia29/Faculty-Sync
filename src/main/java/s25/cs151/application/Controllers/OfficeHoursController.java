package s25.cs151.application.Controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import s25.cs151.application.DAOInterfaces.OfficeHoursDAOInt;
import s25.cs151.application.DAOs.OfficeHoursDAO;
import s25.cs151.application.Main;
import s25.cs151.application.JavaBeans.OfficeHoursDataBean;

import java.time.Year;
import java.util.List;

public class OfficeHoursController {

    @FXML
    private ComboBox<String> semesterDropdown;

    @FXML
    private TextField yearField;

    @FXML
    private CheckBox mon, tue, wed, thu, fri;

    @FXML
    private Button submitBtn, homeBtn, viewOfficeHourBtn;

    @FXML
    private TableColumn<OfficeHoursDataBean, String> semesterColumn;
    @FXML
    private TableColumn<OfficeHoursDataBean, String> yearColumn;
    @FXML
    private TableColumn<OfficeHoursDataBean, String> daysColumn;

    @FXML
    private TableView<OfficeHoursDataBean> officeHoursTable;

    private OfficeHoursDAOInt csvManager;
    private ObservableList<OfficeHoursDataBean> officeHourData;

    @FXML
    public void initialize() {
        semesterDropdown.setItems(FXCollections.observableArrayList("Spring", "Summer", "Fall", "Winter"));
        semesterDropdown.setValue("Spring");

        csvManager = new OfficeHoursDAO("permanentData/semesterEntries.csv");

        semesterColumn.setCellValueFactory(CellData -> new SimpleObjectProperty<>(CellData.getValue().getSemester()));
        yearColumn.setCellValueFactory(CellData -> new SimpleObjectProperty<>(CellData.getValue().getYear()));
        daysColumn.setCellValueFactory(CellData -> new SimpleObjectProperty<>(CellData.getValue().getDays()));


        List<OfficeHoursDataBean> entries = csvManager.getSemesterOfficeHours();
        officeHourData = FXCollections.observableArrayList(entries);
        officeHoursTable.setItems(officeHourData);
    }

    @FXML
    private void SubmitOp(ActionEvent event) {
        boolean valid = true;
        StringBuilder errorMsg = new StringBuilder();

        // Validate semester
        if (semesterDropdown.getValue() == null) {
            valid = false;
            errorMsg.append("Please select a semester.\n");
        }

        // Validate year
        String yearText = yearField.getText().trim();
        if (yearText.isEmpty()) {
            valid = false;
            errorMsg.append("Please enter year.\n");
        } else if (!yearText.matches("\\d{4}")) {
            valid = false;
            errorMsg.append("Year must be a 4-digit number.\n");
        } else if (Integer.parseInt(yearText) > Year.now().getValue()) {
            valid = false;
            errorMsg.append("Year must not be in the future.\n");
        }

        // Validate days
        boolean[] days = {mon.isSelected(), tue.isSelected(), wed.isSelected(), thu.isSelected(), fri.isSelected()};
        boolean atLeastOneDay = false;
        for (boolean day : days) {
            if (day) {
                atLeastOneDay = true;
                break;
            }
        }

        if (!atLeastOneDay) {
            valid = false;
            errorMsg.append("Select at least one day.\n");
        }

        if (!valid) {
            showAlert(Alert.AlertType.ERROR, "Form Error", errorMsg.toString());
        } else {
            officeHourData.clear();
            officeHoursTable.setItems(officeHourData);

            OfficeHoursDataBean entry = new OfficeHoursDataBean(semesterDropdown.getValue(), yearText, days);

            csvManager.storeSemesterOfficeHours(entry);
            csvManager.displaySortedOfficeHours();

            List<OfficeHoursDataBean> entries = csvManager.getSemesterOfficeHours();
            officeHourData = FXCollections.observableArrayList(entries);
            officeHoursTable.setItems(officeHourData);


            showAlert(Alert.AlertType.INFORMATION, "Success", "Office hours submitted!");
        }
    }

    @FXML
    private void HomeOp(ActionEvent event) {
        Main.loadHomeView();
    }

    @FXML
    private void handleViewOfficeHours(ActionEvent event) {
        // Scroll or focus on the table view, or navigate if needed
        officeHoursTable.requestFocus();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
