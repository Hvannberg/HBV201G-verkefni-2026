package is.vidmot.view;

import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *
 *  Lýsing  : Custom component fyrir Ferð. Erfir frá GridPane. Inniheldur tilviksbreytur
 *  fyrir gögnin á spjaldinu.
 *
 *
 *****************************************************************************/
public class FerdSpjald extends GridPane {

    @FXML
    private TextField fxHeiti;

    @FXML
    private TextField fxDagsetning;

    @FXML
    private TextField fxAfangastadur;


    /**
     * Les inn .fxml skrá og setur controllerinn sem þessi hlutur
     */
    public FerdSpjald() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/is/vidmot/ferdSpjald-view.fxml"));
        fxmlLoader.setRoot(this);   // rótin á viðmótstrénu sett hér
        fxmlLoader.setController(this); // controllerinn settur hér en ekki í .fxml skránni
        try {
            fxmlLoader.load();          // viðmótstréð lesið inn (þ.e. .fxml skráin)
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        System.out.println("editable=" + fxHeiti.isEditable()
                + " disabled=" + fxHeiti.isDisabled()
                + " parentDisabled=" + fxHeiti.getParent().isDisabled());
    }

    public Property<String> heitiProperty() {
        return fxHeiti.textProperty();
    }

    public Property<String> afangastadurProperty() {
        return fxAfangastadur.textProperty();
    }

    public Property<String> dagsetningProperty() {
        return fxDagsetning.textProperty();
    }

}
