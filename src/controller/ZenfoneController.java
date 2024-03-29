package controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.Client;
import model.LojaInterface;

import java.io.IOException;
import java.net.URL;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;

public class ZenfoneController implements Initializable{//Controller do Zenfone
    public static final String titulo = "Compra de Zenfone";

    @FXML
    private Label disponibilidade;

    @FXML
    private Spinner<Integer> qtd;
    //Spinner contendo o valor que o cliente pode comprar
    private SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10,0);


    @FXML
    private JFXButton btnComprar;

    @FXML
    private JFXHamburger hamburguer;

    @FXML
    private JFXDrawer drawer;

    //Método que irá chamar remotamente o método de removerItem do servidor

    public void removerItem(ActionEvent event) throws IOException, NotBoundException, AlreadyBoundException {
        TelaController.exibirJanela(Alert.AlertType.WARNING, "Espera", "Aguarde um momento!", "Compra sendo efetuada..."); //Mostra o alerta
        LojaInterface loja = (LojaInterface) Naming.lookup("rmi://"+ClientController.getIps()+":5099/loja"); //Objeto Remoto
        boolean res = loja.removerItem("Zenfone",this.qtd.getValue()); //Chama o método de remover o item no servidor e armazena o resultado
        for(Client cl : ClientController.getInstance().getLojas() ){ //Percorre as lojas
            if(cl.getNome().equals(ClientController.getInstance().getCurrentStore())){
                this.disponibilidade.setText(cl.getQuantidade("Zenfone")+ " itens no estoque!");  //Modifica a quantidade total do produto
                if(!res){ //Caso a compra não for realizada com sucesso
                    TelaController.exibirJanela(Alert.AlertType.ERROR, "Alerta de Erro", "Erro!", "Não tem produto no estoque");
                }
                else // Caso contrário
                    TelaController.exibirJanela(Alert.AlertType.CONFIRMATION, "Alerta de SUCESSO!", "Sucesso!", "Compra realizada com sucesso!");

            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { //Método "construtor" da tela
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
                    this.disponibilidade.setText(cl.getQuantidade("Zenfone")+ " itens no estoque!");
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
