/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.psc.MicroCom.Persistencia;

import br.edu.ifnmg.psc.MicroCom.Aplicacao.Entidade;
import br.edu.ifnmg.psc.MicroCom.Aplicacao.Repositorio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.Query;

/**
 *
 * @author petronio
 */
public abstract class DAOGenerico<T extends Entidade> implements Repositorio<T> {
    
    protected Connection conexao;

    protected abstract String consultaAbrir();
    protected abstract String consultaInsert();
    protected abstract String consultaUpdate();
    protected abstract String consultaDelete();
    protected abstract String consultaBuscar();
    
    protected abstract void carregaParametros(T obj, PreparedStatement consulta);
    protected abstract T carregaObjeto(ResultSet dados);
    
    @Override
    public boolean Salvar(T obj) {
        try {
            String sql = "";
            
            // Verifica se o objeto já foi salvo no BD
            if(obj.getId() == 0) {
                
                //Se o id == 0 então pega a consulta de inserção
                sql = this.consultaInsert();
                
            } else {
                
                //Se o id > 0 então pega a consulta de atualização
                sql = this.consultaUpdate();
            }
            
            // Utilizando a conexão existente cria um Statement (comando)
            PreparedStatement consulta = BD.getConexao().prepareStatement(sql);
            
            // Carrega os parâmetros da consulta
            this.carregaParametros(obj, consulta);
            
            // Executa a consulta
            boolean resultado = consulta.executeUpdate() > 0;
            
            // Se a consulta obteve êxito e o objeto acabou de ser inserido
            // if(resultado && obj.getId() == 0){
                
            //} else 
            return resultado;
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAOGenerico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAOGenerico.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public T Abrir(long id) {
        try {
            // Pega a consulta para abrir (select)
            String sql = this.consultaAbrir();
            
            // Utilizando a conexão aberta, cria um Statement (comando)
            PreparedStatement consulta = BD.getConexao().prepareStatement(sql);
            
            // Coloca o parâmetro da consulta (id)
            consulta.setLong(1, id);
            
            // Executa a consulta select e recebe os dados de retorno
            ResultSet dados = consulta.executeQuery();
            
            // Se houverem registros
            if(dados.next())
                return this.carregaObjeto(dados); // Carrega o objeto com os dados retornados
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAOGenerico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAOGenerico.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    @Override
    public boolean Apagar(T obj) {
        try {
            // Pega a consulta para abrir (select)
            String sql = this.consultaDelete();
            
            // Utilizando a conexão aberta, cria um Statement (comando)
            PreparedStatement consulta = BD.getConexao().prepareStatement(sql);
            
            // Coloca o parâmetro da consulta (id)
            consulta.setLong(1, obj.getId());
            
            // Executa a consulta
            return consulta.executeUpdate() > 0;
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAOGenerico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAOGenerico.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    public List<T> Buscar(T filtro) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
