package talaash.preprocessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import talaash.Indexer.fnorm;

/**
 *
 * @author asheesh
 */


public class parallelwaveletprocessing extends Thread{
    
    public int arg;
   public parallelwaveletprocessing(int p)
    {
        arg=p;
    }
        
         public parallelwaveletprocessing()
    {
        
    }
        
    public String indexingpath;
    public int size;
    static int[][] querywavelet; 
    static int[][] rquerywavelet; 
    static int[][] gquerywavelet; 
    static int[][] bquerywavelet; 
    static float [][] list;
    static float [][] rlist;
    static float [][] glist;
    static float[][] blist;
    static double[]cv;
    static float [][] totalsimilarity;
    static int [] finalList;        
    static int [][] querymatrix;
    static int [][][] rgbquery;
    
    public void wavelet(String path) throws FileNotFoundException, IOException, ClassNotFoundException
    {          
   // public int[][][] rgb;
       
      
        //this method computes haar wavelet transformation for whole set and is exectued during preprocessing
      try{ 
         int[][][] waveletvalue;    // for storing wavelets of gray images
     int[][][] rwaveletvalue;    // for storing wavelets of red 
     int[][][] gwaveletvalue;    // for storing wavelets of green
     int[][][] bwaveletvalue;    // for storing wavelets of blue
     int[][][] dvalue; 
    
        // set the indexingpath and size of the list
        indexingpath=path;
             File f=new File(indexingpath);
             File [] DBfiles=f.listFiles();
            size=DBfiles.length;
           // System.out.println("1.size value in wavelet preprocessing is = " +size);
            Color c;            
            
            int [][][]red=new int [size][256][256];
            int [][][] green=new int  [size][256][256];
            int [][][]blue=new int  [size][256][256];
            
        
            for(int i=0;i<size;i++)
            {
                BufferedImage bi=ImageIO.read(DBfiles[i]);
                for(int j=0;j<256;j++)
                {
                    for(int k=0;k<256;k++)
                    {
                        c=new Color(bi.getRGB(j, k));
                        red[i][j][k]=c.getRed();
                        green [i][j][k]=c.getGreen();
                        blue [i][j][k]=c.getBlue();
                           
                    }
                }
            }
           //  System.out.println("red = "+red[8][0][7]);
             //System.out.println("green = "+green[8][0][7]);
            
            
        // get the gray values of all files required for haar transformation  which have to be computed beforehand      
             FileInputStream fiss=new FileInputStream(new File("C:/Users/asheesh/Documents/NetBeansProjects/Talaash/gray.txt"));
        ObjectInputStream oiss=new ObjectInputStream(fiss);
        dvalue=(int [][][])oiss.readObject();           
       fiss.close();
       oiss.close();
       
       // check here whether gray values are there or not
       if(dvalue.length!=size)
       {
          // System.out.println(" if mein ghusa 85");
           JOptionPane.showMessageDialog(null, "the system is computing the gray values now");
          // System.out.println("gray values are not stored , the system is computing them now.");
           Colorprocessing cpobj=new Colorprocessing(indexingpath);
           cpobj.ComputeHistogram();         
         
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
         
         FileInputStream fiss2=new FileInputStream(new File("C:/Users/asheesh/Documents/NetBeansProjects/Talaash/gray.txt"));
        ObjectInputStream oiss2=new ObjectInputStream(fiss2);
        dvalue=(int [][][])oiss2.readObject();            
       fiss2.close();
       oiss2.close();
       
       } // if ends
       
       
            
       //System.out.println("dvalues se pehle ruka");
       
       waveletvalue=new int[size][][];
        rwaveletvalue=new int[size][][];
         gwaveletvalue=new int[size][][];
        bwaveletvalue=new int[size][][];
        
            // create an object to access methods
            WaveletTransform wtobj=new WaveletTransform();
            //waveletvalue[0]=wtobj.transform(dvalue[0]);
            // wavelet transformed values are stored in waveletvalue array
            float [] []DeltaA=new float[size][];
            float [] []rDeltaA=new float[size][];
            float [] []gDeltaA=new float[size][];
            float [] []bDeltaA=new float[size][];
            fnorm fnobj=new fnorm();
            
            for(int i=0;i<size;i++)
            {
                waveletvalue[i]=wtobj.transform(dvalue[i]);                
                DeltaA[i]=fnobj.fnormfeature(waveletvalue[i]);                
            }          
            
            // System.out.println(" wavelet value gray = "+waveletvalue[1][8][9]);
            // System.out.println(" delta value gray = "+DeltaA[1][9]);
             
                dvalue=null;
                FileOutputStream fos=new FileOutputStream(new File("C:/Users/asheesh/Documents/NetBeansProjects/Talaash/wavelet.txt"));
                ObjectOutputStream oos=new ObjectOutputStream(fos);
                oos.writeObject(waveletvalue);          
      
                waveletvalue=null;
                fos.close();
                oos.close();
                FileOutputStream fs=new FileOutputStream(new File("C:/Users/asheesh/Documents/NetBeansProjects/Talaash/fnormvalues.txt"));
                ObjectOutputStream os=new ObjectOutputStream(fs);
                os.writeObject(DeltaA);
                DeltaA=null;
                fs.close();
                os.close();
        
                
            for(int i=0;i<size;i++)
            {
                rwaveletvalue[i]=wtobj.transform(red[i]);            
                rDeltaA[i]=fnobj.fnormfeature(rwaveletvalue[i]);            
            }
//                System.out.println(" red wavelet value = "+rwaveletvalue[1][8][9]);            
//                System.out.println(" delta value red = "+rDeltaA[1][9]);
//                System.out.println("  value red = "+red[1][207][9]);
                red=null;
            FileOutputStream rfos=new FileOutputStream(new File("C:/Users/asheesh/Documents/NetBeansProjects/Talaash/rwavelet.txt"));
            ObjectOutputStream roos=new ObjectOutputStream(rfos);
            roos.writeObject(rwaveletvalue);
                
      
            rwaveletvalue=null;
            rfos.close();
            roos.close();
    
             FileOutputStream rfs=new FileOutputStream(new File("C:/Users/asheesh/Documents/NetBeansProjects/Talaash/rfnormvalues.txt"));
            ObjectOutputStream ros=new ObjectOutputStream(rfs);
            ros.writeObject(rDeltaA);
            rDeltaA=null;
            rfs.close();
            ros.close();
        
            
            for(int i=0;i<size;i++)
            {
                gwaveletvalue[i]=wtobj.transform(green[i]);    
                gDeltaA[i]=fnobj.fnormfeature(gwaveletvalue[i]);                
            }
//               System.out.println(" green wavelet value = "+gwaveletvalue[1][8][9]);            
//                System.out.println(" delta value green = "+gDeltaA[1][9]);
//             System.out.println("  value green = "+green[1][207][9]);
                        green=null;
            FileOutputStream gfos=new FileOutputStream(new File("C:/Users/asheesh/Documents/NetBeansProjects/Talaash/gwavelet.txt"));
            ObjectOutputStream goos=new ObjectOutputStream(gfos);
            goos.writeObject(gwaveletvalue);
                
      
            gwaveletvalue=null;
            gfos.close();
            goos.close();
    
             FileOutputStream gfs=new FileOutputStream(new File("C:/Users/asheesh/Documents/NetBeansProjects/Talaash/gfnormvalues.txt"));
            ObjectOutputStream gos=new ObjectOutputStream(gfs);
            gos.writeObject(gDeltaA);
            gDeltaA=null;
            gfs.close();
            gos.close();
    
            for(int i=0;i<size;i++)
            {
                bwaveletvalue[i]=wtobj.transform(blue[i]);                    
                bDeltaA[i]=fnobj.fnormfeature(bwaveletvalue[i]);                        
            }
            
                        blue=null;
            FileOutputStream bfos=new FileOutputStream(new File("C:/Users/asheesh/Documents/NetBeansProjects/Talaash/bwavelet.txt"));
            ObjectOutputStream boos=new ObjectOutputStream(bfos);
            boos.writeObject(bwaveletvalue);
                
      
            bwaveletvalue=null;
            bfos.close();
            boos.close();
    
             FileOutputStream bfs=new FileOutputStream(new File("C:/Users/asheesh/Documents/NetBeansProjects/Talaash/bfnormvalues.txt"));
            ObjectOutputStream bos=new ObjectOutputStream(bfs);
            bos.writeObject(bDeltaA);
            bDeltaA=null;
            bfs.close();
            bos.close();
    
            //System.out.println("wavelet0 length = "+waveletvalue[0].length);
           // System.out.println("wavelet value = "+waveletvalue[0][0][0]);
            
            // store wavelet values in a text file which is wavelets
            
        }      
    catch (IOException ioe)
    {
        System.out.println("ioe exception  "+ioe);
    }
    
    }
    
