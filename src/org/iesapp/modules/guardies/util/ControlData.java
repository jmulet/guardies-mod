/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.modules.guardies.util;

import java.util.Calendar;
import org.iesapp.framework.util.CoreCfg;
import org.iesapp.util.DataCtrl;


/**
 *
 * @author Josep
 */
public class ControlData extends DataCtrl {
    private final Cfg cfg;

    public ControlData(Cfg cfg)
    {
            super();
            this.cfg = cfg;
   }

    public ControlData(java.sql.Date date, Cfg cfg)
    {
            super(date);
            this.cfg = cfg;
    }

    public ControlData(java.util.Date date, Cfg cfg)
    {
            super(date);
            this.cfg = cfg;
    }

    public ControlData(java.util.Calendar cal, Cfg cfg)
    {
            super(cal);
            this.cfg = cfg;
    }


    //determina si es hora adequada per mostrar incidencies en el system tray
    public boolean displayMessage()
    {
         boolean show = false;
         Calendar cal2 = null;
         Calendar cal  = null;

         int h = this.getIntHora();
         cal = Calendar.getInstance();
         cal.setTime(cfg.getCoreCfg().getIesClient().getDatesCollection().getHoresClase()[h-1]);
         cal.set(Calendar.YEAR, m_cal.get(Calendar.YEAR));
         cal.set(Calendar.MONTH, m_cal.get(Calendar.MONTH));
         cal.set(Calendar.DAY_OF_MONTH, m_cal.get(Calendar.DAY_OF_MONTH));
         cal.add(Calendar.MINUTE, cfg.activaMissatges);

         cal2 = (Calendar) cal.clone();
         cal2.add(Calendar.SECOND, 4); //show during 4 sec

         //System.out.println("ara es hora" + h);
         //System.out.println("comparing dates " + cal.getTime() + ";  " +  cal2.getTime() + ";  "  + m_cal.getTime());

         if(cal.compareTo(m_cal)<=0 && m_cal.compareTo(cal2)<=0 )
         {
            show = true;
         }

         return show;
    }

    //aquesta subrutina s'ha de perfeccionar; dona l'hora de classe 1, 2, ...
    public int getIntHora(int antelacio)
    {
         int hsel = 1;
         //int nhores = CoreCfg.horesClase.length;
         
         Calendar cal2 = null;
         Calendar cal  = null;
         
         for(int i=1; i<14; i++)
         {
            cal = Calendar.getInstance();
            cal.setTime(cfg.getCoreCfg().getIesClient().getDatesCollection().getHoresClase()[i-1]);
            cal.set(Calendar.YEAR, m_cal.get(Calendar.YEAR));
            cal.set(Calendar.MONTH, m_cal.get(Calendar.MONTH));
            cal.set(Calendar.DAY_OF_MONTH, m_cal.get(Calendar.DAY_OF_MONTH));

            cal2 = Calendar.getInstance();
            cal2.setTime(cfg.getCoreCfg().getIesClient().getDatesCollection().getHoresClase()[i]);
            cal2.set(Calendar.YEAR, m_cal.get(Calendar.YEAR));
            cal2.set(Calendar.MONTH, m_cal.get(Calendar.MONTH));
            cal2.set(Calendar.DAY_OF_MONTH, m_cal.get(Calendar.DAY_OF_MONTH));

            cal.add(Calendar.MINUTE, -antelacio);
            cal2.add(Calendar.MINUTE, -antelacio);

            if(cal.compareTo(m_cal)<=0 && m_cal.compareTo(cal2)<=0 )
            {
                hsel = i;
                break;
            }
         }

         if(m_cal.compareTo(cal2)>0) {
                 hsel = 14;
         }

         
         return hsel;
    }

    public int getIntHora()
    {
        return getIntHora(0);
    }


    public boolean esTarda()
    {
         boolean estarda= false;

         Calendar cal = Calendar.getInstance();
         cal.set(Calendar.HOUR_OF_DAY, 14);
         cal.set(Calendar.MINUTE, 60-CoreCfg.coreDB_tardaAntelacio);
         cal.set(Calendar.SECOND,0);

         if(m_cal.compareTo(cal)>0) {
             estarda = true;
         }

         return estarda;
    }

    //comprova si la guardia a l'hora h-èssima es pot signar o no d'acord amb l'hora actual.
    public boolean esGuardiaSignable(int h)
    {
       Calendar cal2 = Calendar.getInstance();
       cal2.setTime(cfg.getCoreCfg().getIesClient().getDatesCollection().getHoresClase()[h-1]);
       cal2.set(Calendar.YEAR, m_cal.get(Calendar.YEAR));
       cal2.set(Calendar.MONTH, m_cal.get(Calendar.MONTH));
       cal2.set(Calendar.DAY_OF_MONTH, m_cal.get(Calendar.DAY_OF_MONTH));
       cal2.add(Calendar.MINUTE, -CoreCfg.coreDB_guardiaAntelacio);
       
       return cal2.before(m_cal);
    }

}
