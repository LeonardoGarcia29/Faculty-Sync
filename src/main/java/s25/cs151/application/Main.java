package s25.cs151.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage)  {
        primaryStage = stage;
        loadHomeView();
    }

    public static void loadHomeView() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/s25/cs151/application/view/home-view.fxml")));
            primaryStage.setTitle("FacultySync");
            primaryStage.setScene(new Scene(root, 820, 620));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadOfficeHoursView() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/s25/cs151/application/view/setOfficeHour-view.fxml")));
            primaryStage.setTitle("Semester's Office Hours");
            primaryStage.setScene(new Scene(root, 820, 623));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadCoursesView() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/s25/cs151/application/view/setCourse-view.fxml")));
            primaryStage.setTitle("Course Entries");
            primaryStage.setScene(new Scene(root, 820, 630));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void loadTimeSlotsView() {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/s25/cs151/application/view/setTimeSlots-view.fxml")));
                primaryStage.setTitle("Semester's Time Slots");
                primaryStage.setScene(new Scene(root, 820, 630));
                primaryStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    public static void loadScheduleView() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/s25/cs151/application/view/OfficeHourSchedule-view.fxml")));
            primaryStage.setScene(new Scene(root, 820, 630));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void loadSearchScheduleView() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/s25/cs151/application/view/searchSchedule-view.fxml")));
            primaryStage.setTitle("Course Entries");
            primaryStage.setScene(new Scene(root, 825, 630));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadEditScheduleView() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/s25/cs151/application/view/EditOfficeHourSchedule-view.fxml")));
            primaryStage.setTitle("Course Entries");
            primaryStage.setScene(new Scene(root, 830, 640));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
