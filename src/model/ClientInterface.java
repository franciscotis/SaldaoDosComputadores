package model;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {

    public void removerItem(int quantidade) throws RemoteException, MalformedURLException, NotBoundException;
    public void  adicionarItem(int quantidade) throws RemoteException, MalformedURLException, NotBoundException;


}
