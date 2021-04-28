import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Elevatorcheck {
    private int atfloor;
    private HashMap<Integer,Integer> person;
    private double nowtime;
    private int numofper = 0;
    private boolean isfine = true;
    private boolean open = false;
    private double opentime = 0;
    private HashMap<Integer,Integer> mmfrom;
    private HashMap<Integer,Integer> mmto ;

    Elevatorcheck(HashMap<Integer,Integer> mm1,HashMap<Integer,Integer> mm2) {
        nowtime = 0;
        atfloor = 1;
        person = new HashMap<>();
        mmfrom = mm1;
        mmto = mm2;
    }

    public void add (String str) {
        if (isfine == false) { return; }
        int len = str.length();
        double temptime = 0;
        int flag2 = 0;
        Pattern pat = Pattern.compile("\\[(.*)\\]([A-Z]{2,7})");
        Matcher mat = pat.matcher(str);
        if (mat.find()) {
            temptime = Double.valueOf(mat.group(1));
            String str1 = mat.group(2);
            //System.out.println(str1+" "+temptime);
            if (str1.equals("ARRIVE")) { flag2 = 1; }
            if (str1.equals("OPEN")) { flag2 = 2; }
            if (str1.equals("OUT")) { flag2 = 3; }
            if (str1.equals("IN")) { flag2 = 4; }
            if (str1.equals("CLOSE")) { flag2 = 5; }
        }
        if (temptime < nowtime) {
            isfine = false;
            return ;
        }
        int tempfloor = 0;
        int id = 0;
        if (flag2 == 1) {
            Pattern pat1 = Pattern.compile("\\[(.*)\\]([A-Z]{2,7})-([+-]?[0-9]*)");
            Matcher mat1 = pat1.matcher(str);
            if (mat1.find()) {
                tempfloor = Integer.valueOf(mat1.group(3));
            }
            if (temptime - nowtime < 0.4 - 0.0000001 || Math.abs(tempfloor - atfloor) != 1 || open == true) {
                if(!((tempfloor == -1 && atfloor == 1)||(tempfloor == 1 && atfloor == -1))) {
                    isfine = false;
                }
            }
            atfloor = tempfloor;
        }
        else if (flag2 == 2) {
            Pattern pat1 = Pattern.compile("\\[(.*)\\]([A-Z]{2,7})-([+-]?[0-9]*)");
            Matcher mat1 = pat1.matcher(str);
            if (mat1.find()) {
                tempfloor = Integer.valueOf(mat1.group(3));
            }
            if (tempfloor != atfloor) {
                isfine = false;
                return ;
            }
            open = true;
            opentime = temptime;
        }
        else if (flag2 == 3) {
            Pattern pat1 = Pattern.compile("\\[(.*)\\]([A-Z]{2,7})-([+-]?[0-9]*)-([+-]?[0-9]*)");
            Matcher mat1 = pat1.matcher(str);
            if (mat1.find()) {
                id = Integer.valueOf(mat1.group(3));
                tempfloor = Integer.valueOf(mat1.group(4));
            }
            if (!person.containsKey(id)) {
                isfine = false;
                return;
            }
            if (!mmto.containsKey(id)) {
                isfine = false;
                return;
            }
            if ( atfloor!=mmto.get(id)||tempfloor != atfloor || open == false) {
                isfine = false;
                return;
            }
            numofper --;
            person.remove(id);
        }
        else if (flag2 == 4) {
            Pattern pat1 = Pattern.compile("\\[(.*)\\]([A-Z]{2,7})-([+-]?[0-9]*)-([+-]?[0-9]*)");
            Matcher mat1 = pat1.matcher(str);
            if (mat1.find()) {
                id = Integer.valueOf(mat1.group(3));
                tempfloor = Integer.valueOf(mat1.group(4));
            }
            person.put(id,tempfloor);
            numofper++;
            if (!mmfrom.containsKey(id)) {
                isfine = false;
                return;
            }
            if( mmfrom.get(id) != atfloor||open == false || numofper > 7) {
                isfine = false;
                return;
            }
        }
        else if (flag2 == 5) {
            Pattern pat1 = Pattern.compile("\\[(.*)\\]([A-Z]{2,7})-([+-]?[0-9]*)");
            Matcher mat1 = pat1.matcher(str);
            if (mat1.find()) {
                tempfloor = Integer.valueOf(mat1.group(3));
            }
            if (tempfloor != atfloor || temptime - opentime < 0.4 - 0.0000001) {
                isfine = false;
                return ;
            }
            open = false;
        }
        nowtime = temptime;
    }

    public boolean isIsfine() {
        if (!person.isEmpty()||nowtime > 200) { return false; }
        return isfine;
    }

}
