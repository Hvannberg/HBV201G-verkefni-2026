package is.vinnsla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Random;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *
 *  Lýsing  : Vinnsluklasi fyrir tening. Teningur geymir tölu og random generator
 *
 *
 *****************************************************************************/
public class Teningur {
    private static final int MAX = 6;
    private final IntegerProperty tala = new SimpleIntegerProperty(MAX);
    private final Random rand = new Random();

    /**
     * skilar tölu
     * @return heiltölu property fyrir tölu
     */
    public IntegerProperty talaProperty() {
        return tala;
    }

    /**
     * Kastar tening þannig að fundinn sé tala af handahófi á bilinu 1 til MAX+1
     */
    public void kasta() {
        tala.set(rand.nextInt(1, MAX + 1));
    }

    @Override
    public String toString() {
        return "Teningur{" +
                "talaProperty=" + tala +
                '}';
    }

    /**
     * prófunaraktygi
     * @param args
     */
    public static void main(String[] args) {
        Teningur t = new Teningur();
        t.kasta();
        System.out.println(t);
    }
}
