package com.oocourse.uml3;

import com.oocourse.uml3.models.common.Visibility;
import com.oocourse.uml3.models.elements.UmlAttribute;
import com.oocourse.uml3.models.elements.UmlInterface;
import com.oocourse.uml3.models.elements.UmlOperation;
import java.util.ArrayList;
import java.util.List;

public class MyUmlInterface {
    private UmlInterface umlele;
    private int selfattr = 0;
    private ArrayList<String> attrid = new ArrayList<>();
    private ArrayList<String> opid = new ArrayList<>();
    private int selfasso = 0;
    private ArrayList<String> selfassoset = new ArrayList<>();
    private ArrayList<String> parent = new ArrayList<>();
    private ArrayList<String> allinterface = new ArrayList<>();
    private boolean hasupdatallinter = false;
    private ArrayList<UmlAttribute> allbute = new ArrayList<>();

    MyUmlInterface(UmlInterface t) {
        umlele = t;
    }

    public String getid() { return umlele.getId(); }

    public void addselfattr(UmlAttribute t) {
        attrid.add(t.getId());
        allbute.add(t);
        selfattr++;
    }

    public UmlInterface getUmlele() {
        return umlele;
    }

    public void addallinter(List<String> str) {
        for (String ss : str) {
            if (!allinterface.contains(ss)) {
                allinterface.add(ss);
            }
        }
    }

    public ArrayList<String> getAllinterface() {
        return allinterface;
    }

    public boolean isHasupdatallinter() {
        return hasupdatallinter;
    }

    public void setHasupdatallinter(boolean hasupdatallinter) {
        this.hasupdatallinter = hasupdatallinter;
    }

    public void addoperation(UmlOperation t) {
        opid.add(t.getId());
    }

    public void addselfasso(String str) {
        selfasso++;
        selfassoset.add(str);
    }

    public void addExAndIm(String str) {
        if (!allinterface.contains(str)) {
            allinterface.add(str);
        }
        parent.add(str);
    }

    public ArrayList<String> getOpid() {
        return opid;
    }

    public int getSelfattr() {
        return selfattr;
    }

    public int getSelfasso() {
        return selfasso;
    }

    public ArrayList<String> getParent() {
        return parent;
    }

    public String getName() {
        return umlele.getName();
    }

    public String getId() {
        return umlele.getId();
    }

    public boolean check006() {
        for (UmlAttribute ss : allbute) {
            if (ss.getVisibility() != Visibility.PUBLIC) {
                return true;
            }
        }
        return false;
    }
}
