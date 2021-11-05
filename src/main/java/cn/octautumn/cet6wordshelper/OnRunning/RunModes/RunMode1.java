package cn.octautumn.cet6wordshelper.OnRunning.RunModes;

import cn.octautumn.cet6wordshelper.OnRunning.RunMode;
import cn.octautumn.cet6wordshelper.OnRunning.Timer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class RunMode1 extends RunMode
{
    Scene upScene;
    Label WordLabel;
    Label TipLabel;
    public Button Ans1;
    public Button Ans2;
    public Button Ans3;
    public Button Ans4;

    public RunMode1(Scene upScene)
    {
        this.upScene = upScene;
        this.timer = new Timer((ProgressBar) upScene.lookup("#TimerBar"), (Label) upScene.lookup("#TimerLabel"), this);
    }

    @Override
    public void run()
    {

    }
}
