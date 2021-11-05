package cn.octautumn.cet6wordshelper.Scenes.Controller;

import cn.octautumn.cet6wordshelper.MainApplication;
import cn.octautumn.cet6wordshelper.OnRunning.RunModes.RunMode1;
import cn.octautumn.cet6wordshelper.Scenes.SceneShowingController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;

public class ShowMode1Window extends SceneShowingController
{

    public ShowMode1Window(URL resource) throws Exception
    {
        super(resource);
    }

    @Override
    protected void beforeShow(Stage upStage) throws Exception
    {

    }

    @Override
    protected void afterShow(Stage upStage) throws Exception
    {
        RunMode1 func = new RunMode1(getThisScene());
        new Thread(func).start();

        ((Button)getThisScene().lookup("#ExitButton")).setOnAction((ActionEvent) ->
        {
            try
            {
                func.exit();
                MainApplication.mainWindowController.backToMainScene();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        });

    }
}
