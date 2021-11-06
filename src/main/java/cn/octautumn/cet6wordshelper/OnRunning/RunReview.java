package cn.octautumn.cet6wordshelper.OnRunning;

import cn.octautumn.cet6wordshelper.DictionaryClass.ChTrans;
import cn.octautumn.cet6wordshelper.DictionaryClass.DictEntry;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static cn.octautumn.cet6wordshelper.MainApplication.*;

public class RunReview
{
    public class UnFamiliarWord
    {

        private IntegerProperty id;

        public void setId(int value) {idProperty().set(value);}

        public int getId() {return idProperty().get();}

        public IntegerProperty idProperty()
        {
            if (id == null) id = new SimpleIntegerProperty(this, "Id");
            return id;
        }

        private StringProperty word;

        public void setWord(String value) {wordProperty().set(value);}

        public String getWord() {return wordProperty().get();}

        public StringProperty wordProperty()
        {
            if (word == null) word = new SimpleStringProperty(this, "Word");
            return word;
        }

        public UnFamiliarWord(int wordID, String word)
        {
            setId(wordID);
            setWord(word);
        }
    }

    Scene upScene;  //该场景
    TextField SearchBox;
    TableView<UnFamiliarWord> WordTable;
    Label WordInfo;

    public RunReview(Scene upScene)
    {
        this.upScene = upScene;

        SearchBox = (TextField) upScene.lookup("#SearchBox");
        WordTable = (TableView) upScene.lookup("#WordTabel");
        WordInfo = (Label) upScene.lookup("#WordInfo");
    }


    public void ShowReview() throws Exception
    {
        WordTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("Id"));
        WordTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("Word"));

        List<UnFamiliarWord> unFamiliarWordList = new ArrayList<>();
        for (int i = 0; i < mainDict.getCount(); i++)
        {
            DictEntry it = mainDict.getData().get(i);
            switch (it.getFamiliar())
            {
                case notFamiliar, N_passInMode1, N_passInMode2 -> unFamiliarWordList.add(new UnFamiliarWord(i, it.getEnS()));
            }
        }
        Platform.runLater(() -> WordTable.setItems(FXCollections.observableList(unFamiliarWordList)));

        WordTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        WordTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->
                {
                    if (WordTable.getSelectionModel().getSelectedIndex() == -1)
                        WordInfo.setText("<在左侧选择不熟悉的单词查看>");
                    else
                        WordInfo.setText(getFurtherWordInfo(WordTable.getItems().get(WordTable.getSelectionModel().getSelectedIndex()).id.get()));
                });

        SearchBox.setOnKeyReleased(ActionEvent ->
        {
            List<UnFamiliarWord> searchRes = new ArrayList<>();
            for (UnFamiliarWord it : unFamiliarWordList)
            {
                if (it.word.get().startsWith(SearchBox.getText()))
                    searchRes.add(it);
            }
            WordTable.setItems(FXCollections.observableList(searchRes));
        });
    }

    private String getFurtherWordInfo(int wordID)
    {
        DictEntry it = mainDict.getData().get(wordID);

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

        return strBuilder.toString();
    }
}
