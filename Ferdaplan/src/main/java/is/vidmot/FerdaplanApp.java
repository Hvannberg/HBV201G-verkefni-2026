package is.vidmot;

import is.vidmot.switcher.View;
import is.vidmot.switcher.ViewSwitcher;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/******************************************************************************
 *  Nafn    :
 *  T-póstur:
 *  Lýsing  : Beinagreind að Application klasa
 *
 *
 *****************************************************************************/
public class FerdaplanApp extends javafx.application.Application {
    /**
     * Ræsir appið
     *
     * @param stage glugginn
     * @throws Exception undnantekning sem verður ef villla
     */
    @Override
    public void start(Stage stage) throws Exception {

        Scene s = new Scene(new Pane());
        ViewSwitcher.setScene(s);

        ViewSwitcher.switchTo (View.ADAL, true, null);
        /**
        // Smíða loader fyrir notendaviðmótið sem er geymt í skránni ferd-view.fxml
        // Gætið þess að .fxml skráin sé undir resources/is/vidmot
           FXMLLoader fxmlLoader = new FXMLLoader(FerdaplanApp.class.getResource("adal-view.fxml"));
        // Smíða senuna með notendaviðmótinu sem er núna lesið inn af resources
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
         +*/
        // Setja titilinn á gluggann
        stage.setTitle("Ferðaplan");
        // Tengja senuna við glugggann
        stage.setScene(s);
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
