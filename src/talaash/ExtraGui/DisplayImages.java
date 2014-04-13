/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package talaash.ExtraGui;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author asheesh
 */
public class DisplayImages extends DefaultListCellRenderer{
    public Component getListCellRendererCompanent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus){
Component c=super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); 
JLabel label=(JLabel)c;
ImageIcon icon =(ImageIcon )value;
label.setIcon(icon);
return c;
}
    
}
