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
import java.text.SimpleDateFormat;
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
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

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
            consulta.setDate(2, new Date(obj.getData().getTime()));
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
            obj.setCliente(clientes.Abrir(dados.getLong("cliente")));
            obj.setData(new java.util.Date(dados.getDate("data").getTime()));
            obj.setValorTotal(dados.getBigDecimal("valortotal"));

            obj.setItens(AbrirItens(obj));

            return obj;

        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<VendaItem> AbrirItens(Venda obj) {
        try {
            // Utilizando a conexão aberta, cria um Statement (comando)
            PreparedStatement consulta = BD.getConexao().prepareStatement(
                    "select id, venda, produto, quantidade, valorunitario from vendasitens where venda = ?");

            // Coloca o parâmetro da consulta (id)
            consulta.setLong(1, obj.getId());

            // Executa a consulta select e recebe os dados de retorno
            ResultSet dados = consulta.executeQuery();

            List<VendaItem> itens = new ArrayList<>();
            while (dados.next()) {
                itens.add(carregaObjetoItem(obj, dados));
            }

            return itens;

        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    private VendaItem carregaObjetoItem(Venda venda, ResultSet dados) {
        try {
            VendaItem item = new VendaItem();
            item.setId(dados.getLong("id"));
            item.setVenda(venda);
            item.setProduto(produtos.Abrir(dados.getLong("produto")));
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
        String sql = "";
        if (obj.getId() > 0) {
            sql = this.filtrarPor(sql, "id", Long.toString(obj.getId()));
        }

        if (obj.getData() != null) {
            sql = this.filtrarPor(sql, "data", df.format(obj.getData()));
        }

        if (obj.getCliente() != null) {
            sql = this.filtrarPor(sql, "cliente", Long.toString(obj.getCliente().getId()));
        }

        return sql;
    }

    @Override
    public boolean Salvar(Venda obj) {
        if (super.Salvar(obj)) {
            
            long id = obj.getId();
            
            if(id == 0){
            
                try {
                    String pegaid = "select max(id) from vendas where cliente = ? and data = ?";
                    
                    PreparedStatement consultaid = BD.getConexao().prepareStatement(pegaid);
                    
                    consultaid.setLong(1, obj.getCliente().getId());
                    consultaid.setDate(2, new java.sql.Date(obj.getData().getTime()));
                    
                    ResultSet retorno = consultaid.executeQuery();
                    
                    retorno.next();
                    
                    id = retorno.getLong(1);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
            
            try {
                String sql = "insert into vendasitens(venda, produto, quantidade, valorunitario) values(?,?,?,?)";

                for (VendaItem i : obj.getItens()) {

                    PreparedStatement consulta = BD.getConexao().prepareStatement(sql);

                    consulta.setLong(1, id);
                    consulta.setLong(2, i.getProduto().getId());
                    consulta.setInt(3, i.getQuantidade());
                    consulta.setBigDecimal(4, i.getValorUnitario());
                    
                    consulta.executeUpdate();

                }

                return true;
            } catch (SQLException ex) {
                Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return false;
    }

}
