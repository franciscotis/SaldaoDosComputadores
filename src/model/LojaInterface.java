package model;


import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LojaInterface extends Remote {

    public boolean removerItem(String nome, int quantidade) throws RemoteException, MalformedURLException, NotBoundException;
    public boolean  adicionarItem(int quantidade) throws RemoteException, MalformedURLException, NotBoundException;
    public int getValue() throws RemoteException;
    public boolean removerEstoque(String nome, int quantidade) throws RemoteException;


}
