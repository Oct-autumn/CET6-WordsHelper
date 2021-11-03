package cn.octautumn.cet6wordshelper;

import cn.octautumn.cet6wordshelper.DictionaryClass.Dictionary;
import cn.octautumn.cet6wordshelper.ShowWindow.ShowHelloWindow;
import cn.octautumn.cet6wordshelper.ShowWindow.ShowWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApplication extends Application
{
    public static final String fileSeparator = System.getProperty("file.separator");
    public static final String WorkingDir = System.getProperty("user.dir");
    public static final Dictionary mainDict = new Dictionary();
    public static final Dictionary HistoryDict = new Dictionary();

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("CET6 单词助手");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

        new ShowHelloWindow(MainApplication.class.getResource("hello-view.fxml"))
                .ShowWindow(primaryStage);
    }

    public static void main(String[] args)
    {
        launch();

    }
}