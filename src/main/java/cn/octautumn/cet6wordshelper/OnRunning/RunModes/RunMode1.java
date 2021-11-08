package cn.octautumn.cet6wordshelper.OnRunning.RunModes;

import cn.octautumn.cet6wordshelper.DictionaryClass.ChTrans;
import cn.octautumn.cet6wordshelper.DictionaryClass.DictEntry;
import cn.octautumn.cet6wordshelper.DictionaryClass.Familiar;
import cn.octautumn.cet6wordshelper.MainApplication;
import cn.octautumn.cet6wordshelper.OnRunning.RunMode;
import cn.octautumn.cet6wordshelper.OnRunning.Timer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class RunMode1 extends RunMode
{
    Scene upScene;  //该场景
    Label WordLabel;    //单词Label
    Label TipLabel;     //提示Label
    ArrayList<Button> Ans = new ArrayList<>();  //选项按钮（们）
    Button ExitButton;

    public RunMode1(Scene upScene)
    {
        this.upScene = upScene;
        this.timer = new Timer((ProgressBar) upScene.lookup("#TimerBar"), (Label) upScene.lookup("#TimerLabel"), this, 300);

        //初始化控件指针
        WordLabel = (Label) upScene.lookup("#WordLabel");
        TipLabel = (Label) upScene.lookup("#TipLabel");
        Ans.add((Button) ((GridPane) upScene.lookup("#AnswerSelections")).getChildren().get(0));
        Ans.add((Button) ((GridPane) upScene.lookup("#AnswerSelections")).getChildren().get(1));
        Ans.add((Button) ((GridPane) upScene.lookup("#AnswerSelections")).getChildren().get(2));
        Ans.add((Button) ((GridPane) upScene.lookup("#AnswerSelections")).getChildren().get(3));
        ExitButton = (Button) upScene.lookup("#ExitButton");
    }

    @Override
    public void run()
    {
        new Thread(this.timer).start();
        setDisableAllSelection(false);

        AtomicBoolean isCorrect = new AtomicBoolean(false);
        int errorCount = 0;
        int wordSum = MainApplication.mainDict.getCount();
        int selWordId = -1;
        String correctMeaning = "";

        for (int wordCount = 0; wordCount < 20; wordCount++)
        {

            int randID;
            ArrayList<DictEntry> selWord = new ArrayList<>();
            while (selWord.size() < 4)
            {
                randID = getRandom(0, wordSum);
                if (!MainApplication.mainDict.getData().get(randID).getFamiliar().equals(Familiar.familiar)
                        && !selWord.contains(MainApplication.mainDict.getData().get(randID)))
                {
                    if (selWord.isEmpty())
                    {
                        selWordId = randID;
                        selWord.add(MainApplication.mainDict.getData().get(randID));
                        setWordLabelText(MainApplication.mainDict.getData().get(randID).getEnS());
                    }
                    else
                    {
                        selWord.add(MainApplication.mainDict.getData().get(randID));
                    }
                }
            }

            setTipLabelText("请在下列选项中选出与该单词对应的译义： ");


            boolean[] isIn = {false, false, false, false};
            int buttonID = 0;
            while (buttonID < 4)
            {
                randID = getRandom(0, 3);
                if (isIn[randID])
                    continue;
                int wordId = randID;
                isIn[wordId] = true;
                int chTransId = getRandom(0, selWord.get(wordId).getChS().size() - 1);
                ChTrans chTrans = selWord.get(wordId).getChS().get(chTransId);
                int meanId = getRandom(0, chTrans.getMean().size() - 1);
                String mean = chTrans.getMean().get(meanId);
                if (randID == 0)
                {
                    System.out.println(buttonID);
                    correctMeaning = chTrans.getPos() + ". " + mean;
                    Platform.runLater(new SetAnswerRunnable(buttonID, this, chTrans.getPos() + ". " + mean, isCorrect, true));
                }
                else
                {
                    Platform.runLater(new SetAnswerRunnable(buttonID, this, chTrans.getPos() + ". " + mean, isCorrect, false));
                }

                buttonID++;
            }
            Platform.runLater(() -> Ans.get(0).requestFocus());

            RunningStatus = 1;
            synchronized (this)
            {
                try
                {
                    while (RunningStatus == 1)
                    {
                        this.wait();
                    }
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            if (RunningStatus == 2)
            {
                setWordLabelText("超时啦! 再接再厉吧. ");
                setTipLabelText("");
                cleanAllSelection();
                setDisableAllSelection(true);
                Platform.runLater(() -> ExitButton.requestFocus());
                return;
            }

            if (isCorrect.get())
            {//如果回答正确
                setTipLabelText("恭喜你，回答正确. 你已答对" + (wordCount - errorCount + 1) + "题 ", Color.FORESTGREEN);
                switch (MainApplication.mainDict.getData().get(selWordId).getFamiliar())
                {
                    case haveNotAppeared -> MainApplication.mainDict.getData().get(selWordId).setFamiliar(Familiar.passInMode1);
                    case notFamiliar -> MainApplication.mainDict.getData().get(selWordId).setFamiliar(Familiar.N_passInMode1);
                    case passInMode2, N_passInMode2 -> MainApplication.mainDict.getData().get(selWordId).setFamiliar(Familiar.familiar);
                }

                try
                {
                    cleanAllSelection();
                    synchronized (this)
                    {
                        wait(1000);
                    }
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {//如果回答错误
                errorCount++;
                setTipLabelText("对不起，回答错误. 你已答错" + errorCount + "题 \n" +
                        "正确答案是：" + correctMeaning + " ", Color.INDIANRED);
                switch (MainApplication.mainDict.getData().get(selWordId).getFamiliar())
                {
                    case haveNotAppeared, passInMode1, N_passInMode1 -> MainApplication.mainDict.getData().get(selWordId).setFamiliar(Familiar.notFamiliar);
                }

                cleanAllSelection();
                try
                {
                    if (errorCount == 2)
                    {
                        RunningStatus = 3;
                        cleanAllSelection();
                        setDisableAllSelection(true);
                        synchronized (this)
                        {
                            wait(5000);
                        }
                        setWordLabelText("错误太多啦! 再接再厉吧.");
                        setTipLabelText("");
                        Platform.runLater(() -> ExitButton.requestFocus());
                        return;
                    }
                    cleanAllSelection();
                    synchronized (this)
                    {
                        wait(5000);
                    }
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }

        RunningStatus = 4;
        setWordLabelText("太棒了，你一共答对了" + (20 - errorCount) + "题 ");
        setTipLabelText("");
        cleanAllSelection();
        setDisableAllSelection(true);
    }


    private void setWordLabelText(String word)
    {
        Platform.runLater(() -> WordLabel.setText(word));
    }

    private void setTipLabelText(String tip, Color color)
    {
        Platform.runLater(() -> {
            TipLabel.setTextFill(color);
            TipLabel.setText(tip);
        });
    }

    private void setTipLabelText(String tip)
    {
        Platform.runLater(() -> {
            TipLabel.setTextFill(Color.BLACK);
            TipLabel.setText(tip);
        });
    }

    private void setDisableAllSelection(Boolean disabled)
    {
        Ans.get(0).setDisable(disabled);
        Ans.get(1).setDisable(disabled);
        Ans.get(2).setDisable(disabled);
        Ans.get(3).setDisable(disabled);
    }

    private void cleanAllSelection()
    {
        Platform.runLater(() -> {
            Ans.get(0).setText("");
            Ans.get(1).setText("");
            Ans.get(2).setText("");
            Ans.get(3).setText("");
        });
    }

    private class SetAnswerRunnable implements Runnable
    {
        int ButtonID;
        final RunMode1 upClass;
        String text;
        AtomicBoolean flag;
        final Boolean isCA;

        public SetAnswerRunnable(int buttonID, RunMode1 upClass, String Text, AtomicBoolean flag, Boolean isCA)
        {
            this.ButtonID = buttonID;
            this.upClass = upClass;
            this.text = Text;
            this.flag = flag;
            this.isCA = isCA;
        }

        @Override
        public void run()
        {
            Ans.get(ButtonID).setText(text);
            Ans.get(ButtonID).setOnAction(ActionEvent -> {
                synchronized (upClass)
                {
                    flag.set(isCA);
                    RunningStatus = 0;
                    upClass.notify();
                }
            });
        }
    }
}
