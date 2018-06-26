package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SaldaoMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent base = FXMLLoader.load(SaldaoMain.class.getResource("Uescape.fxml"));
            Scene cena = new Scene(base);
            primaryStage.setScene(cena);
            primaryStage.setResizable(false);
            primaryStage.setTitle("UESCAPÉ");
                primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getStackTrace());
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
