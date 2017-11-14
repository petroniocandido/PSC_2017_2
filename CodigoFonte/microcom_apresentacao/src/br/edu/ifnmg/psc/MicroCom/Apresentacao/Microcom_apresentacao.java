/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.psc.MicroCom.Apresentacao;

import br.edu.ifnmg.psc.MicroCom.Aplicacao.Cliente;
import br.edu.ifnmg.psc.MicroCom.Aplicacao.ClienteRepositorio;
import br.edu.ifnmg.psc.MicroCom.Aplicacao.ViolacaoRegraNegocioException;
import br.edu.ifnmg.psc.MicroCom.Persistencia.ClienteDAO;
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
        /*
        try {
        ClienteRepositorio clientes = new ClienteDAO();
        Cliente x = new Cliente();
        x.setNome("Amanda");
        x.setCpf("123.456.789-12");
        boolean Salvar = clientes.Salvar(x);
        } catch (ViolacaoRegraNegocioException ex) {
        Logger.getLogger(Microcom_apresentacao.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        ClienteRepositorio clientes = new ClienteDAO();
        Cliente x = clientes.Abrir(1);
        
        System.out.println(x);
        
        clientes.Apagar(x);
    }
    
}
