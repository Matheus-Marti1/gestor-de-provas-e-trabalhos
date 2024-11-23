/*
 *@author:<Matheus Augusto Marti>
 */

package br.edu.fateczl.gestordeprovasetrabalhos.model;

import androidx.annotation.NonNull;

public class Prova extends Atividade{
    private boolean isRecuperacao;
    private Disciplina disciplina;
    public Prova(int id, String descricao, String data, boolean isRecuperacao, Disciplina disciplina) {
        super(id, descricao, data);
        this.isRecuperacao = isRecuperacao;
        this.disciplina = disciplina;
    }

    public boolean isRecuperacao() {
        return isRecuperacao;
    }

    public void setRecuperacao(boolean isRecuperacao) {
        this.isRecuperacao = isRecuperacao;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    @Override
    public double calcularImpactoNota() {
        if (getNota() == 0) {
            return 0;
        }
        if (getPeso() > 1) {
            return getNota() * (getPeso()/10);
        } else return getNota() * getPeso();
    }

    @NonNull
    @Override
    public String toString() {
        double notaMedia = calcularImpactoNota();
        return super.toString() + ", Disciplina: " + disciplina.getNome() +
                ", Recuperação: " + (isRecuperacao ? "Sim" : "Não") + ", Nota na Média: " + notaMedia;
    }
}
