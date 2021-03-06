package cn.octautumn.cet6wordshelper.DictionaryClass;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import static cn.octautumn.cet6wordshelper.MainApplication.familiarWord;
import static cn.octautumn.cet6wordshelper.MainApplication.mainDict;

public class DictOperation
{
    public static void ConstructDictionary(JsonNode input)
    {
        //构造词库数据结构
        int wordSum = input.get("count").asInt();
        mainDict.setVerify(input.get("verify").asText());
        mainDict.setCount(wordSum);
        int wordKey = 0;

        for (Iterator<JsonNode> it = input.get("data").iterator(); it.hasNext(); wordKey++)
        {
            JsonNode NowEntry = it.next();
            DictEntry newEntry = new DictEntry();

            newEntry.setFamiliar(Familiar.valueOf(NowEntry.get("familiar").asText("haveNotAppeared")))
                    .setEnS(NowEntry.get("enS").asText());

            if (newEntry.getFamiliar().equals(Familiar.familiar))
                familiarWord++;

            for (JsonNode NowChTrans : NowEntry.get("chS"))
            {
                ChTrans newChTrans = new ChTrans();

                newChTrans.setId(NowChTrans.get("id").asInt())
                        .setPos(NowChTrans.get("pos").asText());

                for (JsonNode NowMean : NowChTrans.get("mean"))
                {
                    newChTrans.addMean(NowMean.asText());
                }

                newEntry.addChS(newChTrans);
            }

            mainDict.addEntry(wordKey, newEntry);
        }

        try
        {
            Thread.sleep(1000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public static JsonNode readAndVerifyDictJson(InputStream input) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode WordListJson = mapper.readTree(input);
        if (WordListJson.has("verify"))
            if (WordListJson.get("verify").asText().equals("T2N0QXV0dW1u"))
                return WordListJson;
        return null;
    }
}
