package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;

public class UescapeController implements Initializable { //Controller da primeira página
    @FXML
    private JFXButton mexicanas;

    @FXML
    private JFXButton mazza;

    @FXML
    private JFXButton amazonas;

    //Ações dos botões

    public void mexicanas(ActionEvent event){
        TelaController.carregarTela(event, "/application/Mexicanas.fxml",
                MexicanasController.titulo);
    }

    public void mazza(ActionEvent event){
        TelaController.carregarTela(event, "/application/Mazza.fxml",
                MazzaController.titulo);

    }

    public void amazonas(ActionEvent event){
        TelaController.carregarTela(event, "/application/TelaCasasAmazonas.fxml",
                AmazonasController.titulo);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { //Método "construtor"
        try {
            ClientController cont = ClientController.getInstance();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }
}
