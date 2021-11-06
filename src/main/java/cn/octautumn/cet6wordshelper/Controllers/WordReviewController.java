package cn.octautumn.cet6wordshelper.Controllers;

import cn.octautumn.cet6wordshelper.MainApplication;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class WordReviewController
{
    public TextField SearchBox;
    public TableView WordTabel;
    public Label WordInfo;

    public void Exit(ActionEvent actionEvent)
    {
        try
        {
            MainApplication.mainWindowController.backToMainScene();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
