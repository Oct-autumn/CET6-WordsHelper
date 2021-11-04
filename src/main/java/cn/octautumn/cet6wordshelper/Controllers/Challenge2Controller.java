package cn.octautumn.cet6wordshelper.Controllers;

import cn.octautumn.cet6wordshelper.MainApplication;
import javafx.event.ActionEvent;

public class Challenge2Controller
{
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
}
