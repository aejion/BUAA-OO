package com.oocourse.uml3;

import com.oocourse.uml3.interact.common.AttributeClassInformation;
import com.oocourse.uml3.models.common.Visibility;
import com.oocourse.uml3.models.elements.UmlAssociationEnd;
import com.oocourse.uml3.models.elements.UmlAttribute;
import com.oocourse.uml3.models.elements.UmlClass;
import com.oocourse.uml3.models.elements.UmlOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
    private ArrayList<UmlAssociationEnd> end = new ArrayList<>();
    private ArrayList<UmlAttribute> allbute = new ArrayList<>();
    private HashMap<String, MyUmlClass> toid = new HashMap<>();
    private HashMap<String, MyUmlInterface> face = new HashMap<>();

    MyUmlClass(UmlClass ele) {
        umlele = ele;
        topfather = ele.getName();
    }

    public UmlClass getUmlele() {
        return umlele;
    }

    public String getid() { return umlele.getId(); }

    public ArrayList<String> getAttridwa() {
        return attridwa;
    }

    public void setToid(HashMap<String, MyUmlClass> toid) {
        this.toid = toid;
    }

    public void setFace(HashMap<String, MyUmlInterface> face) {
        this.face = face;
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
        allbute.add(t);
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

    public void addend(UmlAssociationEnd temp) {
        end.add(temp);
    }

    public boolean check001() {
        HashSet<String> temp = new HashSet<>();
        for (UmlAttribute temp1 : allbute) {
            String str = temp1.getName();
            if (str != null) {
                if (temp.contains(str)) {
                    return true;
                }
                temp.add(str);
            }
        }
        for (UmlAssociationEnd temp1 : end) {
            String str = temp1.getName();
            if (str != null) {
                if (temp.contains(str)) {
                    return true;
                }
                temp.add(str);
            }
        }
        return false;
    }

    public HashSet<AttributeClassInformation> get001() {
        HashSet<String> temp = new HashSet<>();
        HashSet<AttributeClassInformation> ss = new HashSet<>();
        HashSet<String> ans = new HashSet<>();
        for (UmlAttribute temp1 : allbute) {
            String str = temp1.getName();
            if (str != null) {
                if (temp.contains(str)) {
                    ans.add(str);
                }
                else { temp.add(str); }
            }
        }
        for (UmlAssociationEnd temp1 : end) {
            String str = temp1.getName();
            if (str != null) {
                if (temp.contains(str)) {
                    ans.add(str);
                }
                else { temp.add(str); }
            }
        }
        for (UmlAttribute temp1 : allbute) {
            String str = temp1.getName();
            if (ans.contains(str)) {
                AttributeClassInformation tt = new AttributeClassInformation(str,umlele.getName());
                ss.add(tt);
            }
        }
        for (UmlAssociationEnd temp1 : end) {
            String str = temp1.getName();
            if (ans.contains(str)) {
                AttributeClassInformation tt = new AttributeClassInformation(str,umlele.getName());
                ss.add(tt);
            }
        }
        return ss;
    }
}

