/******************************************************************************
 *  Nafn    :
 *  T-póstur:
 *  Lýsing  : er module skrá sem skilgreinir hvaða forritasöfn eru nauðsynleg og hver eru
 *  aðgengileg forritasöfnum  *
 *
 *****************************************************************************/
module ModuleNafn {
    requires javafx.fxml;
    requires javafx.controls;
    requires jdk.xml.dom;
    opens is.vidmot to javafx.fxml;

    exports is.vidmot;
    exports is.vinnsla;
    exports is.vidmot.controller;
    opens is.vidmot.controller to javafx.fxml;
    exports is.vidmot.view;
    opens is.vidmot.view to javafx.fxml;
}