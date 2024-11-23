package br.edu.fateczl.gestordeprovasetrabalhos.model;

import androidx.annotation.NonNull;

public class Disciplina {
    private int id;
    private String nome;
    private String professor;

    public Disciplina(int id, String nome, String professor) {
        this.id = id;
        this.nome = nome;
        this.professor = professor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    @NonNull
    @Override
    public String toString() {
        return id + " - " + nome;
    }

    public String toStringCompleto() {
        return id + " - " + nome  + " - " + professor;
    }
}
