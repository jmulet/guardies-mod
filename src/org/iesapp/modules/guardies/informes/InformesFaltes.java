/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InformesFaltes.java
 *
 * Created on 12-abr-2011, 18:53:48
 */

package org.iesapp.modules.guardies.informes;

import com.toedter.calendar.JSpinnerDateEditor;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import jxl.*;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.iesapp.framework.table.CellTableState;
import org.iesapp.framework.table.MyIconLabelRenderer;
import org.iesapp.framework.table.TextAreaRenderer;
import org.iesapp.framework.util.IconUtils;
import org.iesapp.modules.guardies.GuardiesModule;
import org.iesapp.modules.guardies.table.CellRendererInformes;
import org.iesapp.modules.guardies.util.Cfg;
import org.iesapp.modules.guardies.util.ControlData;
import org.iesapp.util.DataCtrl;
import org.iesapp.util.StringUtils;


/**
 *
 * @author Josep
 */
public class InformesFaltes extends javar.JRDialog {
    private ArrayList<InfoStruct> mResum;
    private final Cfg cfg;


    /** Creates new form InformesFaltes :: Default constructor */
    public InformesFaltes(Frame par, boolean modal, Cfg cfg) {
        super(par,modal);
        this.cfg = cfg;
        startUp(null, null);
        
    }

    /** Creates new form InformesFaltes :: Selects one teacher in one month */
    public InformesFaltes(Frame par, boolean modal, String abrev, java.util.Date udate, int torn, Cfg cfg) {
        super(par,modal);
        this.cfg = cfg;
        startUp(abrev, udate);
        if(torn>0) {
            jComboBox1.setSelectedIndex(1);
        }
    }


    private void startUp(String abrev, java.util.Date udate)
    {
        this.setIconImage(new ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/informesIcon.gif")).getImage());

        initComponents();
        
         jTable1.setIntercellSpacing( new java.awt.Dimension(2,2) );
         jTable1.setGridColor(java.awt.Color.gray);
         jTable1.setShowGrid(true);

        // atenció aixo dona problemes si no s'ha inicialitzat mai aquesta part
        // en el fitxer signaturesGUI.jar, cal que es defineixi sempre al entrar.
        int nlen = GuardiesModule.abrev2prof.size();
        llistaProfes =  new String[nlen];
        int i = 0;
        int pos = -1;
        for(String ky : GuardiesModule.abrev2prof.keySet())
        {
            String nom = GuardiesModule.abrev2prof.get(ky);
            llistaProfes[i] = nom + " ["+ky+"]";
            if(ky.equals(abrev)) {
                pos = i;
            }
            i +=1;
        }

        jList1.setListData(llistaProfes);
        jList2.setListData(llistaProfes);

        if(nlen>0)
        {
        
        if(abrev == null )
        {
            jList1.setSelectedIndex(0);
            jList2.setSelectedIndex(llistaProfes.length-1);
            jList2.ensureIndexIsVisible(llistaProfes.length-1) ;
        }
        else
        {
            jList1.setSelectedIndex(pos);
            jList2.setSelectedIndex(pos);
            jList1.ensureIndexIsVisible(pos);
            jList2.ensureIndexIsVisible(pos);
        }
        }

        ControlData cd1 = null;
        ControlData cd2 = null;
        if(udate == null)
        {
               Calendar cal  = Calendar.getInstance();
               cal.set(Calendar.DAY_OF_MONTH, 1);
               
               cd1 =new ControlData(cal.getTime(),cfg);
               cd2 =new ControlData(cfg);
        }
        else
        {
            cd1 = new ControlData(udate,cfg);
            cd2 = new ControlData(udate,cfg);
        }
        jDateChooser1.setDate(cd1.getDate());
        jDateChooser2.setDate(cd2.getDate());
        sqlDate1 = cd1.getDataSQL();
        sqlDate2 = cd2.getDataSQL();

        doReport();


        //this is to listen to changes in date:
        jDateChooser1.getDateEditor().addPropertyChangeListener(
        new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if ("date".equals(evt.getPropertyName())) {

                ControlData cd = new ControlData(jDateChooser1.getDate(),cfg);
                sqlDate1 = cd.getDataSQL();
                doReport();
        }
        }
        });


