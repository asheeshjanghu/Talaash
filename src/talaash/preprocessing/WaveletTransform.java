/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package talaash.preprocessing;

/**
 *
 * @author asheesh
 */
public class WaveletTransform {
    
    public int plength;    
       
   public int[][] transform(int[][] matrix)
    {
        
        plength=matrix.length;
        int x=plength;
        int newmatrix[][]=new int[x][x];
        int anewmatrix[][]=new int[x][x];
        
        while(x>64)
        {   
            // row transformation
        int count =0;
        for(int i=0;i<x;i=i+1)
        {
            for(int j=0;j<x;j=j+2)
            {
                newmatrix[i][count+x/2]=matrix[i][j]-matrix[i][j+1];
                newmatrix[i][count]=matrix[i][j]+matrix[i][j+1];                
                count++;
            }
            count=0;
        }
        
        
         count=0;
         
         // over columns
         for(int j=0;j<x;j=j+1)
        {
            for(int i=0;i<x-1;i=i+2)
            {
                anewmatrix[count+x/2][j]=newmatrix[i][j]-newmatrix[i+1][j];
                anewmatrix[count][j]=newmatrix[i][j]+newmatrix[i+1][j];                
                count++;
            }
            count=0;
        }
         matrix=anewmatrix;
         x=x/2;
    }
        
        x=plength;
        int [][]newvalue=new int[x][x];
        for(int i=0;i<x;i++)
            for(int j=0;j<x;j++)
                newvalue[i][j]=anewmatrix[i][j];
        
        anewmatrix=null;
        newmatrix=null;
         return newvalue;
    }
    

    
    
}
