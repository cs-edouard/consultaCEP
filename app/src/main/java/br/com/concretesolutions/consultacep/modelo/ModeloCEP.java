package br.com.concretesolutions.consultacep.modelo;

import java.io.Serializable;

public class ModeloCEP implements Serializable {

    private String cep;
    private String tipoDeLogradouro;
    private String logradouro;
    private String bairro;
    private String cidade;

    @Override
    public String toString() {
        return "CEP:\n" +
                cep + " - " +
                tipoDeLogradouro + ' ' + logradouro + '\n' +
                bairro + ' ' + cidade + '\n' +
                estado;
    }

    private String estado;

    // GETTERs & SETTERs ---------------------------------
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTipoDeLogradouro() {
        return tipoDeLogradouro;
    }

    public void setTipoDeLogradouro(String tipoDeLogradouro) {
        this.tipoDeLogradouro = tipoDeLogradouro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
