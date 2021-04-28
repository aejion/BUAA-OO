package com.oocourse.uml2;

import com.oocourse.uml2.models.elements.UmlInteraction;
import com.oocourse.uml2.models.elements.UmlLifeline;
import com.oocourse.uml2.models.elements.UmlMessage;

import java.util.ArrayList;

public class MyUmlinteraction {
    private UmlInteraction my;
    private ArrayList<UmlLifeline> life = new ArrayList<>();
    private ArrayList<UmlMessage> message = new ArrayList<>();

    MyUmlinteraction(UmlInteraction t) {
        my = t;
    }

    public void addlife(UmlLifeline t) {
        life.add(t);
    }

    public void addmess(UmlMessage t) {
        message.add(t);
    }

    public int getallife() {
        return life.size();
    }

    public int getallmess() {
        return message.size();
    }

    public int find(String name) {
        int ans = 0;
        for (UmlLifeline t : life) {
            if (t.getName().equals(name)) {
                ans++;
            }
        }
        return ans;
    }

    public int getnum(String name) {
        String id = new String();
        for (UmlLifeline t : life) {
            if (t.getName().equals(name)) {
                id = t.getId();
                break;
            }
        }
        int ans = 0;
        for (UmlMessage t : message) {
            if (t.getTarget().equals(id)) {
                ans++;
            }
        }
        return ans;
    }
}
