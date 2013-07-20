/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.modules.guardies.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.iesapp.clients.iesdigital.guardies.CellModel;
/**
 *
 * @author Josep
 */
public class CellRendererFaltes extends DefaultTableCellRenderer {

    //Colors per colorificar les guardies
    
    public static Color colors[] = new Color[]{
           new Color(255,255,255),
           new Color(255,200,100),
           new Color(100,200,100),
           new Color(150,150,250),
           new Color(255,200,225),
           new Color(40,250,70),
           new Color(30,160,80),
           new Color(20,170,90),
           new Color(10,220,100),
           new Color(10,30,110),
           new Color(10,230,120),
    };


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

    if(column > 0)
    {
        text = (String) value;
        String codig = "[]";
        int i0 = text.lastIndexOf('[');
        int i1 = text.lastIndexOf(']');
        if(i0 >0 && i1>0) {
                codig = text.substring(i0, i1);
            }

        try{
           code = (int) Double.parseDouble(codig.substring(1,codig.length()));
             //int code =1;
           text = text.substring(0,i0);
        }
        catch(java.lang.NumberFormatException cE)
        {
            code = -1;
        }
        catch(java.lang.StringIndexOutOfBoundsException cE)
        {
            code = -1;
        }


    }
    else
    {
        acell = (CellModel) value;
        code  = acell.getStatus();
        text  = acell.getText();
    }



    JCheckBox jCheckBox1;
    jCheckBox1 = new javax.swing.JCheckBox();

    //decide which icon to set
    JLabel comp =  new JLabel();
    if(code>=0) {
            comp = new JLabel(icons[code],JLabel.RIGHT);
        }
    comp.setHorizontalTextPosition(JLabel.LEFT);
    comp.setText((String)text);


    comp.setOpaque(true);
   // colored column

 
   if(column==6 ) //profe guarda i on guarda
   {
        comp.setToolTipText("Tria el professor que cobreix la guardia");
   }
   else if(column==7)
   {
        comp.setToolTipText("Doble-click per triar Aula");
   }
   else if (column == 4)
   {
        jCheckBox1.setSelected( Double.parseDouble(text) > 0);
   }

   if( text.trim().length() == 0)
   {
       comp.setText(" ");
       comp.setIcon(null);
   }

    //segons si esta seleccionat
    if (isSelected) {
            comp.setForeground(table.getSelectionForeground());
            comp.setBackground(table.getSelectionBackground());
    } else
    {

//        if(row%2 ==0 )
//        {
//        comp.setBackground(Cfg.colorParell);
//        }
//        else
//        {
//        comp.setBackground(Cfg.colorSenar);
//        }

        if(column>=6) {
                comp.setBackground(new Color(255,240,200));
            }

        comp.setForeground(table.getForeground());

    }
    

    if(column!=0) {
            comp.setIcon(null);
        }

    if(column==4) {
            return jCheckBox1;
        }
    else {
            return comp;
        }
  }

}
