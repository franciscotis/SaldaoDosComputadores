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

public class Loja extends UnicastRemoteObject implements LojaInterface, Serializable { //Classe que terá seu objeto exportado para ser acessado remotamente
    private List<Produto> produtos; //Lista de produtos
    private Map<String,List<Produto>> afiliadas; //Hash contendo o nome da afiliada e a quantidade de produto em estqoue
    private ArrayList<ClientInterface> clients;


    protected Loja() throws RemoteException, MalformedURLException, NotBoundException { //Construtor
        super(); //Chama o método da classe mãe
        this.produtos = new LinkedList<>(); //declaração da likedlist
        //Adição de produtos
        this.produtos.add(new Produto("Monitor",10));
        this.produtos.add(new Produto("Notebook",10));
        this.produtos.add(new Produto("Tablet",10));
        this.produtos.add(new Produto("iPhone",10));
        this.produtos.add(new Produto("Zenfone",10));
        this.produtos.add(new Produto("Estabilizador",10));
        this.afiliadas = new HashMap<>(); //declaração da hashmap
        this.clients = new ArrayList<ClientInterface>(); //declaração do ararylist
        //Povoamento do hash das afiliadas.
        this.addAfiliadas("Amazonas");
        this.addAfiliadas("Mazza");
        this.addAfiliadas("Mexicanas");



    }

    public void addAfiliadas(String nome){
        //Método que armazena a quantidade disponivel de produtos nas afiliadas. Inicialmente todas tem 10 produtos disponiveis
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
    } //Retorna as afiliadas

    @Override
    public boolean removerItem(String nome, int quantidade) throws RemoteException, MalformedURLException, NotBoundException {
        //Método que remove um item
        synchronized (this) { //Synchronized para evitar a concorrência
            for(Produto a : this.produtos){ //Procura os produtos
                if(a.getNome().equalsIgnoreCase(nome)){ //Caso ache o produto que queira remover
                    if (a.getQuantidade() >= quantidade && a.getQuantidade()!=0) { // Caso a quantidade que queira remover seja maior ou igual que a disponivel e diferente de zero
                        for(ClientInterface i : this.clients){ //Avisa as afiliadas a remoção
                            i.removerItem(nome,quantidade); //Remove os itens nas afiliadas
                        }
                        this.removerEstoque(nome, (quantidade)); //Remove no armazenamento local
                        for(Map.Entry<String, List<Produto>> entry : this.afiliadas.entrySet()){ //Percorre a hash com os dados das afiliadas
                                for(Produto prod : entry.getValue()){
                                    if(prod.getNome().equals(nome))
                                        prod.retirarQuantidade(quantidade); //Retira a quantidade de produto da afiliada ( armazenamento local)
                                }
                        }

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
    public boolean adicionarItem(String nome, int quantidade) throws RemoteException, MalformedURLException, NotBoundException {
        //Método que adiciona um item no estoque
        for(Produto a : this.produtos){
            if(a.getNome().equals(nome)){
                a.setQuantidade(a.getQuantidade()+quantidade); //Adiciona item localmente
                for(ClientInterface i : this.clients){
                    i.adicionarItem(nome,quantidade); //Adiciona item nas afiliadas
                }
                for(Map.Entry<String, List<Produto>> entry : this.afiliadas.entrySet()){
                    for(Produto prod : entry.getValue()){
                        if(prod.getNome().equals(nome))
                            prod.adicionarQuantidade(quantidade); //Adiciona item no estoque das afiliadas localmente
                    }
                }
                return true;
            }

        }
        return false;
    }




    @Override
    public boolean removerEstoque(String nome, int quantidade) throws RemoteException { // Método que remove uma quantidade do estoque local
        for(Produto a : this.produtos){
            if(a.getNome().equals(nome)){
                a.retirarQuantidade(quantidade); //Retira
            }
        }
        return true;
    }


    public void Add(ClientInterface client) throws RemoteException{ //Método que adiciona um cliente na lista de clients
        clients.add(client);
    }

    @Override
    public void Remove(ClientInterface client) throws RemoteException { //Método que remove um cliente na lista de clients
        int index = this.clients.indexOf(client);
        this.clients.remove(index);
    }


}
