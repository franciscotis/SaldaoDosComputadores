package model;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Loja extends UnicastRemoteObject implements LojaInterface {
    // Hash com produto e qtd total
    List<Produto> produtos;
    Map<String,Produto> afiliadas;
    int qtd = 100;


    protected Loja() throws RemoteException, MalformedURLException, NotBoundException {
        super();
        this.produtos = new LinkedList<>();
        this.produtos.add(new Produto("Monitor",30));
        this.produtos.add(new Produto("Notebook",30));
        this.produtos.add(new Produto("Tablet",30));
        this.produtos.add(new Produto("iPhone",30));
        this.produtos.add(new Produto("Zenfone",30));
        this.produtos.add(new Produto("Estabilizador",30));
        this.afiliadas = new HashMap<>();
        this.addAfiliadas("Amazonas");
        this.addAfiliadas("Mazza");
        this.addAfiliadas("Mexicanas");


    }

    public void addAfiliadas(String nome){
        this.afiliadas.put(nome,new Produto("Monitor",10));
        this.afiliadas.put(nome,new Produto("Notebook",10));
        this.afiliadas.put(nome,new Produto("Tablet",10));

        this.afiliadas.put(nome,new Produto("iPhone",10));

        this.afiliadas.put(nome,new Produto("Zenfone",10));

        this.afiliadas.put(nome,new Produto("Estabilizador",10));



    }

    @Override
    public boolean removerItem(String nome, int quantidade) throws RemoteException, MalformedURLException, NotBoundException {
        if (this.qtd >= quantidade) {
            ClientInterface mexicanas = (ClientInterface) Naming.lookup("rmi://localhost:5098/Mexicanas");
            ClientInterface amazonas = (ClientInterface) Naming.lookup("rmi://localhost:5098/Amazonas");
            ClientInterface mazza = (ClientInterface) Naming.lookup("rmi://localhost:5098/Mazza");
            mexicanas.removerItem(nome, quantidade);
            mazza.removerItem(nome, quantidade);
            amazonas.removerItem(nome, quantidade);
            this.removerEstoque(nome,(quantidade*3));
            return true;
        }
        return false;
    }




    @Override
    public boolean adicionarItem(int quantidade) throws RemoteException, MalformedURLException, NotBoundException {
            ClientInterface mexicanas = (ClientInterface) Naming.lookup("rmi://localhost:5098/Mexicanas");
            ClientInterface amazonas = (ClientInterface) Naming.lookup("rmi://localhost:5098/Amazonas");
            ClientInterface mazza = (ClientInterface) Naming.lookup("rmi://localhost:5098/Mazza");


       // mexicanas.adicionarItem(quantidade);
            this.qtd += quantidade;
            return true;
    }

    public int getValue(){
        return this.qtd;
    }

    @Override
    public boolean removerEstoque(String nome, int quantidade) throws RemoteException {
        for(Produto a : this.produtos){
            if(a.getNome().equals(nome)){
                a.retirarQuantidade(quantidade);
                System.out.println("SALDÃO - > "+a.getNome()+" QTD"+a.getQuantidade());
            }
        }
        return true;
    }

    public int getQtd() {
        return qtd;
    }
}
