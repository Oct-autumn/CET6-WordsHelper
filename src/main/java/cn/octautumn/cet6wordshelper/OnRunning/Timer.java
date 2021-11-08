package cn.octautumn.cet6wordshelper.OnRunning;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class Timer implements Runnable
{
    final RunMode func;
    int totalTime;

    ProgressBar timerBar;
    Label timerLabel;

    public Timer(ProgressBar timerBar, Label timerLabel, RunMode func, int totalTime)
    {
        this.func = func;
        this.timerBar = timerBar;
        this.timerLabel = timerLabel;
        this.totalTime = totalTime;
    }

    @Override
    public void run()
    {
        int countTime = totalTime;

        //初始化倒计时栏
        Platform.runLater(() -> {
            timerBar.setProgress(1.0);
            timerLabel.setText("剩余时间：" + String.format("%03ds", totalTime));
        });

        while (countTime != 0 && func.RunningStatus != 3 && func.RunningStatus != 4)
        {
            try
            {
                synchronized (this)
                {
                    wait(1000);
                }
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            countTime--;
            double tmp_cal = (double) countTime / totalTime;

            int finalCountTime = countTime;
            Platform.runLater(() -> {
                timerBar.setProgress(tmp_cal);
                timerLabel.setText("剩余时间：" + String.format("%03ds", finalCountTime));
            });

        }

        synchronized (func)
        {
            if (func.RunningStatus != 3 && func.RunningStatus != 4)
            {
                func.RunningStatus = 2;
                func.notify();
            }
        }
    }
}