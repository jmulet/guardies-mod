/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.modules.guardies;

import org.iesapp.framework.pluggable.DockingFrameworkApp;

/**
 *
 * @author Josep
 */
public class GuardiesTest extends DockingFrameworkApp {

    /**
     * Creates new form GuardiesTest
     */
    public GuardiesTest(String[] args) {
        super(args);
        initComponents();
        this.appClass = getClass();
        this.requiredJar = "org-iesapp-modules-guardies.jar";
        this.requiredModuleName = "org.iesapp.modules.guardies.GuardiesModule";
        this.initializeFramework();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


    
    
     public static void main(String[] args) {
           java.awt.EventQueue.invokeLater(new DisplayApp(args));     
    }
     
    private static class DisplayApp implements Runnable
    {
            private final String[] args;
            public DisplayApp(String[] args)
            {
                this.args = args;
            }
            
            @Override
            public void run() {
                 GuardiesTest app = new GuardiesTest(args);
                 app.setVisible(true);
            }
        
    }
}