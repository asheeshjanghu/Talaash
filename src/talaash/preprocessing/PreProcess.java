/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package talaash.preprocessing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author asheesh
 */
public class PreProcess {
    
    public String path;   
    
    public PreProcess(String indexingpath)
    {
        path=indexingpath;        
    }
    
    public void starthistogramcal() throws ClassNotFoundException
    {
       try{
         Colorprocessing cpobj=new Colorprocessing(path);         
         cpobj.ComputeHistogram();
         
         System.out.println("1");                      
        FileOutputStream fos=new FileOutputStream(new File("C:/Users/asheesh/Documents/NetBeansProjects/Talaash/abcd.txt"));        
        ObjectOutputStream oos=new ObjectOutputStream(fos);        
        oos.writeObject(cpobj.histogram());
        cpobj.setnull("rgb");
        fos.close();
        oos.close();        
                    
        FileOutputStream foss=new FileOutputStream(new File("C:/Users/asheesh/Documents/NetBeansProjects/Talaash/gray.txt"));        
        ObjectOutputStream ooss=new ObjectOutputStream(foss);        
        ooss.writeObject(cpobj.getgray()); 
        cpobj.setnull("pixelgray");
        foss.close();
        ooss.close();        
         System.out.println("2");   
         
         
        }
        catch (IOException ioe)
        {
            System.out.println(ioe);            
        }         
     finally
       {
          // System.out.println("saale");
       }
    }
    
    public void setpath(String indexingpath) throws FileNotFoundException, IOException
    {
    String pathsent=indexingpath;
    FileOutputStream fos=new FileOutputStream(new File("C:/Users/asheesh/Documents/NetBeansProjects/Talaash/indexingpath.txt"));        
        ObjectOutputStream oos=new ObjectOutputStream(fos);        
        oos.writeObject(pathsent);
        fos.close();
        oos.close();        
        
    }
}
