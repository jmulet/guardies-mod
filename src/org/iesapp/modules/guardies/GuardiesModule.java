/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.modules.guardies;

import ch.swingfx.twinkle.NotificationBuilder;
import ch.swingfx.twinkle.style.INotificationStyle;
import ch.swingfx.twinkle.style.theme.DarkDefaultNotification;
import ch.swingfx.twinkle.window.Positions;
import com.l2fprod.common.swing.StatusBar;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.iesapp.clients.iesdigital.dates.DatesControl;
import org.iesapp.clients.iesdigital.guardies.CellModel;
import org.iesapp.clients.iesdigital.guardies.RowModel;
import org.iesapp.clients.iesdigital.professorat.BeanProfessor;
import org.iesapp.clients.iesdigital.spaces.BeanEspai;
import org.iesapp.framework.admin.cfg.ChangePwd;
import org.iesapp.framework.admin.cfg.DlgConfiguration;
import org.iesapp.framework.admin.cfg.SgdConfig;
import org.iesapp.framework.data.User;
import org.iesapp.framework.dialogs.AboutDlg;
import org.iesapp.framework.pluggable.StatusBarZone;
import org.iesapp.framework.pluggable.TopModuleWindow;
import org.iesapp.framework.util.CoreCfg;
import org.iesapp.modules.guardies.dialogs.CellsEditorAdvanced;
import org.iesapp.modules.guardies.dialogs.DlgEspais;
import org.iesapp.modules.guardies.dialogs.DlgFaltesPrevistes;
import org.iesapp.modules.guardies.dialogs.DlgFestius;
import org.iesapp.modules.guardies.dialogs.EnviaMissatges;
import org.iesapp.modules.guardies.dialogs.EsborraSig;
import org.iesapp.modules.guardies.dialogs.FulldeGuardies;
import org.iesapp.modules.guardies.dialogs.ValidacioDlg;
import org.iesapp.modules.guardies.dialogs.ValidacioDlg2;
import org.iesapp.modules.guardies.dialogs.cfg.FormCfg;
import org.iesapp.modules.guardies.dialogs.horaris.EditorHoraris;
import org.iesapp.modules.guardies.informes.InformesFaltes;
import org.iesapp.modules.guardies.informes.InformesFaltesAcumul;
import org.iesapp.modules.guardies.informes.InformesGuardies;
import org.iesapp.modules.guardies.informes.ReportingClass;
import org.iesapp.modules.guardies.table.CellRenderer;
import org.iesapp.modules.guardies.util.Cfg;
import org.iesapp.modules.guardies.util.ControlData;
import org.iesapp.util.StringUtils;

/**
 *
 * @author Josep
 */
public class GuardiesModule extends TopModuleWindow {

    private DefaultTableModel modelTable1;
    public String userKey;
    private int segTranscorr;
    private int timerInterval;
    public int intDiaSetmana;
    public String ctrlDia;
    public String ctrlDiaComplet;
    public boolean esfestiu;
    public String dl;
    public FulldeGuardies gFrame;
    private MouseListener mouseListener;
    public static HashMap<String, String> abrev2prof;
    public static HashMap<String, java.lang.Number> abrev2sgdID;
    public static HashMap<String, java.lang.Number> torn2prof;
    public boolean tornTarda;
    private String commandReserves;
    private String commandFitxes;
    private JLabel jLabelTime;
    private DateChooser dateChooser;
    private Object dia_abans;
    private PropertyChangeListener propertyChangeListener;
    private Cfg cfg;
    private LinkedHashMap<String, BeanProfessor> mapProf;
    private LinkedHashMap<String, RowModel> horariModel;
    /**
     * Creates new form GuardiesModule
     */
    public GuardiesModule() {
        this.moduleDescription = "A module devised to control teacher's absences";
        this.moduleDisplayName = "Guàrdies";
        this.moduleName = "guardies";
        dl = "'";
      }
    
    @Override
    public void postInitialize(){
        //This is the coreExtension
        cfg = new Cfg(coreCfg);   
       
        // estableix el dia de setmana
        ControlData cd = new ControlData(cfg);
        ctrlDia = cd.getDataSQL();
        ctrlDiaComplet = cd.getDiaMesComplet();
        dia_abans = ctrlDiaComplet;
        intDiaSetmana = cd.getIntDia();
        tornTarda = false;   
        dateChooser = new DateChooser(cd.getDate(), cfg);
        
        propertyChangeListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    Date newdate = (Date) evt.getNewValue();
                    Calendar newcal = Calendar.getInstance();
                    newcal.setTime(newdate);
                    ControlData cd = new ControlData(newcal,cfg);
                    ctrlDia = cd.getDataSQL();
                    ctrlDiaComplet = cd.getDiaMesComplet();
                    intDiaSetmana = cd.getIntDia();
                    esfestiu = new DatesControl(cd.getDate(), coreCfg.getIesClient()).esFestiu();

                    if (esfestiu || intDiaSetmana > 5) {
                        fillTable(userKey);
                        dateChooser.formataData();
                        return;
                    }

                   
                    if (!cfg.getCoreCfg().getIesClient().getGuardiesClient().getGuardiesCollection().areSignaturesForDate(ctrlDia)) {
                        Object[] options = {"No", "Sí"};
                        int conf = JOptionPane.showOptionDialog(javar.JRDialog.getActiveFrame(), "Les signatures d'aquest dia encara no s'han creat.\n"
                                + "És recomanable que el programa les generi el dia que toca.\nVoleu crear-les de qualsevol manera?",
                                "Confirmació", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        if (conf == 1) {
                            creaSignaturesDia();
                        }
                    }


                    //Comentar aquesta linia
                    //parent.checkFaltesPrevistes();

                    fillTable(userKey);
                    //System.out.println(parent.ctrlDia + " " + parent.intDiaSetmana + " " +parent.esfestiu);
                    //                if(parent.esfestiu)
                    //                    jLabelDate.setText(parent.ctrlDiaComplet + " *FESTIU*");
                    //                else
                    //                jLabelDate.setText(parent.ctrlDiaComplet);
                    //en principi no seria necessari, pero així ja estaran creades.

                    dateChooser.formataData();
                }
            }
        };

        dateChooser.addChangeDateListener(propertyChangeListener);
        //Si el torn de tarda està activat, determina si estic a la tarda
        if(cfg.activaTarda) 
        {
            tornTarda = cd.esTarda();
           // System.out.println("inici "+tornTarda);
        }
       
        //determina si es festiu i si no, creara les signatures del dia
        esfestiu = new DatesControl(cd.getDate(),coreCfg.getIesClient()).esFestiu();
        
     
        
        initComponents();
        
        jLabelTime = new JLabel(cd.getHoraReduida());
        jLabelTime.setIcon(new ImageIcon(GuardiesModule.class.getResource("/org/iesapp/modules/guardies/icons/clock.gif")));
        
             
