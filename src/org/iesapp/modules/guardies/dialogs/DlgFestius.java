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

import ch.swingfx.twinkle.NotificationBuilder;
import ch.swingfx.twinkle.style.INotificationStyle;
import ch.swingfx.twinkle.style.theme.DarkDefaultNotification;
import ch.swingfx.twinkle.window.Positions;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import org.iesapp.framework.table.CellDateEditor;
import org.iesapp.framework.table.CellDateRenderer;
import org.iesapp.framework.table.CellTableState;
import org.iesapp.framework.table.MyIconButtonRenderer;
import org.iesapp.framework.table.TextAreaEditor;
import org.iesapp.framework.table.TextAreaRenderer;
import org.iesapp.framework.util.CoreCfg;
import org.iesapp.modules.guardies.GuardiesModule;
import org.iesapp.modules.guardies.util.Cfg;

/**
 *
 * @author Josep
 */
public class DlgFestius extends javax.swing.JDialog {
    private DefaultTableModel modelTable1;
    private final CoreCfg coreCfg;


    /** Creates new form DlgFaltesPrevistes */
    public DlgFestius(GuardiesModule par, boolean modal, CoreCfg coreCfg) {
        super((JFrame) null, modal);
        this.coreCfg = coreCfg;
        initComponents();
         jTable1.setIntercellSpacing( new java.awt.Dimension(2,2) );
         jTable1.setGridColor(java.awt.Color.gray);
         jTable1.setShowGrid(true);
         parent = par;
 
         String SQL1 = "SELECT * FROM "+ Cfg.prefix+ "festius order by id desc";
         try {
            Statement st =coreCfg.getMysql().createStatement();
            ResultSet rs1 =coreCfg.getMysql().getResultSet(SQL1,st);

            while (rs1!=null && rs1.next()) {
                int id = rs1.getInt("id");
                java.sql.Date desde = rs1.getDate("desde");
                java.sql.Date fins = rs1.getDate("fins");
                String comment = rs1.getString("commentari");
   
                Calendar cal = Calendar.getInstance();

                CellTableState cts = new CellTableState( "", id, 0);
                modelTable1.insertRow(jTable1.getRowCount(), new Object[]
                            {cts, desde, fins, comment});
            }
            rs1.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DlgFestius.class.getName()).log(Level.SEVERE, null, ex);
        }


        jTable1.getModel().addTableModelListener( new TableModelListener()
              {
              public void tableChanged(TableModelEvent e)
               {

                    int mrow = e.getFirstRow();
                    int mcol = e.getColumn();
                    if((mcol == 1 || mcol==2) )
                    {
                          java.sql.Date from = (java.sql.Date) jTable1.getValueAt(mrow, 1);
                          java.sql.Date fins = (java.sql.Date) jTable1.getValueAt(mrow, 2);

                        java.util.Date date = Calendar.getInstance().getTime();
                        java.sql.Date avui = new java.sql.Date(date.getTime());

                        if(from.compareTo(avui)<=0)
                        {
                          INotificationStyle style = new DarkDefaultNotification().withWindowCornerRadius(8).withWidth(300).withAlpha(0.86f);

                        // Now lets build the notification
                         new NotificationBuilder().withStyle(style) // Required. here we set the previously set style
                        .withTitle("Atenció") // Required.
                        .withMessage("Fila"+ (mrow+1)+": Periodes festius nomes es fan efectius si la data d'inici és a partir de demà.") // Optional
                        .withIcon(new ImageIcon(NotificationBuilder.ICON_EXCLAMATION)) // Optional. You could also use a String path
                        .withDisplayTime(3000) // Optional
                        .withPosition(Positions.NORTH_EAST) // Optional. Show it at the center of the screen
                        .showNotification(); // this returns a UUID that you can use to identify events on the listener

                        }

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
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editor de festius");

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
                "id", "Des de dia", "Fins dia", "Comentaris",
            }
        );

        String[] icons = new String[] {
            "/org/iesapp/modules/guardies/icons/delete.gif"
        };

        jTable1.setModel(modelTable1);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.getColumnModel().getColumn(0).setCellRenderer(new MyIconButtonRenderer(icons));
        jTable1.getColumnModel().getColumn(1).setCellEditor(new CellDateEditor());
        jTable1.getColumnModel().getColumn(2).setCellEditor(new CellDateEditor());
        jTable1.getColumnModel().getColumn(1).setCellRenderer(new CellDateRenderer());
        jTable1.getColumnModel().getColumn(2).setCellRenderer(new CellDateRenderer());

        jTable1.getColumnModel().getColumn(3).setCellRenderer(new TextAreaRenderer());
        jTable1.getColumnModel().getColumn(3).setCellEditor(new TextAreaEditor());

        jTable1.getColumnModel().getColumn(0).setPreferredWidth(30);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(80);

        jTable1.setRowHeight(32);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Períodes festius amb els dies d'inici i final inclosos");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/exit.gif"))); // NOI18N
        jButton1.setText("Aplica i Tanca");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Afegeix un nou període no lectiu");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/insert.gif"))); // NOI18N
        jButton2.setText("Inserta");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(221, 221, 221)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(jButton1))
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
            java.sql.Date from = (java.sql.Date) jTable1.getValueAt(i, 1);
            java.sql.Date fins = (java.sql.Date) jTable1.getValueAt(i, 2);
            
            java.util.Date date = Calendar.getInstance().getTime();
            java.sql.Date avui = new java.sql.Date(date.getTime());
            java.sql.Date dema = new java.sql.Date(date.getTime());

