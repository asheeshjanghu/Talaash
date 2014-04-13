package talaash.ExtraGui;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import talaash.Tasveer_Talaash;
    

public class ListViewer {

    public void viewme(String path,int[] returnedlist , String framename) throws IOException {
        
        JFrame frame=new JFrame();        
        frame.setTitle(framename);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        DefaultListModel listModel = new DefaultListModel();
        int count = 0;
        for (int i = 0; i < 10; i++)
        {
            //System.out.println("check path"+listOfFiles[i]);
            String name = listOfFiles[returnedlist[i]].toString();
            // load only JPEGs
            
                ImageIcon ii = new ImageIcon(ImageIO.read(listOfFiles[returnedlist[i]]));
                listModel.add(count++, ii);
            
        }
        JList lsm=new JList(listModel);
        lsm.setVisibleRowCount(2);
        lsm.setLayoutOrientation(lsm.HORIZONTAL_WRAP);
        lsm.setSelectionInterval(0, 10);
        lsm.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        frame.add(new JScrollPane(lsm));

        frame.pack();
        frame.setVisible(true);
    }

}
