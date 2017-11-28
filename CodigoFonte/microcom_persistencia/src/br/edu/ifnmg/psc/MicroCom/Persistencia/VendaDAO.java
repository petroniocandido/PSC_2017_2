/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.psc.MicroCom.Persistencia;

import br.edu.ifnmg.psc.MicroCom.Aplicacao.ClienteRepositorio;
import br.edu.ifnmg.psc.MicroCom.Aplicacao.ProdutoRepositorio;
import br.edu.ifnmg.psc.MicroCom.Aplicacao.RepositorioBuilder;
import br.edu.ifnmg.psc.MicroCom.Aplicacao.Venda;
import br.edu.ifnmg.psc.MicroCom.Aplicacao.VendaItem;
import br.edu.ifnmg.psc.MicroCom.Aplicacao.VendaRepositorio;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author petronio
 */
public class VendaDAO extends DAOGenerico<Venda> implements VendaRepositorio {
    
    ClienteRepositorio clientes = RepositorioBuilder.getClienteRepositorio();
    ProdutoRepositorio produtos = RepositorioBuilder.getProdutoRepositorio();

    @Override
    protected String consultaAbrir() {
        return "select id, cliente, data, valortotal from vendas where id = ?";
    }

    @Override
    protected String consultaInsert() {
        return "insert into vendas(cliente, data, valortotal) values(?,?,?)";
    }

    @Override
    protected String consultaUpdate() {
        return "update vendas set cliente=?, data=?, valortotal=? where id = ?";
    }

    @Override
    protected String consultaDelete() {
        return "delete vendas where id = ?";
    }

    @Override
    protected String consultaBuscar() {
        return "select id, cliente, data, valortotal from vendas ";
    }

    @Override
    protected void carregaParametros(Venda obj, PreparedStatement consulta) {
        try {
            consulta.setLong(1, obj.getCliente().getId());
            consulta.setDate(2, new Date( obj.getData().getTime() ));
            consulta.setBigDecimal(3, obj.getValorTotal());
            
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected Venda carregaObjeto(ResultSet dados) {
        try {
            
            Venda obj = new Venda();
            obj.setId(dados.getLong("id"));
            obj.setCliente( clientes.Abrir( dados.getLong("cliente") ) );
            obj.setData( new java.util.Date( dados.getDate("data").getTime() ) );
            obj.setValorTotal(dados.getBigDecimal("valortotal"));
            
            obj.setItens( AbrirItens(obj) );
            
            return obj;
            
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private List<VendaItem> AbrirItens(Venda obj){
        try {
            // Utilizando a conexão aberta, cria um Statement (comando)
            PreparedStatement consulta = BD.getConexao().prepareStatement(
                    "select id, venda, produto, quantidade, valorunitario from vendasitens where venda = ?");
            
            // Coloca o parâmetro da consulta (id)
            consulta.setLong(1, obj.getId());
            
            // Executa a consulta select e recebe os dados de retorno
            ResultSet dados = consulta.executeQuery();
            
            List<VendaItem> itens = new ArrayList<>();
            while(dados.next())
                itens.add(carregaObjetoItem(obj, dados));
            
            return itens;
            
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
    private VendaItem carregaObjetoItem(Venda venda, ResultSet dados){
        try {
            VendaItem item = new VendaItem();
            item.setId(dados.getLong("id"));
            item.setVenda(venda);
            item.setProduto( produtos.Abrir( dados.getLong("produto")) );
            item.setQuantidade(dados.getInt("quantidade"));
            item.setValorUnitario(dados.getBigDecimal("valorunitario"));
            
            return item;
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    

    @Override
    protected String carregaParametrosBusca(Venda obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
