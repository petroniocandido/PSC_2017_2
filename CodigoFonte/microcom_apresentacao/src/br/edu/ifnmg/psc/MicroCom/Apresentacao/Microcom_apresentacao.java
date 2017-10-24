/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.psc.MicroCom.Apresentacao;

import br.edu.ifnmg.psc.MicroCom.Aplicacao.Cliente;
import br.edu.ifnmg.psc.MicroCom.Aplicacao.ViolacaoRegraNegocioException;
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
        try {
            Cliente c = new Cliente();
            c.setNome("aju");
            c.setCpf("200.160.200-78");
        } catch (ViolacaoRegraNegocioException ex) {
            System.out.println(ex.getMessage());
        }
        
        String cpf = "12345678901";
        System.out.println(cpf.substring(0,3)+"."+cpf.substring(3,6)+"."+cpf.substring(6,9)+"-"+cpf.substring(9,11));
        
    }
    
}
