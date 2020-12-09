import java.io.*;
import java.util.*;

public class Predictor {

    public static void main(String[] args) throws IOException {

      //  long t2=System.currentTimeMillis();

        FileWriter fw = new FileWriter("./results/test_predictions.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        List<String> fakord=new ArrayList<>();

        File file=new File("./data/test_tokens_fake.txt");
        BufferedReader reader=null;
        String temp=null;
        try{
            reader=new BufferedReader(new FileReader(file));
            while((temp=reader.readLine())!=null){
                fakord.add(temp);
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
/*        for(int i=0; i<fakord.size();i++)
        {
            System.out.println(fakord.get(i));
        }
*/
        List<String> conf=new ArrayList<>();
        File file1=new File("./data/all_confusingWords.txt");
        BufferedReader reader1=null;
        String temp1=null;
        try{
            reader1=new BufferedReader(new FileReader(file1));
            while((temp1=reader1.readLine())!=null){
                conf.add(temp1);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(reader1!=null){
                try{
                    reader1.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

/*        for(int i=0;i<conf.size();i++)
        {
            System.out.println(conf.get(i));
        }
*/
        String confarr[][] = new String[conf.size()][2];
        for(int i=0;i<conf.size();i++)
        {
            String[] strArr = conf.get(i).split(":");
            for(int j=0;j<strArr.length;j++)
            {
                confarr[i][j]=strArr[j];
            }
        }
/*        for (int i=0;i<conf.size();i++)
        {
            for(int j=0;j<2;j++)
            {
                System.out.print(confarr[i][j]+" ");
            }
            System.out.print("\n");
        }*/
        int freqsum=0;
        for (int i=0;i<conf.size();i++)
        {
            for(int j=0; j<2;j++)
            {
                freqsum= Collections.frequency(fakord,confarr[i][j])+freqsum;
            }
        }
/*        System.out.println(Collections.frequency(fakord,"accept"));
        System.out.println(Collections.frequency(fakord,"except"));
        System.out.println(Collections.frequency(fakord,"adverse"));
        System.out.println(Collections.frequency(fakord,"averse"));
        System.out.println(Collections.frequency(fakord,"advice"));
        System.out.println(Collections.frequency(fakord,"advise"));
        System.out.println(Collections.frequency(fakord,"affect"));
        System.out.println(Collections.frequency(fakord,"aisle"));
        System.out.println(Collections.frequency(fakord,"isle"));
        System.out.println(Collections.frequency(fakord,"aloud"));
        System.out.println(Collections.frequency(fakord,"allowed"));
        System.out.println(Collections.frequency(fakord,"altar"));
        System.out.println(Collections.frequency(fakord,"alter"));
        System.out.println(Collections.frequency(fakord,"amoral"));
        System.out.println(Collections.frequency(fakord,"immoral"));
        System.out.println(Collections.frequency(fakord,"appraise"));
        System.out.println(Collections.frequency(fakord,"apprise"));
        System.out.println(Collections.frequency(fakord,"assent"));
        System.out.println(Collections.frequency(fakord,"ascent"));
*/

        //System.out.println(freqsum);
        String wordsapp[][]=new String[freqsum][3];
        int loc[][]=new int[freqsum][2];
        int wordsrow=0;
        int sen=-1;
        int wor=0;
        for(int a=0;a<fakord.size();a++)
        {
            if(fakord.get(a).equals("<s>"))
            {
                sen++;
                wor=0;
            }
            /*else if(fakord.get(a).equals("</s>"))
            {
                wor=0;
            }*/
            else
            {
                wor++;
            }
            for (int i = 0; i < conf.size(); i++)
            {
                for (int j = 0; j < 2; j++)
                {
                    if(fakord.get(a).equals(confarr[i][j])&&a>=1)
                    {
                        wordsapp[wordsrow][1]=fakord.get(a);
                        wordsapp[wordsrow][0]=fakord.get(a-1);
                        if(j==0)
                        {
                            wordsapp[wordsrow][2]=confarr[i][1];
                        }
                        else if(j==1)
                        {
                            wordsapp[wordsrow][2]=confarr[i][0];
                        }
                        loc[wordsrow][0]=sen;
                        loc[wordsrow][1]=wor;
                        //System.out.println(wordsapp[wordsrow][0]+" "+wordsapp[wordsrow][1]+" "+wordsapp[wordsrow][2]);
                        //System.out.println(loc[wordsrow][0]+" "+loc[wordsrow][1]);
                        wordsrow++;
                    }
                }
            }
        }
        //System.out.println(wordsrow);


        List<String> bigr=new ArrayList<>();
        File file2=new File("./results/bigrams.txt");
        BufferedReader reader2=null;
        String temp2=null;
        try{
            reader2=new BufferedReader(new FileReader(file2));
            while((temp2=reader2.readLine())!=null){
                bigr.add(temp2);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(reader2!=null){
                try{
                    reader2.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
/*        for (int i=0;i<bigr.size();i++)
        {
            System.out.println(bigr.get(i));
        }
        System.out.println(bigr.size());
*/
        String bigrarr[][] = new String[bigr.size()][3];
        for(int i=0;i<bigr.size();i++)
        {
            String[] strArr = bigr.get(i).split(" ");
            for(int j=0;j<strArr.length;j++)
            {
                bigrarr[i][j]=strArr[j];
            }
        }

/*        for(int i=0;i<bigr.size();i++)
        {
            for(int j=0; j<3;j++)
            {
                System.out.print(bigrarr[i][j]+" ");
            }
            System.out.print("\n");
        }
*/
        List<String> gtta=new ArrayList<>();
        File file3=new File("./results/GTTable.txt");
        BufferedReader reader3=null;
        String temp3=null;
        try{
            reader3=new BufferedReader(new FileReader(file3));
            while((temp3=reader3.readLine())!=null){
                gtta.add(temp3);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(reader3!=null){
                try{
                    reader3.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        String gttaarr[] = new String[gtta.size()];
        for(int i=0;i<gtta.size();i++)
        {
            String[] strArr = gtta.get(i).split(" ");
            gttaarr[i]=strArr[1];
        }
/*        for(int i=0;i<gtta.size();i++)
        {
            System.out.println(gttaarr[i]);
        }
        System.out.println(gttaarr.length);
*/

/*        for(int i=0;i<gtta.size();i++)
        {
            for(int j=0; j<2;j++)
            {
                System.out.print(gttaarr[i][j]+" ");
            }
            System.out.print("\n");
        }
 */



        List<String> single=new ArrayList<>();
        List<String> single1=new ArrayList<>();
        Map<String, Integer> mapsing = new HashMap<String, Integer>();
        File file4=new File("./data/train_tokens.txt");
        BufferedReader reader4=null;
        String temp4=null;
        try{
            reader4=new BufferedReader(new FileReader(file4));
            while((temp4=reader4.readLine())!=null){
                single.add(temp4);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(reader4!=null){
                try{
                    reader4.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        single1.addAll(single);
        single1=removeDuplicate(single1);
        for(int i=0; i<single1.size();i++)
        {
            mapsing.put(single1.get(i),i);
        }
        int singcou[]=new int[mapsing.size()];

        for (int i=0; i<single.size();i++)
        {
            singcou[mapsing.get(single.get(i))]++;
        }
        /*for(int i=0; i<singcou.length;i++)
        {
            System.out.println(singcou[i]+" ");
        }*/
        //System.out.println(singcou.length);






        String c="0";
        //int c1=0;
        //int c2=0;
        double comp1=-1000;
        double comp2=-1000;
        double compl1=-1;
        double compl2=-1;
        double pl1=-1;
        double pl2=-1;

        List<String> mistgt=new ArrayList<>();
        List<String> mistlp=new ArrayList<>();
        for (int i=0;i<wordsrow;i++)
        {
            for(int j=0;j<bigr.size();j++)
            {
                comp1=-1000;
                compl1=-1;
                pl1=-1;
                if(wordsapp[i][0].equals(bigrarr[j][0])&&wordsapp[i][2].equals(bigrarr[j][1]))
                {
                    //c1=Integer.parseInt(bigrarr[j][2]);
                    c=bigrarr[j][2];
                    comp1=Double.parseDouble(gttaarr[Integer.parseInt(c)]);
                    compl1=Integer.parseInt(c);
                    comp2=-1000;
                    compl2=-1;
                    pl2=-1;
                    for(int a=0;a<bigr.size();a++)
                    {
                        if(wordsapp[i][0].equals(bigrarr[a][0])&&wordsapp[i][1].equals(bigrarr[a][1]))
                        {
                            //c2=Integer.parseInt(bigrarr[a][2]);
                            c=bigrarr[a][2];
                            comp2=Double.parseDouble(gttaarr[Integer.parseInt(c)]);
                            compl2=Integer.parseInt(c);
                        }
                    }

                    ///////////////////
                    ///////////////////
                    //////GT///////////
                    ///////////////////
                    if(comp1>comp2)
                    //if(c1>c2)
                    {
                        //System.out.println(wordsapp[i][0]+" "+wordsapp[i][1]+" "+wordsapp[i][2]+" ");
                        if(i>=1&&loc[i][0]==Integer.parseInt(mistgt.get(mistgt.size()-1).split(":")[0]))
                        {

                            mistgt.set(mistgt.size()-1,mistgt.get(mistgt.size()-1)+""+loc[i][1]+",");

                        }
                        else
                        {
                            mistgt.add(loc[i][0]+":"+loc[i][1]+",");
                        }
                    }

                    ////////////////////
                    ///////////////////
                    //////LLLLLL////////
                    ////////////////////
                    pl1=(compl1+1);///(singcou[mapsing.get(wordsapp[i][0])]+bigr.size());
                    pl2=(compl2+1);///(singcou[mapsing.get(wordsapp[i][0])]+bigr.size());
                    if(pl1>pl2)
                    {

                        if(i>=1&&loc[i][0]==Integer.parseInt(mistlp.get(mistlp.size()-1).split(":")[0]))
                        {

                            mistlp.set(mistlp.size()-1,mistlp.get(mistlp.size()-1)+""+loc[i][1]+",");

                        }
                        else
                        {
                            mistlp.add(loc[i][0]+":"+loc[i][1]+",");
                        }
                    }



                }
            }
        }

        FileWriter fw1 = new FileWriter("./results/test_predictions.txt");
        BufferedWriter bw1 = new BufferedWriter(fw1);

        for (int i=0;i<mistgt.size();i++)
        {
            System.out.println(mistgt.get(i));
            bw1.write(mistgt.get(i)+"\n");
        }
        bw1.flush();
        bw1.close();
        System.out.println(mistlp.size());
        //System.out.println(fakord.size());

       /* for (int i=0; i<loc.length;i++)
        {
            for(int j=0; j<loc[0].length;j++)
            {
                System.out.print(loc[i][j]+" ");
            }
            System.out.print("\n");
        }
        System.out.println(loc.length);
*/
      //  System.out.println(System.currentTimeMillis()-t2);

    }

    public static List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }
}
