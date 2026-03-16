package is.vidmot.controller;

import is.vidmot.switcher.View;
import is.vidmot.switcher.ViewSwitcher;
import is.vinnsla.Atburdur;
import is.vinnsla.Ferd;
import is.vinnsla.Ferdaplan;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvananberg
 *  T-póstur:
 *  Lýsing  : Controller eða stýring fyrir notendaviðmótið
 *  Controller fyrir upphafsviðmót í Ferðaplani, verkefni 3, 2026
 *
 *
 *****************************************************************************/
public class AdalController {

    private static final String FERD_BAETT_VID = "Ferð %s bætt við ";
    private static final String FERD_EYTT = "Ferð %s eytt";
    private static final String FERD_SKODUD = "Ferð %s skoðuð";
    private static final String ENGIN_FERD_VALIN = "Veldu ferð til að skoða eða eyða";
    private static final String FERD_VALIN = "Ferð %s valin";
    public static final String SKRA_FANNST_EKKI = "Skrá fannst ekki ";
    public static final String FERDIR_TXT = "/ferdir.txt";

    @FXML
    private ListView<Ferd> fxListiFerdir;

    @FXML
    private Button fxEyda;

    @FXML
    private Button fxSkoda;

    @FXML
    private Label fxSkilabod;

    // vinnslan
    private Ferdaplan ferdaplan = new Ferdaplan();

    /**
     * Frumstillingaraðferð sem er keyrð eftir að búið er að hlaða inn .fxml skránni en áður
     * en viðmótstréð er birt
     */
    public void initialize() {
        try {
            ferdaplan.lesaLista(FERDIR_TXT);

        } catch (FileNotFoundException e) {
            fxSkilabod.setText(SKRA_FANNST_EKKI);
        }
        fxListiFerdir.setItems(ferdaplan.getFerdaListi());
        tengjaSkodaEydaHnappa();
        tengjaAtburdVidSkilabod();
        tengjaValinFerd();
    }

    /**
     * Hjálaraðferð sem hlustar á valda ferð og segir módelinu að ný ferð sé valin
     */
    private void tengjaValinFerd() {
        fxListiFerdir.getSelectionModel().selectedItemProperty().addListener((obs, gamla, nyja)
                -> ferdaplan.veljaFerd(nyja));
    }

    /**
     * Hjálparaðferð sem gerir fxEyda og fxSkoda óvirka ef ekkert er valið
     */
    private void tengjaSkodaEydaHnappa() {
        fxEyda.disableProperty().bind(
                fxListiFerdir.getSelectionModel().selectedItemProperty().isNull());
        fxSkoda.disableProperty().bind(
                fxListiFerdir.getSelectionModel().selectedItemProperty().isNull());
    }

    /**
     * Hjálparaðferð sem bindindur stöðu módelsins við textaskilaboð
     */
    private void tengjaAtburdVidSkilabod() {
        fxSkilabod.textProperty().bind(Bindings.createStringBinding(() -> // callable lambda fall
                {
                    if (ferdaplan.valinFerdProperty().get() == null) {
                        return String.format(ENGIN_FERD_VALIN);
                    }
                    return switch (ferdaplan.sidastiAtburdurProperty().get()) {
                        case Atburdur.NY_FERD -> String.format(FERD_BAETT_VID, ferdaplan.valinFerdProperty().get());
                        case Atburdur.EYDA -> String.format(FERD_EYTT, ferdaplan.valinFerdProperty().get());
                        case Atburdur.SKODA -> String.format(FERD_SKODUD, ferdaplan.valinFerdProperty().get());
                        case Atburdur.VELJA -> String.format(FERD_VALIN, ferdaplan.valinFerdProperty().get());
                    };
                }
                , ferdaplan.sidastiAtburdurProperty()
                , ferdaplan.valinFerdProperty())); // vaktaðar breytur
    }

    /**
     * Birtir dialog sem notandi getur bætt við ferð í og bætir henni við í módelið
     *
     * @param event
     */
    @FXML
    private void onBaetaVid(ActionEvent event) {
        Optional<Ferd> result = FerdDialogWrapper.birtaDialog(fxSkilabod.getScene().getWindow());
        if (!result.isEmpty()) {
            ferdaplan.nyFerd(result.get());
        }
    }

    /**
     * Birtir staðfestingadialog og eyðir ferðinni í módelinu ef notandi staðfestir
     *
     * @param event
     * @throws IOException ef ekki var hægt að lesa .fxml skrána sem geymir staðfestingardialog
     */
    @FXML
    private void onEyda(ActionEvent event) throws IOException {
        Ferd f = fxListiFerdir.getSelectionModel().getSelectedItem();
        if (f != null) {
            StadfestingEydaDialogController s = new StadfestingEydaDialogController();
            if (s.birta(f)) {
                ferdaplan.eydaFerd(f);
            }
        }
    }

    /**
     * Birtir ferðina í sama glugga
     *
     * @param event
     */
    @FXML
    private void onSkoda(ActionEvent event) {
        Ferd f = fxListiFerdir.getSelectionModel().getSelectedItem();
        ferdaplan.skodaFerd(f);
        if (f != null) {
            ViewSwitcher.switchTo(View.FERD, false, f);
        }
    }
}

