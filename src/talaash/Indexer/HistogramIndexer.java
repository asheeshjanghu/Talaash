/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package talaash.Indexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import talaash.ExtraGui.histgui;
import talaash.Tasveer_Talaash;

/**
 *
 * @author asheesh
 */
public class HistogramIndexer extends Thread{
    
    public String queryimagepath;
    
    public  HistogramIndexer(String path) 
    {
        queryimagepath=path;
    }
   
    
    public int [][] []rgb;    
    public int [][] []rgbvalues;        
    public int size;
    public int [] counter;
    public int [][] diffvalue; 
    public int [][] rgbdiffvalue; 
    
    public int min=99999;
            
            int p=0;
    
    // bring histogram of all db images
    public int[] indexer(int arg) throws ClassNotFoundException
    {
        
    try {    
            FileInputStream fis=new FileInputStream(new File("C:/Users/asheesh/Documents/NetBeansProjects/Talaash/abcd.txt"));
            ObjectInputStream ois=new ObjectInputStream(fis);
              
            rgb=(int [][][])ois.readObject();
            fis.close();
            ois.close();            
                       
            histgui queryobj=new histgui();
            rgbvalues=queryobj.comphist(queryimagepath);
            size=rgb[0].length;
        
            counter=new int[size];
            diffvalue=new int[size][2];
            rgbdiffvalue=new int[size][2];
               for(int i=0;i<size;i++)
            {                
                diffvalue[i][0]=0;
                diffvalue[i][1]=0;
                
                rgbdiffvalue[i][0]=0;
                rgbdiffvalue[i][1]=0;
                for(int j=0;j<255;j++)
                {
                    diffvalue[i][0]+=Math.abs(rgbvalues[0][0][j]-rgb[0][i][j]);
                    rgbdiffvalue[i][0]+=Math.abs((rgbvalues[1][0][j]-rgb[1][i][j])+((rgbvalues[2][0][j]-rgb[2][i][j]))+(rgbvalues[3][0][j]-rgb[3][i][j]));
                }
                
            }
            
           if(arg==1)
           {
               counter=getmin(diffvalue);
           }
           else if( arg==2)
           {
               counter=getmin(rgbdiffvalue);
           }
           // System.out.println("counter= ");
//            for(int i=0;i<20;i++)
//              System.out.println(counter[i]);
           
    }   
    catch(IOException ioe)
    {
        System.out.println(ioe);
    }
    catch(ClassNotFoundException cnf)
    {
        System.out.println(cnf);
    }
     return counter;    
  }
    
    int [] getmin(int [][]diffvalue)
    {
         for(int i=0;i<size;i++)
            {        
            for(int j=0;j<size;j++)
            {
            if((diffvalue[j][0]<=min)&&(diffvalue[j][1]==0))
            {
                min=diffvalue[j][0];
                counter[p]=j;                
            }
            }            
            diffvalue[counter[p]][1]=1;
            min=99999;
            p++;
            }             
            p=0;
            return counter;
    }
    
    public void setsize () throws ClassNotFoundException
    {
       try
       {
           FileInputStream fis=new FileInputStream(new File("C:/Users/asheesh/Documents/NetBeansProjects/Talaash/abcd.txt"));
       
            ObjectInputStream ois=new ObjectInputStream(fis);
              
            rgb=(int [][][])ois.readObject();
            fis.close();
            ois.close();
            
            this.size=rgb[0].length;
            System.out.println("size = "+size);
            
       }
            catch(IOException ioe)
        {
            System.out.println(ioe);
        }
        catch(ClassNotFoundException cnf)
        {
            System.out.println(cnf);
        }
       
    }
    
}
