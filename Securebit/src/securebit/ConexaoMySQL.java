package securebit;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {
    
    // Substitua "root" e "" pelo seu usuário e senha do MySQL local, caso sejam diferentes
    private static final String URL = "jdbc:mysql://localhost:3306/securebit_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Usarprorocket!"; 

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o MySQL: " + e.getMessage());
            return null;
        }
    }
}