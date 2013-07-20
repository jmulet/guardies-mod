/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * alfabetPanel.java
 *
 * Created on 08-abr-2011, 17:12:06
 */

package org.iesapp.modules.guardies;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import javax.swing.JLabel;
/**
 *
 * @author Josep Mulet
 */
public class AlfabetPanel extends javax.swing.JPanel {
    private List list_lletres;
    private GuardiesModule parent;
    private javax.swing.JLabel[] jLabels;
    private javax.swing.JLabel  jLabelTitol;
    private int NLABELS;
    private String fontName;
    private int fontSize;

    private Color colorSelected;
    private Color colorNoSelected;
    private Color colorBackgMouseOver;
    private int   sizeSelected;
    private int   sizeNoSelected;

   

    /** Creates new form alfabetPanel */
    public AlfabetPanel(GuardiesModule par) {
        parent = par;
        //initComponents();
        my_initComponents();

    }

    private void my_initComponents()
    {

        fontName = "Arial";
        fontSize = 24;
        colorNoSelected   = new java.awt.Color(80,80,80);
        colorSelected = new java.awt.Color(0, 0, 100);
        colorBackgMouseOver = new java.awt.Color(255, 255, 200);
        sizeNoSelected    = fontSize;
        sizeSelected  = (int) (fontSize*1.3);


        NLABELS = 26;
        String[] lletra = {"A","B","C","D","E","F","G","H","I","J","K",
                           "L","M","N","O","P","Q","R","S","T","V","W",
                           "X", "Y","Z","*"};
        list_lletres = Arrays.asList(lletra);
        

        jLabels = new javax.swing.JLabel[NLABELS];

        jLabelTitol = new javax.swing.JLabel();
        jLabelTitol.setText("MATI:  ");
        jLabelTitol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/sun.png")) );
        jLabelTitol.setFont(new java.awt.Font(fontName, 0, 12));
        jLabelTitol.setForeground(Color.GRAY);
        add(jLabelTitol);

        for (int i=0; i<NLABELS; i++)
        {
            jLabels[i] = new javax.swing.JLabel();
            jLabels[i].setText(lletra[i]);
            jLabels[i].setFont(new java.awt.Font(fontName, 0, sizeNoSelected));
            jLabels[i].setForeground(colorNoSelected);
            jLabels[i].setToolTipText("Filtra llinatges amb lletra " + lletra[i]);
            jLabels[i].addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelsMouseClicked(evt);
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelsMouseEntered(evt);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelsMouseExited(evt);
            }
            });
            add(jLabels[i]);
        }

        jLabels[NLABELS-1].setFont(new java.awt.Font(fontName, 1, sizeSelected));
        jLabels[NLABELS-1].setForeground(colorSelected);
        jLabels[NLABELS-1].setToolTipText("Mostra tots");


    }

    

    private void jLabelsMouseClicked(java.awt.event.MouseEvent evt) {
        JLabel tmp = (JLabel) evt.getSource();
        parent.userKey = tmp.getText();
        parent.fillTable(parent.userKey);
        for(int i=0; i<NLABELS; i++)
        {
           jLabels[i].setFont(new java.awt.Font(fontName, 0, sizeNoSelected));
           jLabels[i].setForeground(colorNoSelected);
        }
        tmp.setFont(new java.awt.Font(fontName, 1, sizeSelected));
        tmp.setForeground(colorSelected);
    }
    
    public void setSelectedLetter(String key)
    {
        
        int id = list_lletres.indexOf(key.toUpperCase());
        if(id<0) {
            return;
        }
        
        for(int i=0; i<NLABELS; i++)
        {
           jLabels[i].setFont(new java.awt.Font(fontName, 0, sizeNoSelected));
           jLabels[i].setForeground(colorNoSelected);
        }
        
        jLabels[id].setFont(new java.awt.Font(fontName, 1, sizeSelected));
        jLabels[id].setForeground(colorSelected);
        
        
    }

   private void jLabelsMouseEntered(java.awt.event.MouseEvent evt) {
        JLabel tmp = (JLabel) evt.getSource();
        tmp.setOpaque(true);
        tmp.setBackground(colorBackgMouseOver);
    }

   private void jLabelsMouseExited(java.awt.event.MouseEvent evt) {
        JLabel tmp = (JLabel) evt.getSource();
        tmp.setOpaque(false);
        tmp.setBackground(new Color(222,222,222));
    }
   
    public void setTorn(boolean tornTarda) {
         if(tornTarda) {
            jLabelTitol.setText("TARDA:  ");
            jLabelTitol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/moon.png")) );
     
        }
        else {
            this.jLabelTitol.setText("MATÍ:  ");
            jLabelTitol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/sun.png")) );     
        }
    }
   
}
