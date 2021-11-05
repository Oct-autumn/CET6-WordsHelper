package cn.octautumn.cet6wordshelper.Controllers;

import cn.octautumn.cet6wordshelper.MainApplication;
import javafx.css.Style;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;

public class Challenge1Controller
{
    public VBox rootBox;
    public ProgressBar TimerBar;
    public Label TimerLabel;
    public Label WordLabel;
    public Label TipLabel;
    public Button Ans1;
    public Button Ans2;
    public Button Ans3;
    public Button Ans4;
    public Button ExitButton;

    public void ExitChallenge(ActionEvent actionEvent)
    {
        try
        {
            MainApplication.mainWindowController.backToMainScene();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void DebugAct(ActionEvent actionEvent)
    {
    }
}
