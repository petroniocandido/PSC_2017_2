/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.psc.MicroCom.Apresentacao;

import br.edu.ifnmg.psc.MicroCom.Aplicacao.Entidade;

/**
 *
 * @author petronio
 */
public class FormBuscar<T extends Entidade> extends javax.swing.JInternalFrame {
    
    private T filtro;
    private FormEditar editar;

    public T getFiltro() {
        return filtro;
    }

    public void setFiltro(T filtro) {
        this.filtro = filtro;
    }

    public FormEditar getEditar() {
        return editar;
    }

    public void setEditar(FormEditar editar) {
        this.editar = editar;
    }
    
    
}
