import java.io.*;
import java.util.*;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import static java.lang.Math.log;

public class ProbEstimator {

    public static void main(String[] args) throws IOException {

        List<String> comord=new ArrayList<>();
        File file=new File("./data/train_tokens.txt");
        BufferedReader reader=null;
        String temp=null;
        try{
            reader=new BufferedReader(new FileReader(file));
            while((temp=reader.readLine())!=null){
                comord.add(temp);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(reader!=null){
                try{
                    reader.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        //System.out.println(comord.size());

        List<String> comord1=new ArrayList<>();
        comord1.addAll(comord);
        List<String> comdel=new ArrayList<>();
        comdel=removeDuplicate(comord1);


        Map<String, Integer> mapdel = new HashMap<String, Integer>();
        for(int i=0; i<comdel.size();i++)
        {
            mapdel.put(comdel.get(i),i);
        }
        int bigr[][]=new int[comdel.size()][comdel.size()];
        for(int i=0;i<comdel.size();i++)
        {
            for (int j = 0; j < comdel.size(); j++)
            {
                bigr[i][j]=0;
            }
        }
        for(int i=0; i<comord.size()-1;i++)
        {
            bigr[mapdel.get(comord.get(i))][mapdel.get(comord.get(i+1))]++;
        }


        //print test
        /*for(int i=0;i<comdel.size();i++)
        {
            for (int j = 0; j < comdel.size(); j++)
            {
                System.out.print(bigr[i][j] + " ");
            }
            System.out.print("\n");
        }*/


        FileWriter fw = new FileWriter("./results/bigrams.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        int count=0;
        int max=0;
        for(int i=0;i<comdel.size();i++)
        {
            for(int j=0;j<comdel.size();j++)
            {
                if(bigr[i][j]!=0)//&&comdel.get(i).equals("</s>")==false&&comdel.get(j).equals("<s>")==false)
                {
                    count++;

                    if(bigr[i][j]>max)
                    {
                        max=bigr[i][j];
                    }
                }

            }
        }
        bw.write(count+"\n");
        //System.out.println(count);
        bw.flush();
        for(int i=0;i<comdel.size();i++)
        {
            for(int j=0;j<comdel.size();j++)
            {
                if(bigr[i][j]!=0&&comdel.get(i).equals("</s>")==false&&comdel.get(j).equals("<s>")==false)
                {
                    bw.write(comdel.get(i)+" "+comdel.get(j)+" "+bigr[i][j]+"\n");
                    bw.flush();
                }
            }
        }
        bw.close();
        int cou[]=new int[max+2];
        for(int i=0;i<max+2;i++)
        {
            cou[i]=0;
        }
        for(int i=0;i<comdel.size();i++)
        {
            for (int j=0;j<comdel.size();j++)
            {
                cou[bigr[i][j]]++;
            }
        }
        double n=0.0;
        n=comord.size()-1-bigr[mapdel.get("</s>")][mapdel.get("<s>")];
        //System.out.println(n);
        //System.out.println(cou[0]);
/*        for(int i=0;i<max+1;i++)
        {
            System.out.println(cou[i]);
        }
*/


        double log[][]=new double[max+2][2];
        for(int i=0;i<max+2;i++)
        {
            for(int j=0;j<2;j++)
            {
                log[i][j]=0;
            }
        }
        for(int i=0; i<max+2;i++)
        {
            log[i][0]=  log(i);
            log[i][1] =  log(cou[i]);

        }
        FileWriter fw1 = new FileWriter("./results/ff.txt");
        BufferedWriter bw1 = new BufferedWriter(fw1);
        for(int i=0;i<max+1;i++)
        {
            bw1.write(log[i][0]+" "+log[i][1]+"\n");
            bw1.flush();
        }
        bw1.close();

        SimpleRegression regression = new SimpleRegression();
        for(int i=1; i<max+2; i++)
        {
            if(cou[i]!=0)
            {
                regression.addData(log[i][0], log[i][1]);
            }
        }

        for(int i=0;i<max+2;i++)
        {
            if(cou[i]==0)
            {
                log[i][1] = regression.predict(log[i][0]);
            }
            //System.out.println(i+" "+log[i][1]);
        }
        final int sul=max+2;
        double logncs[] = new double[sul];
        double ncs[]= new double[sul];
        double cs[]= new double[sul];
        double logcs[]=new double[sul];
        for(int i=0; i<logncs.length;i++)
        {
            if(cou[i]==0)
            {
                logncs[i]=regression.predict(log[i][0]);
                ncs[i]=Math.pow(Math.E,logncs[i]);
            }
            else
            {
                ncs[i]=cou[i];
            }

        }

        FileWriter fw2 = new FileWriter("./results/GTTable.txt");
        BufferedWriter bw2 = new BufferedWriter(fw2);
        //System.out.println(cou[1]+" "+comord.size());
        bw2.write("0 "+log(cou[1]/n)+"\n");
        for(int i=1; i<cs.length-1;i++)
        {
                cs[i] = (i + 1) * ncs[i + 1] / ncs[i];
                logcs[i] = log(cs[i]);
                //System.out.println(i + " " + logcs[i]);
                bw2.write(i + " " + logcs[i]+"\n");
                bw2.flush();
        }
        bw.close();
        //System.out.println(ncs[1]+" "+cs[0]+" "+logcs[0]);

        double aaa=cou[0]/comord.size();
        double logaaa=log(aaa)+logcs[0];
        //System.out.println(cou[1]+" "+cou[0]+" "+logaaa);


    }

    public static List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }



}
