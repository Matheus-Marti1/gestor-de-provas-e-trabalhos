package br.edu.fateczl.gestordeprovasetrabalhos.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    private int id;
    private String nome;
    private String professor;
    private List<Atividade> atividades;

    public Disciplina(int id, String nome, String professor) {
        this.id = id;
        this.nome = nome;
        this.professor = professor;
        this.atividades = new ArrayList<>();
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

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Disciplina: ").append(id).append(" - ").append(nome)
                .append("\nProfessor: ").append(professor)
                .append("\nAtividades:\n");

        for (Atividade atividade : atividades) {
            sb.append("  - ").append(atividade.toString()).append("\n");
        }

        return sb.toString();
    }
}
