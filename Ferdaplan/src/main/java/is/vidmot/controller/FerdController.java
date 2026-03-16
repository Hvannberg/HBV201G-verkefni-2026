package is.vidmot.controller;

import is.vidmot.switcher.View;
import is.vidmot.switcher.ViewSwitcher;
import is.vidmot.view.FerdSpjald;
import is.vinnsla.Ferd;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *
 *  Lýsing  : Controller fyrir að skoða Ferð í Ferðaplan, verkefni 3, 2026
 *
 *
 *****************************************************************************/
public class FerdController implements GognInterface<Ferd> {

    @FXML
    private FerdSpjald fxFerdSpjald;

    /**
     * Útfærir aðferðina og tengir vinnsluhlut f við viðmótshlut fxFerdSpjald
     *
     * @param f ferðin í vinnslumódelinu
     */
    public void setGogn(Ferd f) {
        fxFerdSpjald.heitiProperty().bind(f.heitiProperty());
        fxFerdSpjald.afangastadurProperty().bind(f.afangastadurProperty());
        fxFerdSpjald.dagsetningProperty().bind((f.dagsetningProperty()));
    }

    /**
     * Handler fyrir til baka hnappinn. Skiptir aftur yfir í ADAL viðmótstré en geymir stöðuna
     *
     * @param event
     */
    @FXML
    public void onTilBaka(ActionEvent event) {
        ViewSwitcher.switchTo(View.ADAL, true);
    }
}