    // wavelwt method ends
    
    
    public int [] comparewavelets(String pathname) throws IOException, FileNotFoundException, ClassNotFoundException
    {
    // this method is exectued on clicking search button and it computes the query wavelt transformation and orders for comparison between it and others
        getpathandsize();
  
    list=new float[size][2];
    rlist=new float[size][2];
    glist=new float[size][2];
    blist=new float[size][2];
    totalsimilarity=new float[size][2];
    finalList= new int[size];
        
    for(int i=0;i<size;i++)
        {
            totalsimilarity[i][0]=0;
            totalsimilarity[i][1]=0;
        }
    // query images gray values collected
        querymatrix=getquerygrayvalue(pathname);
        rgbquery=new int[3][256][256];
          rgbquery=computergb(pathname);
        // query image wavelet transformation performed
        WaveletTransform wtobj=new WaveletTransform();
        querywavelet=wtobj.transform(querymatrix);
        rquerywavelet=wtobj.transform(rgbquery[0]);
        gquerywavelet=wtobj.transform(rgbquery[1]);
        bquerywavelet=wtobj.transform(rgbquery[2]);
        int dim=64;
        cv=new double[dim];
        cv=cvalue();
//        for(int i=0;i<20;i++)
//        System.out.println("  cv[i] = "+cv[i]);
        
        
//        list=getorder(querywavelet,0,cv);
//        rlist=getorder(rquerywavelet,1,cv);
//        glist=getorder(gquerywavelet,2,cv);
//        blist=getorder(bquerywavelet,3,cv);
        
        parallelwaveletprocessing pwpobj=new parallelwaveletprocessing(0);
        pwpobj.start();
        
        parallelwaveletprocessing rpwpobj=new parallelwaveletprocessing(1);
        rpwpobj.start();
        
        parallelwaveletprocessing gpwpobj=new parallelwaveletprocessing(2);
        gpwpobj.start();
        
        parallelwaveletprocessing bpwpobj=new parallelwaveletprocessing(3);
        bpwpobj.start();
        
        
        while(pwpobj.isAlive()==true || rpwpobj.isAlive()==true || gpwpobj.isAlive()==true || bpwpobj.isAlive()==true);
            
            
        if(rlist.length==0)
            System.out.print(" error in list");
//        else
//            ;
//        
        //System.out.println("rlist no.2 =  "+rlist[2][0]);
        
        for(int i=0;i<size;i++)
        {
            totalsimilarity[i][0]=rlist[i][0]+glist[i][0]+blist[i][0];
            totalsimilarity[i][1]=0;
        }
        querywavelet=null;
        rquerywavelet=null;
        gquerywavelet=null;
        bquerywavelet=null;
        list=null;
        rlist=null;
        glist=null;
        blist=null;
        querymatrix=null;
        rgbquery=null;
        
        finalList=getlist(totalsimilarity);
        
        totalsimilarity=null;
    return finalList;    
        
    }
    
