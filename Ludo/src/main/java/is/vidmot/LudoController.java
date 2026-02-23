package is.vidmot;

import is.vinnsla.Ludo;
import is.vinnsla.Reitur;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *  Lýsing  : Controller eða stýring fyrir notendaviðmótið
 *  Inniheldur handlera fyrir notendaviðmót, býr til leið í notendaviðmóti og
 *  setur upp bindingar og listenera við vinnsluna  *
 *
 *****************************************************************************/
public class LudoController {
    // fastar
    private static final String[] MYNDIR_STYLECLASS
            = {"one", "two", "three", "four", "five", "six"};
    private static final String[] LEIKMADUR_STYLECLASS = {"leikmadur0", "leikmadur1"};

    private static final String NAESTI_GERIR = "Leikmaður ";
    private static final String GERIR = " gerir";
    private static final String VANN = " vann";

    private static final Map<Reitur.ReiturTegund, String> REITIR_STYLECLASS = Map.of(
            Reitur.ReiturTegund.BYRJUN, "reitur-byrjun",
            Reitur.ReiturTegund.MARK, "reitur-mark",
            Reitur.ReiturTegund.VENJULEGUR, "reitur-venjulegur");


    // tilviksbreytur í notendaviðmóti
    @FXML
    private GridPane fxBord;
    @FXML
    private Button fxTeningur;
    @FXML
    private Button fxNyrLeikur;
    @FXML
    private Label fxSkilabodHverGerir;
    @FXML
    private Label fxLeidbeiningar;

    // aðrar tilviksbreytu
    private final Map<Reitur, StackPane> vidmotLeid
            = new HashMap<>(); //hakkatafla á milli reita (módelið) og reita í viðmóti
    // vinnslan
    private final Ludo ludo = new Ludo(); // tenging við módelið (vinnsluna)


    /**
     * Frumstillir notendaviðmót eftir að viðmótstré hefur verið lesið inn.
     * Býr til leið á lúdóborði og setur upp tengingar (bindingar eða listenera)
     * ið módelið (vinnsluna)
     *
     * @throws IOException
     */
    public void initialize() throws IOException {
        // búa til leiðina á lúdó borðinu með réttum styleclasses
        buaTilLeid();

        // binda teningamyndirnar við teninginn
        tengjaTeningamyndir();

        // bindur reitina á borðinu við reitinn sem leikmaður er á
        tengjaReitiVidLeikmenn();

        // binda hnappana við ástandið á leiknum
        tengjaHnappaVidAstandLeiks();

        // bindur skilaboðin um hver á að gera og hver er sigurvegari við gögn úr vinnslunni v
        tengjaSkilabodVidSHverGerir();

        // bindur leiðbeiningarnar hvort teningur eða nýr leikur eru virkir
        tengjaLeidbeiningarVidStodu();

    }


    /**
     * Fyrir hvern leikmann, uppfærir mynd á viðmótsreiit í samræmi við nýjan reit leikmanns sem
     * er vaktaður í gegnum index leikmanns
     */
    private void tengjaReitiVidLeikmenn() {
        for (int i = 0; i < 2; i++) {
            final int idx = i; // "frystum" gildið fyrir lömbduna

            ludo.getLeikmadur(idx).indexProperty()
                    .addListener((obs, gamlaGildi, nyttGildi)
                            -> {
                        // fjarlægja styleclass fyrir gamla reitinn
                        if (gamlaGildi != null // gætum þess að gamla gildi sé löglegt
                                && gamlaGildi.intValue() >= 0) {
                            vidmotLeid.get(ludo.getReitur(gamlaGildi))
                                    .getStyleClass()
                                    .remove(LEIKMADUR_STYLECLASS[idx]);
                        }
                        if (ludo.erSynilegPed().get() // aðeins ef peð eiga að vera sýnileg
                                && (nyttGildi != null)
                                && nyttGildi.intValue() >= 0) { // gætum þess að nyttGildi sé löglegt
                            vidmotLeid.get(ludo.getReitur(nyttGildi))
                                    .getStyleClass()
                                    .add(LEIKMADUR_STYLECLASS[idx]);
                        }
                    });
        }
    }

    /**
     * Birtir skilaboð um hver gerir, hver vann og hvort leikmaður sé í árekstri og þá við hvern
     */
    private void tengjaSkilabodVidSHverGerir() {
        fxSkilabodHverGerir.textProperty().bind(
                Bindings.createStringBinding(() -> {
                            if (ludo.erIGangi().get() || ludo.erBidaEftirKasti().get()) {
                                return NAESTI_GERIR + ludo.leikmadurProperty().get() + GERIR;
                            } else if (ludo.erLokid().get()) {
                                return NAESTI_GERIR + ludo.leikmadurProperty().get() + VANN;
                            } else if (ludo.erStadaArekstur().get()) {
                                return "Árekstur við " + ludo.leikmadurProperty().get();
                            } else {
                                return "";
                            }
                        },
                        ludo.erLokid(),
                        ludo.erIGangi(),
                        ludo.erBidaEftirKasti(),
                        ludo.erStadaArekstur(),
                        ludo.leikmadurProperty())
        );
    }

