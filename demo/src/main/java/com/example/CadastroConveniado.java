package com.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// CLASSE RESPONSÁVEL PELO CADASTRAMENTO DE NOVOS CLIENTES CONVENIADOS
@Entity
@Table(name = "clienteconveniado")
public class CadastroConveniado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "numCadastro")
    private int numCadastro;
    @Column(name = "nome")
    private String nome;
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "email")
    private String email;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "endereço")
    private String endereco;

    public CadastroConveniado() {

    }

    public CadastroConveniado(int numCadastro, String nome, String telefone, String email, String cpf,
            String endereco) {

        this.numCadastro = numCadastro;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
        this.endereco = endereco;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumCadastro() {
        return numCadastro;
    }

    public void setNumCadastro(int numCadastro) {
        this.numCadastro = numCadastro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "NUMERO DE CADASTRO: " + numCadastro +
                "NOME CLIENTE:" + nome +
                "CPF: " + cpf + "TELEFONE: " + telefone + "Endereco:" + endereco + " Email:" + email + "";
    }
}