    int[][][] computergb(String path) throws IOException
    {
        BufferedImage bi=(ImageIO.read(new File(path)));
        Color c;
        int [][][]rgb=new int[3][256][256];
        for(int i=0;i<256;i++)
        {
            for(int j=0;j<256;j++)
            {
                c=new Color(bi.getRGB(i, j));
                rgb[0][i][j]=c.getRed();
                rgb[1][i][j]=c.getGreen();
                rgb[2][i][j]=c.getBlue();
            }
        }
        return rgb;
    }
    
    public int[][] getquerygrayvalue(String path) throws IOException
    {
        int[][] querygrayvalue;
        querygrayvalue=new int[256][256];        
        // this method computes the gray values of every pixel of queryimage
        File f=new File(path);
        BufferedImage bfimage=ImageIO.read(f);                    
                    int height =bfimage.getHeight();
                     int width=bfimage.getWidth();
                     float d=0;   
                    for(int j=0;j<height;j++)
                    {
                        for(int k=0;k<width;k++)
                        {
                        Color rgb=new Color(bfimage.getRGB(j,k));
                         d=(float)((0.2125*rgb.getRed())+(0.7154*rgb.getGreen())+(0.072*rgb.getBlue()));
                         querygrayvalue[j][k]=(int)d;
                        }
                    }
                    
        return querygrayvalue;
    }

    public float [][] getorder(int [][] querywavelet,int x , double []C) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        getpathandsize();
        //System.out.println("2. size value here is "+size);
       // int [][][] waveletvalues=new int[size][][];
        float [] []DeltaA=new float[size][];
        int [] list=new int[size];
       //String pathname=null;
       String normpath=null;
        if(x==0)
        {
         //   pathname="C:/Users/asheesh/Documents/NetBeansProjects/Talaash/wavelet.txt";
            normpath="C:/Users/asheesh/Documents/NetBeansProjects/Talaash/fnormvalues.txt";
        }
        else if (x==1)
        {
           // pathname="C:/Users/asheesh/Documents/NetBeansProjects/Talaash/rwavelet.txt";
            normpath="C:/Users/asheesh/Documents/NetBeansProjects/Talaash/rfnormvalues.txt";
        }
        
