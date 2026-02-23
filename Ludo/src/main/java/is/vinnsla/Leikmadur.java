package is.vinnsla;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Random;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *
 *  Lýsing  : Leikmaður í leik. Geymir nafn og index á leið á borði
 *
 *
 *****************************************************************************/
public class Leikmadur {

    public static final int BYRJENDAREITUR = -1;

    private final SimpleStringProperty nafn = new SimpleStringProperty();
    private final SimpleIntegerProperty index =
            new SimpleIntegerProperty(BYRJENDAREITUR); // index er -1 í byrjun ef leikmaður er
                // ekki á borði en annars vísar hann í reit á borði (1 til og með lengd leiðar) - 1

    /**
     * Smiður sem tekur inn nafn leikmanns og setur tilviksbreytuna
     * @param nafn nafn leikmanns
     */
    public Leikmadur(String nafn) {
        this.nafn.set(nafn);
    }

    @Override
    /**
     * Endurskrifuð aðferð sem segir hvernig eigi að birta leikmann
     */
    public String toString() {
        return nafn.get();
    }

    public SimpleIntegerProperty indexProperty() {
        return index;
    }

    // get aðferðir
    public int getIndex() {
        return index.get();
    }

    public void setIndex(int i) {
        index.set(i);
    }


    /**
     * Skilar væntanlegum reit ef miðað er við að teningur sé i og max sé stærsti index á leið
     * Engin hliðarverkun
     * @param i  tala á teningi
     * @param max stærsti index á leið
     * @return væntur reitur
     */
    public int vaenturReitur(int i, int max) {
        int nytt = index.get() + i;
        return Math.min (nytt,max);
    }

    /**
     * Færir peð leikmanns í reit i
     *
     * @param i sem peð leikmanns færist í
     */
    public void faera(int i) {
        index.set(i);
    }
    /**
     * Peð leikmanns sett á byrjendareit
     */
    public void aByrjendareit() {
        index.set(BYRJENDAREITUR);   //byrjendareitur er fyrsti reiturinn
                                     // en ekki af borði, felum hvað er byrjendareitur
    }

    /**
     * Prófunaraktygi - tómt hér
     * @param args ónotað
     */
    public static void main(String[] args) {
        int max = 7;
        Leikmadur l = new Leikmadur("blár");
        for (int i = 1; i < 10; i++) {
            l.faera(i);
            System.out.println("leikmaður " + l + "reitur " + l.getIndex());
        }

        l = new Leikmadur("gulur");
        boolean iMark = false;
        Random rand = new Random();
        while (!iMark) {
            int i = rand.nextInt(6);
            l.faera(i);
            if (l.getIndex() >= max) {
                iMark = true;
            }
        }

    }
}
