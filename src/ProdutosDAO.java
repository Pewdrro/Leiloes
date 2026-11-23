/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public boolean cadastrarProduto(ProdutosDTO produto){
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        try (Connection conexao = new conectaDAO().connectDB()) {
            if (conexao == null) {
                return false;
            }
            try (PreparedStatement comando = conexao.prepareStatement(sql)) {
                comando.setString(1, produto.getNome());
                comando.setInt(2, produto.getValor());
                comando.setString(3, produto.getStatus());
                comando.executeUpdate();
                return true;
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null,
                    "Não foi possível cadastrar o produto: " + erro.getMessage(),
                    "Erro ao cadastrar",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        String sql = "SELECT id, nome, valor, status FROM produtos ORDER BY id";
        listagem.clear();

        try (Connection conexao = new conectaDAO().connectDB()) {
            if (conexao == null) {
                return listagem;
            }
            try (PreparedStatement comando = conexao.prepareStatement(sql);
                 ResultSet resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    ProdutosDTO produto = new ProdutosDTO();
                    produto.setId(resultado.getInt("id"));
                    produto.setNome(resultado.getString("nome"));
                    produto.setValor(resultado.getInt("valor"));
                    produto.setStatus(resultado.getString("status"));
                    listagem.add(produto);
                }
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null,
                    "Não foi possível listar os produtos: " + erro.getMessage(),
                    "Erro ao consultar",
                    JOptionPane.ERROR_MESSAGE);
        }
        return listagem;
    }
    
    
    
        
}

