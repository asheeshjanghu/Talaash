/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package talaash.Indexer;

/**
 *
 * @author asheesh
 */
public class similarity {
    
    
    public double similaritymeasure(double[]queryimg,double[]folderimg)
    {
        double max=0,min=0;
        int length=queryimg.length;
        int n=length;
        double []c=new double[n];
        double sim=0;
        double alpha[]=new double[n];
        double nsqr=n*n;
        for(int i=0;i<queryimg.length;i++)
        {
            c[i]=(((2*i)-1)/nsqr);
            alpha[i]=1;
            if(queryimg[i]>=folderimg[i])
            {
                max=queryimg[i];
                min=folderimg[i];
                if(queryimg[i]!=0&&folderimg[i]!=0)
                {
                    alpha[i]=min/max;
                }
                else 
                    alpha[i]=1;
            }
           
            sim+=(c[i]*alpha[i]);
            
        }
        // System.out.println("sim value="+sim);
        return sim;
    }
    
}

