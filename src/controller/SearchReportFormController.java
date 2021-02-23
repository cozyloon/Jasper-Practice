package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.util.HashMap;

public class SearchReportFormController {
    public TextField txtSearch;

    public void btnFindOnAction(ActionEvent actionEvent) throws JRException {
        JasperDesign jasperDesign = JRXmlLoader.load(this.getClass().getResourceAsStream("/report/find.jrxml"));
        JasperReport compileReport = JasperCompileManager.compileReport(jasperDesign);
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("query",(txtSearch.getText()));
        JasperPrint fillReport = JasperFillManager.fillReport(compileReport, parameters, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(fillReport,false);
    }
}
