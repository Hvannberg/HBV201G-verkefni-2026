package is.vidmot.controller;

import is.vinnsla.Ferd;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Optional;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *
 *  Lýsing  : 
 *
 *
 *****************************************************************************/
public class StadfestingEydaDialogController extends Dialog<ButtonType> {


    // tilviksbreytur

    @FXML
    private TextField fxHeiti;

    public StadfestingEydaDialogController () throws IOException {

        FXMLLoader fl = new FXMLLoader(StadfestingEydaDialogController.class.getResource("/is/vidmot/stadfestingEyda-view.fxml"));
        fl.setController (this);
        DialogPane p = fl.load ();
        setDialogPane (p);
    }
    boolean birta (Ferd f) {
        fxHeiti.textProperty().bind(f.heitiProperty());
        Optional<ButtonType> result = showAndWait();
        return !result.isEmpty() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE;
    }
}