        else if (x==2)
        {
            //pathname="C:/Users/asheesh/Documents/NetBeansProjects/Talaash/gwavelet.txt";
            normpath="C:/Users/asheesh/Documents/NetBeansProjects/Talaash/gfnormvalues.txt";
        }
        
        else if (x==3)
            {
              //  pathname="C:/Users/asheesh/Documents/NetBeansProjects/Talaash/bwavelet.txt";
                normpath="C:/Users/asheesh/Documents/NetBeansProjects/Talaash/bfnormvalues.txt";
            }
//        
//       FileInputStream fiss=new FileInputStream(new File(pathname));
//        ObjectInputStream oiss=new ObjectInputStream(fiss);
//        waveletvalues=(int [][][])oiss.readObject();            
//       fiss.close();
//       oiss.close();
       
        FileInputStream fss=new FileInputStream(new File(normpath));
        ObjectInputStream oss=new ObjectInputStream(fss);
        DeltaA=(float [][])oss.readObject();            
       fss.close();
       oss.close();
//       
//           System.out.println("waveletvalues size =  "+waveletvalues.length);
//           System.out.println("size value =  "+size);
       fnorm fnobj=new fnorm();
    int dim=64;
       float [] DeltaQuery=fnobj.fnormfeature(querywavelet);
       
       float [][] Alpha=new float[size][dim];
       //float [] C=new float[256];
       
       for(int i=0;i<size;i++)
       {
           for(int j=0;j<dim;j++)
           {
               if(DeltaA[i][j]==0||DeltaQuery[j]==0)
                   Alpha[i][j]=1;
               else if(DeltaA[i][j]-DeltaQuery[j]>0)
               Alpha[i][j]=DeltaQuery[j]/DeltaA[i][j];
               else
                   Alpha[i][j]=DeltaA[i][j]/DeltaQuery[j];
               
           }
       }
       
           
           float [][] similarity=new float[size][2];
       for(int i=0;i<size;i++)
       {
           for(int j=0;j<dim;j++)
           {
               similarity[i][0]+=C[j]*Alpha[i][j];
           }
           similarity[i][1]=0;
          // System.out.println("similarity ==  "+similarity[i][0]);
       }
       
//       waveletvalues=null;
       DeltaA=null;
       list=null;
       DeltaQuery=null;
       C=null;
       Alpha=null;
       
       return similarity;
//           list=getlist(similarity);       
//           return list;
    }
    
    public int[] getlist(float [][]distance ) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        getpathandsize();
        
        int p=0;
        int [] counter=new int[size];
        float max=0;
        
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                if(distance[j][0]>max && distance[j][1]==0)
                {
                    max=distance[j][0];                    
                    counter[p]=j;
                }
            }
            
            distance[counter[p]][1]=1;
            p++;
            max=0;
        }
        
        return counter;
    }
    
    public void setpath(String path) throws FileNotFoundException, IOException
    {
        String pathsent=path;
        FileOutputStream fos=new FileOutputStream(new File("C:/Users/asheesh/Documents/NetBeansProjects/Talaash/waveletindexingpath.txt"));        
        ObjectOutputStream oos=new ObjectOutputStream(fos);        
        oos.writeObject(pathsent);
        fos.close();
        oos.close();        
    
    }
    public void getpathandsize() throws FileNotFoundException, IOException, ClassNotFoundException
    {        
        FileInputStream fis=new FileInputStream(new File("C:/Users/asheesh/Documents/NetBeansProjects/Talaash/waveletindexingpath.txt"));
        ObjectInputStream ois=new ObjectInputStream(fis);
        indexingpath=(String)ois.readObject();            
            File f=new File(indexingpath);            
             File [] DBfiles=f.listFiles();
            size=DBfiles.length;
            
    }
    
    public double[] cvalue()
    {
        int dim=64;
        double []C=new double[dim];
           for(int j=0;j<dim;j++)
           {
                C[j]=(double)((((2*j)-1))/(64.0*64.0));
                //System.out.println("c[j] ==  "+C[j]);
           }
           return C;
    }
    
    @Override
public  void  run()
   {
       try
       {
       if(arg==0)
       {
           list=getorder(querywavelet,0,cv);
       }
        if(arg==1)
       {
           rlist=getorder(rquerywavelet,1,cv);
       }
       
       else if(arg==2)
       {
           glist=getorder(gquerywavelet,2,cv);
       }
       
       else if(arg==3)
       {
                
            blist=getorder(bquerywavelet,3,cv);
                
       }
       }
        catch (FileNotFoundException ex) {
                    Logger.getLogger(parallelwaveletprocessing.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(parallelwaveletprocessing.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(parallelwaveletprocessing.class.getName()).log(Level.SEVERE, null, ex);
                }
   }
}

    