//        if(java.awt.SystemTray.isSupported() && CoreCfg.admin)
//            stray = new SysTray(this); //activa el system tray    
    }


    @Override
    public void refreshUI() {
       
        
        abrev2prof = new HashMap<String, String>();
        abrev2sgdID = new HashMap<String, java.lang.Number>();
        torn2prof = new HashMap<String, java.lang.Number>();
        
        userKey = "*";   //by default show all teachers
         
        creaSignaturesDia();
        
        jRadioButtonMenuItem1.setSelected(tornTarda);
        jAlphabetPanel.setTorn(tornTarda);
       

        mouseListener = new org.iesapp.framework.util.JPopupMenuShower(jPopupMenu1);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if(CoreCfg.admin) {
            enablePopup();
        }

        jTable1.setIntercellSpacing( new java.awt.Dimension(2,2) );
        jTable1.setGridColor(java.awt.Color.gray);
        jTable1.setShowGrid(true);
       
     

        segTranscorr = 0;
        
        fillTable(userKey);
     
        //el timer es crida cada segon per actualizar el relloge
        //tambe controla si s'ha de fer un refresc de la db
        timerInterval = 60000; //cada 60 seg actualiza el rellotge

        Timer timer = new Timer(timerInterval, new ActionListener ()
        {
            public void actionPerformed(ActionEvent e)
            {
                tasksTimer();
            }
        });

        timer.start();

      


//        if(stray != null) stray.displayMessage("Benvinguts a Guàrdies");

        jMenuItem22.setEnabled(false);
        jMenuItem23.setEnabled(false);
        

        gFrame = new FulldeGuardies(this,cfg);
        gFrame.setLocationRelativeTo(null);
        gFrame.setVisible(false);
        
        System.out.println("done refreshUI");
       //prefectura
        if(coreCfg.getUserInfo().getGrant()==User.PREF)
        {
            jMenuItem18.setVisible(false);
            jMenu6.setVisible(false);
        }
        
        if(coreCfg.getUserInfo().getGrant()!=User.ADMIN && coreCfg.getUserInfo().getGrant()!=User.PREF)
        {
             jMenu6.setVisible(false);
             jMenu7.setVisible(false);
             jMenuItem18.setVisible(false);
             jMenuItem5.setVisible(false);
             jMenuItem12.setVisible(false);
            
              //Si no ets l'usuari guardies deshabilita el boto de guardies
             jButton1.setEnabled(coreCfg.getUserInfo().getGrant()==User.GUARD);
        }
       
         
      
        updateUIStates();
      
    }
    
    
    
    public void startUp()
    {
        System.out.println("Startup");
        esfestiu = new DatesControl(new java.util.Date(), coreCfg.getIesClient()).esFestiu(); //determina si es festiu
        creaSignaturesDia();
        fillTable(userKey);
               
       
     }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem21 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane()
        ;
        jTable1 = new javax.swing.JTable(){

            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;   //Disallow the editing of any cell
            }

            public Component prepareRenderer
            (TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer( renderer, row, column);
                // We want renderer component to be
                //transparent so background image is visible
                if( c instanceof JComponent )
                ((JComponent)c).setOpaque(!esfestiu);
                return c;
            }

            ImageIcon image = new ImageIcon( new ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/holiday.jpg")).getImage() );
            public void paint(java.awt.Graphics g )
            {
                // tile the background image
                java.awt.Dimension d = getSize();
                for( int x = 0; x < d.width; x += image.getIconWidth() )
                for( int y = 0; y < d.height; y += image.getIconHeight() )
                g.drawImage( image.getImage(), x, y, null, null );
                // Now let the paint do its usual work
                super.paint(g);
            }
        };
        jAlphabetPanel = new org.iesapp.modules.guardies.AlfabetPanel(this);  //new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        legendaNoDeterminat = new javax.swing.JLabel();
        legendaSignat = new javax.swing.JLabel();
        legendaNoHiEs = new javax.swing.JLabel();
        legendaSortida = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPopupMenu1.setLabel("Menu");
        jPopupMenu1.setLightWeightPopupEnabled(false);

        jMenuItem6.setText("Edita");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem6);
        jPopupMenu1.add(jSeparator1);

        jMenuItem7.setText("Marca tots ?");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem7);

        jMenuItem8.setText("Marca tots OK");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem8);

        jMenuItem9.setText("Marca tots Falta");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem9);

        jMenuItem10.setText("Marca tots Sortida");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem10);
        jPopupMenu1.add(jSeparator2);

        jMenuItem11.setText("Desa com document Excel");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem11);

        jMenu1.setText("Fitxer");

        jMenuItem22.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem22.setText("ves a Fitxes-Tutoria");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem22);

        jMenuItem23.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem23.setText("ves a Reserves");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem23);
        jMenu1.add(jSeparator6);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem2.setText("Sortir");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Dades");

        jRadioButtonMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.ALT_MASK));
        jRadioButtonMenuItem1.setText("Mostra torn Tarda");
        jRadioButtonMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jRadioButtonMenuItem1);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItem4.setText("Refresca");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Informes");

        jMenuItem21.setText("Guardies realitzades");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem21);
        jMenu4.add(jSeparator5);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        jMenuItem5.setText("Faltes");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem5);

        jMenuItem12.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F10, 0));
        jMenuItem12.setText("Faltes acumulades");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem12);

        jMenuItem25.setText("Aules disponibles");
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem25);

        jMenuBar1.add(jMenu4);

        jMenu7.setText("Eines");

        jMenu2.setText("Editors");

        jMenuItem14.setText("Editor faltes previstes");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem14);

        jMenuItem15.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem15.setText("Editor d'horaris");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem15);

        jMenuItem17.setText("Editor festius");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem17);

        jMenuItem18.setText("Editor d'espais");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem18);

        jMenu7.add(jMenu2);

        jMenuItem16.setText("Envia missatges");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem16);

        jMenuBar1.add(jMenu7);

        jMenu6.setText("Gestió");

        jMenuItem13.setText("Canvi de contrassenya");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem13);
        jMenu6.add(jSeparator4);

        jMenuItem24.setText("Esborra signatures");
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem24);

        jMenuItem19.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem19.setText("Reset");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem19);
        jMenu6.add(jSeparator3);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jMenuItem3.setText("Opcions generals");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem3);

        jMenuItem20.setText("Prefèrencies d'aplicació");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem20);

        jMenuBar1.add(jMenu6);

        jMenu8.setText("Ajuda");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem1.setText("Sobre l'aplicació");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem1);

        jMenuBar1.add(jMenu8);

        jTable1.setOpaque(false);
        jTable1.setFillsViewportHeight( esfestiu );

        modelTable1 = new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Personal", "1a hora", "2a hora", "3a hora",
                "4a hora", "5a hora", "6a hora", "7a hora"
            }
        );
        jTable1.setModel(modelTable1);
        jTable1.setModel(modelTable1);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.getColumnModel().getColumn(0).setCellRenderer(new CellRenderer());
        jTable1.getColumnModel().getColumn(1).setCellRenderer(new CellRenderer());
        jTable1.getColumnModel().getColumn(2).setCellRenderer(new CellRenderer());
        jTable1.getColumnModel().getColumn(3).setCellRenderer(new CellRenderer());
        jTable1.getColumnModel().getColumn(4).setCellRenderer(new CellRenderer());
        jTable1.getColumnModel().getColumn(5).setCellRenderer(new CellRenderer());
        jTable1.getColumnModel().getColumn(6).setCellRenderer(new CellRenderer());
        jTable1.getColumnModel().getColumn(7).setCellRenderer(new CellRenderer());

        jTable1.setRowHeight(cfg.alturaCella);

        TableColumn col = jTable1.getColumnModel().getColumn(0);
        col.setPreferredWidth(120);
        //col.setResizable(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jAlphabetPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 17, 5));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/guardiaIcon.gif"))); // NOI18N
        jButton1.setText("Guàrdies");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        legendaNoDeterminat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/icon01.gif"))); // NOI18N
        legendaNoDeterminat.setText("Pendent");

        legendaSignat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/icon02.gif"))); // NOI18N
        legendaSignat.setText("Signat");

        legendaNoHiEs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/icon03.gif"))); // NOI18N
        legendaNoHiEs.setText("Falta");

        legendaSortida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/modules/guardies/icons/icon04.gif"))); // NOI18N
        legendaSortida.setText("Sortida");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentContainer());
        getContentContainer().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(legendaNoDeterminat)
                        .addGap(18, 18, 18)
                        .addComponent(legendaSignat)
                        .addGap(18, 18, 18)
                        .addComponent(legendaNoHiEs)
                        .addGap(24, 24, 24)
                        .addComponent(legendaSortida)
                        .addContainerGap())))
            .addComponent(jAlphabetPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jAlphabetPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(legendaNoDeterminat)
                    .addComponent(legendaSignat)
                    .addComponent(legendaNoHiEs)
                    .addComponent(legendaSortida))
                .addGap(2, 2, 2))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        //deal with jPopupMenu
        //    if(evt.getButton()==MouseEvent.BUTTON1)
        //    {
            //       mselCol = jTable1.getSelectedColumn();
            //       mselRow = jTable1.getSelectedRow();
            //    }

        if(evt.getClickCount()==cfg.nClick & evt.getButton()==MouseEvent.BUTTON1)  //only respond to left double-click
        {
            int mcol = jTable1.getSelectedColumn();
            int mrow = jTable1.getSelectedRow();
            if(mcol<0 || mrow <0) {
                return;
            }

            CellModel acell = (CellModel) jTable1.getValueAt(mrow, mcol);

            // VALIDACIO
            //
            if(!CoreCfg.admin && cfg.valida )
            {
                if(cfg.tactil>0)
                {
                    ValidacioDlg2 dlg = new ValidacioDlg2( null, true, acell.codig, coreCfg);
                    dlg.setLocationRelativeTo(null);
                    if(dlg.pwdRequired())
                    {

                        dlg.setVisible(true);
                        boolean pwd = dlg.isValidated;
                        dlg.dispose();
                        if(!pwd) {
                            return;
                        }

                    }
                    //dlg.setLocation(evt.getXOnScreen(),evt.getYOnScreen());
                }
                else
                {
                    ValidacioDlg dlg = new ValidacioDlg(javar.JRDialog.getActiveFrame(), true, acell.codig, coreCfg);
                    if(dlg.pwdRequired())
                    {

                        dlg.setVisible(true);
                        boolean pwd = dlg.isValidated;
                        dlg.dispose();
                        if(!pwd) {
                            return;
                        }

                    }
                }

            }

            // mode = false;  modifica qualssevol cel.la
            // mode = true;   no modifica les guardies
            // status = -1;   augmenta en un l'estat de la cel.la de forma ciclica
            // status >= 0;   força a l'estat que s'indica

            if(CoreCfg.admin)   //aixo es per evitar que el professorat s'equivoquin i nomes siguin per error una única hora
            //El problema es que no surt el missatge de "encara no pot signar la guàrdia".
            //Solucio: 12/9/11
            //Si no hi ha res signat, signa tot com a bloc
            //al haver-hi alguna cosa signada signa nomes la cella on clica
            {
                if(mcol==0)
                {
                    //int retorna = 1;
                    //for(int col=1; col<8; col++)  retorna = this.alterStatus(mrow, col, -1, true, retorna);
                    //                    Point p = evt.getPoint();
                    //                    mselRow = jTable1.rowAtPoint(p);
                    //                    mselCol = jTable1.columnAtPoint(p);

                    //                     mselRow = mrow;
                    //                     mselCol = mcol;

                    CellsEditorAdvanced dlg = new CellsEditorAdvanced(this,true, mrow, mcol, coreCfg);
                    dlg.setLocationRelativeTo(null);
                    dlg.setVisible(true);

                }
                else
                {
                    int retorna = this.alterStatus(mrow, mcol, -1, false, 1);
                }
            }
            else if(!CoreCfg.admin && !cfg.valida )  //com a usuari - sense validacio
            {
                //determina si ja ha signat alguna hora
                boolean haClicat = false;
                for(int col=1; col<8; col++)
                {
                    CellModel mycell = (CellModel) jTable1.getValueAt(mrow, col);
                    if(mycell.status==1)
                    {
                        haClicat = true;
                        break;
                    }
                }

                //si ja ha clicat nomes afectara a la cel.la en questió sino a totes
                if(haClicat)
                {
                    if(mcol==0) {
                        return;
                    } //no fa res si clica sobre el seu nom
                    int retorna = this.alterStatus(mrow, mcol, -1, false, 1);
                }
                else
                {
                    int retorna = 1;
                    for(int col=1; col<8; col++) {
                        retorna = this.alterStatus(mrow, col, -1, true, retorna);
                    }
                }
            }

        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        String[] keys = new String[]{"a","b","c","d","e","f","g","h","i",
            "j","k","l","m","n","o","p","q","r",
            "s","t","u","v","w","x","y","z","*"};
        List<String> list = Arrays.asList(keys);
        String key = ""+evt.getKeyChar();
        if(list.contains(key.toLowerCase()) )
        {
            userKey = key.toUpperCase();
            jAlphabetPanel.setSelectedLetter(userKey);
            this.fillTable(userKey);
        }

    }//GEN-LAST:event_jTable1KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        gFrame.setVisible(true);
        gFrame.toFront();
        gFrame.setExtendedState(JFrame.NORMAL);  //to prevent iconize
        //gFrame.Update();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed

        jPopupMenu1.setVisible(false);
        int mselRow = jTable1.getSelectedRow();
        int mselCol = jTable1.getSelectedColumn();
        // System.out.println(mselRow +","+ mselCol);
        CellsEditorAdvanced dlg = new CellsEditorAdvanced(this, true, mselRow, mselCol, coreCfg);
        dlg.setLocationRelativeTo(null);
        dlg.setVisible(true);

        dlg.dispose();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed

        int retorna = 1;
        int mselRow = jTable1.getSelectedRow();
        if(mselRow<0) {
            return;
        }
        for(int i=1; i<8; i++) {
            retorna = alterStatus(mselRow, i, 0, true, retorna);
        }
        jPopupMenu1.setVisible(false);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        int retorna = 1;
        int mselRow = jTable1.getSelectedRow();
        if(mselRow<0) {
            return;
        }

        for(int i=1; i<8; i++) {
            retorna = alterStatus(mselRow, i, 1, true, retorna);
        }
        jPopupMenu1.setVisible(false);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        int retorna = 1;
        int mselRow = jTable1.getSelectedRow();
        if(mselRow<0) {
            return;
        }

        for(int i=1; i<8; i++) {
            retorna = alterStatus(mselRow, i, 2, true, retorna);
        }
        jPopupMenu1.setVisible(false);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        int retorna = 1;
        int mselRow = jTable1.getSelectedRow();
        if(mselRow<0) {
            return;
        }

        for(int i=1; i<8; i++) {
            retorna = alterStatus(mselRow, i, 3, true, retorna);
        }
        jPopupMenu1.setVisible(false);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed

        FileDialog fd = new FileDialog((JFrame) null,
            "Desa taula com document Excel...",
            FileDialog.SAVE);
        fd.setLocationRelativeTo(null);
        fd.setVisible(true);
        String file = fd.getFile();
        String dir = fd.getDirectory();
        if(file == null || dir == null) {
            return;
        }
        if( !StringUtils.AfterLast(file, ".").trim().equals("xls") ) {
            file +=".xls";
        }

        try {
            WritableWorkbook workbook = Workbook.createWorkbook(new File(dir+file));
            WritableSheet sheet =  workbook.createSheet("Horari"+ctrlDia, 0);

            //headers
            sheet.addCell(  new Label(0, 0, "Personal")  );
            sheet.addCell(  new Label(1, 0, "1a Hora")  );
            sheet.addCell(  new Label(2, 0, "2a Hora")  );
            sheet.addCell(  new Label(3, 0, "3a Hora")  );
            sheet.addCell(  new Label(4, 0, "4a Hora")  );
            sheet.addCell(  new Label(5, 0, "5a Hora")  );
            sheet.addCell(  new Label(6, 0, "6a Hora")  );
            sheet.addCell(  new Label(7, 0, "7a Hora")  );

            for(int i=0; i<jTable1.getRowCount(); i++)
            {
                CellModel acell = (CellModel) jTable1.getValueAt(i, 0);
                String cadena = acell.getText();
                sheet.addCell(new Label(0, i+1, cadena ));

                WritableFont boldRedFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
                boldRedFont.setColour(Colour.RED);
                WritableCellFormat boldRed = new WritableCellFormat(boldRedFont);

                WritableFont boldBlueFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
                boldBlueFont.setColour(Colour.BLUE);
                WritableCellFormat boldBlue = new WritableCellFormat(boldBlueFont);

                WritableFont boldGreenFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
                boldGreenFont.setColour(Colour.GREEN);
                WritableCellFormat boldGreen = new WritableCellFormat(boldGreenFont);

                WritableFont boldOrangeFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
                boldOrangeFont.setColour(Colour.ORANGE);
                WritableCellFormat boldOrange = new WritableCellFormat(boldOrangeFont);

                for(int j=1; j<8; j++){
                    acell = (CellModel) jTable1.getValueAt(i, j);
                    cadena = acell.getText();

                    if(acell.status==0) {
                        sheet.addCell(new Label(j, i+1, cadena, boldBlue ));
                    }
                    else if(acell.status==1) {
                        sheet.addCell(new Label(j, i+1, cadena, boldGreen ));
                    }
                    else if(acell.status==2) {
                        sheet.addCell(new Label(j, i+1, cadena, boldRed ));
                    }
                    else if(acell.status==3) {
                        sheet.addCell(new Label(j, i+1, cadena, boldOrange ));
                    }
                }
            }

            for(int x=0;x<8;x++)
            {
                CellView cell=sheet.getColumnView(x);
                cell.setAutosize(true);
                sheet.setColumnView(x, cell);
            }

            workbook.write();
            workbook.close();
        } catch (IOException ex){
            Logger.getLogger(InformesFaltes.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (WriteException ex) {
            Logger.getLogger(InformesFaltes.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            Desktop.getDesktop().open(new File(dir+file));
        } catch (IOException ex) {
            Logger.getLogger(GuardiesModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        try {
            Process proc = Runtime.getRuntime().exec(commandFitxes);
        } catch (IOException ex) {
            Logger.getLogger(GuardiesModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        try {
            Process proc = Runtime.getRuntime().exec(commandReserves);
        } catch (IOException ex) {
            Logger.getLogger(GuardiesModule.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jRadioButtonMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem1ActionPerformed
        tornTarda = jRadioButtonMenuItem1.isSelected();
        this.fillTable(userKey);
    }//GEN-LAST:event_jRadioButtonMenuItem1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        fillTable(userKey);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        InformesGuardies dlg = new InformesGuardies(javar.JRDialog.getActiveFrame(), true, cfg);
        dlg.setLocationRelativeTo(null);
        dlg.setVisible(true);
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        org.iesapp.modules.guardies.informes.InformesFaltes frame = new org.iesapp.modules.guardies.informes.InformesFaltes(javar.JRDialog.getActiveFrame(), false, cfg);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        InformesFaltesAcumul dlg = new InformesFaltesAcumul(javar.JRDialog.getActiveFrame(), false, cfg);
        dlg.setLocationRelativeTo(null);
        dlg.setVisible(true);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed

        ArrayList list = new ArrayList<BeanDisponibilitat>();

        //Select taula d'aules disponibles
        for(int dia=1; dia<=5; dia++)
        {
            String sdia = "";
            switch(dia)
            {
                case 1: sdia="Dilluns";break;
                case 2: sdia="Dimarts";break;
                case 3: sdia="Dimecres";break;
                case 4: sdia="Dijous";break;
                case 5: sdia="Divendres";break;

            }
            for(int h=1; h<=7; h++)
            {
                String shora = h+"a";
                String condition = " utilizable_guardia=1 AND aula"
                                 + " NOT IN (SELECT aula FROM sig_horaris WHERE dia="
                                 + dia+" AND hora="+ h + ") ORDER BY aula";
                ArrayList<BeanEspai> listEspais = cfg.getCoreCfg().getIesClient().getSpacesCollection().listEspais(condition);
                for(BeanEspai be: listEspais)
                {
                        BeanDisponibilitat bean = new BeanDisponibilitat();
                        bean.setAula(be.getAula()+ " : "+be.getDescripcio());
                        bean.setDia(sdia);
                        bean.setHora(shora);
                        bean.setReservable(be.getReservable()>0?"SI":"NO");
                        list.add(bean);
                }             
            }
        }

        if(!list.isEmpty())
        {
            ReportingClass rc = new ReportingClass();
            rc.reportDisponibilitat(list, new HashMap());
        }
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed

        DlgFaltesPrevistes dlg = new DlgFaltesPrevistes(this, true, coreCfg);
        dlg.setLocationRelativeTo(null);
        dlg.setVisible(true);
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        EditorHoraris dlg = new EditorHoraris(this, true, coreCfg);
        dlg.setLocationRelativeTo(null);
        dlg.setVisible(true);
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        DlgFestius dlg = new DlgFestius(this, true, coreCfg);
        dlg.setLocationRelativeTo(null);
        dlg.setVisible(true);
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        DlgEspais dlg = new DlgEspais(javar.JRDialog.getActiveFrame(), true, coreCfg);
        dlg.setLocationRelativeTo(null);
        dlg.setVisible(true);
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed

        if(coreCfg.getUserInfo().getIdSGD()<=0)
        {
            JOptionPane.showMessageDialog(this, "El vostre usuari no té assignat una\nid d'usuari SGD i per tant només\npodeu realitzar operacions de lectura.");
            return;
        }

        if(coreCfg.getSgd().isClosed())
        {
            SgdConfig dlg = new SgdConfig(javar.JRDialog.getActiveFrame(), true, coreCfg);
            dlg.setLocationRelativeTo(null);
            dlg.setVisible(true);
            if(dlg.Ok)
            {
                EnviaMissatges dlg2 = new EnviaMissatges(this, true, cfg);
                dlg2.setLocationRelativeTo(null);
                dlg2.setVisible(true);
            }
            dlg.dispose();
        }
        else
        {
            EnviaMissatges dlg2 = new EnviaMissatges(this, true, cfg);
            dlg2.setLocationRelativeTo(null);
            dlg2.setVisible(true);
        }

    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        ChangePwd dlg = new ChangePwd(javar.JRDialog.getActiveFrame(), true, coreCfg);
        dlg.setLocationRelativeTo(null);
        dlg.setVisible(true);
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
        EsborraSig dlg = new EsborraSig(javar.JRDialog.getActiveFrame(), true, dateChooser.getDate(), coreCfg);
        dlg.setLocationRelativeTo(null);
        dlg.setVisible(true);
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        startUp();
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed

        /* Needs confirmation even when entering as Admin, just for security reasons*/
        if(cfg.tactil>0)
        {
            ValidacioDlg2 dlg = new ValidacioDlg2(javar.JRDialog.getActiveFrame(), true, "Admin", coreCfg);
            dlg.setLocationRelativeTo(null);
            dlg.setVisible(true);
            boolean pwd = dlg.isValidated;
            dlg.dispose();
            if(!pwd) {
                return;
            }
        }
        else
        {
            ValidacioDlg dlg = new ValidacioDlg(javar.JRDialog.getActiveFrame(), true, "Admin", coreCfg);
            dlg.setLocationRelativeTo(null);
            dlg.setVisible(true);
            boolean pwd = dlg.isValidated;
            dlg.dispose();
            if(!pwd) {
                return;
            }
        }

        DlgConfiguration dlgframe = new DlgConfiguration(javar.JRDialog.getActiveFrame(), true);
        dlgframe.setLocationRelativeTo(null);
        dlgframe.setResizable(false);
        dlgframe.setVisible(true);
        dlgframe.dispose();
        this.startUp(); //fa un hard reset

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        FormCfg dlg = new FormCfg(javar.JRDialog.getActiveFrame(), true, cfg);
        dlg.setLocationRelativeTo(null);
        dlg.setVisible(true);
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        AboutDlg dlgframe = new AboutDlg(javar.JRDialog.getActiveFrame(), true, "Guàrdies", "Un programa per gestionar les guàrdies del professorat.", coreCfg);
        dlgframe.setLocationRelativeTo(null);
        dlgframe.setResizable(false);
        dlgframe.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

//    
//    
//    private void onFormOpenened()
//    {
//        
//       //crea una única instancia del full de guardies
//       //Mogut del constructor al handler del windowopened
//       gFrame = new FulldeGuardies(this);
//       gFrame.setLocationRelativeTo(null);
//       gFrame.setVisible(false);
//        
//        //prefectura
//        if(coreCfg.getUserInfo().getGrant()==User.PREF)
//        {
//            jMenuItem18.setVisible(false);
//            jMenu6.setVisible(false);
//        }
//        
//        if(coreCfg.getUserInfo().getGrant()!=User.ADMIN && coreCfg.getUserInfo().getGrant()!=User.PREF)
//        {
//             jMenu6.setVisible(false);
//             jMenu7.setVisible(false);
//             jMenuItem18.setVisible(false);
//             jMenuItem5.setVisible(false);
//             jMenuItem12.setVisible(false);
//             //jStatusPanel.onlineUsers1.setVisible(false);
//             
//              //Si no ets l'usuari guardies deshabilita el boto de guardies
//             jButton1.setEnabled(coreCfg.getUserInfo().getGrant()==User.GUARD);
//        }
//       
//         
//      
//        updateUIStates();
//      
//    }
    
    
   
     public void eraseTable()
    {
           //elimina la taula antiga
            while(jTable1.getRowCount()>0)
            {
                 modelTable1.removeRow(0);
            }
    }


    // Omple la taula amb el professorat el llinatge del qual començi per key
    // key = * significa tots
    // Aquesta es l'estructura de la base de dades NOVA

    public void fillTable(String key)
    {
        
        jTable1.setOpaque(!esfestiu);
        jTable1.setFillsViewportHeight( esfestiu );
        
        this.eraseTable();
        if( coreCfg.getMysql().isClosed() ) {
            return;
        }
          
        if (esfestiu) {
            return;
        }
        
          //MATI O TARDA (Choose correct table name and set table headers)
        int iIni;
        int iEnd;
        if(tornTarda)
        {
            iIni = 8;
            iEnd = 14;
            for(int i=1; i<8; i++) {
                jTable1.getColumnModel().getColumn(i).setHeaderValue( (i+7)+"a hora");
            }
         }
        else
        {
            iIni = 1;
            iEnd = 8;
            for(int i=1; i<8; i++) {
                jTable1.getColumnModel().getColumn(i).setHeaderValue(i+"a hora");
            }
        }
        
        //Guardies table is filled according to this linked map
        //Step 1: include all professorat (orderer by lastname)
        //Step 2: include horari to map
        //Step 3: include guardies to map
        //Step 4: include status to map
        //Step 5: include comments (homework)
        //All these steps are implemented in the iesdigital client
        //Returns LinkedHashMap<String, RowModel> hm = new LinkedHashMap<String, RowModel>();
        horariModel = cfg.getCoreCfg().getIesClient().getGuardiesClient().getGuardiesCollection().getRowModelMap(intDiaSetmana, ctrlDia, tornTarda);

        for (String ky : horariModel.keySet()) {
            RowModel rmodel2 = horariModel.get(ky);
            String name = rmodel2.cells[0].text.trim();

            //nomes introdueix a la grid aquells que coincideixin amb la cerca per llinatge
            if (name.startsWith(userKey) || userKey.equals("*")) {

                if (rmodel2.toBeShown()) {
                    modelTable1.addRow(new Object[]{
                                rmodel2.cells[0], rmodel2.cells[1], rmodel2.cells[2], rmodel2.cells[3],
                                rmodel2.cells[4], rmodel2.cells[5], rmodel2.cells[6], rmodel2.cells[7]
                            });
               }
            }
        }

        //Mostra l'estadistica (should be done over all data)
        doStatistics();
    }


    private void tasksTimer()
    {
       //Get new time
        ControlData cd = new ControlData(cfg);
        
       //Canvia l'hora en el statusbar
        jLabelTime.setText(cd.getHoraReduida());
        
       //Implementa un autorefresc
        if(CoreCfg.coreDB_autorefresc != 0)
        {
        segTranscorr += (int) (timerInterval/1000.); //ha de coincidir amb el del rellotge
            if(segTranscorr > CoreCfg.coreDB_autorefresc)
            {
                 fillTable(userKey);
                 gFrame.doRefresh();
                 segTranscorr = 0;
            }
        }

        //Staff from statusBar
        
       //comprova si la data que hi ha al menu és l'actual; si no la marca com a vermell
       String ara = cd.getDiaMesComplet();

       if(!CoreCfg.admin && cfg.activaTarda && tornTarda != cd.esTarda())
       {
           fillTable("*"); //carrega amb tots
           tornTarda = cd.esTarda();
           jRadioButtonMenuItem1.setSelected(tornTarda);
       }

       jAlphabetPanel.setTorn(tornTarda);
       
     
       //comprova si s'ha produit en el rellotge intern un canvi de dia i amb aixo actualitza
       if(!ara.equals(dia_abans))
       {
           //canvia les dates a les del nou dia
            ctrlDia = cd.getDataSQL();
            ctrlDiaComplet = cd.getDiaMesComplet();
            intDiaSetmana = cd.getIntDia();
            fillTable(userKey);
            dateChooser.removeChangeDateListener(propertyChangeListener);
            dateChooser.setDate(cd.getDate());
            
           //omple la taula
            fillTable("*");
            dia_abans = ara;
            creaSignaturesDia(); //crea les signatures del dia si es necessari
            dateChooser.addChangeDateListener(propertyChangeListener);
            dateChooser.formataData();

       }

       
//Gestiona tasques programades. Només un ordinador se n'hauria d'encarregar.
//tots els altres han de tenir gestionaTasques=0;
       
//       if(Cfg.gestionaTasques)
//       {
//            //agafa l'hora actual
//            String hora = cd.getHoraReduida();
//            for(int i=0; i<Cfg.listTasques.size(); i++)
//            {
//                int dia = Cfg.listTasques.get(i).getDia();
//                java.sql.Time st = Cfg.listTasques.get(i).getHora();
//                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");           
//                String horaProgramada = formatter.format(st);
//                String tasca = Cfg.listTasques.get(i).getTasca();
//                
//                //System.out.println("compara"+horaProgramada+"hora"+hora);
//                
//                if( ( (dia==0 &&  parent.intDiaSetmana<6) || dia == parent.intDiaSetmana) && (hora.equals(horaProgramada)) )
//                {
//                    if(tasca.equals("IMPORTSGD-FITXES"))
//                    {
//                           //Importa dades de la base sgd
//                            if(Cfg.activaImportSGD>0)
//                            {
//                                UpdateFromSGD updatesgd = new UpdateFromSGD("curso"+StringUtils.anyAcademic_primer(), 
//                                                                            StringUtils.anyAcademic(), "PROGRAMADA");
//                                updatesgd.start();
//                            }
//                    }
//                    else if(tasca.equals("PRESENCIA"))
//                    {     
//                        //System.out.println("fent run start"+GuardiesGUI.sp+GuardiesGUI.sp.isRunning());
//                        GuardiesGUI.sp= new SensorPresencia();
//                        if(GuardiesGUI.sp!=null && !GuardiesGUI.sp.isRunning()) GuardiesGUI.sp.start();
//                    }
//                }
//            }
//          
//       
//       }
//
//       //Comprova la connexió a la base de dades
//       nsec += 1;
//
//       if(nsec > Cfg.refresh_checkConnection)
//       {
//
//           //el sistema ha d'intentar re-establir la connexio (sobretot en mode usuari)
//                if(!CoreCfg.admin){
//                    establishConnection();
//           }   
//
//        nsec =0;
//       }


    
        
        
//Comprova si hi ha problemes amb les guardies i treu un missatge d'alerta
//        if(Cfg.activaMissatges > 0 && CoreCfg.admin && stray != null)
//        {
//            if(new ControlData(coreCfg).displayMessage())
//            {
//                String info = gFrame.getInfo();
//                if( !info.equals("") )  stray.displayMessage(info);
//            }
//        }

        
    }  
    



// Aquesta funcio modifica l'estat d'una cel.la de la taula,
// respon a un doble-clik, simplement augmenta en 1 l'estat
// mode = false;  modifica qualssevol cel.la
// mode = true;   no modifica les guardies
// status = -1;   augmenta en un l'estat de la cel.la de forma ciclica
// status >= 0;   força a l'estat que s'indica

//Retorna = -1; error no es pot signar perque es massa tard
//Retorna = -2; error no es pot signar encara la guardia, es massa prest
//Retorna = -3; usuari no autoritzat per signar
//Retorna = 0; no s'ha fet res perque es un camp en blanc que no es signa
//Retorna = 1; OK
    
//No permet alterar status d'un professor que no sigui ell mateix si entra com usuari 
//diferent a PREF, ADMIN, GUARD    
    
public int alterStatus(int row, int col, int stat, boolean mode, int last)
{
     if(coreCfg.getUserInfo().getGrant()!=User.GUARD && coreCfg.getUserInfo().getGrant()!=User.PREF && coreCfg.getUserInfo().getGrant()!=User.ADMIN)
     {
        CellModel aCell00 = (CellModel) jTable1.getValueAt(row,0);
        String abrev00 = aCell00.getCodig();
        if(!coreCfg.getUserInfo().getAbrev().equals(abrev00))
        {
           String sms = "Ho setim. No teniu prou privilegis com per signar un altre usuari.";
         
           if(last !=-3)
           {
           INotificationStyle style = new DarkDefaultNotification().withWindowCornerRadius(8).withWidth(300).withAlpha(0.86f);

                    // Now lets build the notification
                     new NotificationBuilder().withStyle(style) // Required. here we set the previously set style
                        .withTitle("Atenció") // Required.
                        .withMessage(sms) // Optional
                        .withIcon(new ImageIcon(NotificationBuilder.ICON_EXCLAMATION)) // Optional. You could also use a String path
                        .withDisplayTime(5000) // Optional
                        .withPosition(Positions.CENTER) // Optional. Show it at the center of the screen
                        .showNotification(); 
           }
           return -3;
        }
     }
    
     int offset=0;
     String taula = "";

     if(tornTarda)
     {
         taula = "signatures_tarda";
         offset = 7;
     }
     else
     {
         taula = "signatures";
     }
     
      //First in the grid
      CellModel aCell = (CellModel) jTable1.getValueAt(row, col);
      
      if( aCell.getText().length() == 0 ) {
          return 0;
      }   //una cel.la buida no es valida

      //aplica la politica fins quan es pot signar una classe (durant l'hora)
      ///int hhora = new ControlData(coreCfg).getIntHora();
      int hhora = new ControlData(cfg).getIntHora(55-cfg.activaPolicy);
      if(!CoreCfg.admin && (col + offset) < hhora && aCell.status ==0 && cfg.activaPolicy !=0 )
      {
           if(last !=-1)
           {
        
           String sms = "És massa tard per signar les classes anteriors a la " + hhora + "a hora" +
                        " (" + coreCfg.getIesClient().getDatesCollection().getHoresClase()[hhora-1] + " hores). Consultau a prefectura.";
           
           INotificationStyle style = new DarkDefaultNotification().withWindowCornerRadius(8).withWidth(300).withAlpha(0.86f);

                    // Now lets build the notification
                     new NotificationBuilder().withStyle(style) // Required. here we set the previously set style
                        .withTitle("Atenció") // Required.
                        .withMessage(sms) // Optional
                        .withIcon(new ImageIcon(NotificationBuilder.ICON_EXCLAMATION)) // Optional. You could also use a String path
                        .withDisplayTime(5000) // Optional
                        .withPosition(Positions.CENTER) // Optional. Show it at the center of the screen
                        .showNotification(); // this retu
           }
           return -1; //no modifiques res (imposible modificar)
      }

      int code = aCell.getStatus();

      if(stat <0)  //a un estat negatiu no ha de canviar res
      {
      if(CoreCfg.admin)
      {
            code +=1;
            if(code > 3) {
                code = 0;
            }
      }
      else
      {

            if(code == 0) {
                code = 1; 
            }//every click will only provide 1 = present
            else{
                return 0;
            }
      }
      }
      else
      {
         code = stat % 4; //assume 4 states
      }



      //es una guardia i cal comprovar si es pot signar o no
      if(aCell.getType()==1 && !CoreCfg.admin)
      {
               ControlData cd = new ControlData(cfg);

               if( !cd.esGuardiaSignable(col) ) // ara.compareTo(iguard) < 0
               {
                       if(!mode){
                         INotificationStyle style = new DarkDefaultNotification().withWindowCornerRadius(8).withWidth(300).withAlpha(0.86f);

                    // Now lets build the notification
                     new NotificationBuilder().withStyle(style) // Required. here we set the previously set style
                        .withTitle("Atenció") // Required.
                        .withMessage("Encar no es pot signar la guàrdia. Intenta-ho més tard.") // Optional
                        .withIcon(new ImageIcon(NotificationBuilder.ICON_EXCLAMATION)) // Optional. You could also use a String path
                        .withDisplayTime(5000) // Optional
                        .withPosition(Positions.CENTER) // Optional. Show it at the center of the screen
                        .showNotification(); // this retu
                       }
                       return -2; //no modifiques res (seria una guardia)
               }
           
      }
   

      this.doStatistics();

   ////////////////////////////////////////////////////////////////////////
   ////////////////////////////////////////////// commit to the database //
   ////////////////////////////////////////////////////////////////////////
      aCell.setStatus(code);
      CellModel finalCell = aCell;

      //Now item to be included in the database
      
      aCell = (CellModel) jTable1.getValueAt(row,0);
      String abrev = aCell.getCodig();
 
      try{
      
      int nup = cfg.getCoreCfg().getIesClient().getGuardiesClient().getGuardiesCollection().updateHorari(abrev, intDiaSetmana, ctrlDia, col, code, tornTarda);

          // Si i només si s'ha actualitzat la base de dades, mostra l'actualització en la grid
          if (nup > 0) {
              jTable1.setValueAt(finalCell, row, col);
          }

            

 ////// INSERTA INFORMACIO SOBRE AQUELLES ABSENCIES EN QUE S'HA INDICAT FEINA O COMMENTARIS


      aCell = (CellModel) jTable1.getValueAt(row, col);
      if(aCell.getType()>0) {
              return 0;
      } //les guardies i camps filtrats no s'introdueixen en diari_guardies.
                             
            
            String condicio = " WHERE DATA="+dl+ctrlDia+dl+" AND HORA="+ (col + offset)
               + " AND PROFE_FALTA='" + abrev + "'";

             String SQL1 =  "SELECT * FROM " + Cfg.prefix + "diari_guardies " + condicio;
           
             Statement st6 = coreCfg.getMysql().createStatement();
             ResultSet rs1 = coreCfg.getMysql().getResultSet(SQL1,st6);
   
                if(rs1!=null && rs1.next())
                {   // ja existeix nomes cal fer un update
                    if(aCell.status < 2) //ha deixat d'esser falta i no hi ha d'estar
                    {

                        SQL1 =  "DELETE FROM "+ Cfg.prefix + "diari_guardies "
                              + condicio;
                         
                        nup = coreCfg.getMysql().executeUpdate(SQL1);

                        return 1;
                    }
                    

                    SQL1 =  "UPDATE "+ Cfg.prefix + "diari_guardies "
                     + " SET feina=?, comentaris=? " + condicio;
                    
                    Object[] updatedValues = new Object[]{aCell.getFeina(), aCell.getComment()};  //.replace("\n", ";") 
                     
                     nup = coreCfg.getMysql().preparedUpdate(SQL1, updatedValues);

               }
               else
               {
                    if(aCell.status < 2) {
                    return 1;
                } //nomes inserta per a falta o sortida
                    //no existeix i cal insertar-lo de nou


                     SQL1 =  "INSERT INTO "+ Cfg.prefix + "diari_guardies ( DATA, DIA_SETMANA, "
                     + "HORA, PROFE_FALTA, PROFE_GUARDA, GRUP, FEINA, COMENTARIS) VALUES( "
                             + "?, ?, ?, ?, ?, ?, ?, ?)";


                     Object[] updatedValues = new Object[]{ctrlDia, intDiaSetmana, col+offset, abrev, new String(), aCell.getText(),
                                                           aCell.getFeina(), aCell.getComment()}; //.replace("\n", ";")

                  
                     nup = coreCfg.getMysql().preparedUpdate(SQL1,updatedValues);
                }

            if(rs1!=null) {
              rs1.close();
              st6.close();
          }

     }
     catch(java.sql.SQLException ex)
     {
         Logger.getLogger(GuardiesModule.class.getName()).log(Level.SEVERE, null, ex);
     }

   
     return 1;
  
}


 public void doStatistics()
 {
        //VARIABLES PER A L'ESTADISTICA
        int nTotal = 0;
        int nPendent = 0;
        int nSignat = 0;
        int nFalta = 0;
        int nSortida = 0;

        //Ho feim sobre l'horari total
        for(RowModel row: horariModel.values())
        {
            if(row.toBeShown())
            {
                for(int j =1; j<8; j++)
                {
                    CellModel cell = row.cells[j];

                    int st = cell.getStatus();
                    if( cell.getText().length() != 0 )
                    {
                    switch(st)
                    {
                        case 0: nPendent += 1; break;
                        case 1: nSignat += 1; break;
                        case 2: nFalta += 1; break;
                        case 3: nSortida += 1; break;
                    }
                    }
                }
            }

        }

     // REALITZA L'ESTADISTICA
        nTotal = nPendent + nSignat + nFalta + nSortida;

        DecimalFormat onedec = new DecimalFormat(".0");

        if(nTotal>0)
        {
            String tpcPendent = onedec.format(100*nPendent/(1.0*nTotal));
            String tpcSignat = onedec.format(100*nSignat/(1.0*nTotal));
            String tpcFalta = onedec.format(100*nFalta/(1.0*nTotal));
            String tpcSortida = onedec.format(100*nSortida/(1.0*nTotal));
            legendaNoDeterminat.setText("Pendent ("+ tpcPendent + " %)");
            legendaSignat.setText("Signat ("+ tpcSignat + " %)");
            legendaNoHiEs.setText("Falta ("+ tpcFalta + " %)");
            legendaSortida.setText("Sortida ("+ tpcSortida + " %)");
        }
        else
        {
            legendaNoDeterminat.setText("Pendent (--- %)");
            legendaSignat.setText("Signat (--- %)");
            legendaNoHiEs.setText("Falta (--- %)");
            legendaSortida.setText("Sortida (--- %)");
        }
 }

 public void enablePopup()
{
        jTable1.addMouseListener(mouseListener);
 }


 public void disablePopup()
 {
        jTable1.removeMouseListener(mouseListener);
 }

 // Crea un camp de signatures per tots els professors en el dia actual
 // d'aquesta forma no ens haurem de preocupar si ha signat o no, sempre
 // ho podrem trobar a la base de dades
 // per a cada profe: primer comprova si existeix en la base de dades, si no l'inserta
 //
 // Una vegada s'ha realitzat la creacio, comprova les faltes previstes del dia

 public void creaSignaturesDia()
 {
        tornTarda = false;
        if(cfg.activaTarda) 
        {
            tornTarda = new ControlData(cfg).esTarda();
        }
       //Per seguritat carrega el fitxer autosig.ini
        cfg.readAutosig();
     
        if(coreCfg.getMysql().isClosed()) {
         return;
     }
        //escriu la data d'avui a la taula de dates de la base de dades
        //retorna true si ja existia
    

        //consulta de tot el professorat
        abrev2prof = new HashMap<String, String>();
        torn2prof = new HashMap<String, java.lang.Number>();
        String SQL1 = "SELECT * FROM " + Cfg.prefix +"professorat ORDER BY NOMBRE";
        try {
            Statement st = coreCfg.getMysql().createStatement();
            ResultSet rs1 = coreCfg.getMysql().getResultSet(SQL1,st);
   
            while (rs1!=null && rs1.next()) {
                String nombre = rs1.getString("NOMBRE");
                String abrev = rs1.getString("ABREV");
                abrev2prof.put(abrev, nombre);
                torn2prof.put(abrev, rs1.getInt("TORN"));
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(GuardiesModule.class.getName()).log(Level.SEVERE, null, ex);
        }
        abrev2prof = StringUtils.getSortedMap(abrev2prof);


        //si es un festiu; o si ja estan creades les signatures, no fa res
        //System.out.println(esfestiu);
        //DataCtrl cd = new DataCtrl();
        esfestiu = new DatesControl(new java.util.Date(),coreCfg.getIesClient()).esFestiu();
        if(esfestiu || intDiaSetmana > 5) 
        {
            fillTable("*");
            return;
        }
            
  
        //Comprova si ja esta
        boolean already = cfg.getCoreCfg().getIesClient().getGuardiesClient().getGuardiesCollection().promptDate(ctrlDia);
        //System.out.println(already);
        if(already) 
        {
            checkFaltesPrevistes();
            fillTable("*");
            return;
        }
       

      for(String key : abrev2prof.keySet()){

          String abrev = key.trim();

          //Quan crea les signatures: per defecte 0 = no signat, sense feina ni comentari
          int stat = 0;      
          String commentg = "";
          int feina = 0;


          if( (torn2prof.get(key)).intValue() == 0 || (torn2prof.get(key)).intValue() == 2 ) {
              cfg.getCoreCfg().getIesClient().getGuardiesClient().getGuardiesCollection().commitHorari(abrev, intDiaSetmana, ctrlDia, false, stat, commentg, feina, false);
          }  //crea signatures mati per a tots els professors del mati
                                                                                                     //crea signatures mati pels professors de mati i tarda
          if( (torn2prof.get(key)).intValue() >= 1) {
              cfg.getCoreCfg().getIesClient().getGuardiesClient().getGuardiesCollection().commitHorari(abrev, intDiaSetmana, ctrlDia, true, stat, commentg, feina, false);
          }  //crea signatures pels profes de tarda
        }
      
        checkFaltesPrevistes();
        checkAutoSignatures();
        fillTable("*");
 }

 //Ara Comprova sempre les faltes previstes
 //Actualitza la base de dades            
      public void checkFaltesPrevistes()
      {
          
          
      //consulta la taula de faltes previstes
        String SQL1 = "SELECT * FROM " + Cfg.prefix +"faltes_previstes where desde<="+
                dl+ctrlDia+dl+" and fins>="+dl+ctrlDia+dl;
       //g System.out.println(SQL1);
       
//        ArrayList<String> falta_previstes = new ArrayList<String>();
//        ArrayList<String> falta_commentg = new ArrayList<String>();
//        ArrayList<Number> falta_feina = new ArrayList<Number>();

        try {
           Statement st = coreCfg.getMysql().createStatement();
           ResultSet rs1 = coreCfg.getMysql().getResultSet(SQL1,st);
   
            while (rs1!=null && rs1.next()) {
                String abrev = rs1.getString("abrev").trim();
                String commentg = rs1.getString("commentg");
                int feina = rs1.getInt("feina");
               // System.out.println("> profe "+abrev+" amb falta prevista per avui....");
                int stat = 2;
                
                int torn = ((java.lang.Number) torn2prof.get(abrev)).intValue();
                if( torn == 0 || torn == 2 ) {
                    cfg.getCoreCfg().getIesClient().getGuardiesClient().getGuardiesCollection().commitHorari(abrev, intDiaSetmana, ctrlDia, false, stat, commentg, feina, true);
                }  //crea signatures mati per a tots els professors del mati
                                                                                                             //crea signatures mati pels professors de mati i tarda
                if(torn >= 1 ) {
                    cfg.getCoreCfg().getIesClient().getGuardiesClient().getGuardiesCollection().commitHorari(abrev, intDiaSetmana, ctrlDia, true, stat, commentg, feina, true);
                }  //crea signatures pels profes de tarda 
              
            }
            if(rs1!=null) {
                rs1.close();
                st.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(GuardiesModule.class.getName()).log(Level.SEVERE, null, ex);
        }
   
     
      }

      // Professors als quals se'ls signa automàticament els dies
      // establerts en el fitxer config/autosig.ini
      public void checkAutoSignatures()
      {
          for(String txt: cfg.autoSig.keySet())
          {
                int stat = 1; //L'estat que posarà és Signat
                  
                String abrev = txt.toUpperCase();
                ArrayList<Integer> dies = cfg.autoSig.get(txt).dies;
                ArrayList<String> hores = cfg.autoSig.get(txt).hores;
                
                
                if(dies.contains(intDiaSetmana))
                {
                    int id = dies.indexOf(intDiaSetmana);
                    String quinesHores = hores.get(id);
                    
                   //s'han  de signat totes
                    if(quinesHores.length() == 0)
                    {
                        int torn = ((java.lang.Number) torn2prof.get(abrev)).intValue();
                        if( torn == 0 || torn == 2 ){
                             cfg.getCoreCfg().getIesClient().getGuardiesClient().getGuardiesCollection().commitHorari(abrev, intDiaSetmana, ctrlDia, false, stat, "", 0, true);  //crea signatures mati per a tots els professors del mati
                        }                                                                //crea signatures mati pels professors de mati i tarda
                        if(torn >= 1 ){
                             cfg.getCoreCfg().getIesClient().getGuardiesClient().getGuardiesCollection().commitHorari(abrev, intDiaSetmana, ctrlDia, true, stat, "", 0, true);  //crea signatures pels profes de tarda 
                        }
                    }
                    else
                    {
                        //S'han de signar nomes algunes hores
                        ArrayList<String> mh = StringUtils.parseStringToArray(quinesHores, ",", StringUtils.CASE_INSENSITIVE);
                        for(int k=0; k<mh.size(); k++)
                        {
                            int h = (int) Double.parseDouble(mh.get(k));
                            boolean esTarda = false;
                            if(h>7)
                            {
                                h -= 7;
                                esTarda = true;
                            }
                            cfg.getCoreCfg().getIesClient().getGuardiesClient().getGuardiesCollection().updateHorari(abrev, intDiaSetmana, ctrlDia, h, stat, esTarda);
                        }
                    }
              }
          }
      }
 


 public void updateUIStates()
    {
          //CoreCfg.admin es refereix a prefectura o administrador
        
          if(CoreCfg.admin)
            {
                enablePopup();
            }
            else
            {
                disablePopup();
            }
            jMenuItem25.setVisible(CoreCfg.admin);
        
            jMenuItem3.setEnabled(CoreCfg.admin);
            jMenuItem5.setEnabled(CoreCfg.admin);
            jMenuItem12.setEnabled(CoreCfg.admin);
            jMenuItem13.setEnabled(CoreCfg.admin);
            jMenuItem14.setEnabled(CoreCfg.admin);
            jMenuItem15.setEnabled(CoreCfg.admin);
            jMenuItem16.setEnabled(CoreCfg.admin);
            jMenuItem17.setEnabled(CoreCfg.admin);
            jMenuItem18.setEnabled(CoreCfg.admin);
            jMenuItem19.setEnabled(CoreCfg.admin);
            jMenuItem20.setEnabled(CoreCfg.admin);
            jMenuItem24.setEnabled(CoreCfg.admin);
            
            jRadioButtonMenuItem1.setEnabled(CoreCfg.admin);
            dateChooser.setEnabled(CoreCfg.admin);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    /*
    private javax.swing.JPanel jAlphabetPanel;
    */
    public org.iesapp.modules.guardies.AlfabetPanel jAlphabetPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    public javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    public javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    public javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    public javax.swing.JTable jTable1;
    private javax.swing.JLabel legendaNoDeterminat;
    private javax.swing.JLabel legendaNoHiEs;
    private javax.swing.JLabel legendaSignat;
    private javax.swing.JLabel legendaSortida;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setMenus(JMenuBar jMenuBar1, JToolBar jToolbar1, StatusBar jStatusBar1) {
        
        jMenuBar1.add(jMenu3);
        jMenuBar1.add(jMenu4);
       
        if(coreCfg.getUserInfo().getGrant()==User.PREF || coreCfg.getUserInfo().getGrant()==User.ADMIN)
        {
             jMenuBar1.add(jMenu7);               
        }
        else
        {
            jMenuItem5.setVisible(false);
            jMenuItem12.setVisible(false);
        }
        
        
        if(coreCfg.getUserInfo().getGrant()==User.ADMIN)
        {
             jMenuBar1.add(jMenu6);
        }
        
        
        
        
        //Controla quins menus s'han de veure
        
        ((StatusBarZone) jStatusBar1.getZone("third")).addComponent(dateChooser);
        ((StatusBarZone) jStatusBar1.getZone("third")).addComponent(jLabelTime);
        
    }

 
}
