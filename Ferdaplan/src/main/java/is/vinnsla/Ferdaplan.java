package is.vinnsla;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *
 *  Lýsing  : Vinnsluklasi fyrir Ferðaplan. Inniheldur lista af ferðum, valda ferð og
 *  síðasta atburð og
 *
 *
 *****************************************************************************/
public class Ferdaplan {

    private final ObservableList<Ferd> ferdaListi = FXCollections.observableArrayList();

    private SimpleObjectProperty<Ferd> valinFerd = new SimpleObjectProperty<>();

    private SimpleObjectProperty<Atburdur> sidastiAtburdur = new SimpleObjectProperty<>(Atburdur.SKODA);

    /**
     * Lesa listann af ferðum úr skránni s
     *
     * @param s nafn á skrá
     * @throws FileNotFoundException
     */
    public void lesaLista(String s) throws FileNotFoundException {
        InputStream is = getClass().getResourceAsStream(s);
        if (is != null) {
            Scanner scanner = new Scanner(is, StandardCharsets.UTF_8);
            while (scanner.hasNextLine()) {
                String ferd = scanner.nextLine();
                Ferd f = new Ferd(ferd);
                ferdaListi.add(f);
            }
        }
    }

    public ObservableList<Ferd> getFerdaListi() {
        return ferdaListi;
    }

    /**
     * Skoða ferð f og skrá það sem síðasta atburð
     *
     * @param f ferðin sem er skoðuð
     */
    public void skodaFerd(Ferd f) {
        valinFerd.set(f);
        sidastiAtburdur.set(Atburdur.SKODA);
    }

    /**
     * Búa til nýja ferð f
     *
     * @param f ferðin
     */
    public void nyFerd(Ferd f) {
        ferdaListi.add(f);
        sidastiAtburdur.set(Atburdur.NY_FERD);
        valinFerd.set(f);
    }

    /**
     * Eyða ferð f
     *
     * @param f ferðin
     */
    public void eydaFerd(Ferd f) {
        ferdaListi.remove(f);
        sidastiAtburdur.set(Atburdur.EYDA);
        valinFerd.set(f);
    }

    /**
     * Velja ferð f
     *
     * @param f ferðin
     */
    public void veljaFerd(Ferd f) {
        sidastiAtburdur.set(Atburdur.VELJA);
        valinFerd.set(f);
    }

// get aðferðir

    public ObjectProperty valinFerdProperty() {
        return valinFerd;
    }

    public SimpleObjectProperty<Atburdur> sidastiAtburdurProperty() {
        return sidastiAtburdur;
    }
}
