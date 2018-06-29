package model;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote { //Interface remota do cliente

    public void removerItem(String nome, int quantidade) throws RemoteException, MalformedURLException, NotBoundException;
    public void  adicionarItem(String nome, int quantidade) throws RemoteException, MalformedURLException, NotBoundException;


}
