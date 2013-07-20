/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EditorHoraris.java
 *
 * Created on 07-may-2011, 2:22:10
 */

package org.iesapp.modules.guardies.dialogs.horaris;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DropMode;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import org.iesapp.framework.util.CoreCfg;
import org.iesapp.framework.util.JPopupMenuShower;
import org.iesapp.modules.guardies.GuardiesModule;
import org.iesapp.modules.guardies.util.Cfg;
import org.iesapp.util.StringUtils;



/**
 *
 * @author Josep
 */
public class EditorHoraris extends javax.swing.JFrame {
    private boolean isListening;
    private DefaultTableModel modelTable1;
    private SessionBean clipboard = null;
    public static DefaultComboBoxModel modelComboAulas;
    private final CoreCfg coreCfg;

    /** Creates new form EditorHoraris */
    public EditorHoraris(GuardiesModule par, boolean modal, CoreCfg coreCfg) {
        this.coreCfg = coreCfg;
        initComponents();
        Time[] horesClase = coreCfg.getIesClient().getDatesCollection().getHoresClase();
        Time[] horesClase_fi = coreCfg.getIesClient().getDatesCollection().getHoresClase_fi();
        for(int i=0; i<14; i++)
        {
            jTable1.setValueAt((i+1)+"a: "+StringUtils.formatTime(horesClase[i])+"-"+
                    StringUtils.formatTime(horesClase_fi[i]), i, 0);
        }
            
//        LookAndFeelInfo[] m_infos = UIManager.getInstalledLookAndFeels(); 
//        for(LookAndFeelInfo kk: m_infos)
//        {
//         System.out.println("classname="+  kk.getClassName()+"  name="+kk.getName());
//        }
//        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//            SwingUtilities.updateComponentTreeUI(EditorHoraris.this);
//        } catch (Exception ex) {
//            System.out.println("Can't load system look and feel");
//        }
        //jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        parent =  par;
        String[] elements = new String[GuardiesModule.abrev2prof.size()];
        int i=0;
        for(String ky: GuardiesModule.abrev2prof.keySet())
        {
            elements[i]= GuardiesModule.abrev2prof.get(ky) + " ["+ky+"]";
            i++;
        }
         autoCompleteCombo1.setData(elements);
         
         JPopupMenuShower mouseListener1 = new org.iesapp.framework.util.JPopupMenuShower(jPopupMenu1);
         jTable1.addMouseListener(mouseListener1);
         
          
         jTable1.setTransferHandler(new DnDHandler());
         jTable1.setDragEnabled(true);
         jTable1.setDropMode(DropMode.USE_SELECTION);
         
         jTable1.setIntercellSpacing( new java.awt.Dimension(2,2) );

         jTable1.setGridColor(java.awt.Color.gray);
         jTable1.setShowGrid(true);

         modelCombo1= new DefaultComboBoxModel();
         modelCombo1.addElement("actual");
         jComboBox1.setModel(modelCombo1);

         // Fill combo guardies
        SessionBean.zonesGuardiaDisponible.clear();
        String SQL1 ="Select lloc from "+ Cfg.prefix + "guardies_zones";
          try {
            Statement st =coreCfg.getMysql().createStatement();
            ResultSet rs1 =coreCfg.getMysql().getResultSet(SQL1,st);
            while (rs1 != null && rs1.next()) {
                String val = rs1.getString("lloc").trim();
                if(val.length() != 0){
                    SessionBean.zonesGuardiaDisponible.add(val);
                }
            }
            rs1.close();
            st.close();
         } catch (SQLException ex) {
            Logger.getLogger(EditorHoraris.class.getName()).log(Level.SEVERE, null, ex);
         }
         

         // Fill combo aules
         modelComboAulas =new DefaultComboBoxModel();
         jComboBox3.setModel(modelComboAulas);

         SQL1 ="Select aula from "+ Cfg.prefix + "espais";
          try {
            Statement st =coreCfg.getMysql().createStatement();
            ResultSet rs1 =coreCfg.getMysql().getResultSet(SQL1,st);
            while (rs1 != null && rs1.next()) {
                String val = rs1.getString("aula").trim();
                if(val.length() != 0) {
                    modelComboAulas.addElement(val);
                }
            }
            rs1.close();
            st.close();
         } catch (SQLException ex) {
            Logger.getLogger(EditorHoraris.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         ensureVisibleTable();
         
         isListening = true;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        autoCompleteCombo1 = new org.iesapp.modules.guardies.util.AutoCompleteCombo();

        jMenuItem1.setText("Copiar");
        jMenuItem1.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jMenuItem1.setPreferredSize(new java.awt.Dimension(183, 22));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setText("Retalla");
        jMenuItem2.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jMenuItem2.setPreferredSize(new java.awt.Dimension(183, 22));
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);

        jMenuItem3.setText("Enganxa");
        jMenuItem3.setEnabled(false);
        jMenuItem3.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jMenuItem3.setPreferredSize(new java.awt.Dimension(183, 22));
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem3);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editor d'horaris");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        modelTable1 = new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1a", null, null, null, null, null},
                {"2a", null, null, null, null, null},
                {"3a", null, null, null, null, null},
                {"4a", null, null, null, null, null},
                {"5a", null, null, null, null, null},
                {"6a", null, null, null, null, null},
                {"7a", null, null, null, null, null},
                {"8a", null, null, null, null, null},
                {"9a", null, null, null, null, null},
                {"10a", null, null, null, null, null},
                {"11a", null, null, null, null, null},
                {"12a", null, null, null, null, null},
                {"13a", null, null, null, null, null},
                {"14a", null, null, null, null, null}
            },
            new String [] {
                "Hora", "DL", "DM", "DX", "DJ", "DV"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        jTable1.setModel(modelTable1);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        jTable1.setRowHeight(88);

        for(int i=1; i<6; i++)
        {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(new SessionCellRenderer());
            jTable1.getColumnModel().getColumn(i).setCellEditor(new SessionCellEditor());
        }
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);

