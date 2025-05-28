package s25.cs151.application.Controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import s25.cs151.application.DAOInterfaces.CourseDAOInt;
import s25.cs151.application.DAOs.CoursesDAO;
import s25.cs151.application.JavaBeans.CourseDataBean;
import s25.cs151.application.Main;


import java.util.List;

public class CoursesController {

    @FXML
    private TextField courseCodeField;

    @FXML
    private TextField courseNameField;

    @FXML
    private TextField courseSectionField;

    @FXML
    private Button submitBtn, homeBtn, viewCoursesBtn;

    @FXML
    private TableColumn<CourseDataBean, String> courseCodeColumn;
    @FXML
    private TableColumn<CourseDataBean, String> courseNameColumn;
    @FXML
    private TableColumn<CourseDataBean, String> courseSectionColumn;

    @FXML
    private TableView<CourseDataBean> coursesTable;

    private CourseDAOInt csvManager;
    private ObservableList<CourseDataBean> courseData;

    @FXML
    public void initialize() {

        csvManager = new CoursesDAO("permanentData/courseEntries.csv");

        courseCodeColumn.setCellValueFactory(CellData -> new SimpleObjectProperty<>(CellData.getValue().getCourseCode()));
        courseNameColumn.setCellValueFactory(CellData -> new SimpleObjectProperty<>(CellData.getValue().getCourseName()));
        courseSectionColumn.setCellValueFactory(CellData -> new SimpleObjectProperty<>(CellData.getValue().getCourseSection()));

        List<CourseDataBean> entries = csvManager.getCourses();
        courseData = FXCollections.observableArrayList(entries);
        coursesTable.setItems(courseData);
    }

    @FXML
    private void SubmitOp(ActionEvent event) {
        boolean valid = true;
        StringBuilder errorMsg = new StringBuilder();

        // Validate Course Code
        String courseCodeText = courseCodeField.getText().trim();
        if (courseCodeText.isEmpty()) {
            valid = false;
            errorMsg.append("Please enter Course Code.\n");
        }
        // Validate Course Name
        String courseNameText = courseNameField.getText().trim();
        if (courseNameText.isEmpty()) {
            valid = false;
            errorMsg.append("Please enter Course Name.\n");
        }
        // Validate Course Section
        String courseSectionText = courseSectionField.getText().trim();
        if (courseSectionText.isEmpty()) {
            valid = false;
            errorMsg.append("Please enter Course Section.\n");
        }

        if (!valid) {
            showAlert(Alert.AlertType.ERROR, "Form Error", errorMsg.toString());
        } else {
            CourseDataBean entry = new CourseDataBean(courseCodeText, courseNameText, courseSectionText);
            if(courseData.contains(entry)) {
                showAlert(Alert.AlertType.ERROR, "Form Error", "Course Already Exists.");
                return;
            }
            courseData.clear();
            coursesTable.setItems(courseData);

            csvManager.storeCourses(entry);
            csvManager.displaySortedCourses();

            List<CourseDataBean> entries = csvManager.getCourses();
            courseData = FXCollections.observableArrayList(entries);
            coursesTable.setItems(courseData);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Courses submitted!");
        }
    }

    @FXML
    private void HomeOp(ActionEvent event) {
        Main.loadHomeView();
    }

    @FXML
    private void handleViewCourses(ActionEvent event) {
        // Scroll or focus on the table view, or navigate if needed
        coursesTable.requestFocus();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}

