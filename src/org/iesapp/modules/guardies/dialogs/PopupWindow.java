/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.iesapp.modules.guardies.dialogs;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Josep
 */
public class PopupWindow {

private static final long serialVersionUID = 1L;
private static final Integer STARTING_POS_X = 300;
private static final Integer STARTING_POS_Y = 300;

public void showMessage(String text) throws Exception {
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
Toolkit t = Toolkit.getDefaultToolkit();
JTextArea area = new JTextArea(7, 30);
JPanel panel = new JPanel();
JFrame frame = new JFrame();
final JWindow window = new JWindow();

final JButton ok = new JButton("Click Me");
ok.setSize(40, 20);

final JButton close = new JButton("Close");
ok.setSize(20, 20);

ActionListener listener = new ActionListener(){
@Override
public void actionPerformed(ActionEvent e) {
if(e.getSource() == ok){
JOptionPane.showMessageDialog(null, "Just a sample message.");
} else if (e.getSource() == close){
window.dispose();
}
}};

ok.addActionListener(listener);
close.addActionListener(listener);

area.setEditable(false);
area.setOpaque(true);
area.setAutoscrolls(true);
area.setText(text);
area.setBackground(new Color(202,219,243));

JScrollPane spane = new JScrollPane(area,
JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
spane.setBorder(BorderFactory.createEtchedBorder());

panel.setBackground(new Color(203,222,148));
panel.add(spane);
panel.add(ok);
panel.add(close);

frame.getContentPane().add(panel);
frame.pack();

window.setLocation((int) (t.getScreenSize().getWidth() - STARTING_POS_X),
        (int) (t.getScreenSize().getWidth() - STARTING_POS_Y));
window.setContentPane(frame.getContentPane());
window.setBounds(0, 0, 265, 168);
window.setAlwaysOnTop(true);

for (int i = 300; i < 450; i = i + 1) {
window.setLocation(
(int) (t.getScreenSize().getWidth() - STARTING_POS_X),
(int) (t.getScreenSize().getWidth() - i));
window.setVisible(true);
}

}


}
