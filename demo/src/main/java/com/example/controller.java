package com.example;

import org.hibernate.Session;
import org.hibernate.Transaction;

import javafx.scene.control.Alert;

// CLASSE RESPONSÁVEL POR DAR UPDATE NO MENU PRINCIPAL E SUAS VÁRIAVEIS SERÁ REUTILIZADAS PARA OUTRAS CLASSES SEM A NECESSIDADE DA CRIAÇÃO DE ALGO NOVO
public class controller {

    Session session = HibernateIni.getSession();
    Transaction transaction = null;
    Long totalVagas = qtd_TotalVags();
    Long vagasOcupadas = qtd_vagasOcupadas();
    Long vagasDisponiveis = totalVagas - vagasOcupadas;

    // COMO A CLASSE CONTROLER É UMA CLASSE QUE FUNCIONARA COM EXTENDS ESSA FUNÇÃO
    // SERÁ UTIL PARA REUTILIZAR CÓDIGO CASO DESEJA ENVIAR ALGUMA TELA DE MENSAGEM.
    public void MensagemAlerta(String mensagemTitulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(mensagemTitulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    // FUNÇÃO PARA ATUALIZAR EM TEMPO REAL AS VAGAS DISPONÍVEL OCUPADAS E LIVRES.
    public Long qtd_TotalVags() {
        String hql = "SELECT MAX(totalVagas) FROM Admin";
        org.hibernate.query.Query<Long> query = session.createQuery(hql, Long.class);
        return query.uniqueResult();
    }

    public Long qtd_vagasLivres() {
        String hql = "SELECT COUNT(*) FROM Admin WHERE StatusVAGAS = 'L'";
        org.hibernate.query.Query<Long> query = session.createQuery(hql, Long.class);
        return query.uniqueResult();
    }

    public Long qtd_vagasOcupadas() {
        String hql = "SELECT COUNT(*) FROM Admin WHERE StatusVAGAS = 'O'";
        org.hibernate.query.Query<Long> query = session.createQuery(hql, Long.class);
        return query.uniqueResult();
    }

    public void Create_vaga() {
        try (Session session = HibernateIni.getSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                for (int i = 0; i <= totalVagas; i++) {
                    int y = i;
                    Long x = Long.valueOf(y);
                    Admin admin = new Admin();
                    admin.setId(x);
                    admin.setStatus("L");
                    session.save(admin);
                }

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
