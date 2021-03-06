/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.modules.guardies.dialogs.horaris;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.iesapp.framework.table.MyCheckBoxRenderer;
import org.iesapp.framework.util.CoreCfg;
import org.iesapp.util.StringUtils;

/**
 *
 * @author Josep
 */
public class GestioPerfils extends javax.swing.JDialog {
    private DefaultTableModel modelTable1;
    private final CoreCfg coreCfg;
    private DefaultComboBoxModel modelCombo1;

    /**
     * Creates new form GestioPerfils
     */
    public GestioPerfils(java.awt.Frame parent, boolean modal, CoreCfg coreCfg) {
        super(parent, modal);
        this.coreCfg = coreCfg;
        initComponents();
        jButton1.setEnabled(false);
        
        
        loadPerfilsCombo();
        loadTable();
    }

    private void loadPerfilsCombo()
    {
        modelCombo1 = new DefaultComboBoxModel();
        modelCombo1.addElement("ACTUAL");
        String SQL1 = "SELECT DISTINCT perfil FROM sig_perfils_horaris ORDER BY perfil";
        try {
            Statement st = coreCfg.getMysql().createStatement();
            ResultSet rs1 = coreCfg.getMysql().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                modelCombo1.addElement(rs1.getString(1));
            }
            rs1.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(GestioPerfils.class.getName()).log(Level.SEVERE, null, ex);
        }
        jComboBox1.setModel(modelCombo1);
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int row, int col)
            {
                return col==0;
            }
        };
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestió de perfils");

        jLabel1.setText("Perfils");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        modelTable1 = new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Tria", "Professor", "Abrev", "N. Classes", "N. Guardies"
            }
        );
        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(modelTable1);
        jTable1.setRowHeight(32);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable1.getColumnModel().getColumn(0).setCellRenderer(new MyCheckBoxRenderer() );
        jTable1.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(40);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(40);
        jScrollPane1.setViewportView(jTable1);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/refresh.gif"))); // NOI18N
        jButton1.setText("Restaura els seleccionats");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/delete.gif"))); // NOI18N
        jButton2.setText("Esborra");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/insert.gif"))); // NOI18N
        jButton3.setText("Crea un perfil amb els seleccionats");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Tots");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        int row = jComboBox1.getSelectedIndex();
        if(row<0)
        {
            return;
        }
        jButton1.setEnabled(row>0);
        jButton3.setEnabled(row==0);
        jCheckBox1.setSelected(false);
        loadTable();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String nomPerfil = (String) jComboBox1.getSelectedItem();
        int nsel = 0;
        for(int i=0; i<jTable1.getRowCount(); i++)
        {
            if((Boolean) jTable1.getValueAt(i, 0))
            {
                String abrev = (String) jTable1.getValueAt(i, 2);
                nsel += 1;
            }
        }  
        if(nsel==0)
        {
            JOptionPane.showMessageDialog(this, "No hi ha res que restaurar.\nNo heu seleccionat cap professor");
        }
        else
        {
            Object[] options = {"No", "Sí"};
            int conf = JOptionPane.showOptionDialog(this, "Es canviaran els horaris actuals de " + nsel + " professors.\nVoleu continuar?",
                    "Confirmació", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (conf != 1) {
                return;
            }

            int nup = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                if ((Boolean) jTable1.getValueAt(i, 0)) {
                    String abrev = (String) jTable1.getValueAt(i, 2);
                    String SQL1 = "DELETE FROM sig_horaris WHERE prof='" + abrev + "'";
                    coreCfg.getMysql().executeUpdate(SQL1);

                    //Copia els horaris del perfil
                    SQL1 = "SELECT * FROM sig_perfils_horaris WHERE perfil='" + nomPerfil + "' AND prof='" + abrev + "'";
                    try {
                        Statement st = coreCfg.getMysql().createStatement();
                        ResultSet rs1 = coreCfg.getMysql().getResultSet(SQL1, st);
                        while (rs1 != null && rs1.next()) {
                            String prof = rs1.getString("prof");
                            String asig = rs1.getString("asig");
                            int curso = rs1.getInt("curso");
                            String nivel = rs1.getString("nivel");
                            String grupo = rs1.getString("grupo");
                            String aula = rs1.getString("aula");
                            int dia = rs1.getInt("dia");
                            int hora = rs1.getInt("hora");
                            String SQL2 = "INSERT INTO sig_horaris (prof, asig, curso, nivel, grupo, aula, dia, hora) "
                                    + "VALUES(?,?,?,?,?,?,?,?)";
                            nup +=coreCfg.getMysql().preparedUpdate(SQL2, new Object[]{prof, asig, curso, nivel, grupo, aula, dia, hora});
                        }
                        rs1.close();
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(GestioPerfils.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    SQL1 = "DELETE FROM sig_guardies WHERE abrev='" + abrev + "'";
                    coreCfg.getMysql().executeUpdate(SQL1);

                    //Copia els perfils de guardies
                    SQL1 = "SELECT * FROM sig_perfils_guardies WHERE perfil='" + nomPerfil + "' AND abrev='" + abrev + "'";
                    try {
                        Statement st = coreCfg.getMysql().createStatement();
                        ResultSet rs1 = coreCfg.getMysql().getResultSet(SQL1, st);
                        while (rs1 != null && rs1.next()) {
                            String lloc = rs1.getString("lloc");
                            int dia = rs1.getInt("dia");
                            int hora = rs1.getInt("hora");
                            String SQL2 = "INSERT INTO sig_guardies (abrev, lloc, dia, hora) VALUES(?,?,?,?)";
                            nup +=coreCfg.getMysql().preparedUpdate(SQL2, new Object[]{abrev, lloc, dia, hora});
                        }
                        rs1.close();
                        st.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(GestioPerfils.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
               
            }
             
            JOptionPane.showMessageDialog(this, "Finalitzat. S'han modificat "+nup+" entrades.");
            jComboBox1.setSelectedItem("ACTUAL");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    //Crea un perfil amb els seleccionats
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-H:m:ss");
        String nomPerfil = "PERFIL"+sdf.format(new java.util.Date());
        int nsel = 0;
        String cond = "(";
        for(int i=0; i<jTable1.getRowCount(); i++)
        {
            if((Boolean) jTable1.getValueAt(i, 0))
            {
                String abrev = (String) jTable1.getValueAt(i, 2);
                nsel += 1;
                cond += "'"+abrev+"',";
            }
        }
        cond = StringUtils.BeforeLast(cond, ",")+")";
        if(nsel==0)
        {
            JOptionPane.showMessageDialog(this, "És impossible crear el perfil.\nNo heu seleccionat cap professor");
            return;
        }
        
        int nup = 0;
        //Copia els perfils dels horaris
        String SQL1 = "SELECT * FROM sig_horaris WHERE prof IN "+cond;
        try {
            Statement st = coreCfg.getMysql().createStatement();
            ResultSet rs1 = coreCfg.getMysql().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                String prof = rs1.getString("prof");
                String asig = rs1.getString("asig");
                int curso = rs1.getInt("curso");
                String nivel = rs1.getString("nivel");
                String grupo = rs1.getString("grupo");
                String aula = rs1.getString("aula");
                int dia = rs1.getInt("dia");
                int hora = rs1.getInt("hora");
                String SQL2 = "INSERT INTO sig_perfils_horaris (perfil, prof, asig, curso, nivel, grupo, aula, dia, hora) "
                        + "VALUES(?,?,?,?,?,?,?,?,?)";
                nup +=coreCfg.getMysql().preparedUpdate(SQL2,new Object[]{nomPerfil,prof,asig,curso,nivel,grupo,aula,dia,hora});
            }
            rs1.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(GestioPerfils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Copia els perfils de guardies
        SQL1 = "SELECT * FROM sig_guardies WHERE abrev IN "+cond;
        try {
            Statement st = coreCfg.getMysql().createStatement();
            ResultSet rs1 = coreCfg.getMysql().getResultSet(SQL1,st);
            while(rs1!=null && rs1.next())
            {
                String abrev = rs1.getString("abrev");
                String lloc = rs1.getString("lloc");
                int dia = rs1.getInt("dia");
                int hora = rs1.getInt("hora");
                String SQL2 = "INSERT INTO sig_perfils_guardies (perfil, abrev, lloc, dia, hora) VALUES(?,?,?,?,?)";
                nup +=coreCfg.getMysql().preparedUpdate(SQL2,new Object[]{nomPerfil,abrev,lloc,dia,hora});
            }
            rs1.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(GestioPerfils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JOptionPane.showMessageDialog(this, "Finalitzat. S'han creat "+nup+" entrades.");
        loadPerfilsCombo();
        loadTable();
        jComboBox1.setSelectedItem(nomPerfil);
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        boolean q = jCheckBox1.isSelected();
        for(int i=0; i<jTable1.getRowCount(); i++)
        {
            jTable1.setValueAt(q, i, 0);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         int row = jComboBox1.getSelectedIndex();
        if(row==0)
        {
            return;
        }
        String profile = (String) jComboBox1.getSelectedItem();
        Object[] options = {"No", "Sí"};
        int conf = JOptionPane.showOptionDialog(this, "Segur que voleu eliminar aquest perfil?",
                "Confirmació", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (conf == 1) {
            int nup = 0;
            String SQL1 = "DELETE FROM sig_perfils_horaris WHERE perfil='"+profile+"'";
            nup = coreCfg.getMysql().executeUpdate(SQL1);
            SQL1 = "DELETE FROM sig_perfils_guardies WHERE perfil='"+profile+"'";
            nup += coreCfg.getMysql().executeUpdate(SQL1);
            
            JOptionPane.showMessageDialog(this, "S'han eliminat "+nup+" entrades.");
            loadPerfilsCombo();
            loadTable();
            jComboBox1.setSelectedIndex(0);
            jButton2.setEnabled(false);
            jButton1.setEnabled(false);
            jButton3.setEnabled(true);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void loadTable()
    {
        while(jTable1.getRowCount()>0)
        {
            modelTable1.removeRow(0);
        }
        
        int row = jComboBox1.getSelectedIndex();
        if(row>0)
        {
            //////////////////////////////////////////////////////////////LOAD FROM PERFIL        
            String perfil = (String) jComboBox1.getSelectedItem();
            HashMap<String,Integer> mapClases = new HashMap<String,Integer>();
            String SQL0 ="SELECT prof, COUNT(id) FROM sig_perfils_horaris WHERE perfil='"+perfil+"' GROUP BY prof";
            try {
                Statement st = coreCfg.getMysql().createStatement();
                ResultSet rs1 = coreCfg.getMysql().getResultSet(SQL0,st);
                while(rs1!=null && rs1.next())
                {
                   mapClases.put(rs1.getString(1), rs1.getInt(2));
                }
                rs1.close();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(GestioPerfils.class.getName()).log(Level.SEVERE, null, ex);
            }
            HashMap<String,Integer> mapGuardies = new HashMap<String,Integer>();
            SQL0 ="SELECT abrev, COUNT(id) FROM sig_perfils_guardies WHERE perfil='"+perfil+"' GROUP BY abrev";
            try {
                Statement st = coreCfg.getMysql().createStatement();
                ResultSet rs1 = coreCfg.getMysql().getResultSet(SQL0,st);
                while(rs1!=null && rs1.next())
                {
                   mapGuardies.put(rs1.getString(1), rs1.getInt(2));
                }
                rs1.close();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(GestioPerfils.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String SQL1 = "SELECT DISTINCT p.abrev, p.nombre FROM sig_perfils_horaris as per"
                    + " INNER JOIN sig_professorat as p ON p.abrev=per.prof WHERE per.perfil='"+perfil+"' ORDER BY p.nombre";
            try {
                Statement st = coreCfg.getMysql().createStatement();
                ResultSet rs1 = coreCfg.getMysql().getResultSet(SQL1,st);
                while(rs1!=null && rs1.next())
                {
                    String abrev = rs1.getString(1);
                    int nc = mapClases.containsKey(abrev)?mapClases.get(abrev):0;
                    int ng = mapGuardies.containsKey(abrev)?mapGuardies.get(abrev):0;
                    modelTable1.addRow(new Object[]{false, rs1.getString(2),abrev, nc, ng});
                }
                rs1.close();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(GestioPerfils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            
            /////////////////////////////////////////////////////////////////////////////ACTUAL
             HashMap<String,Integer> mapClases = new HashMap<String,Integer>();
            String SQL0 ="SELECT prof, COUNT(id) FROM sig_horaris GROUP BY prof";
            try {
                Statement st = coreCfg.getMysql().createStatement();
                ResultSet rs1 = coreCfg.getMysql().getResultSet(SQL0,st);
                while(rs1!=null && rs1.next())
                {
                   mapClases.put(rs1.getString(1), rs1.getInt(2));
                }
                rs1.close();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(GestioPerfils.class.getName()).log(Level.SEVERE, null, ex);
            }
            HashMap<String,Integer> mapGuardies = new HashMap<String,Integer>();
            SQL0 ="SELECT abrev, COUNT(id) FROM sig_guardies GROUP BY abrev";
            try {
                Statement st = coreCfg.getMysql().createStatement();
                ResultSet rs1 = coreCfg.getMysql().getResultSet(SQL0,st);
                while(rs1!=null && rs1.next())
                {
                   mapGuardies.put(rs1.getString(1), rs1.getInt(2));
                }
                rs1.close();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(GestioPerfils.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
            String SQL1 = "SELECT DISTINCT p.abrev, p.nombre FROM sig_horaris as per"
                    + " INNER JOIN sig_professorat as p ON p.abrev=per.prof ORDER BY p.nombre";
            try {
                Statement st = coreCfg.getMysql().createStatement();
                ResultSet rs1 = coreCfg.getMysql().getResultSet(SQL1);
                while(rs1!=null && rs1.next())
                {
                    String abrev = rs1.getString(1);
                    int nc = mapClases.containsKey(abrev)?mapClases.get(abrev):0;
                    int ng = mapGuardies.containsKey(abrev)?mapGuardies.get(abrev):0;
                    modelTable1.addRow(new Object[]{false, rs1.getString(2),abrev, nc, ng});
                }
                rs1.close();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(GestioPerfils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        jLabel2.setText("Aquest perfil conté "+jTable1.getRowCount()+" professors.");
        
    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
