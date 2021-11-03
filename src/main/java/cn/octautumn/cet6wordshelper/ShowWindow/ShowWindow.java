package cn.octautumn.cet6wordshelper.ShowWindow;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;

public abstract class ShowWindow
{
    FXMLLoader thisFxmlLoader = null;
    Scene scene = null;

    abstract void onLoad(Stage upStage) throws Exception;
    abstract void onRunning(Stage upStage) throws Exception;

    public ShowWindow(URL resource) throws Exception
    {
        thisFxmlLoader = new FXMLLoader(resource);
    }

    protected void addInto(Stage upStage) throws Exception
    {
        scene = new Scene(thisFxmlLoader.load());
        upStage.setScene(scene);
    }

    public void ShowWindow(Stage upStage) throws Exception
    {
        onLoad(upStage);
        addInto(upStage);
        onRunning(upStage);
    }
}
