/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.modules.guardies.util;

import java.util.ArrayList;

/**
 *
 * @author Josep
 */
public class AutoSig {
 
         public String abrev="";
         public ArrayList<Integer> dies;
         public ArrayList<String> hores;
         
         public AutoSig()
         {
             dies = new ArrayList<Integer>();
             hores = new ArrayList<String>();
         }
         public AutoSig(String abrev, ArrayList<Integer> dies, ArrayList<String> hores)
         {
             this.abrev  = abrev;
             this.dies = dies;
             this.hores = hores;
         }
}   
