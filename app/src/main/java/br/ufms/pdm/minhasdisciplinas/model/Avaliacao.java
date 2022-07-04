package br.ufms.pdm.minhasdisciplinas.model;

public class Avaliacao {
    public String nome_avaliacao;
    public String tipo;         /* opções: Atividade, Lista de exercícios, Prova, Trabalho */
    public String data_entrega; /* formato dd/mm/yyyy-HH:MM ; datetime? */
    public float nota;          /* 2 casas decimais */

    public Avaliacao() {
    }

    public Avaliacao(String nome_avaliacao, String tipo, String data_entrega, float nota) {
        this.nome_avaliacao = nome_avaliacao;
        this.tipo = tipo;
        this.data_entrega = data_entrega;
        this.nota = nota;
    }

    public String getNome_avaliacao() {
        return nome_avaliacao;
    }

    public void setNome_avaliacao(String nome_avaliacao) {
        this.nome_avaliacao = nome_avaliacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData_entrega() {
        return data_entrega;
    }

    public void setData_entrega(String data_entrega) {
        this.data_entrega = data_entrega;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }
}


