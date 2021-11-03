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
    exports cn.octautumn.cet6wordshelper.ShowWindow;
    opens cn.octautumn.cet6wordshelper.ShowWindow to javafx.fxml;
    exports cn.octautumn.cet6wordshelper.Controllers;
    opens cn.octautumn.cet6wordshelper.Controllers to javafx.fxml;
}