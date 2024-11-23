package br.edu.fateczl.gestordeprovasetrabalhos.model;

import androidx.annotation.NonNull;

public abstract class Atividade {
    private int id;
    private String descricao;
    private String data;
    private double peso;
    private double nota;

    public Atividade(int id, String descricao, String data) {
        this.id = id;
        this.descricao = descricao;
        this.data = data;
        this.peso = 0;
        this.nota = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public abstract double calcularImpactoNota();

    @NonNull
    @Override
    public String toString() {
        return "Atividade: " + id + " - " + descricao + ", Data: " + data + ", Peso: " +
                peso + ", Nota: " + nota;
    }

}
