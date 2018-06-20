package model;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements ClientInterface {
    public Client() throws RemoteException, AlreadyBoundException {
    }
    private int quantidade = 100;


    public void adicionarItem(int qtd){
        quantidade+=qtd;
    }

    public void removerItem(int qtd){
        quantidade-=qtd;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
