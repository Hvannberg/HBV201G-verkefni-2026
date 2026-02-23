package is.vinnsla;

import javafx.beans.property.SimpleObjectProperty;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *
 *  Lýsing  : Vinnsluklasi fyrir hvern reit á borði
 *
 *
 *****************************************************************************/
public class Reitur {

    private int rod; // röð á borði
    private int dalkur; // dálkur á borði
    private SimpleObjectProperty<ReiturTegund> tegund =
            new SimpleObjectProperty<>(); // til að merkja hvort reitur er
    // byrjunarreitur eða markreitur

    public enum ReiturTegund {
        BYRJUN, // byrjunarreitur
        VENJULEGUR, // hvorki byrjunar- né markreitur
        MARK // markreitur
    }

    /**
     * get aðferð fyrir tegund
     *
     * @return tegundarproperty
     */
    public SimpleObjectProperty<ReiturTegund> tegundProperty() {
        return tegund;
    }

    /**
     * Smiður sem býr til reit og setur röð og dálk
     *
     * @param rod    röðin
     * @param dalkur dálkurinn
     */
    public Reitur(int rod, int dalkur) {
        this.rod = rod;
        this.dalkur = dalkur;
    }

    /**
     * Smiður sem býr til reit og merkir hvort er byrjunar eða markreitur eða venjulegur
     *
     * @param i      röð
     * @param i1     dálkur
     * @param tegund byrjunar eða markreitur
     */
    public Reitur(int i, int i1, ReiturTegund tegund) {
        this(i, i1);
        this.tegund.set(tegund);
    }

// get aðferðir

    /**
     * skilar röð
     *
     * @return röð
     */
    public int getRod() {
        return rod;
    }

    /**
     * skilar dálki
     *
     * @return dálkur
     */
    public int getDalkur() {
        return dalkur;
    }
}



