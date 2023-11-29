package com.example;

import java.io.IOException;

import org.hibernate.Session;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FXConsultaCliente extends controller {
    @FXML
    private Button btn_Confirmar;

    @FXML
    private Button btn_Pesquisar;

    @FXML
    private Button btn_editar;

    @FXML
    private TextField txt_ConsultaCpf;

    @FXML
    private TextField txt_Nome;

    @FXML
    private TextField txt_cpf;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_endereco;

    @FXML
    private TextField txt_nunCadastro;

    @FXML
    private TextField txt_telefone;

    @FXML
    void btn_ClickVoltar(ActionEvent event) throws IOException {
        Main.setRoot("MenuPrincipal");
    }

    @FXML
    void Btn_ClickPesquisar(ActionEvent event) {
        String cpf = txt_ConsultaCpf.getText();
        CadastroConveniado con = obterCadastroPorCPF(cpf);

        if (con != null) {
            txt_Nome.setText(con.getNome());
            txt_cpf.setText(con.getCpf());
            txt_email.setText(con.getEmail());
            txt_endereco.setText(con.getEndereco());
            txt_telefone.setText(con.getTelefone());
            txt_nunCadastro.setText(String.valueOf(con.getNumCadastro()));
        } else {
            System.out.println("erro!");
        }
    }

    @FXML
    void btn_ClickConfirmar(ActionEvent event) {

    }

    @FXML
    void btn_ClickEditar(ActionEvent event) {

    }

    public CadastroConveniado obterCadastroPorCPF(String cpf) {
        try (Session session = HibernateIni.getSession()) {
            String hql = "FROM CadastroConveniado x WHERE x.cpf = :cpf";
            org.hibernate.query.Query<CadastroConveniado> query = session.createQuery(hql, CadastroConveniado.class);
            query.setParameter("cpf", cpf);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