        jLabel1.setText("Professor/a");

        jLabel3.setText("Perfils");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Actual" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Codi d'aules");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 25, 5));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/genera.gif"))); // NOI18N
        jButton3.setText("Gestió de perfils");
        jButton3.setPreferredSize(new java.awt.Dimension(135, 29));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/refresh.gif"))); // NOI18N
        jButton2.setText("Refresh");
        jButton2.setPreferredSize(new java.awt.Dimension(120, 29));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/delete.gif"))); // NOI18N
        jButton5.setText("Buidar horari");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/save.gif"))); // NOI18N
        jButton1.setText("Desa com actual");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/exit.gif"))); // NOI18N
        jButton4.setText("Tanca");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);

        autoCompleteCombo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoCompleteCombo1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(autoCompleteCombo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(2, 2, 2)
                        .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel4)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(autoCompleteCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inSearchProfe()
    {
         //cerca quants de perfils té aquest professor
        
        String abrev = GetProfe();
        if(abrev.length() == 0) {
            return;
        }
        //Comprova el professor quants de perfils te en total
        modelCombo1.removeAllElements();
        modelCombo1.addElement("actual");
        String SQL0 = "SELECT DISTINCT perfil FROM sig_perfils_horaris where prof='"+abrev+"'";
         try {
            Statement st =coreCfg.getMysql().createStatement();
            ResultSet rs0 =coreCfg.getMysql().getResultSet(SQL0,st);
            while (rs0 != null && rs0.next()) {
                String perfil = rs0.getString("perfil");
                modelCombo1.addElement(perfil);
            }
            if(rs0!=null){
                rs0.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(EditorHoraris.class.getName()).log(Level.SEVERE, null, ex);
        }

        fillFromActual("");
    }
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if(jTable1.isEditing()) {
            jTable1.editCellAt(0, 0);
        }
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    //Gestio de perfils
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
      GestioPerfils gp = new GestioPerfils(this,true,coreCfg);
      gp.setLocationRelativeTo(null);
      gp.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    //desa com a perfil
    private void saveAsPerfil()
    {
          if(jTable1.isEditing()) {
            jTable1.editCellAt(0, 0);
        }
       //demana quin nom de perfil vull

        String perfil = "";
        perfil = JOptionPane.showInputDialog(this, "Nom del nou perfil?");
        if(perfil == null || perfil.length() == 0) {
            return;
        }

        modelCombo1.addElement(perfil);

        String profe =(String) autoCompleteCombo1.getSelectedItem();
        String abrev = "";
        if(profe.contains("[") && profe.contains("]"))
        {
        abrev = StringUtils.AfterLast(profe, "[");
        abrev = StringUtils.BeforeLast(abrev, "]").toUpperCase();
        }

       //esborra tot el que pugui tenir aquest perfil
        String SQL1 = "DELETE from "+Cfg.prefix+"perfils_guardies WHERE perfil='"+perfil+"'";
        int nup =coreCfg.getMysql().executeUpdate(SQL1);
        SQL1 = "DELETE from "+Cfg.prefix+"perfils_horaris WHERE perfil='"+perfil+"'";
        nup =coreCfg.getMysql().executeUpdate(SQL1);

        
        for(int i=1; i<6; i++)
        {
            int dia = i;
            for(int j=0; j<14; j++)
            {
               int hora= j+1;
               //Agafa el bean
               SessionBean bean = (SessionBean) jTable1.getValueAt(j, dia);
               if(bean!=null && bean.type==SessionBean.CLASSE)
               {
          
                   SQL1 = "INSERT INTO " + Cfg.prefix + "perfils_horaris (perfil,prof,asig,curso,nivel,grupo,aula,dia,hora) "
                           + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

                   Object[] updatedValues = new Object[]{perfil, abrev, bean.nomClase, bean.nivel>0?bean.nivel:"", bean.estudis, bean.grup, bean.aula, dia, hora};

                   nup =coreCfg.getMysql().preparedUpdate(SQL1, updatedValues);

               }
               else if(bean!=null && bean.type==SessionBean.GUARDIA)
               {
                   for(String zona: bean.zonesGuardia)
                   {
                        SQL1 = "INSERT INTO " + Cfg.prefix + "perfils_guardies (perfil,abrev,lloc,dia,hora) "
                            +" VALUES(?, ?, ?, ?, ?)";


                    Object[] updatedValues = new Object[]{perfil, abrev, zona, dia, hora};

                    nup =coreCfg.getMysql().preparedUpdate(SQL1, updatedValues);
                       
                   }
               }
            }
       }

    }
    
    //Save actual table as actual
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(jTable1.isEditing()) {
            jTable1.editCellAt(0, 0);
        }


        String profe =(String) autoCompleteCombo1.getSelectedItem();
        String abrev = "";
        if(profe.contains("[") && profe.contains("]"))
        {
        abrev = StringUtils.AfterLast(profe, "[");
        abrev = StringUtils.BeforeLast(abrev, "]").toUpperCase();
        }

        String SQL1 = "";
        int nup=0;

        //primer esborra les entrades de la base de dades
        SQL1 = "DELETE FROM " + Cfg.prefix + "guardies  WHERE abrev='"+abrev+"'";
        nup =coreCfg.getMysql().executeUpdate(SQL1);
//        System.out.println("borrado 1"+nup);
        SQL1 = "DELETE FROM " + Cfg.prefix + "horaris  WHERE prof='"+abrev+"'";
        nup =coreCfg.getMysql().executeUpdate(SQL1);

        for(int i=1; i<6; i++)
        {
            int dia = i;
            for(int j=0; j<14; j++)
            {
               int hora= j+1;
               //Agafa el bean
               SessionBean bean = (SessionBean) jTable1.getValueAt(j, dia);
               if(bean!=null && bean.type==SessionBean.CLASSE)
               {
          
                   SQL1 = "INSERT INTO " + Cfg.prefix + "horaris (prof,asig,curso,nivel,grupo,aula,dia,hora) "
                           + " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

                   Object[] updatedValues = new Object[]{abrev, bean.nomClase, bean.nivel>0?bean.nivel:"", bean.estudis, bean.grup, bean.aula, dia, hora};

                   nup =coreCfg.getMysql().preparedUpdate(SQL1, updatedValues);

               }
               else if(bean!=null && bean.type==SessionBean.GUARDIA)
               {
                   for(String zona: bean.zonesGuardia)
                   {
                        SQL1 = "INSERT INTO " + Cfg.prefix + "guardies (abrev,lloc,dia,hora) "
                            +" VALUES(?, ?, ?, ?)";


                    Object[] updatedValues = new Object[]{abrev, zona, dia, hora};

                    nup =coreCfg.getMysql().preparedUpdate(SQL1, updatedValues);
                       
                   }
               }
            }
       }

    }//GEN-LAST:event_jButton1ActionPerformed

    //Selection changed
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
       doRefresh();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void doRefresh()
    {
        int nsel = jComboBox1.getSelectedIndex();

        if(nsel == 0) {
            fillFromActual("");
        }
        else {
            fillFromActual((String) jComboBox1.getSelectedItem());
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       doRefresh();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
       int row = jTable1.getEditingRow();
       int col = jTable1.getEditingColumn();
       if(row>=0 && col>=0){
           clipboard = (SessionBean) jTable1.getValueAt(row, col);
           if(clipboard!=null) {
                jMenuItem3.setEnabled(true);
            }
       }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        int row = jTable1.getEditingRow();
       int col = jTable1.getEditingColumn();
       if(row>=0 && col>=0){
           jTable1.editCellAt(0, 0);
           clipboard = (SessionBean) jTable1.getValueAt(row, col);
           if(clipboard!=null) {
                jMenuItem3.setEnabled(true);
            }
           jTable1.setValueAt(new SessionBean(), row, col);
              jTable1.editCellAt(row, col);
       }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    //Enganxa
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
       int row = jTable1.getEditingRow();
       int col = jTable1.getEditingColumn();
       if(row>=0 && col>=0 && clipboard!=null){
            jTable1.editCellAt(0, 0);
            //We must create a new instance based on clipboard
            jTable1.setValueAt(new SessionBean(clipboard), row, col);
            jTable1.editCellAt(row, col);

        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void autoCompleteCombo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoCompleteCombo1ActionPerformed
       inSearchProfe();
       ensureVisibleTable();
    }//GEN-LAST:event_autoCompleteCombo1ActionPerformed

    //Buida l'horari
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        emptyTimeTable();
    }//GEN-LAST:event_jButton5ActionPerformed


    private String GetProfe()
    {

        String profe =(String) autoCompleteCombo1.getSelectedItem();
        if(profe == null) {
            return "";
        }
        String abrev = "";
        if(profe.contains("[") && profe.contains("]"))
        {
        abrev = StringUtils.AfterLast(profe, "[");
        abrev = StringUtils.BeforeLast(abrev, "]");
        }
        else
        {
            return "";
        }

        return abrev;
    }



    private void fillFromActual(String perfil)
    {
        jTable1.editCellAt(0,0);
        if(perfil == null) {
            return;
        }

        String taulaHoraris="";
        String taulaGuardies="";
        String condition="";
        
        if(perfil.length() == 0)
        {
            taulaHoraris = "horaris";
            taulaGuardies = "guardies";
        }
        else
        {
            taulaHoraris = "perfils_horaris";
            taulaGuardies = "perfils_guardies";
            condition = " AND perfil='"+perfil+"' ";
        }

        String abrev = GetProfe();
        if(abrev.length() == 0) {
            return;
        }

       

        emptyTimeTable();
           

        for(int i=1; i<6; i++)
        {
           try {
                    String SQL1 = "SELECT * from "+ Cfg.prefix +taulaHoraris+" where prof='" + abrev +
                            "' " + "and dia=" + i + condition;
                    Statement st =coreCfg.getMysql().createStatement();
                    ResultSet rs1 =coreCfg.getMysql().getResultSet(SQL1,st);
                    while (rs1!=null && rs1.next()) {
                        String asig = rs1.getString("asig");
                        int curso = rs1.getInt("curso");
                        String nivel = rs1.getString("nivel");
                        String grupo = rs1.getString("grupo");
                        String aula = rs1.getString("aula");
                        int hora = rs1.getInt("hora");


                        if(hora<=14){
                            SessionBean bean = new SessionBean();
                            bean.type=SessionBean.CLASSE;
                            bean.setNomClase(asig);
                            bean.setNivel(curso);
                            bean.setGrup(grupo);
                            bean.setEstudis(nivel);
                            bean.setAula(aula);
                            jTable1.setValueAt(bean, hora-1, i);
//                            jTable1.setValueAt(asig, 5*(i-1), hora+1);
//                            jTable1.setValueAt(aula, 5*(i-1)+1, hora+1);
//                            jTable1.setValueAt(curso, 5*(i-1)+2, hora+1);
//                            jTable1.setValueAt(nivel, 5*(i-1)+3, hora+1);
//                            jTable1.setValueAt(grupo, 5*(i-1)+4, hora+1);
                        }
                    }
                    rs1.close();
                    st.close();


                   
                    SQL1 = "SELECT dia,hora,GROUP_CONCAT(lloc,'') AS lloc from "+ Cfg.prefix + taulaGuardies + " where abrev='" +
                            abrev + "' " + "and dia=" + i + condition+" GROUP BY dia,hora";
                     Statement st2 =coreCfg.getMysql().createStatement();
                     rs1 =coreCfg.getMysql().getResultSet(SQL1,st2);
                    
                    SessionBean bean = new SessionBean();
                    bean.type=SessionBean.GUARDIA;
                        
                    while (rs1!=null && rs1.next()) {
                        String lloc = rs1.getString("lloc");
                        int hora = rs1.getInt("hora");
                        bean.zonesGuardia = StringUtils.parseStringToArray(lloc, ",", StringUtils.CASE_UPPER);
    
                        if(hora>0 && hora<=14){
                              jTable1.setValueAt(bean, hora-1, i);
//                            jTable1.setValueAt(asig, 5*(i-1), hora+1);
//                            jTable1.setValueAt(aula, 5*(i-1)+1, hora+1);
//                            jTable1.setValueAt(curso, 5*(i-1)+2, hora+1);
//                            jTable1.setValueAt(nivel, 5*(i-1)+3, hora+1);
//                            jTable1.setValueAt(grupo, 5*(i-1)+4, hora+1);
//                            jTable1.setValueAt("GUARDIA", 5*(i-1), hora+1);
//                            jTable1.setValueAt(lloc, 5*(i-1)+1, hora+1);
//                            //jTable1.setValueAt(curso, 6*(i-1)+2, hora+1);
//                            //jTable1.setValueAt(nivel, 6*(i-1)+3, hora+1);
//                            //jTable1.setValueAt(grupo, 6*(i-1)+4, hora+1);

                        }
                    }
                    rs1.close();
                    st2.close();
               }
                catch (SQLException ex) {
                    Logger.getLogger(EditorHoraris.class.getName()).log(Level.SEVERE, null, ex);
                }
        }

        //updateRowHeights();
    }
    
    private void updateRowHeights()
{
    try
    {
        for (int row = 0; row < jTable1.getRowCount(); row++)
        {
            int rowHeight = jTable1.getRowHeight();

            for (int column = 0; column < jTable1.getColumnCount(); column++)
            {
                Component comp = jTable1.prepareRenderer(jTable1.getCellRenderer(row, column), row, column);
                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
            }

            jTable1.setRowHeight(row, rowHeight);
        }
    }
    catch(ClassCastException e) {}
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
    private org.iesapp.modules.guardies.util.AutoCompleteCombo autoCompleteCombo1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
    private GuardiesModule parent;
    private DefaultComboBoxModel modelCombo1;

    //Ensure that the first non-blank hora is shown
    private void ensureVisibleTable() {
        int firstRow = 0;
        for(int i=0; i<jTable1.getRowCount();i++)
        {
            int nonblank = 0;
            for(int j=1; j<6;j++)
            {
                SessionBean bean = (SessionBean) jTable1.getValueAt(i, j);
                if(bean!=null && bean.type!=SessionBean.BLANK)
                {
                    nonblank +=1;
                }
            }
            if(nonblank>0)
            {
                firstRow = i;
                break;
            }
        }
        
        java.awt.Rectangle rect = jTable1.getCellRect(firstRow, 0, true);
        jTable1.scrollRectToVisible(rect);
        jTable1.setRowSelectionInterval(firstRow, firstRow);
    }

    private void emptyTimeTable() {
         //esborra la taula
        for(int i=1; i<6; i++)
        {
             for(int j=0; j<jTable1.getRowCount();j++)
             {
                   SessionBean bean = new SessionBean();
                   jTable1.setValueAt(bean, j, i);
             }            
        }
    }
}
