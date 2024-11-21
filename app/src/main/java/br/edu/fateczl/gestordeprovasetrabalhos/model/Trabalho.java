package br.edu.fateczl.gestordeprovasetrabalhos.model;

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
        if (getNota() == null) {
            return 0;
        }
        return getNota() * getPeso();
    }

    @Override
    public String toString() {
        return super.toString() + ", Disciplina: " + disciplina + ", Integrantes: " +
                numIntegrantes + ", Apresentação: " + (requerApresentacao ? "Sim" : "Não");
    }
}
