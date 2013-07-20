/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.modules.guardies;

/**
 *
 * @author Josep
 */
public class BeanDisponibilitat {
    
    protected String aula="";
    protected String reservable="";
    protected String dia="";
    protected String hora="";

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
     * @return the reservable
     */
    public String getReservable() {
        return reservable;
    }

    /**
     * @param reservable the reservable to set
     */
    public void setReservable(String reservable) {
        this.reservable = reservable;
    }

    /**
     * @return the dia
     */
    public String getDia() {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(String dia) {
        this.dia = dia;
    }

    /**
     * @return the hora
     */
    public String getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(String hora) {
        this.hora = hora;
    }
    
}
