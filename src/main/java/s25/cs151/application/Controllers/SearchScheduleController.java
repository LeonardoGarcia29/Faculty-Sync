package s25.cs151.application.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import s25.cs151.application.DAOInterfaces.ScheduleDAOInt;
import s25.cs151.application.DAOs.ScheduleDAO_CSV;
import s25.cs151.application.JavaBeans.ScheduleBean;
import s25.cs151.application.Main;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SearchScheduleController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<ScheduleBean> resultsTable;

    @FXML
    private TableColumn<ScheduleBean, String> nameColumn;

    @FXML
    private TableColumn<ScheduleBean, String> courseColumn;

    @FXML
    private TableColumn<ScheduleBean, String> timeSlotColumn;

    @FXML
    private TableColumn<ScheduleBean, String> reasonColumn;

    @FXML
    private TableColumn<ScheduleBean, String> commentColumn;

    @FXML
    private TableColumn<ScheduleBean, java.time.LocalDate> dateColumn;

    private ScheduleDAOInt csvManager;
    private ObservableList<ScheduleBean> filteredData;

    @FXML
    public void initialize() {
        csvManager = new ScheduleDAO_CSV("permanentData/schedules.csv");

        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getStudentName()));
        courseColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getCourse()));
        timeSlotColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getTimeSlot()));
        reasonColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getReason()));
        commentColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getComment()));
        dateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getScheduleDate()));
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String query = searchField.getText().toLowerCase().trim();
        List<ScheduleBean> allSchedules = csvManager.getSchedules();

        List<ScheduleBean> result = allSchedules.stream()
                .filter(s -> s.getStudentName().toLowerCase().contains(query))
                .sorted(Comparator.comparing(ScheduleBean::getScheduleDate).reversed()
                        .thenComparing(ScheduleBean::getTimeSlot, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        filteredData = FXCollections.observableArrayList(result);
        resultsTable.setItems(filteredData);

    }

    @FXML
    private void handleDelete(ActionEvent event){
        if (resultsTable.getSelectionModel().getSelectedItem() != null) {

            ScheduleBean deleteItem = resultsTable.getSelectionModel().getSelectedItem();
            List<ScheduleBean> allSchedules = csvManager.getSchedules();
            allSchedules.remove(deleteItem);
            csvManager.storeSchedules(allSchedules);
            handleSearch(new ActionEvent());
        }
        else{
            showAlert(Alert.AlertType.ERROR, "Failed", "Please select Office Hour to delete!");
        }
    }


    @FXML
    private void HomeOp(ActionEvent event) {
        Main.loadHomeView();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
