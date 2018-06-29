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

public class Client extends UnicastRemoteObject implements ClientInterface { //Classes do Cliente
    private List<Produto> produtos; //Lista de produtos
    private String nome; //Nome
    public Client(String nome) throws RemoteException, AlreadyBoundException { //Construtor
        this.produtos = new LinkedList<>();
        this.nome = nome;
    }


    public void adicionarItem(String nome,int qtd){ //Método de adicionar item
        for(Produto a : this.produtos){
            if(a.getNome().equals(nome)){
                a.adicionarQuantidade(qtd);
            }
        }
    }

    public String getNome() {
        return nome;
    } //getNome

    public void removerItem(String nome, int qtd){ //Método de remover item do estoque
        for(Produto a : this.produtos){
            if(a.getNome().equals(nome)){
                a.retirarQuantidade(qtd);
                System.out.println(this.getNome()+ " - > "+a.getNome()+" QTD "+a.getQuantidade());

            }
        }
    }

    public int getQuantidade(String nome) { //Método que retorna a quantidade em estoque
        for(Produto a : this.produtos){
            if(a.getNome().equals(nome)){
                return a.getQuantidade();
            }
        }
        return -1;
    }

    public void addProduto(String nome, int qtd){
        this.produtos.add(new Produto(nome,qtd));
    } //Método de adicionar um produto

    public void editarProduto(String nome, int qtd){ //Método de editar a quantidade disponível de produto
        for(Produto p : this.produtos){
            if(p.getNome().equals(nome)){
                p.setQuantidade(qtd);
            }
        }
    }

    public List<Produto> getProdutos() {
        return produtos;
    } //Método que retorna todos os produtios
}
