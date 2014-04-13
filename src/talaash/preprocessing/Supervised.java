/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package talaash.preprocessing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author asheesh
 */
public class Supervised {
    
    public Supervised(){}
    
    public double[][] group() throws FileNotFoundException, IOException, ClassNotFoundException
    {
     FileInputStream fis=new FileInputStream(new File("C:/Users/asheesh/Documents/NetBeansProjects/Talaash/abcd.txt")) ;
     ObjectInputStream ois=new ObjectInputStream(fis);
     int [][][]histogram=(int [][][])ois.readObject();
     
     int x=histogram[0][0].length;
     int size=histogram[0].length;
   //  int [][] newhistogram=new int[size][x/8];
     int count=0;
     int sum=0;
     System.out.println(size +" = size and initial bins = "+x);
     
     int k=5;
     int []centroid=new int[k];
     int div=size/k;
     centroid[0]=0;
     centroid[1]=div+5;
     centroid[2]=2*div+10;
     centroid[3]=3*div+10;
     centroid[4]=4*div+10;
     
     for(int i=0;i<5;i++)
         System.out.println(" "+i+ " centroid =  "+centroid[i]);
         
     int [] cluster=new int[size];
     
    double  [][]distance=new double [size][k+1];
    double [][] mean=new double[k][x];
    int c=0;
     int indx=0;
     
       for(int i=0;i<size;i++)
         {
             for(int j=0;j<k;j++)
             {
                 distance[i][j]=0;
             }
         }
     // for calculating initial means
         for(int j=0;j<size;j++)
         {
             if(c<100)
            {
             for(int bin=0;bin<x;bin++)         
                {
                     mean[indx][bin]+=(histogram[0][j][bin]);
                }
             cluster[j]=indx;
             distance[j][k]=indx;
            //  System.out.println(j+" cluster ="+cluster[j]);
             c++;
            }             
         else 
         {             
             c=0;
             indx++;         
         }             
         }
     indx=0;
     
     for(int i=0;i<k;i++)
     {
         for(int bin=0;bin<x;bin++)         
         {
             mean[i][bin]=mean[i][bin]/100;
         //System.out.println("cluster no. ="+i+"ki bin no. "+bin+  " ki  mean value  ="+mean[i][bin]);
         }
     }
     
     return distance;
    }
}
