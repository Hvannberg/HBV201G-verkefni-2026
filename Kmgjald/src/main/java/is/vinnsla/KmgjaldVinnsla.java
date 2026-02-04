package is.vinnsla;

import java.util.Scanner;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *  Lýsing  : Er vinnsluklasi fyrir kílómetragjaldsappið. Gerir ekkert í notendaviðmótinu.
 *  Skilur aðgerðir eins og að reikna út ekna kílómetra, reikna upphæð og skrá mánuð.
 *
 *  Verkefni 2 - 2026
 *****************************************************************************/
public class KmgjaldVinnsla  {
    private static final double[] GJALD_KILOMETRA = {6.95, 4.15};

    private int heildEknirKm;
    private int heildarGreidsla;
    private int fjoldiManada;

    // get og set aðferðir

    /**
     * skilar heildar eknum km
     * @return heildEknirKm
     */

    public int getHeildEknirKm() {
        return heildEknirKm;
    }

    /**
     * skilger heildargreiðslu kmgjalds
     * @return heildarGreidsla
     */

    public int getHeildarGreidsla() {
        return heildarGreidsla;
    }

    /**
     * skilar fjöldi mánaða sem hafa verið skráðir
     * @return fjoldiManada
     */

    public int getFjoldiManada() {
        return fjoldiManada;
    }

    /**
     * Bæta við mánaðar km og gjald
     *
     * @param upphaf  km staða í upphafi
     * @param lok     km staða í lok
     * @param flokkur gjaldflokkur
     */

    public void skraManud(int upphaf, int lok, String flokkur) {
        fjoldiManada++;
        heildarGreidsla += (int) ((lok - upphaf) * getGjaldKilometra(flokkur));
        heildEknirKm += lok - upphaf;
    }

    /**
     * skilar kílómetragjald fyrir flokkur
     * @param flokkur flokkar eru strengur
     * @return kílómetragjald
     */

    public double getGjaldKilometra(String flokkur) {
        if (flokkur.equals("A")) {
            return GJALD_KILOMETRA[0];
        }
        else if (flokkur.equals("B")) {
            return GJALD_KILOMETRA[1];
        }
        return 0;
    }

    /**
     * Reiknar út gjald mánaðarins út frá fjölda km og gjaldflokkinum
     *
     * @param km      fjöldi ekinna km í mánuðinum
     * @param flokkur gjaldflokkur
     * @return kílómetragjaldið
     */

    public int reiknaManGjald(int km, String flokkur) {
        return (int) Math.round(km * getGjaldKilometra(flokkur));
    }

    /**
     * Segir til um hvort gjaldflokkur sé löglegur
     *
     * @param flokkur gjaldflokkur
     * @return true ef flokkurinn er löglegur annars false
     */

    public boolean erLoglegt(String flokkur) {
        return flokkur.equals("A") || flokkur.equals("B");
    }

    /**
     * Prófunaraktygi fyrir klasann. Má nota IntelliJ Run til að keyra vinnsluna
     *
     * @param args ónotað
     */
    static void main(String[] args) {
        KmgjaldVinnsla kmGjaldVinnsla = new KmgjaldVinnsla();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            // lesa inn gögnin af console (Scanner)
            int upphaf = sc.nextInt();
            int lok = sc.nextInt();
            String flokkur = sc.next();
            // Skrá mánuð
            kmGjaldVinnsla.skraManud(upphaf, lok, flokkur);
            System.out.println("gjaldið er " + kmGjaldVinnsla.getGjaldKilometra(flokkur));
            System.out.println(kmGjaldVinnsla.getHeildEknirKm());
            System.out.println(kmGjaldVinnsla.getHeildarGreidsla());
            System.out.println(kmGjaldVinnsla.getFjoldiManada());
            // hér má bæta við til að prófa aðrar aðferðir í þessum klasa
        }
    }
}
