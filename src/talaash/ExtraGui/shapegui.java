/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package talaash.ExtraGui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import talaash.preprocessing.WaveletTransform;

/**
 *
 * @author asheesh
 */
public class shapegui {
    
     public void view(String args) throws IOException {
      
        String path=args;
        
    int[][] querywavelet;     
    
    // query images gray values collected
        int [][] querymatrix=getquerygrayvalue(path);
          
        // query image wavelet transformation performed
        WaveletTransform wtobj=new WaveletTransform();
        querywavelet=wtobj.transform(querymatrix);
        int width=querywavelet.length;
        BufferedImage queryshape=new BufferedImage(width,width,BufferedImage.TYPE_INT_RGB);
                //ImageIO.read(new File("C:/Users/asheesh/Documents/dataset//402.jpg"));
        
        for(int i=0;i<width;i++)
            for(int j=0;j<width;j++)
            {   
                queryshape.setRGB(i, j, querywavelet[i][j]);
            }
         ImageIO.write(queryshape,  "jpg", new File("C:/Users/asheesh/Documents/dataset//shape.jpg"));       
        
        JFrame frame = new JFrame();
        frame.setSize(500, 500);
   
        
        System.out.println("path in shape view method ="+path);
                
        // for showing the image whose wavelet transformation we wish to see
        File  f=new File(path);
        BufferedImage bi=ImageIO.read(f);
        JTabbedPane jtp=new JTabbedPane();
        JLabel imagelabel=new JLabel();
        jtp.addTab("Image view", imagelabel);
        imagelabel.setIcon(new ImageIcon(bi));
        
        
        String path2="C:\\Users\\asheesh\\Documents\\dataset\\shape.jpg";       
        File  f2=new File(path2);
         BufferedImage bi2=ImageIO.read(f2);
        JLabel imagelabel2=new JLabel();
        jtp.addTab("wavelet view", imagelabel2);
        imagelabel2.setIcon(new ImageIcon(bi2));
        
        frame.add(jtp);
     frame.setVisible(true);
     frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     }
 
     
    public int[][] getquerygrayvalue(String path) throws IOException
    {
        int[][] querygrayvalue;
        querygrayvalue=new int[256][256];        
        // this method computes the gray values of every pixel of queryimage
        File f=new File(path);
        BufferedImage bfimage=ImageIO.read(f);                    
                    int height =bfimage.getHeight();
                     int width=bfimage.getWidth();
                     double d=0;   
                    for(int j=0;j<height;j++)
                    {
                        for(int k=0;k<width;k++)
                        {
                        Color rgb=new Color(bfimage.getRGB(j,k));
                         d=(0.2125*rgb.getRed())+(0.7154*rgb.getGreen())+(0.072*rgb.getBlue());
                         
                         querygrayvalue[j][k]=(int)d;
                        }
                    }
                    
        return querygrayvalue;
    }
     
}
