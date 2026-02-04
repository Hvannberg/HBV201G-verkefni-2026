package is.vidmot;

import is.vinnsla.KmgjaldVinnsla;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *  Lýsing  : Controller eða stýring fyrir notendaviðmótið
 *  Upphafskóðinn var búinn til í SceneBuilder með View / Show Sample Controller Skeleteon
 *
 *  Verkefni 1 - 2025
 *****************************************************************************/


public class KmgjaldController {

    /**
     * Fastar í klasanum, dæmigert static of final
     */
    private static final String RANGT = "rangt-inntak"; // styleClass
    private static final String NEIKVAED_KM = "Neikvæð km staða";
    private static final String NEIKVAED_TALA = "Neikvæð tala";

    @FXML
    private TextField fxFlokkur;

    @FXML
    private TextField fxLok;

    @FXML
    private TextField fxUpphaf;

    @FXML
    private Label fxFjManada;

    @FXML
    private Label fxGjald;

    @FXML
    private Label fxHeildGjald;

    @FXML
    private Label fxHeildKm;

    @FXML
    private Label fxManGjald;

    @FXML
    private Label fxManKm;

    @FXML
    private Label fxVillubodKm;

    @FXML
    private Label fxVillubodUpphaf;


    // hlutur af vinnsluklasa
    private final KmgjaldVinnsla kmgjaldVinnsla = new KmgjaldVinnsla();

    /**
     * Frumstillir notendaviðmótið eftir að búið er að lesa það inn en áður
     * en það er birt. Setur gjald á /km fyrir sjálfgefna flokkinn
     */
    public void initialize() {
        fxGjald.setText(kmgjaldVinnsla.getGjaldKilometra(fxFlokkur.getText()) + "");
    }

    /**
     * handler fyrir að uppfæra flokk og birta gjald per km
     * ef flokkur er ólöglegur birtist rauður rammi utan um sviðið
     * rauði ramminn hverfur við upphaf næsta atburðar á viðmótshlutinn
     *
     * @param event ónotað
     */
    @FXML
    @Override
    public void onFlokkur(ActionEvent event) {
        fjarlaegjaRangt(fxFlokkur); // fjarlægja ranga styleClass
        // Hér sér vinnslan um að ákveða hvort flokkurinn er löglegur
        if (kmgjaldVinnsla.erLoglegt(fxFlokkur.getText())) {
            double gjald = kmgjaldVinnsla.getGjaldKilometra(fxFlokkur.getText());
            fxGjald.setText(gjald + "");
            uppfaeraKmOgGjald();
        }
        else {
            setRangurStill(fxFlokkur);
        }
    }

    /**
     * handler fyrir að skrá upphafsstöðu, eknir km staða uppfærist
     *
     * @param event
     */
    @FXML
    @Override
    public void onUpphaf(ActionEvent event) {
        kmStada(fxUpphaf, fxVillubodUpphaf);
    }


    /**
     * handler fyrir að skrá lokastöðu, eknir km staða uppfærist
     * Villuboð birt ef inntak er neikvætt eða km staða neikvæð
     * Reitur breytist í rautt ef inntak er texti eða ólögleg tala
     *
     * @param event
     */
    @FXML
    @Override
    public void onLoka(ActionEvent event) {
        kmStada(fxLok, fxVillubodKm);
    }

    /**
     * Skoðar inntak fyrir kílómetrastöðu. Ef löglegt þá er km og gjald fyrir mánuðinn uppfært
     * Ef inntakið er ólöglegt þá er inntakshlutur gerður rauðuru
     * Ef km staða verður neikvæð þá birtist villuboð
     *
     * @param text
     * @param villubod
     */
    private void kmStada(TextInputControl text, Label villubod) {
        fjarlaegjaRangt(text);
        fjarlaegjaVillubod(villubod);
        try {
            if (!erNeikvaed(text)) {
                uppfaeraKmOgGjald();
            }
            else {
                villubod.setText(NEIKVAED_TALA); // neikvæð tala sett inn
                hreinsaKmOgGjald();
            }
        }
        catch (NumberFormatException e) { // rangt snið á inntaki
            setRangurStill(text);
        }
    }

    /**
     * Handler fyrir að hreinsa upphafs, lokakm og gjald
     *
     * @param event
     */
    @FXML
    @Override
    public void onHreinsa(ActionEvent event) {
        hreinsa();
    }

    /**
     * Handler fyrir að skrá km stöðu og bæta við heildarkm og
     * heildar greiðslu og bætir við fjölda mánaða
     * birtir niðurstöðurnar
     *
     * @param event
     */
    @FXML
    @Override
    public void onSkraKmStodu(ActionEvent event) {
        kmgjaldVinnsla.skraManud(Integer.parseInt(fxUpphaf.getText()),
                Integer.parseInt(fxLok.getText()),
                fxFlokkur.getText());
        fxHeildGjald.setText(kmgjaldVinnsla.getHeildarGreidsla() + "");
        fxHeildKm.setText(kmgjaldVinnsla.getHeildEknirKm() + "");
        fxFjManada.setText(kmgjaldVinnsla.getFjoldiManada() + "");
    }


    /**
     * Skilar true ef tala er neikvæð
     *
     * @param c talan sem á að athuga
     * @return true ef neikvæð annars false
     */
    private boolean erNeikvaed(TextInputControl c) {
        return (Integer.parseInt(c.getText()) < 0);
    }


    /**
     * uppfærir kílómetrastöðu og gjald fyrir mánuðinn nema kílómetrastaða sé neikvæð, þá er
     * birt villuboð
     */
    private void uppfaeraKmOgGjald() {
        int manKm = Integer.parseInt(fxLok.getText()) - Integer.parseInt(fxUpphaf.getText());
        if (manKm >= 0) {
            fxManKm.setText(manKm + " ");
            fxManGjald.setText(kmgjaldVinnsla.reiknaManGjald(manKm, fxFlokkur.getText()) + "");
        }
        else {
            fxVillubodKm.setText(NEIKVAED_KM); // neikvæð kílómetra staða
            hreinsaKmOgGjald();
        }
    }

    /**
     * Hreinsa svið fyrir mánaðar km og kílómetragjald
     */
    private void hreinsaKmOgGjald() {
        fxManGjald.setText("0");
        fxManKm.setText("0");
    }

    /**
     * Hreinsar svið fyrir upphaf og loka km og uppfærir mánaðar km og gjald
     */
    private void hreinsa() {
        fxLok.setText("0");
        fxUpphaf.setText("0");
        uppfaeraKmOgGjald();
    }

    /**
     * Setur ramma utan um inntakssvið rautt
     *
     * @param c inntakssviðið
     */
    private void setRangurStill(Control c) {
        if (!c.getStyleClass().contains(RANGT)) {
            c.getStyleClass().add(RANGT);
        }
    }

    /**
     * Fjarlægir rauðan ramma af viðmótshlut c
     *
     * @param c viðmótshlutur sem breyta á útliti
     */
    private void fjarlaegjaRangt(Control c) {
        c.getStyleClass().remove(RANGT);
    }

    /**
     * Fjarlæagja villuboð úr Label
     *
     * @param villubod villuboðið sem á að hreinsa
     */
    private void fjarlaegjaVillubod(Label villubod) {
        villubod.setText("");
    }
}


