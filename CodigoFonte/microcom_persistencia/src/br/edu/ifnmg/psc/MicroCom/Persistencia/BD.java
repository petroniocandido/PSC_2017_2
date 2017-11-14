/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.psc.MicroCom.Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author petronio
 */
public class BD {
    
    private static Connection conexao;
    
    public static Connection getConexao() throws ClassNotFoundException, SQLException{
        // Se a conexão ainda não estiver iniciada
        if(conexao == null){
            
            // Carrega o driver do MySQL na memória
            Class.forName("com.mysql.jdbc.Driver");
            
            // o Gerenciador de Drivers abre uma conexão com o SGBD a partir da connection string
            conexao = DriverManager.getConnection("jdbc:mysql://localhost/microcom", "root", "root");
        }
        return conexao;
    }
    
}
