package cn.octautumn.cet6wordshelper.Scenes.Controller;

import cn.octautumn.cet6wordshelper.OnRunning.RunReview;
import cn.octautumn.cet6wordshelper.Scenes.SceneShowingController;
import javafx.stage.Stage;

import java.net.URL;

public class ShowReviewWindow extends SceneShowingController
{

    public ShowReviewWindow(URL resource) throws Exception
    {
        super(resource);
    }

    @Override
    protected void beforeShow(Stage upStage) throws Exception
    {

    }

    @Override
    protected void afterShow(Stage upStage) throws Exception
    {
        new RunReview(getThisScene()).ShowReview();
    }
}
