package controller;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

public class VoltarController { //Construtor da tela de voltar

    public void voltar(ActionEvent event) throws AlreadyBoundException, IOException, NotBoundException {
        switch(ClientController.getInstance().getCurrentStore()){
            case "Amazonas":
                TelaController.carregarTela(event, "/application/TelaCasasAmazonas.fxml",
                        AmazonasController.titulo);
                break;
            case "Mazza":
                TelaController.carregarTela(event, "/application/Mazza.fxml",
                        MazzaController.titulo);
                break;
            case "Mexicanas":
                TelaController.carregarTela(event, "/application/Mexicanas.fxml",
                        MexicanasController.titulo);
                break;
        }

    }
}
