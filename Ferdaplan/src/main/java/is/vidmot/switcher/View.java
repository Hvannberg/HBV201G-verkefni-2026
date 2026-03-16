package is.vidmot.switcher;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 *
 * viðbætur fyrir Ferdaplan verkefni
 */
public enum View {
    ADAL("/is/vidmot/adal-view.fxml"),
    FERD("/is/vidmot/ferd-view.fxml"),
    FERDDIALOG("/is/vidmot/nyFerd-dialog.fxml"),
    FERDSPJALD ("/is/vidmot/ferdSpjald-view.fxml");


    private String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
