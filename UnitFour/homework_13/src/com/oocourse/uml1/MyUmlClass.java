package com.oocourse.uml1;

import com.oocourse.uml1.models.common.Visibility;
import com.oocourse.uml1.models.elements.UmlAttribute;
import com.oocourse.uml1.models.elements.UmlClass;
import com.oocourse.uml1.models.elements.UmlOperation;

import java.util.ArrayList;
import java.util.List;

public class MyUmlClass {
    private UmlClass umlele;
    private ArrayList<String> attrid = new ArrayList<>();
    private int selfattr = 0;
    private ArrayList<String> opid = new ArrayList<>();
    private int selfasso = 0;
    private ArrayList<String> selfassoset = new ArrayList<>();
    private ArrayList<String> allassoset = new ArrayList<>();
    private ArrayList<String> parent = new ArrayList<>();
    private boolean hasupdatattr = false;
    private int otherattr = 0;
    private boolean hasupdatasso = false;
    private int otherasso = 0;
    private boolean hasupdatassoset = false;
    private String topfather;
    private boolean hasupdatop = false;
    private ArrayList<String> allinterface = new ArrayList<>();
    private boolean hasupdatallinter = false;
    private boolean hasupdatattrwa = false;
    private ArrayList<String> attridwa = new ArrayList<>();

    MyUmlClass(UmlClass ele) {
        umlele = ele;
        topfather = ele.getName();
    }

    public ArrayList<String> getAttridwa() {
        return attridwa;
    }

    public void addattrwa(List<String> str) {
        for (String ss : str) {
            if (!attridwa.contains(ss)) {
                attridwa.add(ss);
            }
        }
    }

    public void setHasupdatattrwa(boolean hasupdatattrwa) {
        this.hasupdatattrwa = hasupdatattrwa;
    }

    public boolean isHasupdatattrwa() {
        return hasupdatattrwa;
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

    public String getTopfather() {
        return topfather;
    }

    public void setTopfather(String topfather) {
        this.topfather = topfather;
    }

    public void setHasupdatop(boolean hasupdatop) {
        this.hasupdatop = hasupdatop;
    }

    public boolean isHasupdatop() {
        return hasupdatop;
    }

    public void addallasso(List<String> str) {
        for (String ss : str) {
            if (!allassoset.contains(ss)) {
                allassoset.add(ss);
            }
        }
    }

    public ArrayList<String> getAllassoset() {
        return allassoset;
    }

    public void setHasupdatassoset(boolean hasupdatassoset) {
        this.hasupdatassoset = hasupdatassoset;
    }

    public boolean isHasupdatassoset() {
        return hasupdatassoset;
    }

    public void setHasupdatasso(boolean hasupdatasso) {
        this.hasupdatasso = hasupdatasso;
    }

    public int getOtherasso() {
        return otherasso;
    }

    public boolean isHasupdatasso() {
        return hasupdatasso;
    }

    public void setOtherasso(int otherasso) {
        this.otherasso = otherasso;
    }

    public void setHasupdatattr(boolean hasupdata) {
        this.hasupdatattr = hasupdata;
    }

    public boolean getHasupdatattr() {
        return hasupdatattr;
    }

    public int getOtherattr() {
        return otherattr;
    }

    public void setOtherattr(int otherattr) {
        this.otherattr = otherattr;
    }

    public void addselfattr(UmlAttribute t) {
        if (t.getVisibility() != Visibility.PRIVATE) {
            attridwa.add(t.getId());
        }
        attrid.add(t.getId());
        selfattr++;
    }

    public void addoperation(UmlOperation t) {
        opid.add(t.getId());
    }

    public ArrayList<String> getAttrid() {
        return attrid;
    }

    public void addselfasso(String str) {
        selfasso++;
        if (!selfassoset.contains(str)) {
            selfassoset.add(str);
            allassoset.add(str);
        }
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

    public ArrayList<String> getParent() {
        return parent;
    }

    public int getSelfasso() {
        return selfasso;
    }

    public ArrayList<String> getSelfassoset() {
        return selfassoset;
    }

    public int getSelfattr() {
        return selfattr;
    }

    public String getName() {
        return umlele.getName();
    }
}