    /**
     * Birtir viðeigandi leiðbeiningar í reit í notendaviðmóti. Annað hvort að bjóða nemanda að kasta eða hefja nýjan leik
     * Ekki hægt að hefja nýjan leik nema leik sé lokið
     */
    private void tengjaLeidbeiningarVidStodu() {
        fxLeidbeiningar.textProperty().bind(
                Bindings.createStringBinding(() -> {
                            if (!fxTeningur.isDisabled()) {
                                return "Ýttu á teninginn til að kasta";
                            } else if (!fxNyrLeikur.isDisabled()) {
                                return "Ýttu á Nýr leikur til að hefja leik";
                            } else {
                                return "";
                            }
                        },
                        fxTeningur.disabledProperty(),
                        fxNyrLeikur.disabledProperty())
        );
    }

    /**
     * Ef leikur er í gangi er nýr leikur óvirkur
     * Ef leikur er í gangi er teningur virkur
     */
    private void tengjaHnappaVidAstandLeiks() {
        // afvirkja nýr leikur ef leikur er í gangi
        fxNyrLeikur.disableProperty().bind(ludo.erIGangi()
                .or(ludo.erBidaEftirKasti()).or(ludo.erStadaArekstur()));
        // afvirkja teninginn ef leikur er ekki í gangi
        fxTeningur.disableProperty().bind(
                ludo.erLokid().or(ludo.erEkkiHafin()));
    }

    /**
     * Fjarlægir stílklasann fyrir gamla gildi teningsins
     * Bætir við stílklasanum fyrir nýja gildi teningsins
     */
    private void tengjaTeningamyndir() {
        // bindur myndir af teningunum við teninginn í gegnum stíla

        ludo.getTeningur().talaProperty().addListener((obs, gamlaGildi, nyttGildi) -> {
            fxTeningur.getStyleClass().remove(MYNDIR_STYLECLASS[gamlaGildi.intValue() - 1]);
            fxTeningur.getStyleClass().add(MYNDIR_STYLECLASS[nyttGildi.intValue() - 1]);
        });
    }

    /**
     * Leiðin er fengin úr vinnslunni (módelinu). Viðmótsreitir (s) eru búnir til fyrir
     * hvern reit (r) og settur á borðið í viðmótinu.
     * (r, s) er bætt í HashMap vidmotLeid
     *
     * @throws IOException
     */
    private void buaTilLeid() throws IOException {
        // ná í leiðina List<Reitur> úr vinnslunni
        List<Reitur> reitir = ludo.getLeid();
        // fyrir hvern reit, r,  á leiðinni
        for (Reitur r : reitir) {
            // búa til viðmótsreit, s
            StackPane sella = nySella();
            // setja styleClass samkvæmt tegund
            setStyleClass(r, sella);
            // bæta viðmótsreitnum s, á borðið í dálk og röð sem er skilgreint í r
            fxBord.add(sella, r.getDalkur(), r.getRod());
            // bæta við (r, s) á HashMap-ið
            vidmotLeid.put(r, sella);
        }
    }

    /**
     * Setur réttan styleClass á sella samkvæmt reitnum r
     *
     * @param r     reiturinn í vinnslunni
     * @param sella í viðmótinu
     */
    private void setStyleClass(Reitur r, StackPane sella) {
        String s = REITIR_STYLECLASS.get(r.tegundProperty().get());
        if (s != null) {
            sella.getStyleClass().add(s);
        }
    }

    /**
     * Les inn viðmótshluti á leið úr .fxml skrá
     *
     * @return viðmótstréð
     * @throws IOException
     */
    private StackPane nySella() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LudoApp.class.getResource("reitur-view.fxml"));
        return fxmlLoader.load();
    }

    /**
     * Handler fyrir að ýta á teninginn. Vinnslan sér um að uppfæra módelið
     *
     * @param event ónotað
     */
    @FXML
    void onLeikaLeik(ActionEvent event) {
        ludo.leikaLeik();
    }


    /**
     * Handler fyrir að hefja nýjan leik. Vinnslan sér um að uppfæra módelið
     *
     * @param event ónotað
     */
    @FXML
    void onNyrLeikur(ActionEvent event) {
        ludo.nyrLeikur();
    }
}
