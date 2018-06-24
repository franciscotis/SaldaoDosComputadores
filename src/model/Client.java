package model;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Client extends UnicastRemoteObject implements ClientInterface { //Classes das outras lojas ex Americanas, Bom Preço etc
    List<Produto> produtos;
    String nome;
    public Client(String nome) throws RemoteException, AlreadyBoundException {
        this.produtos = new LinkedList<>();
        this.nome = nome;
    }


    public void adicionarItem(String nome,int qtd){
        for(Produto a : this.produtos){
            if(a.getNome().equals(nome)){
                a.adicionarQuantidade(qtd);
            }
        }
    }

    public String getNome() {
        return nome;
    }

    public void removerItem(String nome, int qtd){
        for(Produto a : this.produtos){
            if(a.getNome().equals(nome)){
                a.retirarQuantidade(qtd);
                System.out.println(this.getNome()+ " - > "+a.getNome()+" QTD "+a.getQuantidade());

            }
        }
    }

    public int getQuantidade(String nome) {
        for(Produto a : this.produtos){
            if(a.getNome().equals(nome)){
                return a.getQuantidade();
            }
        }
        return -1;
    }

    public void addProduto(String nome, int qtd){
        this.produtos.add(new Produto(nome,qtd));
    }

    public void editarProduto(String nome, int qtd){
        for(Produto p : this.produtos){
            if(p.getNome().equals(nome)){
                p.setQuantidade(qtd);
            }
        }
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
}
