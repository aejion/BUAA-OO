import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        HashMap<Integer,Integer> mmfrom = new HashMap<>();
        HashMap<Integer,Integer> mmto = new HashMap<>();
        Elevatorcheck elea = new Elevatorcheck(mmfrom,mmto);
        Elevatorcheck eleb = new Elevatorcheck(mmfrom,mmto);
        Elevatorcheck elec = new Elevatorcheck(mmfrom,mmto);
        Elevatorcheck eled = new Elevatorcheck(mmfrom,mmto);
        Elevatorcheck elee = new Elevatorcheck(mmfrom,mmto);
        in.nextLine();
        while(in.hasNext()) {
            String str = in.nextLine();
            int len = str.length();
            if (str.charAt(len-1)=='A') { elea.add(str); }
            else if (str.charAt(len-1)=='B') { eleb.add(str); }
            else if (str.charAt(len-1)=='C') { elec.add(str); }
            else if (str.charAt(len-1)=='D') { eled.add(str); }
            else if (str.charAt(len-1)=='E') { elee.add(str); }
            else {
                Pattern pat = Pattern.compile("\\[.*\\]([0-9]*)-FROM-([+-]?[0-9]*)-TO-([+-]?[0-9]*)");
                Matcher mat = pat.matcher(str);
                if(mat.find()) {
                    int id = Integer.valueOf(mat.group(1));
                    int from = Integer.valueOf(mat.group(2));
                    int to = Integer.valueOf(mat.group(3));
                    //System.out.println(id+" "+from + " "+to );
                    mmfrom.put(id,from);
                    mmto.put(id,to);
                }
            }
        }
        if (elea.isIsfine()&&eleb.isIsfine()&&elec.isIsfine()&&eled.isIsfine()&&elee.isIsfine()){
            System.out.println("Accept!");
        }
        else {System.out.println("Wrong!");}
        in.close();
    }
}
