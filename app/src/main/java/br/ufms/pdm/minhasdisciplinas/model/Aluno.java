package br.ufms.pdm.minhasdisciplinas.model;

public class Aluno {
    public String nome_aluno;
    public String email;
    public String curso;
    public byte[] foto;

    public Aluno() {
    }

    public Aluno(String nome_aluno, String email, String curso, byte[] foto) {
        this.nome_aluno = nome_aluno;
        this.email = email;
        this.curso = curso;
        this.foto = foto;
    }

    /* Usar construtor sem foto? */

    public String getNome_aluno() {
        return nome_aluno;
    }

    public void setNome_aluno(String nome_aluno) {
        this.nome_aluno = nome_aluno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
