/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.modules.guardies.informes;

/**
 *
 * @author Josep
 */
public class BeanLlistatFaltes {

     public BeanLlistatFaltes()
    {
        nombre = "";
        data = "";
        comentari = "";
        h1 = "";
        h2 = "";
        h3 = "";
        h4 = "";
        h5 = "";
        h6 = "";
        h7 = "";
    }


    public BeanLlistatFaltes(String _nombre, String _data, String _h1, String _h2,
            String _h3, String _h4, String _h5, String _h6, String _h7)
    {
        nombre = _nombre;
        data = _data;
        h1 = _h1;
        h2 = _h2;
        h3 = _h3;
        h4 = _h4;
        h5 = _h5;
        h6 = _h6;
        h7 = _h7;
    }
    
    protected String nombre;
    protected String data;
    protected String h1;
    protected String h2;
    protected String h3;
    protected String h4;
    protected String h5;
    protected String h6;
    protected String h7;
    protected String comentari;


    public BeanLlistatFaltes getMe()
    {
        return this;
    }
    /**
     * Get the value of h7
     *
     * @return the value of h7
     */
    public String getH7() {
        return h7;
    }

    /**
     * Set the value of h7
     *
     * @param h7 new value of h7
     */
    public void setH7(String h7) {
        this.h7 = h7;
    }

    /**
     * Get the value of h6
     *
     * @return the value of h6
     */
    public String getH6() {
        return h6;
    }

    /**
     * Set the value of h6
     *
     * @param h6 new value of h6
     */
    public void setH6(String h6) {
        this.h6 = h6;
    }

    /**
     * Get the value of h5
     *
     * @return the value of h5
     */
    public String getH5() {
        return h5;
    }

    /**
     * Set the value of h5
     *
     * @param h5 new value of h5
     */
    public void setH5(String h5) {
        this.h5 = h5;
    }

    /**
     * Get the value of h4
     *
     * @return the value of h4
     */
    public String getH4() {
        return h4;
    }

    /**
     * Set the value of h4
     *
     * @param h4 new value of h4
     */
    public void setH4(String h4) {
        this.h4 = h4;
    }

    /**
     * Get the value of h3
     *
     * @return the value of h3
     */
    public String getH3() {
        return h3;
    }

    /**
     * Set the value of h3
     *
     * @param h3 new value of h3
     */
    public void setH3(String h3) {
        this.h3 = h3;
    }


    /**
     * Get the value of h2
     *
     * @return the value of h2
     */
    public String getH2() {
        return h2;
    }

    /**
     * Set the value of h2
     *
     * @param h2 new value of h2
     */
    public void setH2(String h2) {
        this.h2 = h2;
    }

    /**
     * Get the value of h1
     *
     * @return the value of h1
     */
    public String getH1() {
        return h1;
    }

    /**
     * Set the value of h1
     *
     * @param h1 new value of h1
     */
    public void setH1(String h1) {
        this.h1 = h1;
    }

    /**
     * Get the value of data
     *
     * @return the value of data
     */
    public String getData() {
        return data;
    }

    /**
     * Set the value of data
     *
     * @param data new value of data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Get the value of nombre
     *
     * @return the value of nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Set the value of nombre
     *
     * @param nombre new value of nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    void setHn(String msg, int i) {
       switch(i)
       {
           case 1: this.h1=msg; break;
           case 2: this.h2=msg; break;    
           case 3: this.h3=msg; break;
           case 4: this.h4=msg; break;
           case 5: this.h5=msg; break;
           case 6: this.h6=msg; break;
           case 7: this.h7=msg; break;
         
       }
    }

    /**
     * @return the comentari
     */
    public String getComentari() {
        return comentari;
    }

    /**
     * @param comentari the comentari to set
     */
    public void setComentari(String comentari) {
        this.comentari = comentari;
    }



}
