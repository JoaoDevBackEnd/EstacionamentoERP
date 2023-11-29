package com.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//CLASSE ADMIN É REPONSÁVEL PELA PARTE GERENCIAL DO BANCO , DAS VAGAS ELA É RESPONSÁVEL POR CRIAR NOVAS VAGAS E DAR UM VALOR POR HORA!!
@Entity
@Table(name = "vagas")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "totalVagas")
    private Long totalVagas;
    @Column(name = "StatusVAGAS")
    private String Status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalVagas() {
        return totalVagas;
    }

    public void setTotalVagas(Long totalVagas) {
        this.totalVagas = totalVagas;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Admin(Long totalVagas, String status) {
        this.totalVagas = totalVagas;
        Status = status;
        // this.id_ticketVaga = id_ticketVaga;
    }

    public Admin() {
    }
}
