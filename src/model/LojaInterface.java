package model;


import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface LojaInterface extends Remote {

    public boolean removerItem(String nome, int quantidade) throws RemoteException, MalformedURLException, NotBoundException;
    public boolean  adicionarItem(int quantidade) throws RemoteException, MalformedURLException, NotBoundException;
    public int getValue() throws RemoteException;
    public boolean removerEstoque(String nome, int quantidade) throws RemoteException;
    public Map<String, List<Produto>> getAfiliadas() throws RemoteException;

    }
