package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Customer;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.util.HashMap;

public class ReportFormController {
    public AnchorPane root;

    public void btnReportOnAction(ActionEvent actionEvent) throws JRException {
        JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/report/Report.jrxml"));
        JasperReport compileReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint fillReport = JasperFillManager.fillReport(compileReport, new HashMap<>(), new JREmptyDataSource(1));
        JasperViewer.viewReport(fillReport);
    }

    public void btnBeanOnAction(ActionEvent actionEvent) throws JRException {
        Customer[] customers = new Customer[3];
        customers[0] = new Customer(1, "amal", "panadura");
        customers[1] = new Customer(2, "kamal", "kandy");
        customers[2] = new Customer(3, "nimal", "galle");

        JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/report/JavaBeanArray.jrxml"));
        JasperReport compileReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint fillReport = JasperFillManager.fillReport(compileReport, new HashMap<>(), new JRBeanArrayDataSource(customers));
        JasperViewer.viewReport(fillReport);

    }

    public void btndataSourceOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/TableForm.fxml"))));
        stage.centerOnScreen();
    }

    public void btnAdapterOnAction(ActionEvent actionEvent) throws JRException {
        JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/report/DbRec.jrxml"));
        JasperReport compileReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint fillReport = JasperFillManager.fillReport(compileReport, new HashMap<>(), DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(fillReport,false);

    }

    public void btnParamsOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/JasperParamsForm.fxml"))));
        stage.centerOnScreen();
    }

    public void btnFindOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/SearchReportForm.fxml"))));
        stage.centerOnScreen();
    }
}
