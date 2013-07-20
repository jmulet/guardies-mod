/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.modules.guardies.util;



import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iesapp.framework.util.CoreCfg;
import org.iesapp.util.StringUtils;

/**
 *
 * @author Josep
 */
public class Cfg {

        public static final String GUARDIESINI = "config/guardies.ini";
        public static int DBTYPE=2;
        public static String  prefix = "sig_"; 
        public static int NH = 8;  //CHECK

    
        //del fitxer de configuració
        public static Color colorGuardies = new Color(255,200,100);
        public static Color colorParell = new Color(255,255,255);
        public static Color colorSenar = new Color(250,250,255);

       //elements color pels informes
        public static Color colorFalta = new Color(255,100,100);
        public static Color colorFaltaJustificada = new Color(155, 0, 150);
        public static Color colorOk = new Color(10, 255, 10);
        public static Color colorFaltenXSignar =new Color(200,255,100);
        public static Color colorSignaTard = new Color(250,255,50);


        public  String lookAndFeel="Nimbus";
        public  String fontName="Helvetica";
        public  int    fontSize=12;
        public  String  accessFile="";
        public  int     nClick=2;
        public  boolean valida=false;
        public  boolean validaSortida=true;
        public  int     activaPolicy=15;
        public  boolean activaTarda=true;
        public  int activaMissatges = 15;
        public int activaImportSGD=0;
        public String remiteMissatges="";

        public int tactil = 0;
        public int alturaCella= 32;

        public ArrayList<String> filtrats;
        public int refresh_checkConnection; //comprova la conexio cada x segons
        public int timeoutSU;

        public boolean gestionaTasques=false;
        public ArrayList<BeanTasca> listTasques;
        
        private final String AUTOSIG = "./config/autosig.ini";
        public HashMap<String,AutoSig> autoSig;
         
        //Store here a pointer to CoreCfg
        protected final CoreCfg coreCfg;
        
    public Cfg(CoreCfg coreCfg) {
        this.coreCfg = coreCfg;
        load();
    }

    private void load() {
        filtrats = new ArrayList<String>();
        listTasques = new ArrayList<BeanTasca>();
        refresh_checkConnection = 30;

        readIniFile();
        readDatabase();

        //Si existeix; carrega el fitxer d'autosignatures
        readAutosig();

    }

