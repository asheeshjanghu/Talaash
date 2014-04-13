/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package talaash.preprocessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author asheesh
 */
public class shapeextractor {
    
    public void extractshape(String args0,String args1) throws IOException
    {
        String path=args0;
        String edgepath=args1;
        BufferedImage bi=ImageIO.read(new File(path));
        BufferedImage bie=ImageIO.read(new File(edgepath));
        int j=bie.getHeight()/2;
        int i=bie.getWidth()/2;
        Color rgb=new Color(0,0,0);
        int black=rgb.getRGB();
        System.out.print("black = "+black);
        
        int up=0,down=0,left=0,right=0;
        int x=0,y=0;
        for(x=2;x<254;x++)
        {
            for(y=2;y<254;y++)
            {
                if(bie.getRGB(x,y)!=0)
                {
                    int count=0;
                    int a=bi.getRGB(x+1, y);
                    int b=bi.getRGB(x-1, y);
                    int c=bi.getRGB(x+2, y);
                    int d=bi.getRGB(x-2, y);
                    int p=bi.getRGB(x+1, y)-bi.getRGB(x-1, y);
                    int q=bi.getRGB(x+2, y)-bi.getRGB(x-2, y);
                        if(p<10000 || q<10000)
                    {
                        bie.setRGB(x, y, black);
                        count++;
                    }
                    
                }
                
            }
        }
                 x=0;
                 y=0;
                 int count=0;
        for(x=2;x<254;x++)
        {
            for(y=2;y<254;y++)
            {
                if(bie.getRGB(x,y)!=0)
                {
                    
                    int a=bi.getRGB(x, y+1);
                    int b=bi.getRGB(x, y-1);
                    int c=bi.getRGB(x, y+2);
                    int d=bi.getRGB(x, y-2);
                    int p=bi.getRGB(x, y+1)-bi.getRGB(x, y-1);
                    int q=bi.getRGB(x, y+2)-bi.getRGB(x, y-2);
                        if(p<10000 || q<10000)
                    {
                        bie.setRGB(x, y, black);
                        count++;
                       // System.out.println("a ="+a);
                    }
                    
                    
                }
                
            }
        }
        

//        
//        for(y=1;y<255;y++)
//        {
//            for(x=1;x<255;x++)
//            {
//                if(bie.getRGB(x,y)!=0)
//                {
//                    int p=bi.getRGB(x, y+1)-bi.getRGB(x, y-1);
//                    if(p<500)
//                    {
//                        bie.setRGB(x, y, black);
//                    }
//                }
//            }
//        }
        ImageIO.write(bie,  "jpg", new File("C:/Users/asheesh/Documents/dataset//shape1.jpg"));       
        // caser for vertical movement
        while(up==0)
        {
            up=bie.getRGB(i, j); 
            j--;
        }
        while(down==0)
        {
            down=bie.getRGB(i, j); 
            j++;
        }
        while(left==0)
        {
            left=bie.getRGB(i, j); 
            i--;
        }
        while(right==0)
        {
            right=bie.getRGB(i, j); 
            i++;
        }
        
        //consider all 8 directions for connectivity and move in all direcctions on the path and find in the way whether
        // it meets one which we got from the up down and right
        
    }


void adjacent (int p,int q,BufferedImage bie)
{
    int x=p;
    int y=q;
   int d1=bie.getRGB(x+1, y);
   int d2=bie.getRGB(x+1, y-1);
   int d3=bie.getRGB(x, y-1);
   int d4=bie.getRGB(x-1, y-1);
   int d5=bie.getRGB(x-1, y);
   int d6=bie.getRGB(x-1, y+1);
   int d7=bie.getRGB(x, y+1);
   int d8=bie.getRGB(x+1, y+1);
   
if(d1!=0)
{
    p=x+1;
    q=y;
}

if(d2!=0)
{
    p=x;
    q=y-1;
}

if(d3!=0)
{
    p=x-1;
    q=y-1;
}

if(d4!=0)
{
    p=x-1;
    q=y-1;
}

if(d5!=0)
{
    p=x-1;
    q=y;
}

if(d6!=0)
{
    p=x-1;
    q=y+1;
}

if(d7!=0)
{
    p=x;
    q=y+1;
}

if(d8!=0)
{
    p=x+1;
    q=y+1;
}


}
}

