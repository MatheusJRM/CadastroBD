package cadastro.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import cadastro.model.util.ConectorBD;

import cadastrobd.model.PessoaJuridica;
import cadastrobd.model.Endereco;

import cadastrobd.enums.TipoPessoa;

public class PessoaJuridicaDAO {

    private final ConectorBD conectorBD;

    public PessoaJuridicaDAO(ConectorBD conectorBD) {
        this.conectorBD = conectorBD;
    }

    public PessoaJuridica getPessoaJuridica(int id) throws SQLException {
        PessoaJuridica PessoaJuridica = null;
        Connection connection = conectorBD.getConnection();
        try {
            PreparedStatement preparedStatement = conectorBD.getPreparedStatement(connection, "SELECT \n"
                    + "  p.*,\n"
                    + "  pj.*,\n"
                    + "  e.*\n"
                    + "FROM \n"
                    + "  Pessoa p\n"
                    + "  INNER JOIN Pessoa_Juridica pj ON p.id = pj.pessoa_id\n"
                    + "  INNER JOIN Endereco e ON p.id_endereco = e.id\n"
                    + "WHERE \n"
                    + "  p.id = ?;", id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                PessoaJuridica = new PessoaJuridica(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("telefone"),
                        new Endereco(resultSet.getInt("id_endereco"), resultSet.getString("logradouro"), resultSet.getString("bairro"), resultSet.getString("numero"), resultSet.getString("cidade"), resultSet.getString("estado"), resultSet.getString("complemento"), resultSet.getString("cep")),
                        resultSet.getString("cnpj"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getPessoaJuridica ->  " + e);
        } finally {
            conectorBD.close(connection);
        }
        return PessoaJuridica;
    }

    public List<PessoaJuridica> getPessoaJuridicas() throws SQLException {
        List<PessoaJuridica> pessoaJuridicas = new ArrayList<>();
        Connection connection = conectorBD.getConnection();
        try {
            PreparedStatement preparedStatement = conectorBD.getPreparedStatement(connection, "SELECT * FROM Pessoa p INNER JOIN Pessoa_Juridica pj ON p.id = pj.pessoa_id INNER JOIN Endereco e ON p.id_endereco = e.id WHERE p.id_tipo_pessoa = ?", TipoPessoa.JURIDICA.getTipo()
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PessoaJuridica pessoaJuridica = new PessoaJuridica(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("telefone"),
                        new Endereco(resultSet.getInt("id_endereco"), resultSet.getString("logradouro"), resultSet.getString("bairro"), resultSet.getString("numero"), resultSet.getString("cidade"), resultSet.getString("estado"), resultSet.getString("complemento"), resultSet.getString("cep")),
                        resultSet.getString("cnpj")
                );
                pessoaJuridicas.add(pessoaJuridica);
            }
        } catch (SQLException e) {
            System.out.println("getPessoaJuridicas ->  " + e);
        } finally {
            conectorBD.close(connection);
        }
        return pessoaJuridicas;
    }

    public void incluirPessoaJuridica(PessoaJuridica PessoaJuridica) throws SQLException {
        Connection connection = conectorBD.getConnection();
        try {
            connection.setAutoCommit(false);

            int idEnderecoCadastrado = cadastrarEndereco(PessoaJuridica.getEndereco(), connection);

            PreparedStatement preparedStatementPessoa = conectorBD.getPreparedStatement(connection, "INSERT INTO Pessoa (nome, telefone, id_endereco, id_tipo_pessoa) VALUES (?, ?, ?, ?)", PessoaJuridica.getNome(), PessoaJuridica.getTelefone(), idEnderecoCadastrado, TipoPessoa.JURIDICA.getTipo());
            preparedStatementPessoa.executeUpdate();

            if (preparedStatementPessoa.getGeneratedKeys().next()) {
                int id = preparedStatementPessoa.getGeneratedKeys().getInt(1);
                PessoaJuridica.setId(id);
                PreparedStatement preparedStatementPessoaJuridica = conectorBD.getPreparedStatement(connection, "INSERT INTO Pessoa_Juridica  (pessoa_id, cnpj) VALUES (?, ?)", id, PessoaJuridica.getCnpj());
                preparedStatementPessoaJuridica.executeUpdate();
                connection.commit();
                System.out.println("Pessoa Juridica cadastrada com sucesso!");
            } else {
                System.out.println("A instrução INSERT não gerou uma chave.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException err) {
                System.out.println("incluirPessoaJuridica ->  " + err);
            }
        } finally {
            conectorBD.close(connection);
        }
    }

    private int cadastrarEndereco(Endereco endereco, Connection connection) throws SQLException {
        PreparedStatement preparedStatementEndereco = conectorBD.getPreparedStatement(connection, "INSERT INTO Endereco (logradouro, numero, bairro, cidade, estado, complemento, cep) VALUES (?, ?, ?, ?, ?, ?, ?)",
                endereco.getLogradouro(), endereco.getNumero(), endereco.getBairro(), endereco.getCidade(), endereco.getEstado(), endereco.getComplemento(), endereco.getCep());
        preparedStatementEndereco.executeUpdate();

        if (preparedStatementEndereco.getGeneratedKeys().next()) {
            int id = preparedStatementEndereco.getGeneratedKeys().getInt(1);
            endereco.setId(id);
            System.out.println("Endereco cadastrado com sucesso!");
            return id;
        } else {
            System.out.println("A instrução INSERT não gerou uma chave.");
            return 0;
        }
    }

    public void alterarPessoaJuridica(PessoaJuridica PessoaJuridica) throws SQLException {
        Connection connection = conectorBD.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatementPessoa = conectorBD.getPreparedStatement(connection, "UPDATE Pessoa SET nome = ?, telefone = ?, id_endereco = ? WHERE id = ?", PessoaJuridica.getNome(), PessoaJuridica.getTelefone(), PessoaJuridica.getEndereco().getId(), PessoaJuridica.getId());
            preparedStatementPessoa.executeUpdate();
            PreparedStatement preparedStatementPessoaJuridica = conectorBD.getPreparedStatement(connection, "UPDATE Pessoa_Juridica SET cnpj = ? WHERE pessoa_id = ?", PessoaJuridica.getCnpj(), PessoaJuridica.getId());
            preparedStatementPessoaJuridica.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException err) {
                System.out.println("alterarPessoaJuridica ->  " + err);
            } finally {
                conectorBD.close(connection);
            }
        }
    }

    public void excluirPessoaJuridica(int id) throws SQLException {
        Connection connection = conectorBD.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatementMovimentacaoVenda = conectorBD.getPreparedStatement(connection, "DELETE FROM Movimentacao_Venda WHERE id_pessoa_Juridica = ?", id);
            preparedStatementMovimentacaoVenda.executeUpdate();
            PreparedStatement preparedStatementPessoaJuridica = conectorBD.getPreparedStatement(connection, "DELETE FROM Pessoa_Juridica WHERE pessoa_id = ?", id);
            preparedStatementPessoaJuridica.executeUpdate();
            PreparedStatement preparedStatementPessoa = conectorBD.getPreparedStatement(connection, "DELETE FROM Pessoa WHERE id = ? AND id_tipo_pessoa = 2", id);
            preparedStatementPessoa.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException err) {
                System.out.println("excluirPessoaJuridica ->  " + err);
            } finally {
                conectorBD.close(connection);
            }
        }
    }
}
