package cn.octautumn.cet6wordshelper.OnRunning;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class Timer implements Runnable
{
    final RunMode func;
    ProgressBar timerBar;
    Label timerLabel;

    public Timer(ProgressBar timerBar, Label timerLabel, RunMode func)
    {
        this.func = func;
        this.timerBar = timerBar;
        this.timerLabel = timerLabel;
    }

    @Override
    public void run()
    {

    }
}