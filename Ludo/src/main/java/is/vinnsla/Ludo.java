package is.vinnsla;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableBooleanValue;

import java.util.ArrayList;
import java.util.List;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *
 *  Lýsing  : Ludo er vinnsluklasi sem heldur utan um Ludo spilið. Heldur  utan um stöðu
 *  leiksins, leiðina, leikmenn, teninginn og  hver gerir næst
 *
 *****************************************************************************/
public class Ludo {

    private enum LeikStada {
        I_GANGI,    // leikur er í gangi, teningur er virkur
        LOKID,      // Leik er lokið, teningur óvirkur og nýr leikur virkur
        EKKI_HAFIN, // Forritið er nýræst og engin leikur hafin,
        // nýr leikur er virkur, engin peð sýnileg
        BIDUR_EFTIR_FYRSTA_KASTI,// Nýr leikur ræstur,  en á eftir að kasta í fyrsta sinn
        // nýr leikur óvirkur, teningur er virkur, engin peð sýnileg
        AREKSTUR // árekstur hefur orðið við annan leikmann
    }

    private final List<Reitur> leid = new ArrayList<>();
    private final Teningur teningur = new Teningur();

    // Harðkóðuð nöfn á leikmönnum, má lesa úr skrá
    private final Leikmadur[] leikmenn = {
            new Leikmadur("blár"),
            new Leikmadur("gulur")};

    private int naesti; // hver gerir næst

    // vaktaðar breytur og get aðferðir fyrir breyturnar

    // núverandi leikmaður
    private final SimpleObjectProperty<Leikmadur> leikmadur = new SimpleObjectProperty<>();

    /**
     * Leikstaða spilsins
     */
    private final ObjectProperty<LeikStada> leikstada =
            new SimpleObjectProperty<>(LeikStada.EKKI_HAFIN);


    private final BooleanBinding erEkkiHafin =
            leikstada.isEqualTo(LeikStada.EKKI_HAFIN);

    public BooleanBinding erEkkiHafin() {
        return erEkkiHafin;
    }

    public final BooleanBinding erLokid = leikstada.isEqualTo(LeikStada.LOKID);

    public BooleanBinding erLokid() {
        return erLokid;
    }

    public final BooleanBinding erIGangi = leikstada.isEqualTo(LeikStada.I_GANGI);

    public BooleanBinding erIGangi() {
        return erIGangi;
    }

    private final BooleanBinding erBidaEftirKasti = leikstada
            .isEqualTo(LeikStada.BIDUR_EFTIR_FYRSTA_KASTI);

    public ObservableBooleanValue erBidaEftirKasti() {
        return erBidaEftirKasti;
    }

    public BooleanBinding erStadaArekstur = leikstada.isEqualTo(LeikStada.AREKSTUR);

    public ObservableBooleanValue erStadaArekstur() {
        return erStadaArekstur;
    }

    BooleanBinding synilegPed = Bindings.createBooleanBinding(()
            -> erIGangi().get() || erLokid().get(), erIGangi(), erLokid());

    public ObservableBooleanValue erSynilegPed() {
        return synilegPed;
    }


    public ObjectProperty<Leikmadur> leikmadurProperty() {
        return leikmadur;
    }

    public Leikmadur getLeikmadur(int i) {
        return leikmenn[i];
    }

    public Reitur getReitur(Number n) {
        return leid.get(n.intValue());
    }

    /**
     * Smiðurinn setur upp leiðina, hardkóðað hér, má lesa inn
     */
    public Ludo() {
        // Setja upp leiðina
        for (int i = 5; i > 0; i--) {
            leid.add(new Reitur(i, 3,
                    (i == 5) ? Reitur.ReiturTegund.BYRJUN :
                            (i == 1) ? Reitur.ReiturTegund.MARK :
                                    Reitur.ReiturTegund.VENJULEGUR));
        }
    }

    // get aðferðir
    public List<Reitur> getLeid() {
        return leid;
    }

    public Teningur getTeningur() {
        return teningur;
    }

    /**
     * Leikur eina umferð
     **/
    public void leikaLeik() {
        if (erBidaEftirKasti().get() || erStadaArekstur().get()) { // kastað í fyrsta skipti
            leikstada.set(LeikStada.I_GANGI);
        }

        // kasta tening
        teningur.kasta();

        // færa leikmann samkvæmt tening
        if (faeraLeikmann()) {
            leikstada.setValue(LeikStada.LOKID);
            return;
        }

        // næsti leikmaður gerir
        setNaesti();
    }


    /**
     * Setur hver á að gera næst
     */
    private void setNaesti() {
        naesti = (naesti + 1) % leikmenn.length;
        leikmadur.set(leikmenn[naesti]);
    }

    /**
     * Færa leikmann sem nemur teningnum, nema ef leikmaður rekst á annan leikmann þá fer Ludó
     * í árekstrastöðu og leikmaður fer á byrjendareit
     *
     * @return skilar true ef leikmaður er kominn í mark, annars false
     */
    private boolean faeraLeikmann() {
        Leikmadur l = leikmadur.get();
        int last = leid.size() - 1;
        int vaenturReitur = l.vaenturReitur(teningur.talaProperty().get(), last);
        if (erArekstur(l, vaenturReitur)) {
            l.aByrjendareit(); // settur á byrjendareit
            leikstada.set(LeikStada.AREKSTUR);
            return false;
        }
        l.faera(vaenturReitur); // færa á reit
        return erKominnIMark(l); // athuga hvort er kominn í mark
    }

    /**
     * Skilar satt ef leikmarðu er kominn í mark
     *
     * @param l leikmaðurinn
     * @return true ef kominn á síðasta reit
     */
    private boolean erKominnIMark(Leikmadur l) {
        return l.getIndex() == leid.size() - 1;
    }

    /**
     * Skilar satt ef leikmaður l er í árekstri við annan leikmann á reit index
     *
     * @param l     leikmaður
     * @param index index á reitinn sem skoðaður er
     * @return true ef árekstur annars false
     */
    private boolean erArekstur(Leikmadur l, int index) {
        for (int i = 0; i < leikmenn.length; i++) {
            if (l != leikmenn[i] && leikmenn[i].getIndex() == index) {
                return true;
            }
        }
        return false;
    }

    /**
     * Nýr leikur hafinn. Leikmenn teknir af borði. Leikmmaður 0 byrjar
     */
    public void nyrLeikur() {
        leikstada.setValue(LeikStada.BIDUR_EFTIR_FYRSTA_KASTI);
        naesti = 0;
        leikmadur.set(leikmenn[naesti]);
        for (Leikmadur value : leikmenn) {
            value.setIndex(-1);
        }
    }
}
