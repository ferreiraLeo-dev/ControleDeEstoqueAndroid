package com.example.controledeestoque;

public class Produto {
    private int id;
    private int quantidade;
    private String descricao;
    private double valorUnitario;

    public Produto(int quantidade, String descricao, double valorUnitario) {
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
    }

    public Produto(int id, int quantidade, String descricao, double valorUnitario) {
        this.id = id;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
    }

    public int getId() {
        return id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }
}
