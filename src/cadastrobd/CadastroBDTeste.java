package cadastrobd;

import cadastro.model.PessoaJuridicaDAO;
import cadastro.model.util.ConectorBD;

import java.sql.SQLException;

public class CadastroBDTeste {

    public static void main(String[] args) throws SQLException {
        ConectorBD conectorBD = new ConectorBD();
        PessoaJuridicaDAO pjDAO = new PessoaJuridicaDAO(conectorBD);

        pjDAO.excluirPessoaJuridica(1009);
    }

}
