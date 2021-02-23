package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Customer;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import tm.CustomerTM;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class TableFormController {
    public AnchorPane root;
    public TableView<CustomerTM> tblCustomer;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;


    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        try {
            loadAllCustomer();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void loadAllCustomer() throws SQLException {

        ObservableList<CustomerTM> oblist = FXCollections.observableArrayList();

        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM jasper.customer ");
        ResultSet resultSet = stm.executeQuery();

        while (resultSet.next()) {

            CustomerTM tm = new CustomerTM(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("address"));
            oblist.add(tm);
            System.out.println(tm);
        }

        tblCustomer.setItems(oblist);

    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/ReportForm.fxml"))));
        stage.centerOnScreen();
    }

    public void btnReportOnAction(ActionEvent actionEvent) throws JRException {
        JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/report/JavaBeanArray.jrxml"));
        JasperReport compileReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint fillReport = JasperFillManager.fillReport(compileReport, new HashMap<>(), new JRBeanCollectionDataSource(tblCustomer.getItems()));
        JasperViewer.viewReport(fillReport, false);
    }


}
