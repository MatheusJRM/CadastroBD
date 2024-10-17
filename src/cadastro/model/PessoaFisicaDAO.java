package cadastro.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import cadastro.model.util.ConectorBD;

import cadastrobd.model.PessoaFisica;
import cadastrobd.model.Endereco;

import cadastrobd.enums.TipoPessoa;

public class PessoaFisicaDAO {

    private final ConectorBD conectorBD;

    public PessoaFisicaDAO(ConectorBD conectorBD) {
        this.conectorBD = conectorBD;
    }

    public PessoaFisica getPessoaFisica(int id) throws SQLException {
        PessoaFisica pessoaFisica = null;
        Connection connection = conectorBD.getConnection();
        try {
            PreparedStatement preparedStatement = conectorBD.getPreparedStatement(connection, "SELECT \n"
                    + "  p.*,\n"
                    + "  pf.*,\n"
                    + "  e.*\n"
                    + "FROM \n"
                    + "  Pessoa p\n"
                    + "  INNER JOIN Pessoa_Fisica pf ON p.id = pf.pessoa_id\n"
                    + "  INNER JOIN Endereco e ON p.id_endereco = e.id\n"
                    + "WHERE \n"
                    + "  p.id = ?;", id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                pessoaFisica = new PessoaFisica(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("telefone"),
                        new Endereco(resultSet.getInt("id_endereco"), resultSet.getString("logradouro"), resultSet.getString("bairro"), resultSet.getString("numero"), resultSet.getString("cidade"), resultSet.getString("estado"), resultSet.getString("complemento"), resultSet.getString("cep")),
                        resultSet.getString("cpf"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getPessoaFisica ->  " + e);
        } finally {
            conectorBD.close(connection);
        }
        return pessoaFisica;
    }

    public List<PessoaFisica> getPessoasFisicas() throws SQLException {
        List<PessoaFisica> pessoasFisicas = new ArrayList<>();
        Connection connection = conectorBD.getConnection();
        try {
            PreparedStatement preparedStatement = conectorBD.getPreparedStatement(connection, "SELECT * FROM Pessoa p INNER JOIN Pessoa_Fisica pf ON p.id = pf.pessoa_id INNER JOIN Endereco e ON p.id_endereco = e.id WHERE p.id_tipo_pessoa = ?", TipoPessoa.FISICA.getTipo()
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PessoaFisica pessoaFisica = new PessoaFisica(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("telefone"),
                        new Endereco(resultSet.getInt("id_endereco"), resultSet.getString("logradouro"), resultSet.getString("bairro"), resultSet.getString("numero"), resultSet.getString("cidade"), resultSet.getString("estado"), resultSet.getString("complemento"), resultSet.getString("cep")),
                        resultSet.getString("cpf")
                );
                pessoasFisicas.add(pessoaFisica);
            }
        } catch (SQLException e) {
            System.out.println("getPessoasFisicas ->  " + e);
        } finally {
            conectorBD.close(connection);
        }
        return pessoasFisicas;
    }

    public void incluirPessoaFisica(PessoaFisica pessoaFisica) throws SQLException {
        Connection connection = conectorBD.getConnection();
        try {
            connection.setAutoCommit(false);

            int idEnderecoCadastrado = cadastrarEndereco(pessoaFisica.getEndereco(), connection);

            PreparedStatement preparedStatementPessoa = conectorBD.getPreparedStatement(connection, "INSERT INTO Pessoa (nome, telefone, id_endereco, id_tipo_pessoa) VALUES (?, ?, ?, ?)", pessoaFisica.getNome(), pessoaFisica.getTelefone(), idEnderecoCadastrado, TipoPessoa.FISICA.getTipo());
            preparedStatementPessoa.executeUpdate();

            if (preparedStatementPessoa.getGeneratedKeys().next()) {
                int id = preparedStatementPessoa.getGeneratedKeys().getInt(1);
                pessoaFisica.setId(id);
                PreparedStatement preparedStatementPessoaFisica = conectorBD.getPreparedStatement(connection, "INSERT INTO Pessoa_Fisica  (pessoa_id, cpf) VALUES (?, ?)", id, pessoaFisica.getCpf());
                preparedStatementPessoaFisica.executeUpdate();
                connection.commit();
                System.out.println("Pessoa Fisica cadastrada com sucesso!");
            } else {
                System.out.println("A instrução INSERT não gerou uma chave.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException err) {
                System.out.println("incluirPessoaFisica ->  " + err);
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

    public void alterarPessoaFisica(PessoaFisica pessoaFisica) throws SQLException {
        Connection connection = conectorBD.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatementPessoa = conectorBD.getPreparedStatement(connection, "UPDATE Pessoa SET nome = ?, telefone = ?, id_endereco = ? WHERE id = ?", pessoaFisica.getNome(), pessoaFisica.getTelefone(), pessoaFisica.getEndereco().getId(), pessoaFisica.getId());
            preparedStatementPessoa.executeUpdate();
            PreparedStatement preparedStatementPessoaFisica = conectorBD.getPreparedStatement(connection, "UPDATE Pessoa_Fisica SET cpf = ? WHERE pessoa_id = ?", pessoaFisica.getCpf(), pessoaFisica.getId());
            preparedStatementPessoaFisica.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException err) {
                System.out.println("alterarPessoaFisica ->  " + err);
            } finally {
                conectorBD.close(connection);
            }
        }
    }

    public void excluirPessoaFisica(int id) throws SQLException {
        Connection connection = conectorBD.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatementMovimentacaoCompra = conectorBD.getPreparedStatement(connection, "DELETE FROM Movimentacao_Compra WHERE id_pessoa_fisica = ?", id);
            preparedStatementMovimentacaoCompra.executeUpdate();
            PreparedStatement preparedStatementPessoaFisica = conectorBD.getPreparedStatement(connection, "DELETE FROM Pessoa_Fisica WHERE pessoa_id = ?", id);
            preparedStatementPessoaFisica.executeUpdate();
            PreparedStatement preparedStatementPessoa = conectorBD.getPreparedStatement(connection, "DELETE FROM Pessoa WHERE id = ? AND id_tipo_pessoa = 1", id);
            preparedStatementPessoa.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException err) {
                System.out.println("excluirPessoaFisica ->  " + err);
            } finally {
                conectorBD.close(connection);
            }
        }
    }
}
