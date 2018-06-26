package model;

import controller.ClientController;

import javax.management.StringValueExp;
import javax.naming.ldap.Control;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Loja extends UnicastRemoteObject implements LojaInterface, Serializable {
    private List<Produto> produtos;
    private Map<String,List<Produto>> afiliadas;
    int qtd = 100;

    private ArrayList<ClientInterface> clients;


    protected Loja() throws RemoteException, MalformedURLException, NotBoundException {
        super();
        this.produtos = new LinkedList<>();
        this.produtos.add(new Produto("Monitor",10));
        this.produtos.add(new Produto("Notebook",10));
        this.produtos.add(new Produto("Tablet",10));
        this.produtos.add(new Produto("iPhone",10));
        this.produtos.add(new Produto("Zenfone",10));
        this.produtos.add(new Produto("Estabilizador",10));
        this.afiliadas = new HashMap<>();
        this.clients = new ArrayList<ClientInterface>();
        this.addAfiliadas("Amazonas");
        this.addAfiliadas("Mazza");
        this.addAfiliadas("Mexicanas");



    }

    public void addAfiliadas(String nome){
        this.afiliadas.put(nome,new ArrayList<Produto>());
        for( Map.Entry<String, List<Produto>> a : this.afiliadas.entrySet()){
            if(a.getKey().equals(nome)){
                a.getValue().add(new Produto("Monitor",10));
                a.getValue().add(new Produto("Tablet",10));
                a.getValue().add(new Produto("iPhone",10));
                a.getValue().add(new Produto("Zenfone",10));
                a.getValue().add(new Produto("Estabilizador",10));


            }

        }

    }


    @Override
    public Map<String, List<Produto>> getAfiliadas() {
        return afiliadas;
    }

    @Override
    public boolean removerItem(String nome, int quantidade) throws RemoteException, MalformedURLException, NotBoundException {
        String aa = ClientController.getIp();
        synchronized (this) {
            for(Produto a : this.produtos){
                if(a.getNome().equalsIgnoreCase(nome)){
                    if (a.getQuantidade() >= quantidade) {
                        for(ClientInterface i : this.clients){
                            i.removerItem(nome,quantidade);
                        }
                        /*
                        ClientInterface mexicanas = (ClientInterface) Naming.lookup("rmi://"+aa+":5098/Mexicanas");
                        ClientInterface amazonas = (ClientInterface) Naming.lookup("rmi://"+aa+":5098/Amazonas");
                        ClientInterface mazza = (ClientInterface) Naming.lookup("rmi://"+aa+":5098/Mazza");
                        mexicanas.removerItem(nome, quantidade);
                        mazza.removerItem(nome, quantidade);
                        amazonas.removerItem(nome, quantidade);
                        */
                        this.removerEstoque(nome, (quantidade));
                        for(Map.Entry<String, List<Produto>> entry : this.afiliadas.entrySet()){
                                for(Produto prod : entry.getValue()){
                                    if(prod.getNome().equals(nome))
                                        prod.retirarQuantidade(quantidade);
                                }
                        }
                        System.out.println("SALDÃO - > "+a.getNome()+" QTD"+a.getQuantidade());
                        for(Map.Entry<String, List<Produto>> entry : this.afiliadas.entrySet()){
                            for(Produto prod : entry.getValue()){
                                System.out.println(entry.getKey() + " " +  prod.getNome() + " " + prod.getQuantidade());
                            }
                        }

                        return true;
                    }

                }
            }

            return false;
        }
    }




    @Override
    public boolean adicionarItem(int quantidade) throws RemoteException, MalformedURLException, NotBoundException {
        synchronized (this) {
            String aa = ClientController.getIp();
            ClientInterface mexicanas = (ClientInterface) Naming.lookup("rmi://"+aa+":5098/Mexicanas");
            ClientInterface amazonas = (ClientInterface) Naming.lookup("rmi://"+aa+":5098/Amazonas");
            ClientInterface mazza = (ClientInterface) Naming.lookup("rmi://"+aa+":5098/Mazza");


            // mexicanas.adicionarItem(quantidade);
            this.qtd += quantidade;
            return true;
        }
    }

    public int getValue(){
        return this.qtd;
    }

    @Override
    public boolean removerEstoque(String nome, int quantidade) throws RemoteException {
        for(Produto a : this.produtos){
            if(a.getNome().equals(nome)){
                a.retirarQuantidade(quantidade);
            }
        }
        return true;
    }

    public int getQtd() {
        return qtd;
    }

    public void Add(ClientInterface client) throws RemoteException{
        clients.add(client);
    }

    @Override
    public void Remove(ClientInterface client) throws RemoteException {
        int index = this.clients.indexOf(client);
        this.clients.remove(index);
    }

}
