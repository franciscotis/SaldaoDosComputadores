package model;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientView2 {

    public static void main(String[] args) throws AlreadyBoundException, RemoteException, MalformedURLException, NotBoundException {
        Client c1 = new Client();
        Registry registry = LocateRegistry.createRegistry(5096);
        registry.bind("client",c1);
        LojaInterface loja = (LojaInterface) Naming.lookup("rmi://localhost:5099/loja");
        while(true){
            System.out.println(c1.getQuantidade());
        }

    }
}
