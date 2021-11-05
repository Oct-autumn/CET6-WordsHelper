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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class RunMode2 extends RunMode
{
    Scene upScene;
    Label WordLabel;
    Label WordTipLabel;
    Label TipLabel;
    TextField Answer;
    Button ExitButton;


    public RunMode2(Scene upScene)
    {
        this.upScene = upScene;
        this.timer = new Timer((ProgressBar) upScene.lookup("#TimerBar"), (Label) upScene.lookup("#TimerLabel"), this, 600);

        //初始化控件指针
        WordLabel = (Label) upScene.lookup("#WordLabel");
        WordTipLabel = (Label) upScene.lookup("#WordTipLabel");
        TipLabel = (Label) upScene.lookup("#TipLabel");
        Answer = (TextField) upScene.lookup("#Answer");
        ExitButton = (Button) upScene.lookup("#ExitButton");
    }

    @Override
    public void run()
    {
        new Thread(this.timer).start();
        Platform.runLater(()->Answer.setDisable(false));

        AtomicBoolean isCorrect = new AtomicBoolean(false);
        int errorCount = 0;
        int wordSum = MainApplication.mainDict.getCount();
        int selWordId;

        for (int wordCount = 0; wordCount < 20; wordCount++)
        {
            int randID;
            DictEntry correctAnswer;
            do
            {
                randID = (int) (Math.random() * (wordSum));
                correctAnswer = MainApplication.mainDict.getData().get(randID);
            } while (correctAnswer.getFamiliar().equals(Familiar.familiar));
            selWordId = randID;

            String correctSpell = correctAnswer.getEnS();
            System.out.println(correctSpell);

            setTipLabelText("根据提示在下面拼写该单词： ");
            cleanAnswerText();

            int tipSize;   //智能提示，减少单词过短时提示过多[Doge]
            if (correctAnswer.getFamiliar().equals(Familiar.notFamiliar)
                    || correctAnswer.getFamiliar().equals(Familiar.N_passInMode1)
                    || correctSpell.length() < 3)
                tipSize = 0;
            else if (correctSpell.length() < 6)
                tipSize = 1;
            else
                tipSize = getRandom(1, 2);

            char[] tipWord = "*".repeat(correctSpell.length()).toCharArray();

            for (int i = 0; i < tipSize; i++)
            {
                while (true)
                {
                    randID = getRandom(0, correctSpell.length() - 1);
                    if (tipWord[randID] != '*')
                        continue;
                    tipWord[randID] = correctSpell.charAt(randID);
                    break;
                }
            }
            setWordLabelText(String.valueOf(tipWord));

            ArrayList<ChTrans> chTrans = correctAnswer.getChS();
            StringBuilder wordTipWord = new StringBuilder();
            for (ChTrans it : chTrans)
            {
                wordTipWord.append(it.getPos()).append(' ');
                ArrayList<String> mean = it.getMean();
                for (int i = 0; i < mean.size() - 2; i++)
                {
                    wordTipWord.append(mean.get(i)).append(',');
                }
                wordTipWord.append(mean.get(mean.size() - 1)).append(';');
                wordTipWord.append('\n');
            }
            setWordTipLabelText(wordTipWord.toString());

            Platform.runLater(new SetAnswerRunnable(this, isCorrect, correctSpell));


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
                cleanAnswerTextAndDisable();
                setWordTipLabelText("");
                setTipLabelText("");
                return;
            }

            if (isCorrect.get())
            {
                setTipLabelText("恭喜你，回答正确. 你已答对" + (wordCount - errorCount + 1) + "题 ");
                switch (MainApplication.mainDict.getData().get(selWordId).getFamiliar())
                {
                    case haveNotAppeared -> MainApplication.mainDict.getData().get(selWordId).setFamiliar(Familiar.passInMode2);
                    case notFamiliar -> MainApplication.mainDict.getData().get(selWordId).setFamiliar(Familiar.N_passInMode2);
                    case passInMode1, N_passInMode1 -> MainApplication.mainDict.getData().get(selWordId).setFamiliar(Familiar.familiar);
                }

                try
                {
                    cleanAnswerText();
                    Thread.sleep(1000);
                    cleanAnswerText();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                errorCount++;
                setTipLabelText("对不起，回答错误. 你已答错" + errorCount + "题 \n" +
                                "正确答案是：" + correctSpell + " ");
                switch (MainApplication.mainDict.getData().get(selWordId).getFamiliar())
                {
                    case haveNotAppeared, passInMode2, N_passInMode2 -> MainApplication.mainDict.getData().get(selWordId).setFamiliar(Familiar.notFamiliar);
                }

                try
                {
                    if (errorCount == 2)
                    {
                        RunningStatus = 3;
                        Thread.sleep(5000);
                        setWordLabelText("错误太多啦! 再接再厉吧.");
                        cleanAnswerTextAndDisable();
                        setWordTipLabelText("");
                        setTipLabelText("");
                        return;
                    }
                    Thread.sleep(5000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
        RunningStatus = 4;
        setWordLabelText("太棒了，你一共答对了" + (20 - errorCount) + "题 ");
        cleanAnswerTextAndDisable();
        setWordTipLabelText("");
        setTipLabelText("");
    }

    private void setWordLabelText(String word)
    {
        Platform.runLater(() -> WordLabel.setText(word));
    }

    private void setWordTipLabelText(String word)
    {
        Platform.runLater(() -> WordTipLabel.setText(word));
    }

    private void setTipLabelText(String tip)
    {
        Platform.runLater(() -> TipLabel.setText(tip));
    }

    private void cleanAnswerText()
    {
        Platform.runLater(() ->
        {
            Answer.setText("");
            Answer.requestFocus();
            Answer.setOnKeyPressed(KeyEvent ->
            {

            });
        });
    }

    private void cleanAnswerTextAndDisable()
    {
        Platform.runLater(() ->
        {
            Answer.setText("");
            Answer.setDisable(true);
        });
    }

    private class SetAnswerRunnable implements Runnable
    {
        final RunMode2 upClass;
        AtomicBoolean flag;
        final String CA;

        public SetAnswerRunnable(RunMode2 upClass, AtomicBoolean flag, String CA)
        {
            this.upClass = upClass;
            this.flag = flag;
            this.CA = CA;
        }

        @Override
        public void run()
        {
            Answer.setOnKeyReleased(KeyEvent ->
            {
                if (KeyEvent.getCode().getName().equals("Enter"))
                {
                    synchronized (upClass)
                    {
                        flag.set(Answer.getText().equalsIgnoreCase(CA));
                        RunningStatus = 0;
                        upClass.notify();
                    }
                }
            });
        }
    }
}