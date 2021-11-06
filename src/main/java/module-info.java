module cn.octautumn.cet6wordshelper {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;

    opens cn.octautumn.cet6wordshelper to javafx.fxml;
    exports cn.octautumn.cet6wordshelper;
    exports cn.octautumn.cet6wordshelper.Scenes;
    exports cn.octautumn.cet6wordshelper.DictionaryClass;
    opens cn.octautumn.cet6wordshelper.Scenes to javafx.fxml;
    exports cn.octautumn.cet6wordshelper.Controllers;
    opens cn.octautumn.cet6wordshelper.Controllers to javafx.fxml;
    exports cn.octautumn.cet6wordshelper.Scenes.Controller;
    opens cn.octautumn.cet6wordshelper.Scenes.Controller to javafx.fxml;
    opens cn.octautumn.cet6wordshelper.OnRunning to javafx.base;
}