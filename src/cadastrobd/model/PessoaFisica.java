package cadastrobd.model;

import cadastrobd.enums.TipoPessoa;

public class PessoaFisica extends Pessoa {

    private String cpf;

    public PessoaFisica() {
    }

    public PessoaFisica(int id, String nome, String telefone, Endereco endereco, String cpf) {
        super(id, nome, telefone, endereco, TipoPessoa.FISICA);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
