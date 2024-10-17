package cadastrobd.model;

import cadastrobd.enums.TipoPessoa;

public class Pessoa {

    private int id;
    private String nome;
    private String telefone;
    private Endereco endereco;
    private TipoPessoa tipoPessoa;

    public Pessoa() {
    }

    public Pessoa(int id, String nome, String telefone, Endereco endereco, TipoPessoa tipoPessoa) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.tipoPessoa = tipoPessoa;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }
}
