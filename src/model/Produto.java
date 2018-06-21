package model;

public class Produto {
    private String nome;
    private int quantidade;

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



}
