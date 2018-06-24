package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.Client;

import java.io.IOException;
import java.net.URL;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;

public class EstabilizadorController implements Initializable
{
    public static final String titulo = "Compra de Estabilizador";

    @FXML
    private Label disponibilidade;

    @FXML
    private Spinner<Integer> qtd;
    private SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,5,0);


    @FXML
    private JFXButton btnComprar;

    @FXML
    private JFXHamburger hamburguer;

    @FXML
    private JFXDrawer drawer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.qtd.setValueFactory(this.valueFactory);
        try {
            VBox box= FXMLLoader.load(getClass().getResource("/application/TelaVoltar.fxml"));
            drawer.setSidePane(box);
            HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(hamburguer);
            burgerTask2.setRate(-1);
            hamburguer.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
                burgerTask2.setRate(burgerTask2.getRate() * -1);
                burgerTask2.play();
                if (drawer.isOpened()) {
                    drawer.close();
                } else
                    drawer.open();
            });
            for(Client cl : ClientController.getInstance().getLojas() ){
                if(cl.getNome().equals(ClientController.getInstance().getCurrentStore())){
                    this.disponibilidade.setText(cl.getQuantidade("Estabilizador")+ " itens no estoque!");
                }
            }
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
