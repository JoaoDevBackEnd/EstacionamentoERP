package com.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notafiscal")
public class NotaFiscal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "dataSaida")
    private String dataSaida;
    @Column(name = "horaSaida")
    private String horaSaida;
    @Column(name = "valor")
    private Float valor;
    @OneToOne
    @JoinColumn(name = "id_ticket")
    private TicketsAtivos id_ticket;

    public NotaFiscal(String dataSaida, String horaSaida, Float valor, TicketsAtivos id_ticket) {
        this.dataSaida = dataSaida;
        this.horaSaida = horaSaida;
        this.valor = valor;
        this.id_ticket = id_ticket;
    }

    public NotaFiscal() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(String dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(String horaSaida) {
        this.horaSaida = horaSaida;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public TicketsAtivos getId_ticket() {
        return id_ticket;
    }

    public void setId_ticket(TicketsAtivos id_ticket) {
        this.id_ticket = id_ticket;
    }
}
