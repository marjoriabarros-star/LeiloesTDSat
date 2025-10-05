import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class conectaDAO {
    
    public Connection connectDB(){
        Connection conn = null;
        
        try {
            // URL CORRIGIDA - useSSL=false resolve o erro SSL
            String url = "jdbc:mysql://localhost:3306/uc11?useSSL=false&user=root&password=l5PV3IYH@";
            conn = DriverManager.getConnection(url);
            
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro de conexão: " + erro.getMessage());
        }
        return conn;
    }
}