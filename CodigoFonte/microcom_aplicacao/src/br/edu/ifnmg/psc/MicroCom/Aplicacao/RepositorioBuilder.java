/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.psc.MicroCom.Aplicacao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author petronio
 */
public class RepositorioBuilder {
    
    private static InputStream arquivo;  // Representa o arquivo físico no disco
    private static Properties prop;      // Responsável por carregar as configurações dentro do arquivo
    
    
    static {
        try {
            arquivo = RepositorioBuilder.class.getResourceAsStream("/config.properties"); //new FileInputStream("config.properties");
            prop = new Properties();
            prop.load(arquivo);
            
        } catch (IOException ex) {
            
            Logger.getLogger(RepositorioBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private static ClienteRepositorio cliente;
    
    public static ClienteRepositorio getClienteRepositorio(){
        if(cliente == null){
            try {
                
                // Carrega a classe
                Class obj = Class.forName(prop.getProperty("ClienteRepositorio"));
                
                // Cria uma nova instância da classe
                cliente = (ClienteRepositorio)obj.newInstance();
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(RepositorioBuilder.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(RepositorioBuilder.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(RepositorioBuilder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cliente;
    }
    
    
    private static ProdutoRepositorio produto;
    
    public static ProdutoRepositorio getProdutoRepositorio(){
        if(produto == null){
            try {
                
                // Carrega a classe
                Class obj = Class.forName(prop.getProperty("ProdutoRepositorio"));
                
                // Cria uma nova instância da classe
                produto = (ProdutoRepositorio)obj.newInstance();
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(RepositorioBuilder.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(RepositorioBuilder.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(RepositorioBuilder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return produto;
    }
    
}
