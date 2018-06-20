package model;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Loja extends UnicastRemoteObject implements LojaInterface {
    // Hash com produto e qtd total
    int qtd = 100;

    protected Loja() throws RemoteException, MalformedURLException, NotBoundException {
        super();

    }

    @Override
    public boolean removerItem(int quantidade) throws RemoteException, MalformedURLException, NotBoundException {
        if (this.qtd >= quantidade) {
            ClientInterface cliente = (ClientInterface) Naming.lookup("rmi://localhost:5098/client");
            cliente.removerItem(quantidade);
            this.removerEstoque(quantidade);
            System.out.println(this.getQtd());
            return true;
        }
        return false;
    }




    @Override
    public boolean adicionarItem(int quantidade) throws RemoteException, MalformedURLException, NotBoundException {
            ClientInterface cliente = (ClientInterface) Naming.lookup("rmi://localhost:5098/client");
            cliente.adicionarItem(quantidade);
            this.qtd += quantidade;
            return true;
    }

    public int getValue(){
        return this.qtd;
    }

    @Override
    public boolean removerEstoque(int quantidade) throws RemoteException {
        this.qtd-=quantidade;
        return true;
    }

    public int getQtd() {
        return qtd;
    }
}
