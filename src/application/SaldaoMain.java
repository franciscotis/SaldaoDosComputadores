package application;

import controller.ClientController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

public class SaldaoMain extends Application { //Aplicação do cliente
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent base = FXMLLoader.load(SaldaoMain.class.getResource("Uescape.fxml")); //Leitura do arquivo FXML contendo a tela inicial
            Scene cena = new Scene(base);
            primaryStage.setScene(cena);
            primaryStage.setWidth(200);
            primaryStage.setHeight(400);
            primaryStage.setResizable(false);
            primaryStage.getIcons().add(new Image("/img/uescapelogo.png")); //mudança da logomarca
            primaryStage.setTitle("UESCAPÉ");
            primaryStage.show();

            primaryStage.setOnCloseRequest(evt -> { //Evento que é chamado quando uma solicitação de fechar a tela é realizada
                try {
                    shutdown(primaryStage); // Chama o método shutdown
                } catch (NotBoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (AlreadyBoundException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getStackTrace());
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

    private static  void shutdown(Stage mainWindow) throws NotBoundException, IOException, AlreadyBoundException, ClassNotFoundException {
        //Método a ser invocado quando o usuário quiser fechar a tela
        Alert alert = new Alert(Alert.AlertType.NONE, "Você realmente gostaria de sair?", ButtonType.YES, ButtonType.NO); // Alerta de confirmação
        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            ClientController.getInstance().writeFile("Mexicanas"); //Atualiza os arquivos .csv
            ClientController.getInstance().writeFile("Amazonas");
            ClientController.getInstance().writeFile("Mazza");
            ClientController.getInstance().sair(); //O cliente sai da lista de clientes do servidor
            Platform.exit(); //Sai da tela
            System.exit(0); //Sai do sistema
        }
    }
}
