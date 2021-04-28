import com.oocourse.elevator3.ElevatorInput;
import com.oocourse.elevator3.ElevatorRequest;
import com.oocourse.elevator3.PersonRequest;
import com.oocourse.elevator3.Request;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numofinnput = Integer.valueOf(in.nextLine());
        HashMap<Integer,Integer> mmto = new HashMap<>();
        HashMap<Integer,Integer> mmat = new HashMap<>();
        ArrayList<Integer> personid = new ArrayList<>();
        HashMap<Integer,Integer> mmfrom = new HashMap<>();
        Elevator ele1 = new Elevator("A","A",personid,mmat);
        Elevator ele2 = new Elevator("B","B",personid,mmat);
        Elevator ele3 = new Elevator("C","C",personid,mmat);
        Elevator ele4 = new Elevator("A","A",personid,mmat);
        Elevator ele5 = new Elevator("A","A",personid,mmat);
        Elevator ele6 = new Elevator("A","A",personid,mmat);
        while (numofinnput > 0 && in.hasNext()) {
            String str = in.nextLine();
            int len = str.length();
            //System.out.println(str);
            if (str.charAt(len - 1) >= '0' && str.charAt(len - 1) <= '9') {
                Pattern pat = Pattern.compile("\\[.*\\]([0-9]*)-FROM-([+-]?[0-9]*)-TO-([+-]?[0-9]*)");
                Matcher mat = pat.matcher(str);
                if(mat.find()) {
                    int id = Integer.valueOf(mat.group(1));
                    int from = Integer.valueOf(mat.group(2));
                    int to = Integer.valueOf(mat.group(3));
                    //System.out.println(id+" "+from+" "+to);
                    personid.add(id);
                    mmto.put(id,to);
                    mmfrom.put(id,from);
                    mmat.put(id,from);
                }
            }
            else {
                Pattern pat = Pattern.compile("\\[.*\\](.*)-ADD-ELEVATOR-([A-Z]*)");
                Matcher mat = pat.matcher(str);
                if(mat.find()) {
                    String id = mat.group(1);
                    String type = mat.group(2);
                    if (id.equals("X1")) {
                        ele4 = new Elevator(type,id,personid,mmat);
                    }
                    if (id.equals("X2")) {
                        ele5 = new Elevator(type,id,personid,mmat);
                    }
                    if (id.equals("X3")) {
                        ele6 = new Elevator(type,id,personid,mmat);
                    }
                }
            }
            numofinnput--;
        }
        while(in.hasNext()) {
            String str = in.nextLine();
            int len = str.length();
            //System.out.println(str);
            if (str.charAt(len-1)=='A') { ele1.add(str); }
            else if (str.charAt(len-1)=='B') { ele2.add(str); }
            else if (str.charAt(len-1)=='C') { ele3.add(str); }
            else if (str.charAt(len-1)=='1' && str.charAt(len-2)=='X') { ele4.add(str); }
            else if (str.charAt(len-1)=='2'&& str.charAt(len-2)=='X') { ele5.add(str); }
            else if (str.charAt(len-1)=='3'&& str.charAt(len-2)=='X') { ele6.add(str); }
        }
        boolean ans = true;
        if (!(ele1.isIsfine()&&ele2.isIsfine()&&ele3.isIsfine()&&ele4.isIsfine()&&ele5.isIsfine()&&ele6.isIsfine())){
            ans = false;
        }
        for (int i = 0; i < personid.size();i++) {
            int j = personid.get(i);
            if (mmat.get(j) != mmto.get(j)) {
                ans = false;
            }
        }
        if (ans == true) {System.out.println("Accept!");}
        else {System.out.println("Wrong!");}
        in.close();
    }
}