    private void readIniFile() {

        File propfile = new File(CoreCfg.contextRoot+File.separator+Cfg.GUARDIESINI);
        if (!propfile.exists()) {
            saveIni();
        }


        Properties props = new Properties();
        //try retrieve data from file
        try {
            FileInputStream filestream = new FileInputStream(CoreCfg.contextRoot+File.separator+Cfg.GUARDIESINI);
            props.load(filestream);

            String message = props.getProperty("nClick");
            nClick = (int) Double.parseDouble(message);

            message = props.getProperty("valida");
            valida = ((int) Double.parseDouble(message)) > 0;

            message = props.getProperty("validaSortida");
            validaSortida = ((int) Double.parseDouble(message)) > 0;

       
            message = props.getProperty("activaPolicy");
            activaPolicy = ((int) Double.parseDouble(message));

            message = props.getProperty("activaTarda");
            activaTarda = ((int) Double.parseDouble(message)) > 0;

            message = props.getProperty("activaMissatges");
            activaMissatges = ((int) Double.parseDouble(message));

            message = props.getProperty("colorFilesSenar");
            colorSenar = org.iesapp.framework.util.ColorUtils.getRGB(message);

            message = props.getProperty("colorFilesParell");
            colorParell = org.iesapp.framework.util.ColorUtils.getRGB(message);

            message = props.getProperty("colorGuardies");
            colorGuardies = org.iesapp.framework.util.ColorUtils.getRGB(message);

            lookAndFeel = props.getProperty("lookAndFeel");
            fontName = props.getProperty("fontName");
            message = props.getProperty("fontSize");
            fontSize = ((int) Double.parseDouble(message));

            message = props.getProperty("colorFalta");
            colorFalta = org.iesapp.framework.util.ColorUtils.getRGB(message);

            message = props.getProperty("colorFaltaJustificada");
            colorFaltaJustificada = org.iesapp.framework.util.ColorUtils.getRGB(message);

            message = props.getProperty("colorOk");
            colorOk = org.iesapp.framework.util.ColorUtils.getRGB(message);

            message = props.getProperty("colorFaltenXSignar");
            colorFaltenXSignar = org.iesapp.framework.util.ColorUtils.getRGB(message);

            message = props.getProperty("colorSignaTard");
            colorSignaTard = org.iesapp.framework.util.ColorUtils.getRGB(message);

            message = props.getProperty("tactil");
            tactil = ((int) Double.parseDouble(message));

            message = props.getProperty("alturaCella");
            alturaCella = ((int) Double.parseDouble(message));

            message = props.getProperty("activaImportSGD");
            activaImportSGD = ((int) Double.parseDouble(message));


            message = props.getProperty("gestionaTasques");
            gestionaTasques = ((int) Double.parseDouble(message)) > 0;

            remiteMissatges = props.getProperty("remiteMissatges").trim();

            filestream.close();
        } catch (IOException ex) {
           Logger.getLogger(Cfg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readDatabase() {
          
        String SQL;

        if (getCoreCfg().getMysql().isClosed()) {
            return;  //do nothing if there is no connection
        }
        SQL = "SELECT ITEM FROM sig_senseguardia";
        try {
            Statement st = getCoreCfg().getMysql().createStatement();
            ResultSet rs = getCoreCfg().getMysql().getResultSet(SQL, st);
            while (rs.next()) {
                filtrats.add(StringUtils.noNull(rs.getString("ITEM")));
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Cfg.class.getName()).log(Level.SEVERE, null, ex);
        }



        SQL = "SELECT * FROM sig_progtasques";
        try {
            Statement st = getCoreCfg().getMysql().createStatement();
            ResultSet rs = getCoreCfg().getMysql().getResultSet(SQL, st);
            while (rs.next()) {
                BeanTasca bt = new BeanTasca();
                bt.setDia(rs.getInt("dia"));
                bt.setHora(rs.getTime("hora"));
                bt.setTasca(rs.getString("tipo"));
                listTasques.add(bt);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Cfg.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void saveIni() {
        Properties props = new Properties();


        try {
            props.setProperty("tablePrefix", "sig_");
            props.setProperty("nClick", "" + nClick);
            props.setProperty("valida", valida ? "1" : "0");
            props.setProperty("validaSortida", validaSortida ? "1" : "0");
            props.setProperty("activaPolicy", "" + activaPolicy);
            props.setProperty("activaTarda", activaTarda ? "1" : "0");
            props.setProperty("activaMissatges", "" + activaMissatges);

            String message = colorSenar.getRed() + ", " + colorSenar.getGreen() + ", " + colorSenar.getBlue();
            props.setProperty("colorFilesSenar", message);

            message = colorParell.getRed() + ", " + colorParell.getGreen() + ", " + colorParell.getBlue();
            props.setProperty("colorFilesParell", message);

            message = colorGuardies.getRed() + ", " + colorGuardies.getGreen() + ", " + colorGuardies.getBlue();
            props.setProperty("colorGuardies", message);

            props.setProperty("lookAndFeel", lookAndFeel);
            props.setProperty("fontName", fontName);
            props.setProperty("fontSize", "" + fontSize);

            message = colorFalta.getRed() + ", " + colorFalta.getGreen() + ", " + colorFalta.getBlue();
            props.setProperty("colorFalta", message);

            message = colorFaltaJustificada.getRed() + ", " + colorFaltaJustificada.getGreen() + ", " + colorFaltaJustificada.getBlue();
            props.setProperty("colorFaltaJustificada", message);

            message = colorOk.getRed() + ", " + colorOk.getGreen() + ", " + colorOk.getBlue();
            props.setProperty("colorOk", message);

            message = colorFaltenXSignar.getRed() + ", " + colorFaltenXSignar.getGreen() + ", " + colorFaltenXSignar.getBlue();
            props.setProperty("colorFaltenXSignar", message);

            message = colorSignaTard.getRed() + ", " + colorSignaTard.getGreen() + ", " + colorSignaTard.getBlue();
            props.setProperty("colorSignaTard", message);

            props.setProperty("tactil", "" + tactil);
            props.setProperty("alturaCella", "" + alturaCella);
            props.setProperty("activaImportSGD", "" + activaImportSGD);
            props.setProperty("gestionaTasques", gestionaTasques ? "1" : "0");
            props.setProperty("remiteMissatges", remiteMissatges);


            FileOutputStream filestream = new FileOutputStream(CoreCfg.contextRoot+File.separator+Cfg.GUARDIESINI);
            props.store(filestream, null);
            filestream.close();

        } catch (IOException ex) {
            Logger.getLogger(Cfg.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Format del fitxer AutoSig MA4=1;2{1,2};5{6};
     */
    public void readAutosig() {

        autoSig = new HashMap<String, AutoSig>();

        File propfile = new File(AUTOSIG);
        if (!propfile.exists()) {
            return;
        }


        Properties props = new Properties();
        try {
            FileInputStream filestream = new FileInputStream(AUTOSIG);
            props.load(filestream);

            Set<Object> keySet = props.keySet();

            Iterator it = keySet.iterator();

            while (it.hasNext()) {
                String abrev = (String) it.next();
                String dies = props.getProperty(abrev);
                ArrayList<String> aux = StringUtils.parseStringToArray(dies, ";", StringUtils.CASE_INSENSITIVE);
                ArrayList<Integer> arraydies = new ArrayList<Integer>();
                ArrayList<String> arrayhores = new ArrayList<String>();

                for (int i = 0; i < aux.size(); i++) {
                    String txt = aux.get(i);
                    if (txt.contains("{")) {
                        String dia = StringUtils.BeforeFirst(txt, "{");
                        int idia = (int) Double.parseDouble(dia);
                        arraydies.add(idia);
                        String hores = StringUtils.AfterFirst(txt, "{");
                        hores = StringUtils.BeforeLast(hores, "}");
                        arrayhores.add(hores);
                    } else {

                        int idia = (int) Double.parseDouble(txt);
                        arraydies.add(idia);
                        arrayhores.add("");
                    }
                }

                AutoSig auto = new AutoSig(abrev, arraydies, arrayhores);
                autoSig.put(abrev, auto);
            }

            filestream.close();
        } catch (IOException ex) {
            Logger.getLogger(Cfg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public CoreCfg getCoreCfg() {
        return coreCfg;
    }
}
