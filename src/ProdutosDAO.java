import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    
    public void cadastrarProduto(ProdutosDTO produto) {
        Connection conn = null;
        PreparedStatement prep = null;
        
        try {
            // Conectar com o banco de dados
            conn = new conectaDAO().connectDB();
            
            if (conn != null) {
                // Comando SQL para inserir o produto
                String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
                prep = conn.prepareStatement(sql);
                
                // Colocar os valores no comando SQL
                prep.setString(1, produto.getNome());
                prep.setInt(2, produto.getValor());
                prep.setString(3, produto.getStatus());
                
                // Executar o comando
                int linhasAfetadas = prep.executeUpdate();
                
                if (linhasAfetadas > 0) {
                    JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        } finally {
            // FECHAR CONEXÕES DE FORMA SEGURA PARA EVITA O ERRO SSL
            try {
                if (prep != null) {
                    prep.close();
                }
            } catch (Exception e) {
                // Ignorar erro no fechamento
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                // Ignorar erro no fechamento
            }
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        Connection conn = null;
        PreparedStatement prep = null;
        ResultSet resultset = null;
        
        try {
            // Conectar com o banco
            conn = new conectaDAO().connectDB();
            
            if (conn != null) {
                // Comando SQL para buscar todos os produtos
                String sql = "SELECT * FROM produtos";
                prep = conn.prepareStatement(sql);
                resultset = prep.executeQuery();
                
                // Percorrer os resultados e adicionar na lista
                while (resultset.next()) {
                    ProdutosDTO produto = new ProdutosDTO();
                    produto.setId(resultset.getInt("id"));
                    produto.setNome(resultset.getString("nome"));
                    produto.setValor(resultset.getInt("valor"));
                    produto.setStatus(resultset.getString("status"));
                    
                    listagem.add(produto);
                }
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
        } finally {
            // FECHAR TUDO DE FORMA SEGURA
            try { if (resultset != null) resultset.close(); } catch (Exception e) {}
            try { if (prep != null) prep.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        
        return listagem;
    }
}