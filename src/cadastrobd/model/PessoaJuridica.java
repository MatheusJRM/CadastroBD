package cadastrobd.model;

import cadastrobd.enums.TipoPessoa;

public class PessoaJuridica extends Pessoa {

    private String cnpj;

    public PessoaJuridica() {
    }

    public PessoaJuridica(int id, String nome, String telefone, Endereco endereco, String cnpj) {
        super(id, nome, telefone, endereco, TipoPessoa.JURIDICA);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
