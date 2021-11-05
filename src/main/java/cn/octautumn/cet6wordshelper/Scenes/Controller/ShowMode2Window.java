package cn.octautumn.cet6wordshelper.Scenes.Controller;

import cn.octautumn.cet6wordshelper.MainApplication;
import cn.octautumn.cet6wordshelper.OnRunning.RunModes.RunMode1;
import cn.octautumn.cet6wordshelper.OnRunning.RunModes.RunMode2;
import cn.octautumn.cet6wordshelper.Scenes.SceneShowingController;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;

public class ShowMode2Window extends SceneShowingController
{

    public ShowMode2Window(URL resource) throws Exception
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
        RunMode2 func = new RunMode2(getThisScene());
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
