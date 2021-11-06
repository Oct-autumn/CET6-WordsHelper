package cn.octautumn.cet6wordshelper.Scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public abstract class SceneShowingController
{
    private Scene thisScene;

    protected abstract void beforeShow(Stage thisStage) throws Exception;

    protected abstract void afterShow(Stage thisStage) throws Exception;

    public SceneShowingController(URL sceneResource)
    {
        try
        {
            thisScene = new Scene(new FXMLLoader(sceneResource).load());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void showIn(Stage thisStage) throws Exception
    {
        //前处理
        beforeShow(thisStage);

        //展示界面
        thisStage.setScene(thisScene);

        //居中显示
        thisStage.centerOnScreen();

        //后处理
        afterShow(thisStage);
    }



    public Scene getThisScene()
    {
        return thisScene;
    }
}
