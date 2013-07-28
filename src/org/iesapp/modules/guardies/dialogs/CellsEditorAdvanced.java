/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CellsEditorAdvanced.java
 *
 * Created on 15-abr-2011, 20:03:42
 */

package org.iesapp.modules.guardies.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.iesapp.clients.iesdigital.guardies.CellModel;
import org.iesapp.framework.table.*;
import org.iesapp.framework.util.CoreCfg;
import org.iesapp.framework.util.IconUtils;
import org.iesapp.modules.guardies.GuardiesModule;
import org.iesapp.modules.guardies.util.Cfg;
import org.iesapp.util.StringUtils;

/**
 *
 * @author Josep
 */
public class CellsEditorAdvanced extends javax.swing.JDialog {
    private final ArrayList codigs_guardies;
    private final int mselRow;
    private final int mselCol;
    private final String initialValue;
    private int currentId;
    private final String abrev;
    private DefaultTableModel modelTable1;
    private GuardiesModule parental;
    private final CoreCfg coreCfg;

    /** Creates new form CellsEditorAdvanced */
    public CellsEditorAdvanced(GuardiesModule parent, boolean modal, int row, int col, CoreCfg coreCfg) {
        super((JFrame) null, modal);
        this.coreCfg = coreCfg;
        initComponents();
        parental = parent;
        
        mselRow = row;
        mselCol = col;

        String [] opcions = new String[] {"Indeterminat", "Signat", "Falta", "Sortida"};
        Icon[] resources = new Icon[]{
            IconUtils.getIconResource(getClass().getClassLoader(), "org/iesapp/modules/guardies/icons/icon01.gif"),
            IconUtils.getIconResource(getClass().getClassLoader(), "org/iesapp/modules/guardies/icons/icon02.gif"),
            IconUtils.getIconResource(getClass().getClassLoader(), "org/iesapp/modules/guardies/icons/icon03.gif"),
            IconUtils.getIconResource(getClass().getClassLoader(), "org/iesapp/modules/guardies/icons/icon04.gif")};

     TableColumnModel cm = jTable1.getColumnModel();

     jTable1.setIntercellSpacing( new java.awt.Dimension(2,2) );
     jTable1.setGridColor(java.awt.Color.gray);
     jTable1.setShowGrid(true);

     javax.swing.JCheckBox mcheck = new javax.swing.JCheckBox();

     DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
     dtcr.setHorizontalAlignment(SwingConstants.CENTER);
     cm.getColumn(0).setCellRenderer( dtcr );

     cm.getColumn(2).setCellEditor(new MyComboBoxEditor(opcions) );
     cm.getColumn(2).setCellRenderer(new MyIconLabelRenderer(opcions, resources) );
     //cm.getColumn(2).setCellRenderer(new MyComboBoxRenderer(opcions) );

     JCheckBox checkbox = new JCheckBox("");
     checkbox.setHorizontalAlignment(SwingConstants.CENTER);

     cm.getColumn(3).setCellEditor( new DefaultCellEditor( checkbox ) ); //new JCheckBox("") ); //
     cm.getColumn(3).setCellRenderer( new MyCheckBoxRenderer() );

     cm.getColumn(4).setCellEditor(new TextAreaEditor());
     cm.getColumn(4).setCellRenderer(new TextAreaRenderer(40));

     CellModel cells[] = new CellModel[8];

     for(int i=0; i<8; i++) {
            cells[i] = (CellModel) parental.jTable1.getValueAt(row, i);
        }
     
     jLabel2.setText(cells[0].text);

     boolean feina = true;
     for(int i=1; i<8; i++)
     {
        if(cells[i].text.length() > 0)
        {
         modelTable1.addRow(new Object[]{ i+"a",cells[i].text, cells[i].status, cells[i].feina > 0, cells[i].comment });
         feina &= cells[i].feina > 0;
         //System.out.println(feina + ";"+( cells[i].feina > 0));
         }
     }

     jCheckBox1.setSelected(feina);


        codigs_guardies= new ArrayList();
        String SQL1 = "Select lloc from " + Cfg.prefix + "guardies_zones ";
        
        try {
            Statement st =coreCfg.getMysql().createStatement();
            ResultSet rs1 =coreCfg.getMysql().getResultSet(SQL1,st);
            while (rs1 != null && rs1.next()) {
                codigs_guardies.add(rs1.getString("lloc"));
            }
            if (rs1 != null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CellsEditorAdvanced.class.getName()).log(Level.SEVERE, null, ex);
        }

       currentId = -1;
       jComentari.setText("");
       abrev = cells[0].codig;
      //Febrer-2012
      //Comprova si hi ha comentaris interns sig_diari_guardies_intern
       SQL1 = "Select * from sig_signatures_comentaris where data='"+parental.ctrlDia+"' AND abrev='"+abrev+"' ";
       try {
            Statement st =coreCfg.getMysql().createStatement();
            ResultSet rs1 =coreCfg.getMysql().getResultSet(SQL1,st);
            while (rs1 != null && rs1.next()) {
                jComentari.setText(rs1.getString("text"));
                currentId = rs1.getInt("id");
            }
            if (rs1 != null){
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CellsEditorAdvanced.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        initialValue = jComentari.getText();
    
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean q = true;
                if(colIndex < 2) q = false;   //Disallow the editing

                String camp = (String) jTable1.getValueAt(rowIndex,1);
                if(codigs_guardies.contains(camp) && colIndex!=2) q = false;

                return q;
            }
        };
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComentari = new javax.swing.JTextField();

        setTitle("Editor de propietats");
        setAlwaysOnTop(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Propietats de");

        modelTable1 = new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hora", "Classe / Guàrdia", "Estat", "Feina", "Comentaris"
            }
        );
        jTable1.setModel(modelTable1);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.setRowHeight(80);
        TableColumn col = jTable1.getColumnModel().getColumn(4);
        col.setPreferredWidth(120);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("...");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/exit.gif"))); // NOI18N
        jButton1.setText("Aplica i Tanca");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Marca'ls tots");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "La feina està a la sala de guàrdies", "Demanar feina a prefectura", "El professor els va deixar feina", "La feina està a l'agenda", "El delegat té la feina" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Indeterminat", "Signat", "Falta", "Sortida" }));
        jComboBox2.setSelectedIndex(2);
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Automatitza:");

        jLabel4.setText("Comentari Intern:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, 0, 125, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, 0, 144, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComentari)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(3, 3, 3))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComentari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(3, 3, 3))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Commit changes to the main table and hide dialog
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
       if(jTable1.isEditing()) {
            jTable1.editCellAt(0, 0);
        }

       for(int mrow=0; mrow < jTable1.getRowCount(); mrow++)
       {
           String shora = (String) this.jTable1.getValueAt(mrow, 0);
           int hora = (int) Double.parseDouble( StringUtils.BeforeLast(shora,"a") );
           int stat = ((Number) this.jTable1.getValueAt(mrow, 2)).intValue();
           boolean q = (Boolean) this.jTable1.getValueAt(mrow, 3);
           int feina = 0;
           if(q) {
                feina = 1;
            }
           String area = (String) this.jTable1.getValueAt(mrow, 4);

           CellModel acell = (CellModel) parental.jTable1.getValueAt(mselRow, hora);
           acell.setFeina(feina);
           acell.setComentari(area);
           parental.jTable1.setValueAt(acell, mselRow, hora);
           int retorna = parental.alterStatus(mselRow, hora, stat, true, 1);
        }

       // Febrer 2012: Desa el comentari intern a la base de dades
       //
       if(!initialValue.equals(jComentari.getText()))
       {
           if(jComentari.getText().length() == 0)
           {
               if(currentId>0) 
               {
                   String SQL1 = "DELETE FROM sig_signatures_comentaris WHERE id="+currentId;
                   int nup =coreCfg.getMysql().executeUpdate(SQL1);
               }
           }
           else
           {
               if(currentId==-1) 
               {
                   String SQL1 = "INSERT INTO sig_signatures_comentaris (abrev,data,text) VALUES('"+abrev+"','"+parental.ctrlDia+"',?)";
                   int nup =coreCfg.getMysql().preparedUpdate(SQL1, new Object[]{jComentari.getText()});
               }
               else
               {
                   String SQL1 = "UPDATE sig_signatures_comentaris SET text=? WHERE id="+currentId;
                   int nup =coreCfg.getMysql().preparedUpdate(SQL1, new Object[]{jComentari.getText()});
               }
           }
       }
       
       this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

    if(evt.getButton()==MouseEvent.BUTTON3)
    {
//        System.out.println("mouse");
//        jPopupMenu1.show(null, evt.getXOnScreen(), evt.getYOnScreen());
//
//        Point p = evt.getPoint();
//        mselRow = jTable1.rowAtPoint(p);
//        mselCol = jTable1.columnAtPoint(p);
//
//        return;
    }

//    if(evt.getButton()==MouseEvent.BUTTON1)  //only respond to left double-click
//    {
//       int mcol = jTable1.getSelectedColumn();
//       int mrow = jTable1.getSelectedRow();
//
//       int stat = ((Number) this.jTable1.getValueAt(mrow, 2)).intValue();
//       parental.alterStatus(parental.mselRow, mcol+1, stat, true);
//
//    }

    }//GEN-LAST:event_jTable1MouseClicked

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        boolean estat = jCheckBox1.isSelected();
        for(int i=0; i<jTable1.getRowCount(); i++)
        {
            String camp = (String) jTable1.getValueAt(i, 1);
            if(!codigs_guardies.contains(camp)) {
                jTable1.setValueAt(estat, i, 3);
            }
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
         String txt = (String) jComboBox1.getSelectedItem();
         for(int i=0; i<jTable1.getRowCount(); i++)
        {
            String camp = (String) jTable1.getValueAt(i, 1);
            if(!codigs_guardies.contains(camp)) {
                jTable1.setValueAt(txt, i, 4);
            }
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        int stat = jComboBox2.getSelectedIndex();
        for(int i=0; i<jTable1.getRowCount(); i++)
        {
            //int oldstat = ((Number) jTable1.getValueAt(i, 2)).intValue();

                jTable1.setValueAt(stat, i, 2);
        }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    @Override
    protected JRootPane createRootPane() {
    JRootPane rootPane2 = new JRootPane();
    KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
    Action actionListener = new AbstractAction() {
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
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JTextField jComentari;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

   
}
