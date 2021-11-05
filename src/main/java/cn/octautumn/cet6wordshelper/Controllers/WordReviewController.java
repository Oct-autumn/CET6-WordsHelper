package cn.octautumn.cet6wordshelper.Controllers;

import cn.octautumn.cet6wordshelper.MainApplication;
import javafx.event.ActionEvent;

public class WordReviewController
{
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
