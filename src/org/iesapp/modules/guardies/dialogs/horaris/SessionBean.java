/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.modules.guardies.dialogs.horaris;

import java.util.ArrayList;

/**
 *
 * @author Josep
 */
public class SessionBean implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    public static final byte BLANK=0;
    public static final byte CLASSE=1;
    public static final byte GUARDIA=2;
    
    protected byte type = BLANK;
    protected boolean editing = false;
   
    public static ArrayList<String> zonesGuardiaDisponible = new ArrayList<String>();
    
    //Si es guardia
    protected ArrayList<String> zonesGuardia = new ArrayList<String>();
    
    //Si es classe
    protected String nomClase="";
    protected String aula="";
    protected int nivel=1;
    protected String estudis="";
    protected String grup="";
    protected java.awt.Color color = new java.awt.Color(240,240,240);
    
    //Problemes d'error
    protected boolean isErrorAula = false;

    public SessionBean()
    {
        
    }
            
    public SessionBean(SessionBean clipboard) {
        this.aula=clipboard.aula;
        this.editing=clipboard.editing;
        this.estudis=clipboard.estudis;
        this.grup=clipboard.grup;
        this.nivel=clipboard.nivel;
        this.nomClase=clipboard.nomClase;
        this.type=clipboard.type;
        this.zonesGuardia=(ArrayList<String>) clipboard.zonesGuardia.clone();
        this.color=clipboard.color;
    }

   
    /**
     * @return the zonesGuardia
     */
    public ArrayList<String> getZonesGuardia() {
        return zonesGuardia;
    }

    /**
     * @param zonesGuardia the zonesGuardia to set
     */
    public void setZonesGuardia(ArrayList<String> zonesGuardia) {
        this.zonesGuardia = zonesGuardia;
    }

    /**
     * @return the nomClase
     */
    public String getNomClase() {
        return nomClase;
    }

    /**
     * @param nomClase the nomClase to set
     */
    public void setNomClase(String nomClase) {
        this.nomClase = nomClase;
    }

    /**
     * @return the aula
     */
    public String getAula() {
        return aula;
    }

    /**
     * @param aula the aula to set
     */
    public void setAula(String aula) {
        this.aula = aula;
    }

    /**
     * @return the nivel
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    /**
     * @return the estudis
     */
    public String getEstudis() {
        return estudis;
    }

    /**
     * @param estudis the estudis to set
     */
    public void setEstudis(String estudis) {
        this.estudis = estudis;
    }

    /**
     * @return the grup
     */
    public String getGrup() {
        return grup;
    }

    /**
     * @param grup the grup to set
     */
    public void setGrup(String grup) {
        this.grup = grup;
    }

    /**
     * @return the type
     */
    public byte getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(byte type) {
        this.type = type;
    }

    /**
     * @return the editing
     */
    public boolean isEditing() {
        return editing;
    }

    /**
     * @param editing the editing to set
     */
    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    /**
     * @return the color
     */
    public java.awt.Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(java.awt.Color color) {
        this.color = color;
    }

    /**
     * @return the isErrorAula
     */
    public boolean isIsErrorAula() {
        return isErrorAula;
    }

    /**
     * @param isErrorAula the isErrorAula to set
     */
    public void setIsErrorAula(boolean isErrorAula) {
        this.isErrorAula = isErrorAula;
    }
    
    
    
    
}
