package cn.octautumn.cet6wordshelper;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class DebugEventHandler implements EventHandler<ActionEvent>
{
    @Override
    public void handle(ActionEvent actionEvent)
    {
        System.out.println("Clicked.");
    }
}
