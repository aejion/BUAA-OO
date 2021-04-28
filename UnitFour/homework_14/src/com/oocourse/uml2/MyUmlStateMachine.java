package com.oocourse.uml2;

import com.oocourse.uml2.models.elements.UmlState;
import com.oocourse.uml2.models.elements.UmlPseudostate;
import com.oocourse.uml2.models.elements.UmlFinalState;
import com.oocourse.uml2.models.elements.UmlTransition;
import com.oocourse.uml2.models.elements.UmlStateMachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MyUmlStateMachine {
    private UmlStateMachine my;
    private ArrayList<UmlState> allstate = new ArrayList<>();
    private UmlPseudostate initstate = null;
    private UmlFinalState finalstate = null;
    private HashMap<String,ArrayList<String>> relation = new HashMap<>();
    private int numtran = 0;
    private HashMap<String,Integer> vis = new HashMap<>();
    private HashSet<String> canget = new HashSet<>();
    private int hashas = 0;

    MyUmlStateMachine(UmlStateMachine t) {
        my = t;
    }

    public void addallstate(UmlState t) {
        allstate.add(t);
    }

    public void setFinalstate(UmlFinalState finalstate) {
        hashas++;
        this.finalstate = finalstate;
    }

    public void setInitstate(UmlPseudostate initstate) {
        hashas++;
        this.initstate = initstate;
    }

    public void addtran(UmlTransition t) {
        numtran++;
        String temp1 = t.getSource();
        ArrayList<String> temparr = new ArrayList<>();
        if (relation.containsKey(temp1)) {
            temparr = relation.get(temp1);
        }
        temparr.add(t.getTarget());
        relation.put(temp1,temparr);
    }

    public int countstate() {
        return allstate.size() + hashas;
    }

    public int getNumtran() {
        return numtran;
    }

    public int find(String name) {
        int ans = 0;
        for (UmlState t : allstate) {
            if (t.getName().equals(name)) {
                ans++;
            }
        }
        if (name == null) { ans += 2; }
        return ans;
    }

    public void dfs(String str) {
        if (vis.containsKey(str)) { return; }
        vis.put(str,1);
        if (!relation.containsKey(str)) { return; }
        ArrayList<String> temp = relation.get(str);
        for (String ss : temp) {
            if (!canget.contains(ss)) {
                canget.add(ss);
            }
            dfs(ss);
        }
    }

    public int findcanget(String str1) {
        vis.clear();
        canget.clear();
        String str = new String();
        for (UmlState t : allstate) {
            if (t.getName().equals(str1)) {
                str = t.getId();
                break;
            }
        }
        dfs(str);
        return canget.size();
    }
}
