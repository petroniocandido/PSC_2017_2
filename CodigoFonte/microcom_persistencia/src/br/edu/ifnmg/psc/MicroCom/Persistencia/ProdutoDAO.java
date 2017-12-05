/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.psc.MicroCom.Persistencia;

import br.edu.ifnmg.psc.MicroCom.Aplicacao.Produto;
import br.edu.ifnmg.psc.MicroCom.Aplicacao.ProdutoRepositorio;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author petronio
 */
public class ProdutoDAO extends DAOGenerico<Produto> implements ProdutoRepositorio {

    @Override
    protected String consultaAbrir() {
        return "select id, nome, descricao, preco, estoque from produtos where id = ?";
    }

    @Override
    protected String consultaInsert() {
        return "insert into produtos(nome, descricao, preco, estoque) values(?,?,?,?)";
    }

    @Override
    protected String consultaUpdate() {
        return "update produtos set nome=?, descricao=?, preco=?, estoque=? from produtos where id = ?";
    }

    @Override
    protected String consultaDelete() {
        return "delete from produtos where id = ?";
    }

    @Override
    protected String consultaBuscar() {
        return "select id, nome, descricao, preco, estoque from produtos ";
    }

    @Override
    protected void carregaParametros(Produto obj, PreparedStatement consulta) {
        try {
            consulta.setString(1, obj.getNome());
            consulta.setString(2, obj.getDescricao());
            consulta.setBigDecimal(3, obj.getPreco());
            consulta.setInt(4, obj.getEstoque());
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    @Override
    protected String carregaParametrosBusca(Produto obj) {
        String sql = "";
        
        if(obj.getId() > 0)
            sql = this.filtrarPor(sql, "id", Long.toString( obj.getId() ));
        
        if(obj.getNome() != null && !obj.getNome().isEmpty())
            sql = this.filtrarPor(sql, "nome", obj.getNome());
        
        return sql;
    }

    @Override
    protected Produto carregaObjeto(ResultSet dados) {
        try {
            Produto obj = new Produto();
            obj.setId(dados.getLong("id"));
            obj.setNome(dados.getString("nome"));
            obj.setDescricao(dados.getString("descricao"));
            obj.setPreco(dados.getBigDecimal("preco"));
            obj.setEstoque(dados.getInt("estoque"));
            
            return obj;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
