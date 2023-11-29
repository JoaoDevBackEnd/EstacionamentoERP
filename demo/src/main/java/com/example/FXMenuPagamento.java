package com.example;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.NonUniqueResultException;

import org.hibernate.Session;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FXMenuPagamento extends controller {

    @FXML
    private Button btn_Confirmar;

    @FXML
    private Button btn_pagar;

    @FXML
    private Button btn_voltar;

    @FXML
    private TextField txt_HoraEntrada;

    @FXML
    private TextField txt_data_saida;

    @FXML
    private TextField txt_dataentrada;

    @FXML
    private TextField txt_horaSaida;

    @FXML
    private TextField txt_nome;

    @FXML
    private TextField txt_pesquisar;

    @FXML
    private TextField txt_placa;

    @FXML
    private TextField txt_valor;

    // DEPOIS DO PAGO O CLIENTE É REMOVIDO DO BANCO *****PODE CRIAR UMA BANCO E
    // SALVAR ALTERAÇÃO EM NÍVEL DE BANCO*******
    @FXML
    void btn_clickPagar(ActionEvent event) {
        String campo = txt_pesquisar.getText();
        if (campo.isEmpty()) {
            MensagemAlerta("", "O CAMPO DE BUSTA ESTÁ VAZIO PREENCHA COM O TELEFONE ou CPF DO CLIENTE");
        } else {
            TicketsAtivos cliente = selectClienteVaga(campo);
            if (cliente != null) {
                atualizarVaga(cliente.getId_vaga());
                removerCLiente(cliente);
                MensagemAlerta("PAGO COM SUCESSO", "VALOR PAGO COM SUCESSO!!!");
            }
        }

    }

    @FXML
    void btn_clickConfirmar(ActionEvent event) {

        String campo = txt_pesquisar.getText();
        // CASO O CAMPO SEJA VAZIO
        if (campo.isEmpty()) {
            MensagemAlerta("", "O CAMPO DE BUSCA ESTÁ VAZIO DIGITE O TELEFONE OU CPF DO CLIENTE!");
        } else {

            TicketsAtivos cliente = selectClienteVaga(campo);
            if (cliente != null) {
                // CASO ENCONTRE UM CLIENTE É CHAMADO TODOS OS DADOS DE ACORDO COM A VAGA E É
                // PASSADO UM VALOR
                String dataSaida = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                String horaSaida = new SimpleDateFormat("HH:mm:ss").format(new Date());
                txt_nome.setText(cliente.getNome());
                txt_placa.setText(cliente.getPlaca());
                txt_dataentrada.setText(cliente.getDataEntrada());
                txt_HoraEntrada.setText(cliente.getHoraEntrada());
                txt_data_saida.setText(dataSaida);
                txt_horaSaida.setText(horaSaida);

                float valorPorHora = FXAdmin.valor;

                // CASO O CLIENTE SEJA UM CONVENIADO
                CadastroConveniado clienteConveniado = verificarClienteConveniado(cliente.getTelefone(),
                        cliente.getCpf());
                if (clienteConveniado != null) {
                    MensagemAlerta("CONVENIADO", "O Cliente: " + clienteConveniado.getNome() + " É CONVENIADO !!!");
                    txt_valor.setText("CONVENIADO !!!");
                    // CASO NÃO SEJA É PASSADO UM VALOR MULTIPLICADO PELA valor PASSADO PELO ADMIN
                } else {
                    if (valorPorHora != 0) {
                        float valor = valor(txt_dataentrada.getText(), dataSaida,
                                txt_HoraEntrada.getText(), horaSaida, valorPorHora);
                        String resultadoFormatado = String.format("%.2f", valor);
                        txt_valor.setText("R$ " + resultadoFormatado);
                    } else {
                        MensagemAlerta("VALOR POR HORA INVÁLIDO",
                                "Certifique-se de definir um valor válido para o preço por hora.");
                    }
                }

                System.out.println(valorPorHora);

            } else {
                MensagemAlerta("NÃO ENCONTRADA",
                        "CLIENTE NÃO ENCONTRADO CERTIFIQUESSE DE TER DIGITADO UM CAMPO VÁLIDO");
            }
        }
    }

    @FXML
    void btn_clickVoltar(ActionEvent event) throws IOException {
        Main.setRoot("MenuPrincipal");
    }

    // SELECIONA O CLIENTE DA TABELA TICKETATIVOS QUE CONTENHA telefone ou cpf
    // IGUAIS E RETORNA O CLIENTE NA VAGA
    public TicketsAtivos selectClienteVaga(String campo_texto) {
        try (Session session = HibernateIni.getSession()) {
            String hql = "FROM TicketsAtivos v WHERE v.telefone = :campo OR v.cpf = :campo";
            org.hibernate.query.Query<TicketsAtivos> query = session.createQuery(hql, TicketsAtivos.class);
            query.setParameter("campo", campo_texto);
            return query.uniqueResult();
        } catch (NonUniqueResultException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    // FUNÇÃO PARA TRATAR O VALOR DA ESTADIA, CONVERTE A DATA DE ENTRADA E A DATA DE
    // SAIDA PARA UM TIPO LONG.
    // ESSE VALOR É TRATO EM MILISSEGUNDOS ENTÃO PRECISA SER DO TIPO LONG
    // DEPOIS É CONVERTIDO EM HORAS E MINUTOS DO TIPO FLOAT
    // E FINALMENTE CALCULA O VALOR POR HORAS ESTACIONADAS
    public float valor(String DataEntrada, String DataSaida, String HoraEntrada, String HoraSaida, float valorPorHora) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date DataHoraEntrada = dateFormat.parse(DataEntrada + " " + HoraEntrada);
            Date DataHoraSaida = dateFormat.parse(DataSaida + " " + HoraSaida);
            long calculoMediaTempo = DataHoraSaida.getTime() - DataHoraEntrada.getTime();
            float calculoMediaTempoFlot = calculoMediaTempo / (1000f * 60 * 60);
            float valor = calculoMediaTempoFlot * valorPorHora;

            return valor;
        } catch (Exception e) {
            return 0;
        }

    }

    public void removerCLiente(TicketsAtivos tickets) {
        session.beginTransaction();
        session.delete(tickets);
        session.getTransaction().commit();
    }

    public void atualizarVaga(Admin vaga) {
        session.beginTransaction();
        vaga.setStatus("L");
        session.update(vaga);
        session.getTransaction().commit();
    }

    public CadastroConveniado verificarClienteConveniado(String telefone, String cpf) {
        try (Session session = HibernateIni.getSession()) {
            String hql = "FROM CadastroConveniado WHERE telefone = :telefone OR cpf = :cpf";
            org.hibernate.query.Query<CadastroConveniado> query = session.createQuery(hql, CadastroConveniado.class);
            query.setParameter("telefone", telefone);
            query.setParameter("cpf", cpf);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
