/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.modules.guardies.dialogs.horaris;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Josep
 */
public class SessionCellRenderer extends DefaultTableCellRenderer {

  /*
   * @see TableCellRenderer#getTableCellRendererComponent(JTable, Object, boolean, boolean, int, int)
   */



  @Override
  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected, boolean hasFocus,
                                                 int row, int column) {

   SessionBean bean = (SessionBean) value;
   
   Component comp = null;
   if(bean==null || bean.type==SessionBean.BLANK) {
           SessionCellBlank cell = new SessionCellBlank();
           cell.setBean(bean);
           comp = cell;
    }
   else if(bean.type==SessionBean.CLASSE)
   {
           SessionCellClasse cell = new SessionCellClasse();
           cell.setBean(bean);
           comp = cell;
   }
  
   else if(bean.type==SessionBean.GUARDIA)
   {
           SessionCellGuardia cell = new SessionCellGuardia();
           cell.setBean(bean);
           comp = cell;
   }
 
   
   return comp;
  }
}
