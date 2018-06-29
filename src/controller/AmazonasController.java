package controller;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.LojaInterface;

import java.io.IOException;
import java.net.URL;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;

public class AmazonasController implements Initializable { //Controller da tela das lojas Amazonas
    public static final String titulo = "CASAS AMAZONAS";

    @Override
    public  void initialize(URL location, ResourceBundle resources) { //"Contrutor" da tela do JavaFX
        try {
            ClientController cont = ClientController.getInstance();
            cont.setCurrentStore("Amazonas");

        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }
 // A seguir estão as ações que acontecem quando o usuário clica em algum item
    public void monitor(ActionEvent event){
        TelaController.carregarTela(event, "/application/Monitor.fxml",
                MonitorController.titulo);
    }

    public void notebook(ActionEvent event){
        TelaController.carregarTela(event, "/application/Notebook.fxml",
                NotebookController.titulo);
    }

    public void tablet(ActionEvent event){
        TelaController.carregarTela(event, "/application/Tablet.fxml",
                TabletController.titulo);
    }

    public void iphone(ActionEvent event){
        TelaController.carregarTela(event, "/application/iPhone.fxml",
                IPhoneController.titulo);
    }

    public void zenfone(ActionEvent event){
        TelaController.carregarTela(event, "/application/Zenfone.fxml",
                ZenfoneController.titulo);
    }

    public void estabilizador(ActionEvent event){
        TelaController.carregarTela(event, "/application/Estabilizador.fxml",
                EstabilizadorController.titulo);
    }


}
