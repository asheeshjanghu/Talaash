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
public class parallelHistogramIndexer extends Thread{
    
    public String queryimagepath;
    public int initial,end;
    
    
    public  parallelHistogramIndexer(String path) 
    {
        queryimagepath=path;
    }
   
     
    public  parallelHistogramIndexer(int p,int q) 
    {   
        initial=p;
        end=q;
    }
    
    public static int [][] []rgb;    
    public static int [][] []rgbvalues;        
    public static int size;
    public int [] counter;
    public  static int [][] diffvalue;  
    public int min=99999;
            
            int p=0;
            
    public void setrgbvalues()
    {
        
    try {    
            FileInputStream fis=new FileInputStream(new File("C:/Users/asheesh/Documents/NetBeansProjects/Talaash/abcd.txt"));
            ObjectInputStream ois=new ObjectInputStream(fis);
              
            rgb=(int [][][])ois.readObject();
            fis.close();
            ois.close();            
            
            histgui queryobj=new histgui();
            rgbvalues=queryobj.comphist(queryimagepath);
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
            
            
    // bring histogram of all db images
    public int[] indexer() throws ClassNotFoundException
    {
           
            counter=new int[size];
            
           for(int i=0;i<Tasveer_Talaash.top;i++)
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
           // System.out.println("counter= ");
//            for(int i=0;i<20;i++)
//              System.out.println(counter[i]);
         
     return counter;    
  }
    
    
    @Override
    public void run(){ 
        
           // System.out.println(" yahan run mein and initial ="+initial +" and end ="+end);
            for(int i=initial;i<end;i++)
            {                
                for(int j=0;j<255;j++)
                {
                    diffvalue[i][0]+=Math.abs(rgbvalues[0][0][j]-rgb[0][i][j]);
                }
                
            }
            
    }
    
     public int getsize () throws ClassNotFoundException
    {
       try
       {
           FileInputStream fis=new FileInputStream(new File("C:/Users/asheesh/Documents/NetBeansProjects/Talaash/abcd.txt"));
       
            ObjectInputStream ois=new ObjectInputStream(fis);
              
            rgb=(int [][][])ois.readObject();
            fis.close();
            ois.close();
            
            this.size=rgb[0].length;
           
            this.diffvalue=new int[this.size][2];
            diffvalue[1][0]=4;
            
            for(int i=0;i<size;i++)
            {
                diffvalue[i][0]=0;
                diffvalue[i][1]=0;
            }
             //System.out.println("diffavlue  length = "+diffvalue.length);
       }
            catch(IOException ioe)
        {
            System.out.println(ioe);
        }
        catch(ClassNotFoundException cnf)
        {
            System.out.println(cnf);
        }
       return size;
    }
   
}
