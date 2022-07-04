package br.ufms.pdm.minhasdisciplinas.model;

public class Disciplina {
    private String id;
    private String nome;
    private String professor;
    private String ano_semestre;         /* formato Ano/Semestre */
    private String periodo_inicio;       /* formato dd/mm/yyyy-dd/mm/yyyy ; date? */
    private String periodo_fim;
    private String turma;
    private int carga_horaria;
    private String situacao;             /* opções: Matriculado, Aprovado, Reprovado */

    public Disciplina() {}

    public Disciplina(String nome, String professor, String ano_semestre, String periodo_inicio, String periodo_fim, String turma, int carga_horaria, String situacao) {
        this.nome = nome;
        this.professor = professor;
        this.ano_semestre = ano_semestre;
        this.periodo_inicio = periodo_inicio;
        this.periodo_fim = periodo_fim;
        this.turma = turma;
        this.carga_horaria = carga_horaria;
        this.situacao = situacao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getAno_semestre() {
        return ano_semestre;
    }

    public void setAno_semestre(String ano_semestre) {
        this.ano_semestre = ano_semestre;
    }

    public String getPeriodo_inicio() {
        return periodo_inicio;
    }

    public void setPeriodo_inicio(String periodo_inicio) {
        this.periodo_inicio = periodo_inicio;
    }

    public String getPeriodo_fim() {
        return periodo_fim;
    }

    public void setPeriodo_fim(String periodo_fim) {
        this.periodo_fim = periodo_fim;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public int getCarga_horaria() {
        return carga_horaria;
    }

    public void setCarga_horaria(int carga_horaria) {
        this.carga_horaria = carga_horaria;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
