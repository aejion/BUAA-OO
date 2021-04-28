import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Elevator {
    private String type;
    private ArrayList<Integer> cango;
    private String name;
    private int floor = 1;
    private boolean dooropen = false;
    private HashMap<Integer,Integer> mmin = new HashMap<>();
    private boolean isfine = true;
    private double onetime;
    private int maxper;
    private double nowtime = 0;
    private double opentime = 0;
    private ArrayList<Integer> person;
    private int numofper = 0;
    private HashMap<Integer,Integer> mmat;

    Elevator(String type1, String name1,ArrayList<Integer> pp,
             HashMap<Integer,Integer> mmat1) {
        person = pp;
        mmat = mmat1;
        cango = new ArrayList<>();
        type = type1;
        switch (type) {
            case "A":
                maxper = 6;
                onetime = 0.4;
                break;
            case "B":
                maxper = 8;
                onetime = 0.5;
                break;
            case "C":
                maxper = 7;
                onetime = 0.6;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        getcango();
        name = name1;
    }

    public void getcango() {
        if (type.equals("A")) {
            cango.add(-3);
            cango.add(-2);
            cango.add(-1);
            cango.add(1);
            cango.add(15);
            cango.add(16);
            cango.add(17);
            cango.add(18);
            cango.add(19);
            cango.add(20);
        } else if (type.equals("B")) {
            cango.add(-2);
            cango.add(-1);
            cango.add(1);
            cango.add(2);
            cango.add(4);
            cango.add(5);
            cango.add(6);
            cango.add(7);
            cango.add(8);
            cango.add(9);
            cango.add(10);
            cango.add(11);
            cango.add(12);
            cango.add(13);
            cango.add(14);
            cango.add(15);
        } else {
            cango.add(1);
            cango.add(3);
            cango.add(5);
            cango.add(7);
            cango.add(9);
            cango.add(11);
            cango.add(13);
            cango.add(15);
        }
    }

    public void add(String str) {
        if (isfine == false) { return; }
        int len = str.length();
        Pattern pat = Pattern.compile("\\[(.*)\\]([A-Z]{2,7})");
        Matcher mat = pat.matcher(str);
        if (mat.find()) {
            String str1 = mat.group(2);
            //System.out.println(str1+" "+temptime);
            if (str1.equals("ARRIVE")) { arrive(str); }
            if (str1.equals("OPEN")) { open(str); }
            if (str1.equals("OUT")) { out(str); }
            if (str1.equals("IN")) { in(str); }
            if (str1.equals("CLOSE")) { close(str); }
        }
    }

    public void arrive(String str){
        Pattern pat1 = Pattern.compile("\\[(.*)\\]([A-Z]{2,7})-([+-]?[0-9]*)");
        Matcher mat1 = pat1.matcher(str);
        double temptime = 0;
        int tempfloor = 0;
        if(mat1.find()) {
            temptime = Double.valueOf(mat1.group(1));
            tempfloor = Integer.valueOf(mat1.group(3));
        }
        if (temptime - nowtime < onetime - 0.0000001 || Math.abs(tempfloor - floor) != 1 || dooropen == true) {
            if(!((tempfloor == -1 && floor == 1)||(tempfloor == 1 && floor == -1))) {
                isfine = false;
                return;
            }
        }
        nowtime = temptime;
        floor = tempfloor;
    }

    public void open(String str){
        Pattern pat1 = Pattern.compile("\\[(.*)\\]([A-Z]{2,7})-([+-]?[0-9]*)");
        Matcher mat1 = pat1.matcher(str);
        int tempfloor = 0;
        double temptime = 0;
        if (mat1.find()) {
            temptime = Double.valueOf(mat1.group(1));
            tempfloor = Integer.valueOf(mat1.group(3));
        }
        if (tempfloor != floor || !cango.contains(tempfloor)) {
            isfine = false;
            return ;
        }
        dooropen = true;
        opentime = temptime;
        nowtime = temptime;
    }

    public void out(String str){
        Pattern pat1 = Pattern.compile("\\[(.*)\\]([A-Z]{2,7})-([+-]?[0-9]*)-([+-]?[0-9]*)");
        Matcher mat1 = pat1.matcher(str);
        int id = 0;
        int tempfloor = 0;
        double temptime = 0;
        if (mat1.find()) {
            temptime = Double.valueOf(mat1.group(1));
            id = Integer.valueOf(mat1.group(3));
            tempfloor = Integer.valueOf(mat1.group(4));
        }
        if (!mmin.containsKey(id) || tempfloor != floor || dooropen == false) {
            isfine = false;
            return;
        }
        numofper--;
        mmin.remove(id);
        mmat.put(id,tempfloor);
        nowtime = temptime;
    }

    public void in(String str){
        Pattern pat1 = Pattern.compile("\\[(.*)\\]([A-Z]{2,7})-([+-]?[0-9]*)-([+-]?[0-9]*)");
        Matcher mat1 = pat1.matcher(str);
        int id = 0;
        int tempfloor = 0;
        double temptime = 0;
        if (mat1.find()) {
            temptime = Double.valueOf(mat1.group(1));
            id = Integer.valueOf(mat1.group(3));
            tempfloor = Integer.valueOf(mat1.group(4));
        }
        numofper++;
        if (mmat.get(id) != tempfloor || tempfloor != floor || dooropen == false || numofper > maxper) {
            isfine = false;
            return;
        }
        mmat.put(id,0);
        mmin.put(id,1);
        nowtime = temptime;
    }

    public void close(String str){
        Pattern pat1 = Pattern.compile("\\[(.*)\\]([A-Z]{2,7})-([+-]?[0-9]*)");
        Matcher mat1 = pat1.matcher(str);
        int tempfloor = 0;
        double temptime = 0;
        if (mat1.find()) {
            temptime = Double.valueOf(mat1.group(1));
            tempfloor = Integer.valueOf(mat1.group(3));
        }
        if (tempfloor != floor || dooropen != true || temptime - opentime < 0.4 - 0.0000001) {
            isfine = false;
            return;
        }
        dooropen = false;
        nowtime = temptime;
    }

    public boolean isIsfine() {
        if (nowtime > 200) { return false; }
        return isfine;
    }
}
