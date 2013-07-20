/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.modules.guardies.table;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.iesapp.clients.iesdigital.guardies.CellModel;
import org.iesapp.modules.guardies.util.Cfg;

/**
 *
 * @author Josep
 */
public class CellRendererFaltes2 extends DefaultTableCellRenderer {

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


    String text = "";
    CellModel acell = null;
    int code = 0;
    int icol = 0;
    String tip="";

//    if(column > 0)
//    {
//        text = (String) value;
//        String codig = "[]";
//        int i0 = text.lastIndexOf("[");
//        int i1 = text.lastIndexOf("]");
//        if(i0 >0 && i1>0) codig = text.substring(i0, i1);
//
//        try{
//           code = (int) Double.parseDouble(codig.substring(1,codig.length()));
//             //int code =1;
//           text = text.substring(0,i0);
//        }
//        catch(java.lang.NumberFormatException cE)
//        {
//            code = -1;
//        }
//        catch(java.lang.StringIndexOutOfBoundsException cE)
//        {
//            code = -1;
//        }
//
//
//    }
//    else
//    {
        acell = (CellModel) value;
        code  = acell.getStatus();
        text  = acell.getText();
        icol = acell.getFeina();  //el camp de feina funciona com a index de color
        tip = acell.getComment();
//    }



    //decide which icon to set
    JLabel comp =  new JLabel();
    if(code>=0) {
            comp = new JLabel(icons[code],JLabel.RIGHT);
        }
    comp.setHorizontalTextPosition(JLabel.LEFT);
    comp.setText((String)text);


    comp.setOpaque(true);
   // colored column

 //segons si esta seleccionat
    if (isSelected) {
            comp.setForeground(table.getSelectionForeground());
            comp.setBackground(table.getSelectionBackground());
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

    }


    //Colorifica
   comp.setBackground(CellRendererFaltes.colors[icol]);
   //Mosta un commentari
   if(  tip.length() != 0) {
            comp.setToolTipText(tip);
        }
   
   return comp;
  }

}
