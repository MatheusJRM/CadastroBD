package cadastro.model.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class ConectorBD {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=loja;encrypt=false;trustServerCertificate=true";
    private static final String USERNAME = "loja";
    private static final String PASSWORD = "1234567890";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    public PreparedStatement getPreparedStatement(Connection connection, String sql, Object... params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement;
    }

    public void close(PreparedStatement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar o Statement: " + e.getMessage());
            }
        }
    }

    public ResultSet getSelect(Connection connection, String sql, Object... params) throws SQLException {
        PreparedStatement preparedStatement = getPreparedStatement(connection, sql, params);
        return preparedStatement.executeQuery();
    }

    public void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar o ResultSet: " + e.getMessage());
            }
        }
    }
}
