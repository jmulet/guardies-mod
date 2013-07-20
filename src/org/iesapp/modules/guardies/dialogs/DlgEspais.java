/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgFestiusjava
 *
 * Created on 04-may-2011, 16:48:13
 */

package org.iesapp.modules.guardies.dialogs;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.iesapp.framework.table.CellTableState;
import org.iesapp.framework.table.MyCheckBoxRenderer;
import org.iesapp.framework.table.MyIconLabelRenderer;
import org.iesapp.framework.util.CoreCfg;
import org.iesapp.modules.guardies.util.Cfg;

/**
 *
 * @author Josep
 */
public class DlgEspais extends javax.swing.JDialog {
    private DefaultTableModel modelTable1;
    private final CoreCfg coreCfg;


    /** Creates new form DlgFaltesPrevistes */
    public DlgEspais(java.awt.Frame par, boolean modal, CoreCfg coreCfg) {
        super(par, modal);
        this.coreCfg = coreCfg;
        initComponents();
       
 
        String SQL1 = "SELECT * FROM "+ Cfg.prefix+ "espais order by id";
        try {
            Statement st =coreCfg.getMysql().createStatement();
            ResultSet rs1 =coreCfg.getMysql().getResultSet(SQL1,st);
            while (rs1!=null && rs1.next()) {
                int id = rs1.getInt("id");
                String aula = rs1.getString("aula");
                String descripcio = rs1.getString("descripcio");
                String zona_guardia = rs1.getString("zona_guardia");
                boolean uguardia = rs1.getInt("utilizable_guardia") > 0;
                boolean reservable = rs1.getInt("reservable") >0;

                CellTableState cts = new CellTableState("", id, 0);

                modelTable1.insertRow(jTable1.getRowCount(), new Object[]
                            {cts, aula, descripcio, zona_guardia, uguardia, reservable});
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DlgEspais.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                if(colIndex==0)
                return false;   //Disallow the editing of any cell
                else
                return true;
            }
        }
        ;
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editor d'espais");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        modelTable1 = new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Aula", "Descripció", "Zona guardia", "Disp. guardia",  "Disp. reserva"
            }
        );

        String[] icons = new String[] {
            "/org/iesapp/modules/guardies/icons/delete.gif"
        };

        jTable1.setModel(modelTable1);
        jTable1.getTableHeader().setReorderingAllowed(false);

        jTable1.getColumnModel().getColumn(0).setCellRenderer(new MyIconLabelRenderer(icons));
        jTable1.getColumnModel().getColumn(4).setCellRenderer(new MyCheckBoxRenderer());
        jTable1.getColumnModel().getColumn(5).setCellRenderer(new MyCheckBoxRenderer());

        JCheckBox checkBox = new JCheckBox();
        checkBox.setHorizontalAlignment(JLabel.CENTER);
        DefaultCellEditor checkBoxEditor = new DefaultCellEditor(checkBox);

        jTable1.getColumnModel().getColumn(4).setCellEditor(checkBoxEditor);
        jTable1.getColumnModel().getColumn(5).setCellEditor(checkBoxEditor);

        jTable1.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);
        jTable1.setRowHeight(32);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Aules o espais disponibles");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/exit.gif"))); // NOI18N
        jButton1.setText("Aplica i Tanca");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/insert.gif"))); // NOI18N
        jButton2.setText("Inserta");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Autogenera");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(275, 275, 275)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //insereix a la taula i crea a la base de dades
    //Tanca i aplica
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if(jTable1.isEditing()) {
            jTable1.editCellAt(0, 0);
        }

        for(int i=0; i<jTable1.getRowCount(); i++)
        {
            CellTableState cts = (CellTableState) jTable1.getValueAt(i, 0);
            int id = ( (Number) cts.getCode() ).intValue();
            String aula = (String) jTable1.getValueAt(i, 1);
            String descripcio= (String) jTable1.getValueAt(i, 2);
            String zona_guardia= (String) jTable1.getValueAt(i, 3);
            int disp_guardia = ((Boolean) jTable1.getValueAt(i, 4))? 1:0;
            int disp_reserva = ((Boolean) jTable1.getValueAt(i, 5))? 1:0;

          
            String SQL1 = "UPDATE " + Cfg.prefix + "espais SET "
                    + "aula=?, descripcio=?, zona_guardia=?, utilizable_guardia=?, "
                    + "reservable=? WHERE id='"+id+"'";

            Object[] updatedValues = new Object[]{aula, descripcio, zona_guardia,
                            disp_guardia, disp_reserva};

            int nup =coreCfg.getMysql().preparedUpdate(SQL1, updatedValues);
            
        }

        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    //Click
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
       int mcol = jTable1.getSelectedColumn();
   
       if(evt.getClickCount()==1 && mcol==0)
       {
           int conf = JOptionPane.showConfirmDialog(this, "Voleu eliminar aquesta entrada de la base de dades?", "Confirmacio",
                                      JOptionPane.YES_NO_OPTION);
           if (conf<0 || conf==1) {
                return;
            }

           int row = jTable1.getSelectedRow();

            CellTableState cts = (CellTableState) jTable1.getValueAt(row, 0);
            int id = ( (Number) cts.getCode() ).intValue();
           
           String SQL1 = "DELETE FROM "+Cfg.prefix+"espais where id='"+id+"'";
           //System.out.println(SQL1);
           int nup =coreCfg.getMysql().executeUpdate(SQL1);
           modelTable1.removeRow(row);
           
       }
    }//GEN-LAST:event_jTable1MouseClicked

    //add new espai
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

            
            String SQL0 = "SELECT MAX(id) from " + Cfg.prefix +"espais";
            int maxid = 0;
            try {
            Statement st =coreCfg.getMysql().createStatement();
            ResultSet rs1 =coreCfg.getMysql().getResultSet(SQL0,st);
            if (rs1 != null && rs1.next()) {
            
                maxid = rs1.getInt(1);
            }
            rs1.close();
            st.close();
            } catch (SQLException ex) {
            Logger.getLogger(DlgEspais.class.getName()).log(Level.SEVERE, null, ex);
            }
            maxid +=1;


            int n = jTable1.getRowCount();
            CellTableState cts = new CellTableState( "", maxid, 0);


            modelTable1.insertRow(n, new Object[]{cts, "", "", "", false, false});

            jTable1.setEditingRow(n);
            ensureVisible(n);

            String SQL1 = "INSERT INTO "+ Cfg.prefix + "espais (id, aula, descripcio, zona_guardia, utilizable_guardia, reservable) "
                      + "VALUES(?, ?, ?, ?, ?, ?)";

            Object[] updatedValues = new Object[]{maxid, "", "", "", 0, 0};
            int nup =coreCfg.getMysql().preparedUpdate(SQL1, updatedValues);
    }//GEN-LAST:event_jButton2ActionPerformed

    //Autogenera els espais
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String SQL1 = "SELECT DISTINCT aula FROM sig_horaris WHERE aula<>'' ORDER BY aula";
       
        //delete table
        while(jTable1.getRowCount()>0) {
            modelTable1.removeRow(0);
        }
        int id = 1;

        try {
            Statement st =coreCfg.getMysql().createStatement();
            ResultSet rs1 =coreCfg.getMysql().getResultSet(SQL1,st);
        while(rs1!=null & rs1.next())
        {

                String aula = rs1.getString("aula");
                CellTableState cts = new CellTableState( "", id, 0);

                modelTable1.insertRow(jTable1.getRowCount(), new Object[]{cts, aula, "", "", true, false});
                id += 1;
            }
            if(rs1 !=null) {
                rs1.close();
                st.close();
            }
          } catch (SQLException ex) {
                Logger.getLogger(DlgEspais.class.getName()).log(Level.SEVERE, null, ex);
        }

        SQL1 = "Truncate sig_espais";
        int nup =coreCfg.getMysql().executeUpdate(SQL1);

        int maxid = 0;
        int n = jTable1.getRowCount();
        for(int i=0; i<n; i++)
        {
            String aula = (String) jTable1.getValueAt(i, 1);
            String descripcio = (String) jTable1.getValueAt(i, 2);
            int guardiable = ((Boolean) jTable1.getValueAt(i, 4))? 1:0;
            int reservable = ((Boolean) jTable1.getValueAt(i, 5))? 1:0;

            SQL1 = "INSERT INTO "+ Cfg.prefix + "espais (id, aula, descripcio, zona_guardia, utilizable_guardia, reservable) "
                      + "VALUES(?, ?, ?, ?, ?, ?)";

            Object[] updatedValues = new Object[]{i+1, aula, descripcio, "", guardiable, reservable};
            nup =coreCfg.getMysql().preparedUpdate(SQL1, updatedValues);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

     private void ensureVisible( int rowIndex )
     {
            // corral into bounds
            rowIndex = Math.max( 0, Math.min( rowIndex, jTable1.getRowCount()- 1 ) );
            final Rectangle r = jTable1.getCellRect( rowIndex, 0, true );
            jTable1.scrollRectToVisible( r );
     }
      

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
   
}

