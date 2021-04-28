import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.lang.Integer;

public class Main {
    public static void main(String[] args) {
        Input myin = new Input();
        HashMap<Integer,Vehicle> hm = new HashMap<Integer,Vehicle>(1000);
        for (;myin.hasNextOperation();) {
            String now = myin.getNewOperation();
            ArrayList<String> nows = 
                new ArrayList<String>(Arrays.asList(now.split(" ")));
            Factory ff = new Factory();
            // for create, need complete using Factory
            if (nows.get(0).equals("create")) {
                Vehicle vec = ff.create(nows);
                hm.put(vec.getId(),vec);
            }
            // for call
            else {
                int nowId = Integer.parseInt(nows.get(1));
                Vehicle vec = hm.get(nowId);
                if (nows.get(2).equals("run")) {
                    vec.run();
                }
                else if (nows.get(2).equals("getPrice")) {
                    vec.getPrice();
                }
                else if (nows.get(2).equals("work")) {
                    if (vec.getClass() == Rotcart.class) { ((Rotcart) vec).work(); }
                    else { ((Worker) vec).work(); }
                }
                else if (nows.get(2).equals("getId")) {
                    vec.getId();
                }
                else if (nows.get(2).equals("getIn")) {
                    ((Manned) vec).getIn();
                }
                else if (nows.get(2).equals("getOff")) {
                    ((Manned) vec).getOff();
                }
            }
        }
    }
} 
