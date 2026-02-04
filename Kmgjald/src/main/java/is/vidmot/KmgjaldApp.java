package is.vidmot;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *  Lýsing  : Einfalt aðalforrit fyrir Kmgjald appið í JavaFX
 *
 *  Verkefni 1 - 2026
 *****************************************************************************/
public class KmgjaldApp extends javafx.application.Application {
    /**
     * Ræsir appið
     *
     * @param stage glugginn
     * @throws Exception undantekning sem verður ef villla
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Smíða loader fyrir notendaviðmótið sem er geymt í skránni kmgjald-view.fxml
        // Gætið þess að .fxml skráin sé undir resources/is/vidmot
        FXMLLoader fxmlLoader = new FXMLLoader(KmgjaldApp.class.getResource("kmgjald-view.fxml"));
        // Smíða senuna með notendaviðmótinu sem er núna lesið inn af resources
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        // Setja titilinn á gluggann
        stage.setTitle("Kílómetra gjald app");
        // Tengja senuna við glugggann
        stage.setScene(scene);
        // Birta gluggann
        stage.show();
    }

    /**
     * Aðalforritið sem ræsir appið
     *
     * @param args ónotað
     */
    public static void main(String[] args) {
        // Ræsa forritið
        launch();
    }
}
