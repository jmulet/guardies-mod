/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.modules.guardies.dialogs.horaris;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Josep 
 *
 */
public class DnDHandler extends TransferHandler {

    int rowold = -1;
    int colold = -1;
    
    public DnDHandler() {
    }

    @Override
    public int getSourceActions(JComponent c) {
        return DnDConstants.ACTION_COPY_OR_MOVE;
    }

    @Override
    public Transferable createTransferable(JComponent comp) {
        JTable table = (JTable) comp;
        rowold = table.getSelectedRow();
        colold = table.getSelectedColumn();

        if(colold==0) {
            return null;
        }
        
        SessionBean value = (SessionBean) table.getModel().getValueAt(rowold, colold);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLEncoder xmlEncoder = new XMLEncoder(baos);
        xmlEncoder.writeObject(value);
        xmlEncoder.close();

        String xml = baos.toString();
        StringSelection transferable = new StringSelection(xml);
        //Això esborra el contingut (fa un move)
        //table.getModel().setValueAt(new SessionBean(), row, col);
        return transferable;
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport info) {
        
        JTable.DropLocation dl = (JTable.DropLocation) info.getDropLocation();

        int col = dl.getColumn();

        if (!info.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return false;
        }

        return col>0;
    }

    @Override
    public boolean importData(TransferSupport support) {

        if (!support.isDrop()) {
            return false;
        }

        if (!canImport(support)) {
            return false;
        }

        JTable table = (JTable) support.getComponent();
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

        JTable.DropLocation dl = (JTable.DropLocation) support.getDropLocation();

        int row = dl.getRow();
        int col = dl.getColumn();

        String data;
        try {
            data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

       ByteArrayInputStream bios = new ByteArrayInputStream(data.getBytes());
       XMLDecoder decoder = new XMLDecoder(bios);
       SessionBean beanR = (SessionBean)decoder.readObject();
       decoder.close();
                
       SessionBean newBean = (SessionBean) tableModel.getValueAt(row, col);
        
        if(rowold>=0 && colold>0 && newBean!=null && newBean.type==SessionBean.BLANK)
        {
            table.getModel().setValueAt(new SessionBean(), rowold, colold);  
            tableModel.setValueAt(beanR, row, col);
        }
        rowold = -1;
        colold = -1;   
         

        return true;
    }
    
//    public static void main(String[] args)
//    {
////        SessionBean bean = new SessionBean();
////        bean.setType(SessionBean.CLASSE);
////        bean.setAula("sdfsdfdsf");
////        ByteArrayOutputStream baos = new ByteArrayOutputStream();
////		XMLEncoder xmlEncoder = new XMLEncoder(baos);
////		xmlEncoder.writeObject(bean);
////		xmlEncoder.close();
////
////		String xml = baos.toString();
//// 
////        System.out.println(xml);
////        
////       ByteArrayInputStream bios = new ByteArrayInputStream(xml.getBytes());
////       XMLDecoder decoder = new XMLDecoder(bios);
////       SessionBean beanR = (SessionBean)decoder.readObject();
////       decoder.close();
////       System.out.println(beanR+beanR.getAula());
////        
////        System.exit(0);
//        
//        SessionBean bean1 = new SessionBean();
//        SessionBean bean2 = new SessionBean();
//        bean1.setType(SessionBean.CLASSE);
//         bean2.setType(SessionBean.CLASSE);
//        bean1.setNomClase("A");
//        bean2.setNomClase("B");
//        JFrame frame = new JFrame();
//        DefaultTableModel tableModel=new DefaultTableModel( new Object
//  [][] { {bean1,bean2}}, new String [] { "L", "M"} );
//  
//  JTable table = new
//  javax.swing.JTable(){@Override
// public boolean isCellEditable(int row, int column) {
//  return true; } };
// 
//  table.setModel(tableModel);
//  table.setTransferHandler(new DnDHandler());
//  table.setRowHeight(90);
//  
//  table.getColumnModel().getColumn(0).setCellRenderer(new SessionCellRenderer());
//  table.getColumnModel().getColumn(1).setCellRenderer(new SessionCellRenderer());
//  table.getColumnModel().getColumn(0).setCellEditor(new SessionCellEditor());
//  table.getColumnModel().getColumn(1).setCellEditor(new SessionCellEditor());
// 
//   table.setDragEnabled(true); 
//   table.setDropMode(DropMode.USE_SELECTION);
//   frame.add(table);
//   frame.pack();
//   frame.setVisible(true);
//    }
}
