package model;

import java.io.Serializable;

public class Produto implements Serializable{ //Classe do produto
    private String nome;
    private int quantidade;
    private static final long serialVersionUID = 1L; //versão da serialização

    public Produto(String nome, int quantidade) {
        this.nome = nome;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void retirarQuantidade(int qtd){
        this.quantidade-=qtd;
    }


    public void adicionarQuantidade(int qtd){
        this.quantidade+=qtd;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
