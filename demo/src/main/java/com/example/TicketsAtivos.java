package com.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "novoticket")
public class TicketsAtivos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "nome")
    private String nome;
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "tipo_veiculo")
    private String tipo_veiculo;
    @Column(name = "placa")
    private String placa;
    @Column(name = "dataEntrada")
    private String dataEntrada;
    @Column(name = "horaentrada")
    private String horaEntrada;
    @ManyToOne
    @JoinColumn(name = "id_vaga")
    private Admin id_vaga;
    @ManyToOne
    @JoinColumn(name = "id_cliente_conveniado")
    private CadastroConveniado clienteConveniado;

    public CadastroConveniado getClienteConveniado() {
        return clienteConveniado;
    }

    public void setClienteConveniado(CadastroConveniado clienteConveniado) {
        this.clienteConveniado = clienteConveniado;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Admin getId_vaga() {
        return id_vaga;
    }

    public void setId_vaga(Admin id_vaga) {
        this.id_vaga = id_vaga;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getTipo_veiculo() {
        return tipo_veiculo;
    }

    public void setTipo_veiculo(String tipo_veiculo) {
        this.tipo_veiculo = tipo_veiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(String dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public TicketsAtivos(String nome, String telefone, String cpf, String tipo_veiculo, String placa,
            String dataEntrada,
            String horaEntrada) {
        this.nome = nome;
        this.telefone = telefone;
        this.tipo_veiculo = tipo_veiculo;
        this.placa = placa;
        this.dataEntrada = dataEntrada;
        this.horaEntrada = horaEntrada;
        this.cpf = cpf;

    }

    public TicketsAtivos() {

    }

    @Override
    public String toString() {
        return "Nome: " + this.nome +
                "Telefone: " + this.telefone +
                "PLACA: " + this.placa +
                "TIPO: " + this.tipo_veiculo +
                "Hora de entrada: " + this.horaEntrada +
                "Data de entrada: " + this.dataEntrada;
    }
}
