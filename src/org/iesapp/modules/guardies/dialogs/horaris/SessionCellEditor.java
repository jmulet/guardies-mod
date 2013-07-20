
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.modules.guardies.dialogs.horaris;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Josep
 */
public class SessionCellEditor extends AbstractCellEditor implements
		TableCellEditor {
    
    private static final long serialVersionUID = 91788157527556239L;
    private SessionBean bean = new SessionBean();
    private SessionCell sessionCell;

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
  
                Component comp= null;
		if (value instanceof SessionBean) {
                       bean = (SessionBean) value;
                      
                       if(bean==null || bean.type==SessionBean.BLANK)
                       {
                           SessionCellBlank cell = new SessionCellBlank();
                           cell.setTable(table);
                           comp = cell;
                           sessionCell = cell;
                       }
                       else if(bean.type==SessionBean.CLASSE)
                       {
                           bean.editing=true;
                           SessionCellClasse cell = new SessionCellClasse();
                           cell.setTable(table);
                           comp = cell;
                           sessionCell = cell;
                       }
                       else if(bean.type==SessionBean.GUARDIA)
                       {
                           bean.editing=true;
                           SessionCellGuardia cell = new SessionCellGuardia();
                           cell.setTable(table);
                           comp = cell;
                          
                           sessionCell = cell;
                       }
                      
                       sessionCell.setBean(bean);
                }

		return comp;
	}

	public Object getCellEditorValue() {
            SessionBean bean2 = sessionCell.getBean();
            bean2.editing=false;
            return bean2;
	}
}