package cn.octautumn.cet6wordshelper.Scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class StageController
{
    Stage thisStage;
    String mainSceneName;
    Scene prevScene;
    Scene nowScene = null;
    HashMap<String, SceneShowingController> Scenes = new HashMap<>();

    public StageController(Stage thisStage) throws IOException
    {
        this.thisStage = thisStage;
    }

    public StageController addScene(String sceneName, SceneShowingController sceneShowingController)
    {
        if (Scenes.isEmpty())
        {
            mainSceneName = sceneName;
        }
        Scenes.put(sceneName, sceneShowingController);
        return this;
    }

    public void showScene(String sceneName) throws Exception
    {
        Scenes.get(sceneName).showIn(thisStage);
    }

    public void backToMainScene() throws Exception
    {

            Scenes.get(mainSceneName).showIn(thisStage);
    }

    public void setMainScene(String name) throws Exception
    {
        if (Scenes.containsKey(name))
            mainSceneName = name;
        else
            throw new Exception("Scene " + name + " does not exist in SceneList");
    }
}
