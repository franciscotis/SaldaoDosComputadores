package model;

import controller.ClientController;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.Scanner;

public class ClientView{

    public static void main(String[] args) throws AlreadyBoundException, IOException, NotBoundException {
        ClientController cont = ClientController.getInstance();
        LojaInterface loja = (LojaInterface) Naming.lookup("rmi://localhost:5099/loja");
        int escolha = 0;
        int choice;
        Scanner teclado = new Scanner(System.in);
        while(escolha==0){
            System.out.println("Escolha 1");
            choice = teclado.nextInt();
            teclado.nextLine();
            if(choice==1){
                System.out.println("Escolha oq deseja fazer ");
                choice = teclado.nextInt();
                teclado.nextLine();
                loja.removerItem("Monitor",choice);
            }
        }
    }
}
