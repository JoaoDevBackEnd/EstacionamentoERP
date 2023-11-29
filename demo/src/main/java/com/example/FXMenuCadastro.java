package com.example;

import org.hibernate.Transaction;

import java.io.IOException;
import java.util.Random;

import org.hibernate.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FXMenuCadastro extends controller {
    @FXML
    private Button btn_cancelar;

    @FXML
    private Button btn_inicio;

    @FXML
    private Button btn_salvar;

    @FXML
    private TextField txt_cpf;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_endereco;

    @FXML
    private TextField txt_fone;

    @FXML
    private TextField txt_nome;

    @FXML
    void btn_click_cancelar(ActionEvent event) {
        txt_cpf.clear();
        txt_email.clear();
        txt_endereco.clear();
        txt_fone.clear();
        txt_nome.clear();
    }

    @FXML
    void btn_click_inicio(ActionEvent event) throws IOException {
        Main.setRoot("MenuPrincipal");
    }

    @FXML
    void btn_click_salvar(ActionEvent event) {
        Random random = new Random();
        int numCadastro = 1000000 + random.nextInt(9999999);
        CadastroConveniado novoCliente = new CadastroConveniado(numCadastro, txt_nome.getText(), txt_fone.getText(),
                txt_email.getText(), txt_cpf.getText(), txt_endereco.getText());
        enviarDados(novoCliente);
        MensagemAlerta("CADASTRO CLIENTE CONVENIADO", "Cliente Cadastrado Com Sucesso!");
    }

    public void enviarDados(CadastroConveniado novocliente) {
        try (Session session = HibernateIni.getSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(novocliente);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }

        }
    }
}
