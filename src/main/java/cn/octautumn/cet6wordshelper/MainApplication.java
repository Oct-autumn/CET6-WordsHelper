package cn.octautumn.cet6wordshelper;

import cn.octautumn.cet6wordshelper.DictionaryClass.Dictionary;
import cn.octautumn.cet6wordshelper.Scenes.Controller.ShowMode1Window;
import cn.octautumn.cet6wordshelper.Scenes.Controller.ShowMode2Window;
import cn.octautumn.cet6wordshelper.Scenes.StageController;
import cn.octautumn.cet6wordshelper.Scenes.Controller.HelloScene;
import cn.octautumn.cet6wordshelper.Scenes.Controller.MainWindow_MenuScene;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApplication extends Application
{
    public static final String fileSeparator = System.getProperty("file.separator");
    public static final String WorkingDir = System.getProperty("user.dir");
    public static final Dictionary mainDict = new Dictionary();

    public static StageController mainWindowController;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("CET6 单词助手");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

        new HelloScene(MainApplication.class.getResource("hello-view.fxml"))
                .showIn(primaryStage);

        Stage subStage = new Stage();
        subStage.setTitle("CET6 单词助手");
        subStage.setResizable(false);
        subStage.show();

        mainWindowController = new StageController(subStage)
                .addScene("Menu", new MainWindow_MenuScene(MainApplication.class.getResource("Menu-view.fxml")))
                .addScene("Mode1", new ShowMode1Window(MainApplication.class.getResource("Challenge1-view.fxml")))
                .addScene("Mode2", new ShowMode2Window(MainApplication.class.getResource("Challenge2-view.fxml")));

        mainWindowController.showScene("Menu");


    }

    public static void main(String[] args)
    {
        launch();

    }
}