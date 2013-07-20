/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.modules.guardies.informes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.modules.guardies.GuardiesModule;
import org.iesapp.modules.guardies.util.Cfg;
import org.iesapp.modules.guardies.util.ControlData;


/**
 *
 * @author Josep
 */
public class DadesFaltesAcumulades {
    private final Cfg cfg;
   
    public DadesFaltesAcumulades(Cfg cfg)
    {  
        this.cfg = cfg;
    }

    public ArrayList getDiesAmbSignatures(int mes, int any)
    {
        ArrayList<Integer> list = new ArrayList<Integer>();
        String SQL1 = "SELECT * FROM sig_data where data>='"+any+"-"+(mes+1)+"-01' AND data<='"+any+"-"+(mes+1)+"-31'";
        //System.out.println(SQL1);
        
        try {
            Statement st = cfg.getCoreCfg().getMysql().createStatement();
            ResultSet rs1 = cfg.getCoreCfg().getMysql().getResultSet(SQL1,st);
            while (rs1 != null && rs1.next()) {
                       java.sql.Date date = rs1.getDate("data");
                       Calendar cal = Calendar.getInstance();
                       cal.setTime(date);
                       list.add(cal.get(Calendar.DAY_OF_MONTH));
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DadesFaltesAcumulades.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
            
    public Object[] getInfoMes(String abrev, int mes, int any) {

            int torn = (GuardiesModule.torn2prof.get(abrev)).intValue();
            int offset = 0;
            String taula = "signatures";
            if(torn>0)
            {
                taula = "signatures_tarda";
                offset = 7;
            }

            String SQL1 = "SELECT * FROM " + Cfg.prefix+taula+" where abrev='"+abrev+"' ";

          
            String[] unaFila = new String[32];

            for(int i=0; i<31;i++){
                unaFila[i]  ="-";
            }

            try {
                Statement st1 = cfg.getCoreCfg().getMysql().createStatement();
                ResultSet rs1 = cfg.getCoreCfg().getMysql().getResultSet(SQL1,st1);
                while (rs1 != null && rs1.next()) {

                   java.sql.Date date = rs1.getDate("data");
                   Calendar cal = Calendar.getInstance();
                   cal.setTime(date);
                   if(cal.get(Calendar.MONTH)==mes && cal.get(Calendar.YEAR)==any)
                   {
                        int[] s = new int[7];
                        java.sql.Time[] h_sqlTime =  new java.sql.Time[7];

                        for(int j=1; j<8; j++)
                        {
                             s[j-1]= rs1.getInt("h"+j);
                             h_sqlTime[j-1] = rs1.getTime("h"+j+"_t");
                             
                             
//                             java.sql.Timestamp h = rs1.getTimestamp("h"+j+"_t");
//                             System.out.println(h+ "  "+sql_h_t);
//                             String h_t = ""; //StringUtils.AfterLast(h, " ").trim();
//                             if(h_t.equals("")) h_t="00:00:00";
//
//
//                             try {
//                                //SimpleDateFormat sdf = new java.text.SimpleDateFormat("hh:mm:ss", new Locale("es", "ES"));
//                                //h_sqlTime[j-1] = new java.sql.Time(sdf.parse(h_t).getTime());
//                                h_sqlTime[j-1] = java.sql.Time.valueOf(h_t);
//                                //System.out.println("Fecha con el formato java.sql.Time: " + h + " ; " + h_t + " ; "+ h_sqlTime[j-1]);
//                                } catch (Exception ex) {
//                                     System.out.println("Fecha con el formato java.sql.Time: " + h + " ; " + h_t + " ; ");
//                                     System.out.println("Error al obtener el formato de la fecha/hora: " + ex.getMessage());
//                                }


                        }

                        //posam falta si no hi ha cap hora signada
                        int dia = cal.get(Calendar.DAY_OF_MONTH);
                        ControlData cd = new ControlData(cal,cfg);

                        int nfaltes = 0;
                        for (int j=0; j<7; j++) {
                        if(s[j]==0 || s[j]==2) {
   nfaltes += 1;
}                   }

                        //cal comprovar si ha estat justificat i marcar-la de morat
                        String SQL2 = "Select * from sig_justificat "+
                        " where abrev='"+abrev+"' AND data='"+ cd.getDataSQL() + "'";
                         
                        int count = 0;
                       
                        try {
                         Statement st = cfg.getCoreCfg().getMysql().getConnection().createStatement();
                         ResultSet rs3 = st.executeQuery(SQL2);

                         while (rs3 != null && rs3.next()) {
                             count +=1;
                         }
                        
                         rs3.close();
                         st.close();
                         } catch (SQLException ex) {
                            Logger.getLogger(InformesFaltes.class.getName()).log(Level.SEVERE, null, ex);
                         }


                        if(s[0]==-1 && s[1]==-1 && s[2]==-1 && s[3]==-1 && s[4]==-1 && s[5]==-1 && s[6]==-1)
                        {
                            //es un professor que no te classes aquest dia (mitja jornada p.e.)
                             unaFila[dia-1] = "-";
                        }
                        //alguna hora amb ==2 vol dir que té falta
                        else if(s[0]==2 || s[1]==2 || s[2]==2 || s[3]==2 || s[4]==2 || s[5]==2 || s[6]==2 )
                        {
                            unaFila[dia-1] = "F";
                        }
                        else if( (s[0]!=1 && s[0]!=3) && (s[1]!=1 && s[1]!=3) && (s[2]!=1 && s[2]!=3) &&
                                 (s[3]!=1 && s[3]!=3) && (s[4]!=1 && s[4]!=3) && (s[5]!=1 && s[5]!=3) && 
                                 (s[6]!=1 && s[6]!=3) )
                        {
                            //no ha fixat cap hora és una falta (descomptant si té sortida cas 3)
                            unaFila[dia-1] = "F";
                        }
                        else
                        {
                                //determina l'hora de començament del professor
                                int hStart = 0;
                                for(int j=0; j<7; j++)
                                {
                                    if (s[j] != -1)
                                    {
                                        hStart= j;
                                        break;
                                    }
                               }

                               //Determina la primera hora signada
                               int hFirst = 0;
                               for(int j=0; j<7; j++)
                               {
                                    if (s[j] == 1)
                                    {
                                        hFirst= j;
                                        break;
                                    }
                               }

                            //calcula la diferència de temps (sempre que sigui possible)
                            long min = 0;
                            if(h_sqlTime[hFirst]!=null)
                            {
                                long diff =h_sqlTime[hFirst].getTime()-cfg.getCoreCfg().getIesClient().getDatesCollection().getHoresClase()[offset+hStart].getTime();
                                      min = (long) (diff / (60000.0));  //diferencia en minuts
                            }
//                            System.out.println("date:"+date + " hstart="+hStart + " hFirst="+hFirst);
//                            System.out.println("cercant la diferencia...");
//                            System.out.println(h_sqlTime[hFirst]+ " ; " + Cfg.horesClase[hStart]);
//                            System.out.println(h_sqlTime[hStart].getTime()+"  ; " +
//                                    Cfg.horesClase[hStart].getTime()+"; diff "+ diff+ " "+ min);

                            //determina si hi ha alguna hora sense signar
                            String etc = "";
                            if(s[0]==0 || s[1]==0 || s[2]==0 && s[3]==0 || s[4]==0 || s[5]==0 || s[6]==0) {
                            etc = "(?)";
                        }

                            if(min>0) {
                            unaFila[dia-1] = "" + min + etc;
                        }
                            else {
                            unaFila[dia-1] = "Ok" + etc;
                        }

                        }

                        //AFEGEIX "-J" de JUSTIFICAT, nomes SI ESTAN TOTES LES FALTES del dia JUSTIFICADES
                        if(count == nfaltes && count>0) {
                        unaFila[dia-1] += "-J";
                    }

                   }

                }
                if(rs1!=null)
                {
                    rs1.close();
                    st1.close();
                }
            } catch (SQLException ex) {
                  Logger.getLogger(InformesFaltesAcumul.class.getName()).log(Level.SEVERE, null, ex);
            }

            return unaFila;
    }

}
