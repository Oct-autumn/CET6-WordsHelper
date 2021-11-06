package cn.octautumn.cet6wordshelper.Controllers;

import cn.octautumn.cet6wordshelper.MainApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.*;

import java.io.IOException;

public class MenuController
{

    public VBox rootBox;
    public Button Mode1Button;
    public Button Mode2Button;
    public Button WordsReviewButton;

    public void Quit(ActionEvent actionEvent)
    {
        Event.fireEvent(rootBox.getScene().getWindow(), new WindowEvent(rootBox.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void ShowAboutDialog(ActionEvent actionEvent)
    {
        Stage aboutWindow = new Stage(StageStyle.DECORATED);
        aboutWindow.initOwner(rootBox.getScene().getWindow());
        aboutWindow.initModality(Modality.WINDOW_MODAL);
        aboutWindow.setTitle("关于");
        try
        {
            aboutWindow.setScene(new Scene(new FXMLLoader(MainApplication.class.getResource("About-view.fxml")).load()));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        aboutWindow.sizeToScene();
        aboutWindow.setResizable(false);
        aboutWindow.showAndWait();
    }

    public void ShowGPL(ActionEvent actionEvent)
    {
        Stage GPLWindow = new Stage(StageStyle.DECORATED);
        GPLWindow.initOwner(rootBox.getScene().getWindow());
        GPLWindow.initModality(Modality.WINDOW_MODAL);
        GPLWindow.setTitle("GPL证书");
        try
        {
            GPLWindow.setScene(new Scene(new FXMLLoader(MainApplication.class.getResource("GPL-view.fxml")).load()));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        GPLWindow.sizeToScene();
        Platform.runLater(()->
        {
            ((Label)GPLWindow.getScene().lookup("#GPLInfo")).setText("""
                            English WordsHelper
                            Copyright (C) 2021  Oct_Autumn

                            This program is free software: you can redistribute it and/or modify
                            it under the terms of the GNU General Public License as published by
                            the Free Software Foundation, either version 3 of the License, or
                            (at your option) any later version.

                            This program is distributed in the hope that it will be useful,
                            but WITHOUT ANY WARRANTY; without even the implied warranty of
                            MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
                            GNU General Public License for more details.

                            You should have received a copy of the GNU General Public License
                            along with this program. If not, see <https://www.gnu.org/licenses/>.""");
        });
        GPLWindow.setResizable(false);
        GPLWindow.showAndWait();
    }

    public void onMode1ButtonClicked(ActionEvent actionEvent)
    {
        try
        {
            MainApplication.mainWindowController.showScene("Mode1");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void onMode2ButtonClicked(ActionEvent actionEvent)
    {
        try
        {
            MainApplication.mainWindowController.showScene("Mode2");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void onWordsReviewButtonClicked(ActionEvent actionEvent)
    {
        try
        {
            MainApplication.mainWindowController.showScene("Review");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
