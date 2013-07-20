/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.modules.guardies.informes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.iesapp.framework.dialogs.ReportFactory;

/**
 *
 * @author Josep
 */
public class ReportingClass {
      private static final String REPORT_LLISTAT_FALTES = "professorat/llistatFaltesProfessorat";
      private static final String REPORT_LLISTAT_FALTES_TARDA = "professorat/llistatFaltesProfessoratTarda";
      private static final String REPORT_RESUM_FALTES = "professorat/resumFaltesProfessorat";
      private static final String REPORT_MENSUAL_FALTES = "professorat/resumMensualProfessorat";
      private static final String REPORT_DISPONIBILITAT = "misc/aulesDisponibilitat";
      private final ReportFactory reportFactory;
 
   

////////////////////////////////////////////////////////////////////////////////
    //Fitxa completa d'un alumne
////////////////////////////////////////////////////////////////////////////////

    public ReportingClass()
    {
            reportFactory = new ReportFactory();
            reportFactory.setReportGeneratedListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                javar.JRDialog dialog = new javar.JRDialog(javar.JRDialog.getActiveFrame(), false);
                dialog.setTitle(reportFactory.getSuitableTitle());
                dialog.setLayout(new BorderLayout());
                dialog.add((Component) reportFactory.getGeneratedReport());
                dialog.pack();
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                dialog.setSize(screenSize.width/2, screenSize.height-100);
                dialog.setLocation(screenSize.width/2, 40);
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
               // DesktopManager.addReport(reportFactory.getGeneratedReport(), reportFactory.getSuitableTitle());
            }
        });
    }

    
    public void reportLlistatFaltes(List bean, HashMap map)
    {
         reportFactory.customReport(bean, map, REPORT_LLISTAT_FALTES);
         reportFactory.generateReport();
    }

    public void reportResumFaltes(List bean, HashMap map)
    {
         reportFactory.customReport(bean, map, REPORT_RESUM_FALTES);
         reportFactory.generateReport();
    }
      
    public void reportMensualFaltes(List bean, HashMap map)
    {
         reportFactory.customReport(bean, map, REPORT_MENSUAL_FALTES);
         reportFactory.generateReport();
    }

    public void reportLlistatFaltesTarda(List list, HashMap map) {
         reportFactory.customReport(list, map, REPORT_LLISTAT_FALTES_TARDA);
         reportFactory.generateReport();         
    }

    public void reportDisponibilitat(ArrayList list, HashMap map) {
         reportFactory.customReport(list, map, REPORT_DISPONIBILITAT);
         reportFactory.generateReport();    
    }

    
}
