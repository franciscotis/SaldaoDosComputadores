package model;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor { // Servidor da loja principal - > Saldao Dos Computadores

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException, NotBoundException {
        Registry registry = LocateRegistry.createRegistry(5099);
        registry.bind("loja",new Loja());
    }
}
