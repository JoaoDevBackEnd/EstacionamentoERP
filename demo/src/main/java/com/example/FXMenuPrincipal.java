package com.example;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class FXMenuPrincipal extends controller {

    @FXML
    private Button btn_admin;
    @FXML
    private Button btn_ConsultarClientes;

    @FXML
    private Button btn_ConsultarTicket;

    @FXML
    private Button btn_cadastrarCliente;

    @FXML
    private Button btn_contato;

    @FXML
    private Button btn_pagamento;

    @FXML
    private Text txt_vagaDisponi;

    @FXML
    private Text txt_vagaOcupada;
    @FXML
    private Button btn_novoTicket;

    @FXML
    void initialize() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::updateValues));
        timeline.setCycleCount(Timeline.INDEFINITE); // Define o ciclo para executar indefinidamente
        timeline.play();
    }

    @FXML
    void btn_ClickCadastrarCliente(ActionEvent event) throws IOException {
        Main.setRoot("tela_cadastro");
    }

    @FXML
    void btn_ClickPagamentoTicket(ActionEvent event) throws IOException {
        Main.setRoot("tela_pagamento");
    }

    @FXML
    void btn_clickConsultarClientes(ActionEvent event) throws IOException {
        Main.setRoot("tela_consulta_cliente");
    }

    @FXML
    void btn_clickConsultarTicket(ActionEvent event) throws IOException {
        Main.setRoot("tela_Consultar_Vagas");
    }

    @FXML
    void btn_clickContato(ActionEvent event) {

    }

    @FXML
    void btn_ClicknovoTicket(ActionEvent event) throws IOException {
        Main.setRoot("tela_novoTicket");
    }

    @FXML
    void btn_clickAdmin(ActionEvent event) throws IOException {
        Main.setRoot("tela_admin");
    }

    private void updateValues(ActionEvent event) {
        Long totalVagas = qtd_TotalVags();
        Long vagasOcupadas = qtd_vagasOcupadas();
        Long vagasDisponiveis = totalVagas - vagasOcupadas;
        if (totalVagas > 0) {
            txt_vagaDisponi.setText(String.valueOf(vagasDisponiveis));
            txt_vagaOcupada.setText(String.valueOf(vagasOcupadas));
        } else {
            txt_vagaDisponi.setText("N/A");
            txt_vagaOcupada.setText("N/A");
        }

    }

}
