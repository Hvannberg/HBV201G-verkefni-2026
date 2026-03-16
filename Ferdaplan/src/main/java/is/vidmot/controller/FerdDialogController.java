package is.vidmot.controller;

import is.vidmot.view.FerdSpjald;
import is.vinnsla.Ferd;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *
 *  Lýsing  : Controller klasi fyrir DialogPane fyrir að bæta við ferð
 *
 *
 *****************************************************************************/
public class FerdDialogController implements GognInterface<Ferd>{
    @FXML
    private FerdSpjald fxFerdSpjald; // viðmótshlutur, custsom component

    private final SimpleObjectProperty<Ferd> ferd = new SimpleObjectProperty<>(); // módelið

    /**
     * Útfærir aðferðina úr GognInterface. Setur ferðina og bindur viðmótshluti við módel hlutinn ferd.
     * @param f ferðin
     */
    @Override
    public void setGogn(Ferd f) {
        this.ferd.set(f);
        fxFerdSpjald.heitiProperty().bindBidirectional (ferd.get().heitiProperty());
        fxFerdSpjald.afangastadurProperty().bindBidirectional (ferd.get().afangastadurProperty());
        fxFerdSpjald.dagsetningProperty().bindBidirectional((ferd.get().dagsetningProperty()));
    }


    public SimpleObjectProperty<Ferd> getFerd() {
        return ferd;
    }

}
