package cn.octautumn.cet6wordshelper.Scenes.Controller;

import cn.octautumn.cet6wordshelper.DictionaryClass.DictOperation;
import cn.octautumn.cet6wordshelper.MainApplication;
import cn.octautumn.cet6wordshelper.Scenes.SceneShowingController;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static cn.octautumn.cet6wordshelper.MainApplication.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

public class HelloScene extends SceneShowingController
{
    public static File wordListJsonFile;
    public static InputStream inputJsonStream = null;

    public HelloScene(URL sceneResource)
    {
        super(sceneResource);
    }

    @Override
    protected void beforeShow(Stage thisStage) throws Exception
    {

    }

    @Override
    protected void afterShow(Stage thisStage) throws Exception
    {
        //加载词库
        final String JsonFilePath = WorkingDir + fileSeparator + "CET6-Words.json"; //常量 词库文件路径
        wordListJsonFile = new File(JsonFilePath);
        if (!(wordListJsonFile).exists())
        {
            while (inputJsonStream == null)
            {
                ButtonType OkButtonType = new ButtonType("好", ButtonBar.ButtonData.CANCEL_CLOSE);
                ButtonType CancelButtonType = new ButtonType("取消", ButtonBar.ButtonData.CANCEL_CLOSE);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("错误");
                dialog.setContentText(
                        "Err-000 未找到词库文件(CET6-Words.json)\n" +
                                "是否手动导入已有词库？（取消将自动加载包内词库）");
                dialog.getDialogPane().getButtonTypes().add(OkButtonType);
                dialog.getDialogPane().getButtonTypes().add(CancelButtonType);
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get().equals(OkButtonType))
                {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("打开词库文件");
                    fileChooser.setInitialDirectory(new File(WorkingDir));
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("Json文件", "*.json"),
                            new FileChooser.ExtensionFilter("All Files", "*.*"));
                    wordListJsonFile = fileChooser.showOpenDialog(thisStage);
                    if (wordListJsonFile != null && wordListJsonFile.exists())
                        inputJsonStream = new FileInputStream(wordListJsonFile);
                    else
                        inputJsonStream = null;
                } else
                {
                    inputJsonStream = MainApplication.class.getResourceAsStream("/cn/octautumn/cet6wordshelper/CET6-Words.json");
                }
            }
        } else
        {
            inputJsonStream = new FileInputStream(wordListJsonFile);
        }

        JsonNode WordListJson = DictOperation.readAndVerifyDictJson(inputJsonStream);

        while (WordListJson == null)
        {
            ButtonType OkButtonType = new ButtonType("好", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType CancelButtonType = new ButtonType("取消", ButtonBar.ButtonData.CANCEL_CLOSE);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("错误");
            dialog.setContentText(
                    "Err-001 词库文件校验失败\n" +
                            "是否手动导入已有词库？（取消将自动加载包内词库）");
            dialog.getDialogPane().getButtonTypes().add(OkButtonType);
            dialog.getDialogPane().getButtonTypes().add(CancelButtonType);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get().equals(OkButtonType))
            {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("打开词库文件");
                fileChooser.setInitialDirectory(new File(WorkingDir));
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Json文件", "*.json"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));
                wordListJsonFile = fileChooser.showOpenDialog(thisStage);
                if (wordListJsonFile != null && wordListJsonFile.exists())
                    inputJsonStream = new FileInputStream(wordListJsonFile);
                else
                    inputJsonStream = null;
            } else
            {
                inputJsonStream = MainApplication.class.getResourceAsStream("/cn/octautumn/cet6wordshelper/CET6-Words.json");

            }

            WordListJson = DictOperation.readAndVerifyDictJson(inputJsonStream);
        }

        DictOperation.ConstructDictionary(WordListJson);
        inputJsonStream.close();

        thisStage.close();
    }
}
