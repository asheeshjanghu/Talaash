package talaash.ExtraGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author asheesh
 */

public class histgui extends JPanel{

    public  int[][][]graysent=new int[5][][];
    public  int[][]sent;
    
    
    histgui(int[][] values) {
       sent=values;
    repaint();
    }

    public histgui() {
        
    }
   
    @Override
    protected void paintComponent(Graphics g) {        
        int max=0;
        for(int i=0;i<255;i++)
        {
            if(sent[0][i]>max)
                max=sent[0][i];
        }
        int count=1;
      //  System.out.println("max="+max);
            while(max/count>=256)
            {
                count++;                
            }
            max=max/count;
//            System.out.println("max="+max);
//            System.out.println("count="+count);
        int x=256-max;
        max+=x+10;        
        for(int i=0;i<=255;i++)
        {
            g.drawLine(i+10, 300, i+10, 300-(sent[0][i]/count));
           // System.out.println(graysent[0][i]/count);
        }
    }
    
    public void view(String args) throws IOException {
      
        
        JFrame frame = new JFrame();
        frame.setSize(500, 500);
   
        String path=args;
       // System.out.println("path in histgui view method ="+path);
        comphist(path);
        
        File  f=new File(path);
        BufferedImage bi=ImageIO.read(f);
        JTabbedPane jtp=new JTabbedPane();
        JLabel imagelabel=new JLabel();
        jtp.addTab("Gray Histogram", new histgui(graysent[0]));
        jtp.addTab("Red H", new histgui(graysent[1]));
        jtp.addTab("Green H", new histgui(graysent[2]));
        jtp.addTab("Blue H", new histgui(graysent[3]));
        jtp.addTab("Image view", imagelabel);
        imagelabel.setIcon(new ImageIcon(bi));
        frame.add(jtp);
     frame.setVisible(true);
     frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       
    }
    
    public  int[][][] comphist(String pathsent)
    {
        
        String path=pathsent;   
       //  System.out.println("path in histgui comphist method ="+path);
    int size=0;
    File  DBfiles;    
    int [][] red;
    int [][] green;
    int [][] blue;
    int [][] gray;
    int redcolor=0,greencolor=0,bluecolor=0;
    
    File pathname=new File(path);
           DBfiles=pathname;
           size=1;
       
        red=new int[size][256];
        green=new int[size][256];
        blue=new int[size][256];
        gray=new int[size][256];
        
    //to compute the histogram of each image
   
            
            for(int i=0;i<size;i++)
            {
                try
                {
                    BufferedImage bfimage=ImageIO.read(DBfiles);
                    int height =bfimage.getHeight();
                     int width=bfimage.getWidth();
                        double d;
                    for(int j=0;j<height;j++)
                    {
                        for(int k=0;k<width;k++)
                        {   Color rgb=new Color(bfimage.getRGB(j,k));
                        redcolor=rgb.getRed();                         
                        greencolor=rgb.getGreen();                       
                        bluecolor=rgb.getBlue();
                         d=(0.2125*redcolor)+(0.7154*greencolor)+(0.072*bluecolor);
                        red[i][redcolor]++;
                        green[i][greencolor]++;
                        blue[i][bluecolor]++;
                        gray[i][(int)d]++;
                        }
                    }
                  }
                
                catch(IOException e) 
                {
                    System.out.println(e);
                    System.out.println("yahan h dikat");
                }
            }
            
            graysent[0]=gray;
            graysent[1]=red;
            graysent[2]=green;
            graysent[3]=blue;            
            return graysent;
        }
    
    // Variables declaration - do not modify
    // End of variables declaration
}

