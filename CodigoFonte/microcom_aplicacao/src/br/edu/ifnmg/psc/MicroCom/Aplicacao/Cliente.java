/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.psc.MicroCom.Aplicacao;

import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author petronio
 */
public class Cliente implements Entidade {
    private long id;
    private String nome, cpf;
    private Date nascimento;
    private static Pattern regex_cpf = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$");

    
    public Cliente() {
    }
    
    

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws ViolacaoRegraNegocioException {
        if(nome == null || nome.isEmpty() || nome.length() < 3 || nome.length() > 200)
            throw new ViolacaoRegraNegocioException("O nome deve conter entre 3 e 200 caracteres!");
        this.nome = nome;
    }

    public String getCpf() {
        return cpf.substring(0,3)+"."+cpf.substring(3,6)+"."+cpf.substring(6,9)+"-"+cpf.substring(9,11);
    }

    public void setCpf(String cpf) throws ViolacaoRegraNegocioException {
        Matcher verificador = regex_cpf.matcher(cpf);
        if(cpf == null || cpf.isEmpty() || ! verificador.matches())
            throw new ViolacaoRegraNegocioException("O CPF deve estar no formato ###.###.###-##!");
        this.cpf = cpf.replace(".", "").replace("-", "");
    }
    
    public void setCpfSimples(String cpf){
        this.cpf = cpf;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 97 * hash + Objects.hashCode(this.nome);
        hash = 97 * hash + Objects.hashCode(this.cpf);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome;
    }
    
    
    
}
