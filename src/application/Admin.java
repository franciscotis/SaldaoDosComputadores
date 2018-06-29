package application;

import controller.ClientController;
import model.LojaInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Admin { //Classe do administrador no qual poderá inserir uma nova quantidade de produtos no estoque

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        Scanner tc = new Scanner(System.in); //Criação de um objeto scanner
        LojaInterface loja = (LojaInterface) Naming.lookup("rmi://"+ ClientController.getIps()+":5099/loja");
        //Busca do objeto no servidor RMI. No qual o método estático getIps() da classe ClientController irá retornar o endereço IP do servidor

        while(true){ // Loop infinito
            System.out.println("Digite o nome do Produto");
            String produto = tc.nextLine(); // Digita o nome do produto
            System.out.println("Digite a quantidade que você deseja adicionar");
            int qtd = tc.nextInt(); //Digita a quantidade do produto que se deseja adicionar
            tc.nextLine();
            try {
                if (loja.adicionarItem(produto, qtd)) // Caso o produto for adicionado com sucesso
                    System.out.println("Produto adicionado com sucesso!");
                else //Caso contrario
                    System.out.println("Não foi possível adicionar mais produtos!");
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        }
    }
}
