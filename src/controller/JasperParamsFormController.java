package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.util.HashMap;

public class JasperParamsFormController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;

    public void btnShowOnAction(ActionEvent actionEvent) throws JRException {
        JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/report/params.jrxml"));
        JasperReport compileReport = JasperCompileManager.compileReport(jasperDesign);
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("txtId",Integer.parseInt(txtId.getText()));
        parameters.put("txtName",txtName.getText());
        parameters.put("txtAddress",txtAddress.getText());
        JasperPrint fillReport = JasperFillManager.fillReport(compileReport, parameters, new JREmptyDataSource());
        JasperViewer.viewReport(fillReport,false);
    }
}
