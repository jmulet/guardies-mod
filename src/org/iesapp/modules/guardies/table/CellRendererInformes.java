/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.modules.guardies.table;

/**
 *
 * @author Josep
*/

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.iesapp.modules.guardies.util.Cfg;

public class CellRendererInformes extends DefaultTableCellRenderer {
 

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected, boolean hasFocus,
                                                 int row, int column) {


   ImageIcon icons[] = {
        new ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/icon01.gif")),
        new ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/icon03.gif")),
        new ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/icon04.gif")),
        new ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/icon01.gif")),
        new ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/icon03.gif"))};

    //decide which icon to set
    JLabel comp = null;
    int code = ((Number) value).intValue();

    String txt = "---";
    String tooltip = null;

    if(code == 0)
    {
           txt = "N.Sig";
           tooltip = "No signat";
    }
    else if(code == 1)
    {
        txt = "F";
        tooltip = "Falta";
    }
    else if(code == 2)
    {
        txt = "S.";
        tooltip = "Sortida";
    }
    else if(code == 3)
    {
        txt = "N.Sig-J";
        tooltip = "No signat - Justificat";
    }
    else if(code == 4)
    {
        txt=  "F-J";
        tooltip = "Falta - Justificat";
    }

  
    if(column > 1 && code >= 0)
    {
        comp = new JLabel(icons[code],JLabel.RIGHT);
        comp.setToolTipText(tooltip);
    }
    else
    {
        comp = new JLabel();
        comp.setForeground(Color.gray);
    }

    if(code ==3 || code ==4)
    {
        comp.setOpaque(true);
        comp.setBackground(Cfg.colorFaltaJustificada);
    }

    comp.setHorizontalTextPosition(JLabel.LEFT);
    comp.setText("  "+txt);


   return comp;
  }

}
