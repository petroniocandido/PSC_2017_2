/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.psc.MicroCom.Apresentacao;

import br.edu.ifnmg.psc.MicroCom.Aplicacao.Produto;
import br.edu.ifnmg.psc.MicroCom.Aplicacao.ProdutoRepositorio;
import br.edu.ifnmg.psc.MicroCom.Aplicacao.RepositorioBuilder;
import br.edu.ifnmg.psc.MicroCom.Aplicacao.ViolacaoRegraNegocioException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author petronio
 */
public class Microcom_apresentacao {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ProdutoRepositorio produtos = RepositorioBuilder.getProdutoRepositorio();
        
        //try {
        //ClienteRepositorio clientes = new ClienteDAO();
        Produto x = new Produto();
        x.setNome("Teste");
        x.setDescricao("alguma bobagem desnecessária que Amanda sugeriu");
        x.setPreco(BigDecimal.valueOf(22.3));
        x.setEstoque(10);
        boolean Salvar = produtos.Salvar(x);
        //} catch (ViolacaoRegraNegocioException ex) {
        //Logger.getLogger(Microcom_apresentacao.class.getName()).log(Level.SEVERE, null, ex);
        //}
        /*
        
        Cliente x = new Cliente(0, "XX", "11111111111", null);
        clientes.Salvar(x);
        Cliente y = new Cliente(0, "XY", "11111111112", null);
        clientes.Salvar(y);
        Cliente z = new Cliente(0, "XX", "11111111113", null);
        clientes.Salvar(z);
        Cliente w = new Cliente(0, "XY", "11111111114", null);
        clientes.Salvar(w);
        Cliente k = new Cliente(0, "XX", "11111111115", null);
        clientes.Salvar(k);
        */
        
        /*
        Cliente filtro = new Cliente(0, "XX", null, null);
        
        List<Cliente> resultado = clientes.Buscar(filtro);
        
        for(Cliente c : resultado)
                clientes.Apagar(c);
        
        //clientes.Apagar(x);
*/
    }
    
}
