/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.modules.guardies.table;

/**
 *
 * @author josep
 */
import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.iesapp.clients.iesdigital.guardies.CellModel;
import org.iesapp.modules.guardies.util.Cfg;

public class CellRenderer extends DefaultTableCellRenderer {

  /*
   * @see TableCellRenderer#getTableCellRendererComponent(JTable, Object, boolean, boolean, int, int)
   */



  @Override
  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected, boolean hasFocus,
                                                 int row, int column) {



    ImageIcon icons[] = {
        new ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/icon01.gif")),
        new ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/icon02.gif")),
        new ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/icon03.gif")),
        new ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/icon04.gif")),
        new ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/icon05.gif")),
        new ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/icon06.gif")),};

    CellModel acell = (CellModel) value;
    String text = acell.getText();
    int type = acell.getType();
    int code = acell.getStatus();
    int shift = 0;
    if(acell.feina == 1 && code > 1) {
            shift = 2;
        }

    if(code ==-1 || code >3) {
            code = 0;
        }
    
    //decide which icon to set
    JLabel comp = null;
   
    
    if(column > 0) {
            comp = new JLabel(icons[code+shift],JLabel.RIGHT);
        }
    else {
            comp = new JLabel(icons[code+shift],JLabel.LEFT);
        }
    
    comp.setHorizontalTextPosition(JLabel.LEFT);
    comp.setText(text);
    if( text.length() != 0) {
            comp.setToolTipText(text);
        }

    // colored backgrounds
    comp.setOpaque(true);


   //segons si esta seleccionat
    if (isSelected) {
            comp.setForeground(table.getSelectionForeground());
            comp.setBackground(table.getSelectionBackground());

        if(type == 1) //guardia
        {
            comp.setBackground(org.iesapp.framework.util.ColorUtils.fade(Cfg.colorGuardies)); //color de la guardia
        }

    } else
    {

        if(row%2 ==0 )
        {
        comp.setBackground(Cfg.colorParell);
        }
        else
        {
        comp.setBackground(Cfg.colorSenar);
        }

        if(type == 1) //guardia
        {
            comp.setBackground(Cfg.colorGuardies); //color de la guardia
        }

        comp.setForeground(table.getForeground());
       
    }


   //altres

   if(column == 0)         //Nom de professor no ha de tenir icona
   {
        comp.setIcon(null);
   }

   if( text.length() == 0)
   {
       comp.setText(" ");
       comp.setIcon(null);
   }

   if(type==2) //es un camp filtrat
   {
      comp.setForeground(new Color(150,150,150));
   }


   return comp;
  }


   /**
     * Validates if input String is a number
     */
    public boolean checkIfNumber(String in) {

        try {

            Integer.parseInt(in);

        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }

}
