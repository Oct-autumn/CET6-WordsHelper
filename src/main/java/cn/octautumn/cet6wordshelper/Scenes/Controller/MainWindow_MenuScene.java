package cn.octautumn.cet6wordshelper.Scenes.Controller;

import cn.octautumn.cet6wordshelper.DictionaryClass.ChTrans;
import cn.octautumn.cet6wordshelper.DictionaryClass.DictEntry;
import cn.octautumn.cet6wordshelper.Scenes.SceneShowingController;
import com.fasterxml.jackson.databind.json.JsonMapper;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import static cn.octautumn.cet6wordshelper.MainApplication.*;
import static cn.octautumn.cet6wordshelper.MainApplication.mainDict;

public class MainWindow_MenuScene extends SceneShowingController
{
    public MainWindow_MenuScene(URL resource) throws Exception
    {
        super(resource);
    }

    @Override
    protected void beforeShow(Stage upStage) throws Exception
    {
        upStage.setOnCloseRequest(windowEvent -> {
            try
            {
                final String JsonFilePath = WorkingDir + fileSeparator + "CET6-Words.json"; //常量 词库文件路径
                final String FamiliarFilePath = WorkingDir + fileSeparator + "已掌握单词.txt"; //常量 词库文件路径
                final String notFamiliarFilePath = WorkingDir + fileSeparator + "未掌握单词.txt"; //常量 词库文件路径

                File OutputFile = new File(JsonFilePath);
                File OutputFile_Familiar = new File(FamiliarFilePath);
                File OutputFile_notFamiliar = new File(notFamiliarFilePath);

                if (!OutputFile.exists())
                {
                    if (!OutputFile.createNewFile())
                    {
                        System.out.println("Error. Failed to create output file. Program quit.");
                        return;
                    }
                }

                BufferedWriter outputWriter = new BufferedWriter(new FileWriter(OutputFile, StandardCharsets.UTF_8));

                JsonMapper mapper = new JsonMapper();

                String WordListJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mainDict);

                outputWriter.write(WordListJson);

                outputWriter.close();

                if (!OutputFile_Familiar.exists())
                {
                    if (!OutputFile_Familiar.createNewFile())
                    {
                        System.out.println("Error. Failed to create output file - OutputFile_Familiar. Program quit.");
                        return;
                    }
                }
                if (!OutputFile_notFamiliar.exists())
                {
                    if (!OutputFile_notFamiliar.createNewFile())
                    {
                        System.out.println("Error. Failed to create output file - OutputFile_notFamiliar. Program quit.");
                        return;
                    }
                }

                BufferedWriter outputWriter_Familiar = new BufferedWriter(new FileWriter(OutputFile_Familiar));
                BufferedWriter outputWriter_notFamiliar = new BufferedWriter(new FileWriter(OutputFile_notFamiliar));

                for (int i = 0; i < mainDict.getCount(); i++)
                {
                    DictEntry it = mainDict.getData().get(i);
                    switch (it.getFamiliar())
                    {
                        case familiar -> outputWriter_Familiar.write("WordID-" + i + "  " + it.getEnS() + '\n');
                        case notFamiliar, N_passInMode1 , N_passInMode2 -> {
                            StringBuilder strBuilder = new StringBuilder();
                            strBuilder.append(it.getEnS());
                            for (ChTrans itChT : it.getChS())
                            {
                                strBuilder.append("\n\t").append(itChT.getPos()).append(". ");
                                for (Iterator<String> itMean = itChT.getMean().iterator(); itMean.hasNext(); )
                                {
                                    strBuilder.append(itMean.next());
                                    if (itMean.hasNext())
                                        strBuilder.append(',');
                                    else
                                        strBuilder.append(';');
                                }
                            }
                            strBuilder.append('\n');
                            outputWriter_notFamiliar.write(strBuilder.toString());
                        }
                    }
                }

                outputWriter_Familiar.close();
                outputWriter_notFamiliar.close();

            } catch (Exception e)
            {
                e.printStackTrace();
            }

            System.exit(0);
        });
    }

    @Override
    protected void afterShow(Stage upStage) throws Exception
    {

    }
}
