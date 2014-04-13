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
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author asheesh
 */
public class kmeans {
    
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
     
     for(int i=0;i<k;i++)
         System.out.println(" "+i+ " centroid =  "+centroid[i]);
         
     int [] cluster=new int[size];
     
    double  [][]distance=new double [size][k+2];
    double [][] mean=new double[k][x];
    int c=0;
     int indx=0;
     
       for(int i=0;i<size;i++)
         {
             for(int j=0;j<k;j++)
             {
                 distance[i][j]=0;                 
             }
             distance[i][k+1]=0;
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
     
     
     //ITERATION LOOP
     
     // for finding new k centroids and finding distances between different points
     
     for(int iteration=0;iteration<50;iteration++)
     {         
               for(int i=0;i<size;i++)
         {
             for(int j=0;j<k;j++)
             {
                 distance[i][j]=0;
             }
         }
     
         // now to find new centroids find the minimum distance centroid from the mean point
         for(int i=0;i<size;i++)
         {             
               int j=cluster[i];           
                 for(int bin=0;bin<x;bin++)
                 {
                     double t=Math.abs((mean[j][bin]-histogram[0][i][bin]));                     
                     distance[i][j]+=t;
                 }
                          distance[i][k]=j;
         }
          
      
        int temp=9999999;
        int ind=0;
                 for(int j=0;j<k;j++)
                 {
                     for(int i=0;i<size;i++)
                     {
                         if(temp>distance[i][j]&& distance[i][k]==j)
                         {
                             temp=(int)distance[i][j];
                             ind=i;
                          //   System.out.println("yahan andar centroid change  i= "+i+"  temp= "+temp+" distance = "+distance[i][j]);
                         }
                     }
                     temp=99999999;
                     centroid[j]=ind;
                  //   System.out.println(j+" centroid ="+centroid[j] +" and out here index = "+ind);
                 }
                 
         
         
         for(int i=0;i<size;i++)
         {
             for(int j=0;j<5;j++)
             {
                 for(int bin=0;bin<x;bin++)
                 {
                     int t=Math.abs((histogram[0][centroid[j]][bin]-histogram[0][i][bin]));                     
                     distance[i][j]+=t;
                 }
             }             
         }
         
         // asssign new clusters to points acc to their distances from diff centroids (to which they are nearest)
         int min =10000000;
         int num=10000;
         
         for(int p=0;p<size;p++)
         {
             for(int i=0;i<k;i++)         
             {
                 if(min>distance[p][i])
                 {
                     min=(int)distance[p][i];
                     num=i;
                 }
            }
             cluster[p]=num;
             distance[p][k]=num;
             min=100000000;
            //  System.out.println(p+" cluster ="+cluster[p]);
        }
         
         
         // calculate  means
             int [] dsum=new int[5];             
             int [] counter=new int[5];
                          
         for(int i=0;i<size;i++)
         {
             if(cluster[i]==0)
             {
                  for(int bin=0;bin<x;bin++)         
                {
                     mean[0][bin]+=(histogram[0][i][bin]);
                }           
                    counter[0]++;
             }
             
             else if(cluster[i]==1)
             {
                   for(int bin=0;bin<x;bin++)         
                {
                     mean[1][bin]+=(histogram[0][i][bin]);
                }
                 counter[1]++;
             }
             
             else if(cluster[i]==2)
             {
                   for(int bin=0;bin<x;bin++)         
                {
                     mean[2][bin]+=(histogram[0][i][bin]);
                }
                 counter[2]++;
             }
             
             else if(cluster[i]==3)
             {
                   for(int bin=0;bin<x;bin++)         
                {
                     mean[3][bin]+=(histogram[0][i][bin]);
                }
                 counter[3]++;
             }
             
             else if(cluster[i]==4)
             {
                   for(int bin=0;bin<x;bin++)         
                {
                     mean[4][bin]+=(histogram[0][i][bin]);
                }
                 counter[4]++;
             }
         }
         
         
         
         for(int i=0;i<5;i++)
         {
             if(counter[i]==0)
             {
                 counter[i]=1;             
              System.out.println(i+ " ka counter 0 reh gaya");
             }
         }
         
         for(int i=0;i<k;i++)
         {
               for(int bin=0;bin<x;bin++)         
                {
                     mean[i][bin]/=counter[i];
                  //  System.out.println(i+"  "+bin+" mean value  ="+mean[i][bin]);
                }
                
         }                                  
     }
     // here iteration loop ends
     
     
    System.out.println(" The results are");
    
    for(int i=0;i<k;i++)
    {
        System.out.println(i+" centroid is= "+centroid[i]);
    }
    
    
    System.out.println(" Before sorting");
    
    for(int i=0;i<size;i++)
    {
        for(int j=0;j<5;j++)
        {
            if(distance[i][k]==j)
            {
                System.out.println(i+" is in cluster =" +j);
            }
        }
    }
    
    // TO SORT 2 DIENSIONAL ARRAY
    
    double[][] newdistance=new double[size][2];
    double min=999999;
    int num=0;
    int clust=0;
   for(int p=0;p<size;p++)
   {
       min=999999;
       for(int i=0;i<size;i++)
    {        
        for(int j=0;j<k;j++)
        {
            if(distance[i][j]<min && distance[i][k+1]==0 && distance[i][k]==j)
            {
                min=distance[i][j];
                num=i;
                clust=(int)distance[i][k];
            }
        }
    }
    distance[num][k+1]=1;
    newdistance[p][0]=clust;
    newdistance[p][1]=num;
    }
    
    
    for(int i=0;i<size;i++)
    {
        for(int j=0;j<5;j++)
        {
            if(distance[i][k]==j)
            {
                System.out.println(i+" is in "+j);
            }
        }
    }
     
    return newdistance;
   }
    
    
}
