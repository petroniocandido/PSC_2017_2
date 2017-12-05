/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.psc.MicroCom.Persistencia;

import br.edu.ifnmg.psc.MicroCom.Aplicacao.Cliente;
import br.edu.ifnmg.psc.MicroCom.Aplicacao.ClienteRepositorio;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author petronio
 */
public class ClienteDAO extends DAOGenerico<Cliente> implements ClienteRepositorio {

    @Override
    protected String consultaAbrir() {
        return "select id, nome, cpf, nascimento from Clientes where id = ?";
    }

    @Override
    protected String consultaInsert() {
        return "insert into Clientes(nome, cpf, nascimento) values(?,?,?)";
    }

    @Override
    protected String consultaUpdate() {
        return "update Clientes set nome = ?, cpf = ?, nascimento = ? where id = ?";
    }

    @Override
    protected String consultaDelete() {
        return "delete from Clientes where id = ?";
    }

    @Override
    protected String consultaBuscar() {
        return "select id, nome, cpf, nascimento from Clientes "; 
    }

    @Override
    protected void carregaParametros(Cliente obj, PreparedStatement consulta) {
        try {
            
            consulta.setString(1, obj.getNome());
            consulta.setString(2, obj.getCpf().replace(".", "").replace("-", ""));
            consulta.setDate(3, new Date(obj.getNascimento().getTime()));
            
            if(obj.getId() > 0)
                consulta.setLong(4, obj.getId());
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected String carregaParametrosBusca(Cliente obj){
        String sql = "";
        
        if(obj.getId() > 0)
            sql = this.filtrarPor(sql, "id", Long.toString( obj.getId() ));
        
        if(obj.getNome() != null && !obj.getNome().isEmpty())
            sql = this.filtrarPor(sql, "nome", obj.getNome());
        
        if(obj.getCpf() != null && !obj.getCpf().isEmpty())
            sql = this.filtrarPor(sql, "cpf", obj.getCpf().replace(".", "").replace("-", ""));        
        
        return sql;
    }

    @Override
    protected Cliente carregaObjeto(ResultSet dados) {
        try {
            Cliente obj = new Cliente(
                dados.getLong("id"), 
                dados.getString("nome"), 
                dados.getString("cpf"), 
                dados.getDate("nascimento") );
            return obj;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
