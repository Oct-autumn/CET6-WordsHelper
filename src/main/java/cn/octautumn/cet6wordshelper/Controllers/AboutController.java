package cn.octautumn.cet6wordshelper.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.WindowEvent;

public class AboutController
{
    public Button OkButton;

    public void Exit(ActionEvent actionEvent)
    {
        OkButton.getScene().getWindow().fireEvent(new WindowEvent(OkButton.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
