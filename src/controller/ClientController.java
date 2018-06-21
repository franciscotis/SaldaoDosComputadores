package controller;

import model.Client;

import java.io.*;
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

    public ClientController() throws AlreadyBoundException, IOException {
        this.lojas = new ArrayList<Client>();
        this.lojas.add(new Client("Mexicanas")); //Mexicanas
        this.lojas.add(new Client("Amazonas")); // Amazonas
        this.lojas.add(new Client("Mazza")); // Mazza
        Registry registry = LocateRegistry.createRegistry(5098);
        registry.bind("Mexicanas",this.lojas.get(0));//Mexicanas
        registry.bind("Amazonas",this.lojas.get(1)); //Amazonas
        registry.bind("Mazza",this.lojas.get(2)); //Mazza

        readFile("Mexicanas.csv",this.lojas.get(0));
        readFile("Amazonas.csv",this.lojas.get(1));
        readFile("Mazza.csv",this.lojas.get(2));

    }


    private void readFile(String nomeArq, Client loja) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/controller/"+nomeArq)));
        String linha = null;
        while((linha = reader.readLine())!=null){
            String[] dadosUsuario = linha.split(";");
            loja.addProduto(dadosUsuario[0],Integer.parseInt(dadosUsuario[1]));
        }
        reader.close();

    }

    public static ClientController getInstance() throws AlreadyBoundException, IOException {
        if(INSTANCE==null){
            INSTANCE = new ClientController();
        }
        return INSTANCE;
    }

}