        //this is to listen to changes in date:
        jDateChooser2.getDateEditor().addPropertyChangeListener(
        new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if ("date".equals(evt.getPropertyName())) {

                ControlData cd = new ControlData(jDateChooser2.getDate(),cfg);
                sqlDate2 = cd.getDataSQL();
                doReport();
        }
        }
        });

    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jDateChooser1 = new com.toedter.calendar.JDateChooser(null, null, null, new JSpinnerDateEditor());
        java.util.Locale mlocale = new java.util.Locale( "ca", "ES", "" );
        jDateChooser2 = new com.toedter.calendar.JDateChooser(null, null, null, new JSpinnerDateEditor());
        java.util.Locale mlocale2 = new java.util.Locale( "ca", "ES", "" );
        jCheckBox3 = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }
        } ;
        jComboBox2 = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Informes / Faltes");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Selecciona rangs");

        jLabel2.setText("Professorat de:");

        jLabel3.setText("   a");

        jLabel4.setText("Data de:");

        jLabel5.setText("   a");

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Inclou No Signat");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jCheckBox2.setText("Inclou Sortides");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.setAutoscrolls(false);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList2.setAutoscrolls(false);
        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jList2);

        jLabel7.setText("Torn");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Matí", "Tarda" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jDateChooser1.setLocale(mlocale);

        jDateChooser2.setLocale(mlocale2);

        jCheckBox3.setSelected(true);
        jCheckBox3.setText("Inclou Justificat");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jCheckBox1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBox2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                                .addComponent(jCheckBox3))
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, 0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel2))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel3)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jCheckBox1)
                                .addComponent(jCheckBox3)
                                .addComponent(jCheckBox2))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7))))
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        jLabel6.setText("Tipus d'informe");

        modelTable1 = new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Professor/a", "Dia", "1a hora", "2a hora", "3a hora",
                "4a hora", "5a hora", "6a hora", "7a hora"
            }
        );
        jTable1.setModel(modelTable1);
        jTable1.getTableHeader().setReorderingAllowed(false);

        jTable1.setRowHeight(28);

        jTable1.getColumnModel().getColumn(0).setPreferredWidth(160);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(90);

        //Columna de data + comentari
        Icon[] resources = new Icon[]{IconUtils.getBlankIcon(),
            IconUtils.getIconResource(getClass().getClassLoader(), "org/iesapp/modules/guardies/icons/postit.gif")};
        jTable1.getColumnModel().getColumn(1).setCellRenderer(
            new MyIconLabelRenderer(resources));

        //Columnes de clases
        for(int i=2; i<9; i++)
        jTable1.getColumnModel().getColumn(i).setCellRenderer(new CellRendererInformes());
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Llistat de faltes", "Resum de faltes" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iesapp/guardies/icons/save.gif"))); // NOI18N
        jButton1.setText("Desa");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iesapp/guardies/icons/exit.gif"))); // NOI18N
        jButton2.setText("Tanca");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iesapp/guardies/icons/view.gif"))); // NOI18N
        jButton3.setText("Informe");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 558, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 605, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 879, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
           doReport();
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
}//GEN-LAST:event_jButton2ActionPerformed

    private void doReport()
    {
          this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            //delete table
            while(jTable1.getRowCount()>0)
            {
                modelTable1.removeRow(0);
            }

            int type = jComboBox2.getSelectedIndex();
            switch(type)
            {
            case(0): doLlistat(); break;
            case(1): doResum(); break;
            }
            
         this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }


    private void doLlistat()
    {
            int torn = jComboBox1.getSelectedIndex();

            String taula = "";

            if(torn == 0)
            {
                taula = "signatures ";
                 for(int i=2; i<9; i++) {
                jTable1.getColumnModel().getColumn(i).setHeaderValue( (i-1)+"a hora");
            }
            }
            else
            {
                taula = "signatures_tarda ";
                 for(int i=2; i<9; i++) {
                jTable1.getColumnModel().getColumn(i).setHeaderValue( (i+6)+"a hora");
            }
            }

            int i1= jList1.getSelectedIndex();
            int i2= jList2.getSelectedIndex();

            listllistatFaltes = new ArrayList<BeanLlistatFaltes>();

            for(int i= i1; i<=i2; i++) //vaig professor per professor
            {
            String item = StringUtils.AfterLast(llistaProfes[i] ,"[");
            String abrev = StringUtils.BeforeLast(item ,"]").trim();


             String SQL1 = "SELECT prof.ABREV, sig.DATA, H1, H2, H3, H4, H5, H6, H7, com.text FROM " + Cfg.prefix + taula + " AS sig " +
                    " INNER JOIN " + Cfg.prefix + "professorat AS prof ON" +
                    " sig.ABREV=prof.ABREV "+
                    " LEFT JOIN sig_signatures_comentaris as com ON (com.abrev=prof.abrev and com.data=sig.data) "+
                    " WHERE sig.DATA>='" + sqlDate1 +
                    "' AND sig.DATA<='" + sqlDate2 + "' AND prof.ABREV='"+ abrev + "' ORDER BY sig.DATA";
           
           
            try {
                 Statement st = cfg.getCoreCfg().getMysql().createStatement();
                 ResultSet rs1 = cfg.getCoreCfg().getMysql().getResultSet(SQL1,st);
                 int nel = 0;
                 while ( rs1!=null && rs1.next()) {

                        BeanLlistatFaltes aline = new BeanLlistatFaltes();

                        int[] hs = new int[7];
                        for(int k=1; k<8; k++) 
                        {
                            hs[k - 1] = rs1.getInt("H" + k);

                        }
                        String data = rs1.getString("DATA");
                        String comtext = rs1.getString("text");
                       
                        Object[] fila = esFalta(hs, abrev, data); //comprova si es falta. A mes, si/no justificada
                        
                        //determina si aquesta fila nomes conte camps justificats
                          boolean solojustificat = true;
                          if(fila != null && !jCheckBox3.isSelected()) //no incloguis els justificats
                          {          
                                    //String tmp="";
                                    for(int ij=2; ij<9; ij++)
                                    {
                                        solojustificat &= ( fila[ij].equals(-1) || fila[ij].equals(3) || fila[ij].equals(4) );           
                                       // tmp += fila[ij]+":";
                                    }
                                    
                                    //System.out.println(tmp +" Solojustifat is "+solojustificat+" for "+abrev+"; "+data);
                                    solojustificat = !solojustificat;
                          }
                        
                        if( fila != null && solojustificat)
                        {
                               
                                fila[0] = GuardiesModule.abrev2prof.get(abrev)+" ["+abrev+"]";
                                CellTableState cts = new CellTableState(StringUtils.Sql2EUData(data, Cfg.DBTYPE),-1,
                                        comtext==null?0:1);
                                cts.setTooltip(comtext);
                                fila[1] = cts;
                                modelTable1.addRow(fila);
                        
                                aline.setNombre( StringUtils.BeforeLast((String) fila[0],"["));
                                aline.setData( ((CellTableState) fila[1]).getText() );
                                aline.setComentari(comtext);
                                
                                boolean showInReport = true;
                                
                                for(int ij=2; ij<9; ij++)
                                {
                                    showInReport |= fila[ij].equals(0) || fila[ij].equals(1) || fila[ij].equals(3);
                                
                                    String msg = "";
                                    switch(((Number) fila[ij]).intValue())
                                    {
                                        case -1 : msg=""; break;
                                        case 0 :  msg="No Sig."; break;
                                        case 1 :  msg="Falta"; break;
                                        case 2 :  msg="Sortida"; break;
                                        case 3 :  msg="No Sig.-J"; break;
                                        case 4 :  msg="Falta-J"; break;
                                    }
                                    aline.setHn(msg, ij-1);
                                    
                                }
                                                                                            
                                if(showInReport) {
                                listllistatFaltes.add(aline);
                            }
                     }

                    nel += 1;
                 }
             if(rs1!=null) {
                 rs1.close();
                 st.close();
             }

            } catch (SQLException ex) {
                Logger.getLogger(InformesFaltes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void doResum()
    {
            int torn = jComboBox1.getSelectedIndex();
 
            boolean incNoSignat = jCheckBox1.isSelected();
            boolean incSortida = jCheckBox2.isSelected();

            String taula = "";

            if(torn == 0)
            {
                taula = "signatures ";
                
            }
            else
            {
                taula = "signatures_tarda ";
              
            }

            int i1= jList1.getSelectedIndex();
            int i2= jList2.getSelectedIndex();

            mResum = new ArrayList<InfoStruct>();

            for(int i= i1; i<=i2; i++) //vaig professor per professor dins del rang
            {
            String item = StringUtils.AfterLast(llistaProfes[i] ,"[");
            String abrev = StringUtils.BeforeLast(item ,"]").trim();


             
//            String SQL1 = "SELECT prof.ABREV, DATA, H1, H2, H3, H4, H5, H6, H7 FROM " + Cfg.prefix + taula + " AS sig " +
//                    "INNER JOIN " + Cfg.prefix + "professorat AS prof ON" +
//                    " sig.ABREV=prof.ABREV WHERE DATA>=" + GuardiesModule.dl + sqlDate1 + GuardiesModule.dl +
//                    " AND DATA<=" + GuardiesModule.dl + sqlDate2 + GuardiesModule.dl + " AND prof.ABREV='"+ abrev + "'";

            
             String SQL1 = "    SELECT "+
                           "      prof.ABREV, "+
                           "      sig.DATA, "+
                           "      H1, "+
                           "      H2, "+
                           "      H3, "+
                           "      H4, "+
                           "      H5, "+
                           "      H6, "+
                           "      H7, "+
                           "     CASE WHEN ISNULL(just.justificats) THEN 0 ELSE just.justificats END AS justificats   "+
                           "    FROM  "+
                           "     sig_signatures AS sig  "+
                           "      INNER JOIN "+
                           "      sig_professorat AS prof  "+
                           "      ON sig.ABREV = prof.ABREV  "+
                           "     LEFT JOIN "+
                           "     ( "+
                           "            SELECT DATA, COUNT(id) AS justificats FROM sig_justificat WHERE abrev='"+abrev+"' GROUP BY DATA "+
                           "     ) AS just  "+
                           "     ON (just.DATA = sig.DATA ) "+
                           "    WHERE sig.DATA>='"+sqlDate1 +"' AND sig.DATA<='"+sqlDate2 +"' AND prof.ABREV = '"+abrev+"'  ";

             
            //System.out.println(SQL1); 
             
            

     
            try {
                
                 Statement st = cfg.getCoreCfg().getMysql().createStatement();
                 ResultSet rs1 = cfg.getCoreCfg().getMysql().getResultSet(SQL1,st);
                
                 int justificats = 0;
                 int nFalta = 0; 
                 int nNoSignat = 0;
                 int nSortida = 0; 
                 String msg = "";
                 InfoStruct info = new InfoStruct();
                 while (rs1!=null && rs1.next()) {

                        abrev = rs1.getString("ABREV");
                        justificats += rs1.getInt("justificats");

                        int nNoSignat_dia = 0;
                        int nFalta_dia = 0;
                        int nSortida_dia = 0;
                        

                        for(int k=1; k<8; k++)
                        {
                           int hs = rs1.getInt("H"+k);

                           if(hs == 0)
                           {
                               nNoSignat_dia += 1;
                            }
                            else if(hs == 2)
                           {
                               nFalta_dia += 1;
                           }
                           else if(hs ==3)
                           {
                               nSortida_dia += 1;
                           }
                        }
  
                        nFalta += nFalta_dia;
                        nNoSignat += nNoSignat_dia;
                        nSortida += nSortida_dia;
                        int nTotal = nFalta_dia;
                        if(incNoSignat) {
                        nTotal += nNoSignat_dia;
                    }
                        if(incSortida) {           
                        nTotal += nSortida_dia;
                    }
                        if(nTotal != 0) {
                        msg += StringUtils.Sql2EUData(rs1.getString("DATA"), Cfg.DBTYPE) + "("+ nTotal +");  ";
                    }           
                 }
                  
                 info.setProfessor(GuardiesModule.abrev2prof.get(abrev));
                 info.setNfalta(nFalta);
                 info.setNjustificats(justificats);
                 info.setNnosignat(nNoSignat);
                 info.setNsortida(nSortida);
                 info.setTxt(msg);
                 
                 if(msg.length() != 0) {
                     mResum.add(info);
                 }
                 
                if(rs1!=null) {
                    rs1.close();
                    st.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(InformesFaltes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
        //Ja tenc la informacio; ara volcam el mapa en la grid
         for(int i= 0; i<mResum.size(); i++) //vaig professor per professor dins del rang
         {
            InfoStruct info = mResum.get(i);
            
            modelTable1.addRow(new Object[]
                  { info.getProfessor(), "" + info.getNjustificats(), ""+ info.getNnosignat(),
                    "" + info.getNfalta(), ""+ info.getNsortida(), info.getTxt() }
            );
            
        }
           

    }

// Exporta les dades a un fitxer EXCEL
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        int type = jComboBox2.getSelectedIndex();
        int torn = jComboBox1.getSelectedIndex();

        String label ="";
        if (torn == 0) {
            label = "Mati-";
        }
        else {
            label = "Tarda-";
        }

        FileDialog fd = new FileDialog(this,
                                  "Desa taula com document Excel...",
                                   FileDialog.SAVE);
                       fd.setLocationRelativeTo(null);
	               fd.setVisible(true);
	  String file = fd.getFile();
	  String dir = fd.getDirectory();
          if(file == null || dir == null) {
            return;
        }
          if( !StringUtils.AfterLast(file, ".").trim().equals("xls") ) {
            file +=".xls";
        }

        try {
           WritableWorkbook workbook = Workbook.createWorkbook(new File(dir+file));
          WritableSheet sheet = null;

           int ncol = 0;
           if(type == 1)
           {
             sheet =  workbook.createSheet("Resum-Faltes-"+label, 0);
          //headers
           sheet.addCell(  new Label(0, 0, "Professor/a")  );
           sheet.addCell(  new Label(1, 0, "Total faltes")  );
           sheet.addCell(  new Label(2, 0, "Total no signat")  );
           sheet.addCell(  new Label(3, 0, "Total falta")  );
           sheet.addCell(  new Label(4, 0, "Total sortida")  );
           sheet.addCell(  new Label(5, 0, "Detall dies")  );

           ncol = 6;

           for(int i=0; i<jTable1.getRowCount(); i++)
           {

               sheet.addCell(new Label(0, i+1, (String) jTable1.getValueAt(i, 0)) );
               for(int j=1; j<5; j++){
                //sheet.addCell(new Label(j, i+1, (String) jTable1.getValueAt(i, j)) );
                String sval = (String) jTable1.getValueAt(i, j);
                int val = (int) Double.parseDouble(sval);
                jxl.write.Number number;
                number = new jxl.write.Number(j, i+1, val);
                sheet.addCell(number);
                }
                sheet.addCell(new Label(5, i+1, (String) jTable1.getValueAt(i, 5)) );

           }


           }
           else if(type == 0)
           {
               sheet =  workbook.createSheet("Llistat-Faltes-"+label, 0);
           //headers
           sheet.addCell(  new Label(0, 0, "Professor/a")  );
           sheet.addCell(  new Label(1, 0, "Dia")  );
           sheet.addCell(  new Label(2, 0, "1a Hora")  );
           sheet.addCell(  new Label(3, 0, "2a Hora")  );
           sheet.addCell(  new Label(4, 0, "3a Hora")  );
           sheet.addCell(  new Label(5, 0, "4a Hora")  );
           sheet.addCell(  new Label(6, 0, "5a Hora")  );
           sheet.addCell(  new Label(7, 0, "6a Hora")  );
           sheet.addCell(  new Label(8, 0, "7a Hora")  );
           ncol = 9;

           for(int i=0; i<jTable1.getRowCount(); i++)
           {
               for(int j=0; j<ncol; j++){
                sheet.addCell(new Label(j, i+1, (String) jTable1.getValueAt(i, j)) );
               }
           }


           }

      
           for(int x=0;x<ncol;x++)
            {
            CellView cell=sheet.getColumnView(x);
            cell.setAutosize(true);
            sheet.setColumnView(x, cell);
            }

           workbook.write();
           workbook.close();

        } catch (IOException ex) {
             Logger.getLogger(InformesFaltes.class.getName()).log(Level.SEVERE, null, ex);
        }
         catch (WriteException ex) {
             Logger.getLogger(InformesFaltes.class.getName()).log(Level.SEVERE, null, ex); }


        try {
          Desktop.getDesktop().open(new File(dir+file));
        } catch (IOException ex) {
           // MyLogger.log("No es pot obrir "+ cfg.doc , MyLogger.logWARNING);
            
        }

      
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        doReport();
    }//GEN-LAST:event_jList1MouseClicked

    private void jList2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MouseClicked
        doReport();
    }//GEN-LAST:event_jList2MouseClicked

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
       doReport();
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
      doReport();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
            //Tipus de report
            int type = jComboBox2.getSelectedIndex();
            setTableModel(type);
            doReport();

    }//GEN-LAST:event_jComboBox2ActionPerformed

    //Double click (justifica / no justifica) la falta
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        int type = jComboBox2.getSelectedIndex();
        if(type >0) {
            return;
        } //(no funciona en mode resum)

        if(evt.getClickCount()==2)
        {
            int col = jTable1.getSelectedColumn();
            int row = jTable1.getSelectedRow();

                     
            if(col==0)
            {
                for(int i=2; i<9; i++) {
                    changeJustification(row,i);
                }
            }
            else if(col > 1)
            {
                changeJustification(row,col);
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    
    //Genera l'informe jasper a partir de la taula
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       
        //Convé cridar perque es generi de nou la llista del bean
       doReport(); 
        
       ReportingClass rc = new ReportingClass();
       HashMap map = new HashMap();
       map.put("datainici", new DataCtrl(jDateChooser1.getDate()).getDiaMesComplet());
       map.put("datafi", new DataCtrl(jDateChooser2.getDate()).getDiaMesComplet());
       
       if(jComboBox2.getSelectedIndex()==0)
       {
           if(jComboBox1.getSelectedIndex()==0) {
                rc.reportLlistatFaltes(listllistatFaltes, map);
            }  
           else {  
                rc.reportLlistatFaltesTarda(listllistatFaltes, map);
            }  
       }
       else
       {
            rc.reportResumFaltes(mResum, map); 
       }
      
       
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        doReport();
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void changeJustification(int row, int col)
    {
            int stat = ((Number) jTable1.getValueAt(row, col)).intValue();
            if(stat<0  || stat==2) {
            return;
        }
            
            String abrev = (String) jTable1.getValueAt(row, 0);
            abrev = StringUtils.AfterLast(abrev, "[");
            abrev = StringUtils.BeforeLast(abrev, "]").trim();

            String data = ((CellTableState) jTable1.getValueAt(row, 1)).getText();
            data = StringUtils.EUData2Sql(data);

                if(stat==0) {
            stat = 3;
        }
                else if(stat == 1) {
            stat = 4;
        }
                else if(stat == 4) {
            stat = 1;
        }
                else if(stat == 3) {
            stat = 0;
        }

                jTable1.setValueAt(stat, row, col);

                int hora = col - 1;
                if(jComboBox1.getSelectedIndex()==1) {
            hora += 7;
        }

                //s'ha justificat
                if(stat==3 || stat==4)
                {
                    String SQL1 = "INSERT INTO "+ Cfg.prefix +"justificat "+
                            " (abrev, data, hora) VALUES('"+abrev+"', '"+data+"', '"+hora+"')";

                    int nup = cfg.getCoreCfg().getMysql().executeUpdate(SQL1);
                    //System.out.println("es posa:: "+SQL1+";"+nup);
                }

                //s'ha llevat una justificacio
                if(stat==0 || stat ==1)
                {
                    String SQL1 = "DELETE FROM "+ Cfg.prefix +"justificat "+
                            " WHERE abrev='"+abrev+"' AND data='"+data+"' AND hora='"+hora+"'";

                    int nup = cfg.getCoreCfg().getMysql().executeUpdate(SQL1);
                    //System.out.println("es lleva:: "+SQL1+";"+nup);
                }


    }

    private Object[] esFalta(int[] hs, String abrev, String data)
    {

        boolean incsurt = jCheckBox2.isSelected();
        boolean incnosign = jCheckBox1.isSelected();
        int torn = jComboBox1.getSelectedIndex();

        Object[] obj = null; // new Object[7];
        boolean falta = false;
        for(int i=0; i<7; i++)
        {
            if(incsurt)
            {
                if(incnosign)
                {
                    if(hs[i] == 0 || hs[i] > 1)
                    {
                        falta = true;
                        break;
                    }
                }
                else
                {
                    if( hs[i] > 1)
                    {
                        falta = true;
                        break;
                    }
                }
            }
            else
            {
                if(incnosign)
                {
                    if(hs[i] == 0 || hs[i] == 2)
                    {
                        falta = true;
                        break;
                    }
                }
                else
                {
                    if( hs[i] == 2)
                    {
                        falta = true;
                        break;
                    }
                }

            }
        }

        if(falta)
        {

            obj = new Object[9];
            for(int i=0; i<7; i++)
            {
                int hora = i+1;
                if(torn==1) {
                    hora += 7;
                }
                
                //comprovem si la trobam a la taula de justificants
                String SQL1 = "Select * from sig_justificat "+
                    " where abrev='"+abrev+"' AND data='"+ data + 
                    "' AND hora='"+hora+"'";
                ResultSet rs2 =null;

                boolean justificat = false;
                try {

                     Statement st = cfg.getCoreCfg().getMysql().getConnection().createStatement();
                     rs2 = st.executeQuery(SQL1);

                    if (rs2 != null && rs2.next()) {
                        justificat = true;
                    }
                    rs2.close();
                    st.close();
                } catch (SQLException ex) {
                    Logger.getLogger(InformesFaltes.class.getName()).log(Level.SEVERE, null, ex);
                }



                if(hs[i]==0)
                {
                      if(justificat) {
                        obj[i+2] = 3;
                    } //no signat Justificat
                      else {
                        obj[i+2] = 0;
                    } //no signat NO Justificat
                }
                else if(hs[i] == 2)
                {
                    if(justificat) {
                        obj[i + 2] = 4;
                    } //falta justificada
                    else {
                        obj[i + 2] = 1;
                    } //falta no justificada
                }
                else if(hs[i] == 3 && incsurt)
                {
                    obj[i + 2] = 2; //sortida
                }
                else
                {
                    obj[i + 2] = -1; //hora buida o OK
                }
            }

        }
        return obj;
    }

    private void setTableModel(int type)
    {

        if(type==0)
        {
            modelTable1 = new javax.swing.table.DefaultTableModel(
                    new Object [][] {

                    },
                    new String [] {
                        "Professor/a", "Dia", "1a hora", "2a hora", "3a hora",
                        "4a hora", "5a hora", "6a hora", "7a hora"
                    }
                );

                jTable1.setModel(modelTable1);
                jTable1.getTableHeader().setReorderingAllowed(false);

                jTable1.setRowHeight(28);
                jTable1.getColumnModel().getColumn(0).setPreferredWidth(160);

                for(int i=2; i<9; i++) {
                jTable1.getColumnModel().getColumn(i).setCellRenderer(new CellRendererInformes());
            }
                jScrollPane1.setViewportView(jTable1);

        }
        else
        {
             modelTable1 = new javax.swing.table.DefaultTableModel(
                    new Object [][] {

                    },
                    new String [] {
                        "Professor/a", "Justificats", "No signat", "Faltes", "Sortides",
                        "Detall dies"
                    }
                );

                jTable1.setModel(modelTable1);
                jTable1.getTableHeader().setReorderingAllowed(false);

                jTable1.setRowHeight(28);


                jTable1.getColumnModel().getColumn(0).setPreferredWidth(160);
                jTable1.getColumnModel().getColumn(5).setPreferredWidth(300);


                DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
                dtcr.setHorizontalAlignment(SwingConstants.CENTER);
                for(int i=1; i<5; i++) {
                jTable1.getColumnModel().getColumn(i).setCellRenderer(dtcr);
            }



                jTable1.getColumnModel().getColumn(5).setCellRenderer(new TextAreaRenderer());
                jScrollPane1.setViewportView(jTable1);


        }
        
         jTable1.setIntercellSpacing( new java.awt.Dimension(2,2) );
         jTable1.setGridColor(java.awt.Color.gray);
         jTable1.setShowGrid(true);


    }

    @Override
        protected JRootPane createRootPane() {
        JRootPane rootPane2 = new JRootPane();
        KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
        AbstractAction actionListener = new AbstractAction() {
          public void actionPerformed(ActionEvent actionEvent) {
            setVisible(false);
          }
        } ;
        InputMap inputMap = rootPane2.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(stroke, "ESCAPE");
        rootPane2.getActionMap().put("ESCAPE", actionListener);

        return rootPane2;
      }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    private String[] llistaProfes;
    private DefaultTableModel   modelTable1;
    private String sqlDate1;
    private String sqlDate2;
    private static ArrayList<BeanLlistatFaltes> listllistatFaltes;

}
