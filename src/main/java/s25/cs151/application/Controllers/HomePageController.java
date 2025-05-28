package s25.cs151.application.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import s25.cs151.application.Main;

import static javafx.application.Platform.*;


public class HomePageController {

    public AnchorPane HomePageBox;
    @FXML
    private void SetOfficeHoursOp(ActionEvent event) {

        Main.loadOfficeHoursView();
    }

    @FXML
    private void SetCoursesOp(ActionEvent event) {

        Main.loadCoursesView();
    }
    @FXML
    public void SetTimeOp(ActionEvent actionEvent) {

        Main.loadTimeSlotsView();
    }
    @FXML
    public void ScheduleOp(ActionEvent actionEvent) {

        Main.loadScheduleView();
    }




    @FXML
    private void QuitPlatformOp(ActionEvent event) {
        exit();

    }


    public void searchScheduleOp(ActionEvent actionEvent) {
        Main.loadSearchScheduleView();
    }

    @FXML
    public void editScheduleOp(ActionEvent actionEvent) {

        Main.loadEditScheduleView();
    }
}
