/*
 *@author:<Matheus Augusto Marti>
 */

package br.edu.fateczl.gestordeprovasetrabalhos.model;

import androidx.annotation.NonNull;

public class Trabalho extends Atividade {
    private boolean requerApresentacao;
    private int numIntegrantes;
    private Disciplina disciplina;

    public Trabalho(int id, String descricao, String data, boolean requerApresentacao, int numIntegrantes, Disciplina disciplina) {
        super(id, descricao, data);
        this.requerApresentacao = requerApresentacao;
        this.numIntegrantes = numIntegrantes;
        this.disciplina = disciplina;
    }

    public boolean isRequerApresentacao() {
        return requerApresentacao;
    }

    public void setRequerApresentacao(boolean requerApresentacao) {
        this.requerApresentacao = requerApresentacao;
    }

    public int getNumIntegrantes() {
        return numIntegrantes;
    }

    public void setNumIntegrantes(int numIntegrantes) {
        this.numIntegrantes = numIntegrantes;
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
        return super.toString() + ", Disciplina: " + disciplina.getNome() + ", Integrantes: " +
                numIntegrantes + ", Apresentação: " + (requerApresentacao ? "Sim" : "Não") + ", Nota na Média: " + notaMedia;
    }
}
