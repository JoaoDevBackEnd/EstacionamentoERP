
package com.example;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class FXnovoTicket extends controller {

    @FXML
    private Button btn_inicio;
    @FXML
    private Button btn_confirmar;
    @FXML
    private RadioButton radio_carro;
    @FXML
    private RadioButton radio_moto;
    @FXML
    private TextField txt_cpf;
    @FXML
    private TextField txt_nome;
    @FXML
    private TextField txt_placa;
    @FXML
    private TextField txt_telefone;

    @FXML
    void btn_clickInicio(ActionEvent event) throws IOException {
        Main.setRoot("MenuPrincipal");
    }

    @FXML
    void btn_clickConfirmar(ActionEvent event) {
        String tipo = radio_carro.isSelected() ? "C" : "M";
        String dataEntra = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String horaEntra = new SimpleDateFormat("HH:mm:ss").format(new Date());

        try (Session session = HibernateIni.getSession()) {
            Transaction transaction = null;
            if (placaJaExistente(session, txt_placa.getText())) {
                MensagemAlerta("PLACA JÁ EXISTENTE", "A placa informada já está em uso.");
                return; // Retorna para evitar a criação do ticket
            }

            // Verificar se o telefone já existe ou CPF estão cadastrado essa verificação
            // E ainda verifica se os campos estão nulos
            if ((txt_cpf.getText().isEmpty() && txt_telefone.getText().isEmpty()) ||
                    (!txt_telefone.getText().isEmpty() && telefoneJaExistente(session, txt_telefone.getText())) ||
                    (!txt_cpf.getText().isEmpty() && cpfJaExistente(session, txt_cpf.getText()))) {

                MensagemAlerta("TELEFONE OU CPF JÁ EXISTENTE",
                        "O Telefone Informado ou CPF Informado já está em uso!!!");
                return;
            }

            try {
                transaction = session.beginTransaction();

                // Encontrar a primeira vaga disponível com status 'L'
                Admin vagaDisponivel = primeiraVagaDisponivel(session);
                if (vagaDisponivel != null) {
                    // Atualizar o status da vaga para "Ocupada"
                    vagaDisponivel.setStatus("O");
                    session.update(vagaDisponivel);

                    // Criar um novo ticket
                    TicketsAtivos novoTicket = new TicketsAtivos(
                            txt_nome.getText(), txt_telefone.getText(), txt_cpf.getText(),
                            tipo, txt_placa.getText(), dataEntra, horaEntra);

                    // Verificar se o cliente é conveniado
                    CadastroConveniado clienteConveniado = verificarClienteConveniado(txt_telefone.getText(),
                            txt_nome.getText());
                    if (clienteConveniado != null) {
                        novoTicket.setCpf(clienteConveniado.getCpf());
                        novoTicket.setClienteConveniado(clienteConveniado);
                    }

                    // Associa o ID da vaga ao novo ticket
                    novoTicket.setId_vaga(vagaDisponivel);

                    // Salva o novo ticket
                    session.save(novoTicket);
                    transaction.commit();
                    MensagemAlerta("CONCLUÍDO", "CADASTRO DE VAGA REALIZADO ! ");
                } else {
                    // Caso não haja vagas disponíveis
                    System.out.println("Não há vagas disponíveis no momento.");
                }
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }
    }

    // VERIFICA SE O CLIENTE É CONVENIADO
    public CadastroConveniado verificarClienteConveniado(String telefone, String nome) {
        try (Session session = HibernateIni.getSession()) {
            String hql = "FROM CadastroConveniado WHERE telefone = :telefone AND nome = :nome";
            org.hibernate.query.Query<CadastroConveniado> query = session.createQuery(hql, CadastroConveniado.class);
            query.setParameter("telefone", telefone);
            query.setParameter("nome", nome);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ATUALIZA A VAGA PARA 'O' NO BANCO DE DADOS
    public void updateStatusVaga(Session session, Long idVaga, String status) {
        try {
            Admin vaga = session.get(Admin.class, idVaga);
            vaga.setStatus(status);
            session.update(vaga);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // FUNÇÃO PARA PEAR A PRIMEIRA VAGA COM STATUS L
    public Admin primeiraVagaDisponivel(Session session) {
        String hql = "FROM Admin v WHERE v.Status = 'L' ORDER BY v.id ASC";
        org.hibernate.query.Query<Admin> query = session.createQuery(hql, Admin.class);
        query.setMaxResults(1);
        return query.uniqueResult();
    }

    public boolean placaJaExistente(Session session, String placa) {
        try {
            String hql = "SELECT COUNT(*) FROM TicketsAtivos WHERE placa = :placa";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("placa", placa)
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean telefoneJaExistente(Session session, String telefone) {
        try {
            String hql = "SELECT COUNT(*) FROM TicketsAtivos WHERE telefone = :telefone";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("telefone", telefone)
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cpfJaExistente(Session session, String cpf) {
        try {
            String hql = "SELECT COUNT(*) FROM TicketsAtivos WHERE cpf = :cpf";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
