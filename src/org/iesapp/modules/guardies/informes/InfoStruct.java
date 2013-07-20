/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.modules.guardies.informes;

/**
 *
 * @author Josep
 */
// Estructura d'informacio per l'informe RESUM
public class InfoStruct
{

    private int nnosignat=0;
    private int nfalta=0;
    private int nsortida=0;
    private int njustificats=0;

    public int getNjustificats() {
        return njustificats;
    }

    public void setNjustificats(int njustificats) {
        this.njustificats = njustificats;
    }

    
    public int getNsortida() {
        return nsortida;
    }

    public void setNsortida(int nsortida) {
        this.nsortida = nsortida;
    }


    public int getNfalta() {
        return nfalta;
    }

    public void setNfalta(int nfalta) {
        this.nfalta = nfalta;
    }


    public int getNnosignat() {
        return nnosignat;
    }

    public void setNnosignat(int nnosignat) {
        this.nnosignat = nnosignat;
    }

    
 
 
    private String professor="";

    private String txt;

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    
    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

   
}