//            if(from.compareTo(avui)<=0)
//            {
//                JOptionPane.showMessageDialog(this,"Fila"+ (i+1)+": La data d'inici ha d'ésser a partir de demà."
//                );
//                return;
//            }

            if(fins.before(from))
            {
                
                  INotificationStyle style = new DarkDefaultNotification().withWindowCornerRadius(8).withWidth(300).withAlpha(0.86f);

                  // Now lets build the notification
                     new NotificationBuilder().withStyle(style) // Required. here we set the previously set style
                        .withTitle("Atenció") // Required.
                        .withMessage("Fila"+ (i+1)+": La data d'inici supera a la d'acabament.") // Optional
                        .withIcon(new ImageIcon(NotificationBuilder.ICON_EXCLAMATION)) // Optional. You could also use a String path
                        .withDisplayTime(3000) // Optional
                        .withPosition(Positions.NORTH_EAST) // Optional. Show it at the center of the screen
                        .showNotification(); // this returns a UUID that you can use to identify events on the listener

                
                jTable1.setEditingColumn(2);
                jTable1.setEditingRow(i);

                return;
            }

            String comment = (String) jTable1.getValueAt(i, 3);
            String SQL1 = "UPDATE " + Cfg.prefix + "festius SET desde=?, fins=?, commentari=? "
                        +" WHERE id='"+id+"'";

            Object[] updatedValues = new Object[]{from, fins, comment};
            int nup =coreCfg.getMysql().preparedUpdate(SQL1, updatedValues);

        }

        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    //Click
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
       int mcol = jTable1.getSelectedColumn();
       int mrow = jTable1.getSelectedRow();



       if(evt.getClickCount()==1 && mcol==0)
       {
           int conf = JOptionPane.showConfirmDialog(this, "Voleu eliminar aquesta entrada de la base de dades?", "Confirmacio",
                                      JOptionPane.YES_NO_OPTION);
           if (conf!=0) {
                return;
            }

           CellTableState cts = (CellTableState) jTable1.getValueAt(mrow, 0);
           int id = ( (Number) cts.getCode() ).intValue();
         
           String SQL1 = "DELETE FROM "+Cfg.prefix+"festius where id='"+id+"'";
           //System.out.println(SQL1);
           int nup =coreCfg.getMysql().executeUpdate(SQL1);
           modelTable1.removeRow(mrow);
       }
    }//GEN-LAST:event_jTable1MouseClicked

    //add new date
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

            
            String SQL0 = "SELECT MAX(id) from " + Cfg.prefix +"festius";
            int maxid = 0;
            try {
            Statement st =coreCfg.getMysql().createStatement();
            ResultSet rs1 =coreCfg.getMysql().getResultSet(SQL0,st);
            if (rs1 != null && rs1.next()) {
                maxid = rs1.getInt("max(id)");
            }
            rs1.close();
            st.close();
            } catch (SQLException ex) {
            Logger.getLogger(DlgFestius.class.getName()).log(Level.SEVERE, null, ex);
            }
            maxid +=1;

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 1);
            java.sql.Date dema = new java.sql.Date(cal.getTime().getTime());
            CellTableState cts = new CellTableState( "", maxid, 0);
            modelTable1.insertRow(0, new Object[]{cts, dema, dema, ""});

            String SQL1 = "INSERT INTO "+ Cfg.prefix + "festius (id, desde, fins, commentari) "
                    + " VALUES(?, ?, ?, ?)";

            Object[] updatedValues = new Object[]{maxid, dema, dema, new String()};

            int nup =coreCfg.getMysql().preparedUpdate(SQL1, updatedValues);


    }//GEN-LAST:event_jButton2ActionPerformed

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
    private GuardiesModule parent;
}
