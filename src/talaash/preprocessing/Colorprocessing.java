/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package talaash.preprocessing;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author asheesh
 */
public class Colorprocessing {
    
    public String path;   
    public int size=0;
    File [] DBfiles;
    int [][][] rgbvalues;
    int [][] red;
    int [][] green;
    int [][] blue;
    int [][] gray;
    int [][][] pixelgray;
    double d=0;
    int []domcolor=new int[1600];
    int []centralcolor;
    Colorprocessing(String pathsent)
    {
        path=pathsent;        
    }
    
    
       void ComputeHistogram()
    {  
           File pathname=new File(path);                  
           DBfiles=pathname.listFiles();        
           size=DBfiles.length;
       System.out.println("3");
        red=new int[size][256];
        green=new int[size][256];
        blue=new int[size][256];
        gray=new int[size][256];
        pixelgray=new int[size][256][256];
        rgbvalues=new int[4][size][256];
        centralcolor=new int [size];
        int redcolor=0,greencolor=0,bluecolor=0;
            int mid=128;
            int p=40;
    //to compute the histogram of each image
   
            
            for(int i=0;i<size;i++)
            {
                try
                {                    
                    BufferedImage bfimage=ImageIO.read(DBfiles[i]);                    
                    int height =bfimage.getHeight();
                     int width=bfimage.getWidth();
                        
                    for(int j=0;j<height;j++)
                    {
                        for(int k=0;k<width;k++)
                        {
                        Color rgb=new Color(bfimage.getRGB(j,k));
                        redcolor=rgb.getRed();                    
                        greencolor=rgb.getGreen();
                        bluecolor=rgb.getBlue();
                         d=(0.2125*redcolor)+(0.7154*greencolor)+(0.072*bluecolor);
                        red[i][redcolor]++;
                        green[i][greencolor]++;
                        blue[i][bluecolor]++;
                        gray[i][(int)d]++;
                        pixelgray[i][j][k]=(int)d;
                        }
                    }
                  }
                
                catch(IOException e) 
                {
                    System.out.print(e);                 
                }
            }
            
        }
    
    // end of compute histogram method
    
       
    int [][][] histogram()
    {
        System.out.println("4");
        rgbvalues[0]=gray;
        rgbvalues[1]=red;
        rgbvalues[2]=green;
        rgbvalues[3]=blue;        

        return rgbvalues;
    }
    
    int [][][] getgray()
    {
        System.out.println("pixelgray length ="+pixelgray.length);
        
        return pixelgray;
    }
     
    int []getcentralcolor()
    {
        return centralcolor;
    }
    
    int getsize()
    {
        return size;
    }
    
    int[][][] getd()
    {        
        return pixelgray;        
    }
    
    void setnull(String arg)
    {
        if(arg=="rgb")
        {
            rgbvalues=null;
            red=null;
            green=null;
            blue=null;
            gray=null;
          
        }
        else if (arg=="pixelgray")
        {
            pixelgray=null;              
        }
        else if( arg=="centralcolor")
        {
            centralcolor=null;
            domcolor=null;
        }
    }
}
