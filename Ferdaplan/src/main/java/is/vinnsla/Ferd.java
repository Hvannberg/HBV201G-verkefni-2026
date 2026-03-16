package is.vinnsla;

import javafx.beans.property.SimpleStringProperty;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *  Lýsing  : Vinnsluklasi fyrir ferð sem hefur eiginleikana heiti, áfangastað
 *  og dagsetningu
 *
 *****************************************************************************/
public class Ferd {
    private SimpleStringProperty heiti = new SimpleStringProperty();
    private SimpleStringProperty afangastadur = new SimpleStringProperty();
    private SimpleStringProperty dagsetning = new SimpleStringProperty();

    // get aðferðir
    public SimpleStringProperty heitiProperty() {
        return heiti;
    }
    public SimpleStringProperty afangastadurProperty() {
        return afangastadur;
    }
    public SimpleStringProperty dagsetningProperty() {
        return dagsetning;
    }

    /**
     * Smíðar ferðina sem lýst er í strengnum ferd með eiginleikum aðgreind með ;
     * @param ferd
     */
    public Ferd(String ferd) {
        String[] gogn = ferd.split(";");
        this.heiti.set(gogn[0]);
        this.afangastadur.set(gogn[1]);
        this.dagsetning.set(gogn[2]);
    }

    public Ferd () {

    }

    /**
     * skilar hvernig ferð birtist í lista
     * @return
     */
    @Override
    public String toString() {
        return  heiti.get();
    }
}
