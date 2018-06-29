package model;


import controller.ClientController;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface LojaInterface extends Remote { //Interface remota da Loja

    public boolean removerItem(String nome, int quantidade) throws RemoteException, MalformedURLException, NotBoundException;
    public boolean  adicionarItem(String nome, int quantidade) throws RemoteException, MalformedURLException, NotBoundException;
    public boolean removerEstoque(String nome, int quantidade) throws RemoteException;
    public Map<String, List<Produto>> getAfiliadas() throws RemoteException;
    public void Add(ClientInterface client) throws RemoteException;
    public void Remove(ClientInterface client) throws RemoteException;

    }
