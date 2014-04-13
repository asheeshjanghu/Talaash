/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package talaash.Indexer;

/**
 *
 * @author asheesh
 */

public class fnorm {
    
    public float [] fnormfeature(int [][] matrix)
    {
        int length=matrix.length;
                
        float[] A=new float[length+1];
        A[0]=0;
        for(int i=1;i<length+1;i++)
        {
            A[i]=0;
            for(int x=0;x<i;x++)
                for(int y=0;y<i;y++)
                {                    
                    A[i]+=matrix[x][y]*matrix[x][y];
                }
            A[i]=(float)Math.sqrt(A[i]);
        }
        float [] DeltaA=new float[length+1];
        DeltaA[0]=0;
        for(int i=1;i<length+1;i++)
        {
            DeltaA[i]=A[i]-A[i-1];
        }
        A=null;
        return DeltaA;
    }
    
}
