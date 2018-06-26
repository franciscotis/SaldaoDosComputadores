package controller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Loja;
import model.Servidor;

import java.io.*;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

public class TelaController {
    public static void carregarTela(ActionEvent evento, String nomeTela, String titulo) {
        if (evento != null) { // tem alguma tela para fechar antes
            Node telaAtual = (Node) evento.getSource();
            telaAtual.getScene().getWindow().hide(); // fecha a tela atual antes de abrir a pr�xima
        }
        try {
            FXMLLoader carregadorTela = new FXMLLoader(TelaController.class.getResource(nomeTela));
            Parent base = (Parent) carregadorTela.load();
            Scene cena = new Scene(base);
            Stage cenario = new Stage();
            cenario.setResizable(false);
            //cenario.getIcons().add(new Image("/img/cactuslogo.png"));
            cenario.setOnCloseRequest(evt -> {
                evt.consume();
                try {
                    shutdown(cenario);
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
            cenario.setScene(cena);
            cenario.setTitle(titulo);
            cenario.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            exibirJanela(AlertType.ERROR, "Alerta de Erro", "Erro!", "Não foi possível carregar a página solicitada!");
        }
    }
    public static void exibirJanela(AlertType tipoAlerta, String titulo, String cabecalho, String conteudo) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(conteudo);
        alerta.showAndWait();
    }

    private static  void shutdown(Stage mainWindow) throws NotBoundException, IOException, AlreadyBoundException, ClassNotFoundException {
        // you could also use your logout window / whatever here instead
        Alert alert = new Alert(Alert.AlertType.NONE, "Você realmente gostaria de sair?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            // you may need to close other windows or replace this with Platform.exit();
            ClientController.getInstance().writeFile("Mexicanas");
            ClientController.getInstance().writeFile("Amazonas");
            ClientController.getInstance().writeFile("Mazza");
            ClientController.getInstance().sair();
            Platform.exit();
            System.exit(0);

        }
    }



}
