package com.example;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class FXAdmin extends controller {

    @FXML
    private Text ADMIN;

    @FXML
    private Button btn_conHora;

    @FXML
    private TextField txt_ValorHora;
    @FXML
    private Button btn_voltar;
    @FXML
    private Button confirmar;

    @FXML
    private TextField txt_NumeroVagas;
    // VARIAVEL ESTÁTICA QUE SERÁ CALCULADA O VALOR POR HORA QUE O ADMIN COLOCAR!
    public static float valor;

    @FXML
    void btn_clickHora(ActionEvent event) {
        valor = Float.parseFloat(txt_ValorHora.getText());
    }

    // FUNÇÃO PARA ATUALIZAR A QUANTIDADE DE VAGAS , PARA ELA SER VALIDA O
    // (totalVagas) Herdada de controller precisa ser igual a (vagasDisponivel)
    // Ou seja nenhum cliente deve estar estacinado para alterar o número de vagas!
    @FXML
    void btn_Confirmar(ActionEvent event) {
        if (totalVagas == vagasDisponiveis) {
            int x = 0;
            Long y = (long) x;
            int z = 10000;
            Long v = (long) z;
            removerVagasExcedentes(y, v);
            String input = txt_NumeroVagas.getText();
            if (!input.isEmpty()) {
                int totalVagas = Integer.parseInt(input);
                if (totalVagas > 0) {
                    for (int i = 1; i <= totalVagas; i++) {
                        String status = "L"; // Defina o status conforme necessário
                        Admin novaVaga = new Admin((long) i, status);
                        QtdVagas(novaVaga);
                    }
                    Create_vaga();
                } else {
                    MensagemAlerta("ERRO", "Insira um número válido de vagas!");
                }
            } else {
                MensagemAlerta("ERRO", "Campo de número de vagas está vazio!");
            }
        }

        else {
            MensagemAlerta("ERRO", "Ainda há clientes estacionados. Aguarde até todas as vagas ficarem livres!");
        }
    }

    @FXML
    void btn_clickVoltar(ActionEvent event) throws IOException {
        Main.setRoot("MenuPrincipal");
    }

    public void QtdVagas(Admin x) {
        try (Session session = HibernateIni.getSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(x);
                transaction.commit();

            } catch (Exception e) {

            }
        }

    }

    public void removerVagasExcedentes(Long limite, long totalVagas) {
        try (Session session = HibernateIni.getSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                String hql = "DELETE FROM Admin WHERE id > :limite AND id <= :totalVagas";
                int rowsAffected = session.createQuery(hql)
                        .setParameter("limite", limite)
                        .setParameter("totalVagas", totalVagas)
                        .executeUpdate();
                transaction.commit();

                System.out.println(rowsAffected + " vagas removidas com sucesso!");
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }
}
