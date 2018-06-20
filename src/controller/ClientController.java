package controller;

import model.Client;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class ClientController {
    private static ClientController INSTANCE =  null;
    // instancias de todos os outros clientes
    private List<Client> lojas;
    public ClientController() throws AlreadyBoundException, RemoteException {
        this.lojas = new ArrayList<Client>();
        this.lojas.add(new Client());
        this.lojas.add(new Client());
        this.lojas.add(new Client());
        Registry registry = LocateRegistry.createRegistry(5098);
        registry.bind("loja1",this.lojas.get(0));
        registry.bind("loja2",this.lojas.get(1));
        registry.bind("loja3",this.lojas.get(2));
    }






    public ClientController getInstance() throws AlreadyBoundException, RemoteException {
        if(this.INSTANCE==null){
            this.INSTANCE = new ClientController();
        }
        return this.INSTANCE;
    }

}
