package com.example;

import java.io.IOException;
import java.util.List;
import org.hibernate.Session;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class FXConsultaVagas {

    @FXML
    private Button btn_Pesquisar;

    @FXML
    private TableView<TicketsAtivos> table_vagas;

    @FXML
    private TableColumn<TicketsAtivos, String> column_vaga;

    @FXML
    private TableColumn<TicketsAtivos, String> column_cpf;

    @FXML
    private TableColumn<TicketsAtivos, String> column_dataentrada;

    @FXML
    private TableColumn<TicketsAtivos, String> column_hora;

    @FXML
    private TableColumn<TicketsAtivos, String> column_placa;

    @FXML
    private TableColumn<TicketsAtivos, String> column_tipo;

    @FXML
    private TableColumn<TicketsAtivos, String> colun_nome;
    @FXML
    private TableColumn<TicketsAtivos, String> colun_telefone;
    @FXML
    private TextField txt_ConsultaCpf;

    @FXML
    void Btn_ClickPesquisar(ActionEvent event) {
        List<TicketsAtivos> tickets = listTickets();
        ObservableList<TicketsAtivos> ticketData = FXCollections.observableArrayList(tickets);

        colun_nome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colun_telefone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefone()));
        column_cpf.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCpf()));
        column_placa.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlaca()));
        column_tipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo_veiculo()));
        column_dataentrada
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDataEntrada()));
        column_hora.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHoraEntrada()));
        column_vaga.setCellValueFactory(cellData -> {
            TicketsAtivos ticket = cellData.getValue();
            Admin admin = ticket.getId_vaga();
            return new SimpleStringProperty(admin != null ? String.valueOf(admin.getTotalVagas()) : "");
        });
        table_vagas.setItems(ticketData); // Define os dados na tabela
    }

    @FXML
    void btn_ClickVoltar(ActionEvent event) throws IOException {
        Main.setRoot("MenuPrincipal");
    }

    public List<TicketsAtivos> listTickets() {
        Session session = HibernateIni.getSession();
        String hql = "From TicketsAtivos";
        org.hibernate.query.Query<TicketsAtivos> query = session.createQuery(hql, TicketsAtivos.class);
        return query.list();
    }

}
