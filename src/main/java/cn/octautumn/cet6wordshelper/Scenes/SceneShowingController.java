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
        beforeShow(thisStage);

        thisStage.setScene(thisScene);

        //坐标居中
        thisStage.setX((Screen.getPrimary().getBounds().getMaxX() - this.thisScene.getWidth()) / 2);
        thisStage.setY((Screen.getPrimary().getBounds().getMaxY() - this.thisScene.getHeight()) / 2);

        afterShow(thisStage);
    }



    public Scene getThisScene()
    {
        return thisScene;
    }
}
