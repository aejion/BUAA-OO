package com.oocourse.uml3;

import com.oocourse.uml3.interact.common.OperationQueryType;
import com.oocourse.uml3.models.common.Direction;
import com.oocourse.uml3.models.common.ElementType;
import com.oocourse.uml3.models.common.Visibility;
import com.oocourse.uml3.models.elements.UmlAttribute;
import com.oocourse.uml3.models.elements.UmlElement;
import com.oocourse.uml3.models.elements.UmlParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MyHandler {
    private HashMap<String,MyUmlClass> toid = new HashMap<>();
    private HashMap<String, UmlAttribute> bute = new HashMap<>();
    private HashMap<String,MyUmlInterface> face = new HashMap<>();

    public void setFace(HashMap<String, MyUmlInterface> face) {
        this.face = face;
    }

    public void setBute(HashMap<String, UmlAttribute> bute) {
        this.bute = bute;
    }

    public void setToid(HashMap<String, MyUmlClass> toid) {
        this.toid = toid;
    }

    public List<String> queryHidden(Object t) {
        if (t.getClass() == MyUmlClass.class) {
            List<String> ans = ((MyUmlClass)t).getAttridwa();
            if (((MyUmlClass)t).isHasupdatattrwa()) {
                return ans;
            }
            List<String> ans1 = new ArrayList<>();
            ArrayList<String> temp1 = ((MyUmlClass)t).getParent();
            for (String ss : temp1) {
                if (toid.containsKey(ss)) {
                    ans1.addAll(queryHidden(toid.get(ss)));
                }
            }
            ((MyUmlClass)t).setHasupdatattrwa(true);
            ((MyUmlClass)t).addattrwa(ans1);
            return ((MyUmlClass)t).getAttridwa();
        }
        return null;
    }

    public Boolean conflict(OperationQueryType[] queryTypes) {
        if (queryTypes.length == 0) { return false; }
        if (queryTypes.length > 2) { return true; }
        if (queryTypes.length == 2) {
            if (queryTypes[0] == OperationQueryType.NON_PARAM
                    && queryTypes[1] == OperationQueryType.PARAM) {
                return true;
            }
            if (queryTypes[1] == OperationQueryType.NON_PARAM
                    && queryTypes[0] == OperationQueryType.PARAM) {
                return true;
            }
            if (queryTypes[0] == OperationQueryType.NON_RETURN
                    && queryTypes[1] == OperationQueryType.RETURN) {
                return true;
            }
            if (queryTypes[1] == OperationQueryType.NON_RETURN
                    && queryTypes[0] == OperationQueryType.RETURN) {
                return true;
            }
        }
        return false;
    }

    public int queryAllAttr(Object t) {
        if (t.getClass() == MyUmlClass.class) {
            int ans1 = ((MyUmlClass)t).getSelfattr();
            int ans = 0;
            if (((MyUmlClass)t).getHasupdatattr()) {
                return ans1 + ((MyUmlClass)t).getOtherattr();
            }
            ArrayList<String> temp1 = ((MyUmlClass)t).getParent();
            for (String ss : temp1) {
                if (toid.containsKey(ss)) {
                    ans += queryAllAttr(toid.get(ss));
                }
            }
            ((MyUmlClass)t).setHasupdatattr(true);
            ((MyUmlClass)t).setOtherattr(ans);
            return ans + ans1;
        }
        return 0;
    }

    public int queryAllAsso(Object t) {
        if (t.getClass() == MyUmlClass.class) {
            int ans1 = ((MyUmlClass)t).getSelfasso();
            int ans = 0;
            if (((MyUmlClass)t).isHasupdatasso()) {
                return ans1 + ((MyUmlClass)t).getOtherasso();
            }
            ArrayList<String> temp1 = ((MyUmlClass)t).getParent();
            for (String ss : temp1) {
                if (toid.containsKey(ss)) {
                    ans += queryAllAsso(toid.get(ss));
                }
            }
            ((MyUmlClass)t).setHasupdatasso(true);
            ((MyUmlClass)t).setOtherasso(ans);
            return ans + ans1;
        }
        return 0;
    }

    public List<String> queryAllAssoset(Object t) {
        if (t.getClass() == MyUmlClass.class) {
            List<String> ans1 = ((MyUmlClass)t).getAllassoset();
            List<String> ans = new ArrayList<>();
            if (((MyUmlClass)t).isHasupdatassoset()) {
                return ans1;
            }
            ArrayList<String> temp1 = ((MyUmlClass)t).getParent();
            for (String ss : temp1) {
                if (toid.containsKey(ss)) {
                    List<String> ans2 = queryAllAssoset(toid.get(ss));
                    for (String ss1 : ans2) {
                        ans.add(ss1);
                    }
                }
            }
            ((MyUmlClass)t).addallasso(ans);
            ((MyUmlClass)t).setHasupdatassoset(true);
            return ((MyUmlClass)t).getAllassoset();
        }
        return null;
    }

    public int queryHasSame(Object t,String name) {
        int ans = 0;
        if (t.getClass() == MyUmlClass.class) {
            ArrayList<String> temp1 = ((MyUmlClass)t).getParent();
            ArrayList<String> temp2 = ((MyUmlClass)t).getAttrid();
            for (String ss : temp2) {
                if (bute.get(ss).getName().equals(name)) {
                    ans++;
                }
            }
            for (String ss : temp1) {
                if (toid.containsKey(ss)) {
                    ans += queryHasSame(toid.get(ss),name);
                }
            }
        }
        return ans;
    }

    public Visibility queryVi(Object t, String name) {
        if (t.getClass() == MyUmlClass.class) {
            ArrayList<String> temp1 = ((MyUmlClass)t).getParent();
            ArrayList<String> temp2 = ((MyUmlClass)t).getAttrid();
            for (String ss : temp2) {
                if (bute.get(ss).getName().equals(name)) {
                    return bute.get(ss).getVisibility();
                }
            }
            for (String ss : temp1) {
                if (toid.containsKey(ss)) {
                    if (queryVi(toid.get(ss),name) != null) {
                        return queryVi(toid.get(ss),name);
                    }
                }
            }
        }
        return null;
    }

    public String queryTop(Object t) {
        if (t.getClass() == MyUmlClass.class) {
            String ans = ((MyUmlClass)t).getTopfather();
            if (((MyUmlClass)t).isHasupdatop()) {
                return ans;
            }
            ArrayList<String> temp1 = ((MyUmlClass)t).getParent();
            for (String ss : temp1) {
                if (toid.containsKey(ss)) {
                    ans = queryTop(toid.get(ss));
                }
            }
            ((MyUmlClass)t).setHasupdatop(true);
            ((MyUmlClass)t).setTopfather(ans);
            return ans;
        }
        return null;
    }

    public List<String> queryInterfaceList(Object t) {
        List<String> ans = new ArrayList<>();
        List<String> ans1 = new ArrayList<>();
        if (t.getClass() == MyUmlClass.class) {
            ans = ((MyUmlClass)t).getAllinterface();
            if (((MyUmlClass)t).isHasupdatallinter()) {
                return ans;
            }
            ArrayList<String> temp1 = ((MyUmlClass)t).getParent();
            for (String ss : temp1) {
                if (toid.containsKey(ss)) {
                    ans1.addAll(queryInterfaceList(toid.get(ss)));
                }
                if (face.containsKey(ss)) {
                    ans1.addAll(queryInterfaceList(face.get(ss)));
                }
            }
            ((MyUmlClass)t).addallinter(ans1);
            ((MyUmlClass)t).setHasupdatallinter(true);
            ans = ((MyUmlClass)t).getAllinterface();
        } else if (t.getClass() == MyUmlInterface.class) {
            ans = ((MyUmlInterface)t).getAllinterface();
            if (((MyUmlInterface)t).isHasupdatallinter()) {
                return ans;
            }
            ArrayList<String> temp1 = ((MyUmlInterface)t).getParent();
            for (String ss : temp1) {
                if (face.containsKey(ss)) {
                    ans1.add(ss);
                    ans1.addAll(queryInterfaceList(face.get(ss)));
                }
            }
            ((MyUmlInterface)t).addallinter(ans1);
            ((MyUmlInterface)t).setHasupdatallinter(true);
            ans = ((MyUmlInterface)t).getAllinterface();
        }
        return ans;
    }

    private HashSet<String> vis = new HashSet<>();

    public void setVis() {
        vis.clear();
    }

    public boolean queryloop(Object t,String to) {
        if (t.getClass() == MyUmlClass.class) {
            MyUmlClass temp = (MyUmlClass) t;
            String id = temp.getid();
            if (vis.contains(id)) {
                if (id == to) { return true; }
                else { return false; }
            }
            vis.add(id);
            ArrayList<String> temp1 = ((MyUmlClass)t).getParent();
            for (String ss : temp1) {
                if (toid.containsKey(ss)) {
                    if (queryloop(toid.get(ss),to)) { return true; }
                }
                if (face.containsKey(ss)) {
                    if (queryloop(face.get(ss),to)) { return true; }
                }
            }
        }
        else if (t.getClass() == MyUmlInterface.class) {
            MyUmlInterface temp = (MyUmlInterface) t;
            String id = temp.getid();
            if (vis.contains(id)) {
                if (id == to) { return true; }
                else { return false; }
            }
            vis.add(id);
            ArrayList<String> temp1 = ((MyUmlInterface)t).getParent();
            for (String ss : temp1) {
                if (toid.containsKey(ss)) {
                    if (queryloop(toid.get(ss),to)) { return true; }
                }
                if (face.containsKey(ss)) {
                    if (queryloop(face.get(ss),to)) { return true; }
                }
            }
        }
        return false;
    }

    public boolean query003(Object t,String to) {
        if (t.getClass() == MyUmlClass.class) {
            MyUmlClass temp = (MyUmlClass) t;
            String id = temp.getid();
            if (vis.contains(id)) {
                return true;
            }
            vis.add(id);
            ArrayList<String> temp1 = ((MyUmlClass)t).getParent();
            for (String ss : temp1) {
                if (toid.containsKey(ss)) {
                    if (query003(toid.get(ss),to)) { return true; }
                }
            }
        }
        else if (t.getClass() == MyUmlInterface.class) {
            MyUmlInterface temp = (MyUmlInterface) t;
            String id = temp.getid();
            if (vis.contains(id)) {
                return true;
            }
            vis.add(id);
            ArrayList<String> temp1 = ((MyUmlInterface)t).getParent();
            for (String ss : temp1) {
                if (face.containsKey(ss)) {
                    if (query003(face.get(ss),to)) { return true; }
                }
            }
        }
        return false;
    }

    public boolean query004(Object t,String to) {
        if (t.getClass() == MyUmlClass.class) {
            MyUmlClass temp = (MyUmlClass) t;
            String id = temp.getid();
            if (vis.contains(id)) {
                if (face.containsKey(id)) {
                    return true;
                }
                else { return false; }
            }
            vis.add(id);
            ArrayList<String> temp1 = ((MyUmlClass)t).getParent();
            for (String ss : temp1) {
                if (toid.containsKey(ss)) {
                    if (query004(toid.get(ss),to)) { return true; }
                }
                if (face.containsKey(ss)) {
                    if (query004(face.get(ss),to)) { return true; }
                }
            }
        }
        else if (t.getClass() == MyUmlInterface.class) {
            MyUmlInterface temp = (MyUmlInterface) t;
            String id = temp.getid();
            if (vis.contains(id)) {
                return true;
            }
            vis.add(id);
            ArrayList<String> temp1 = ((MyUmlInterface)t).getParent();
            for (String ss : temp1) {
                if (toid.containsKey(ss)) {
                    if (query004(toid.get(ss),to)) { return true; }
                }
                if (face.containsKey(ss)) {
                    if (query004(face.get(ss),to)) { return true; }
                }
            }
        }
        return false;
    }

    public boolean query005(ArrayList<UmlElement> ele) {
        for (UmlElement tt:ele) {
            if (tt.getElementType() == ElementType.UML_PARAMETER) {
                UmlParameter temp = (UmlParameter) tt;
                if (temp.getDirection() == Direction.RETURN) {
                    continue;
                }
                if (temp.getName() == null) {
                    return true;
                }
            } else if (tt.getElementType() == ElementType.UML_CLASS) {
                if (tt.getName() == null) { return true; }
            } else if (tt.getElementType() == ElementType.UML_ATTRIBUTE) {
                if (!toid.containsKey(tt.getParentId()) && !face.containsKey(tt.getParentId())) {
                    continue;
                }
                if (tt.getName() == null) { return true; }
            } else if (tt.getElementType() == ElementType.UML_OPERATION) {
                if (tt.getName() == null) { return true; }
            } else if (tt.getElementType() == ElementType.UML_INTERFACE) {
                if (tt.getName() == null) { return true; }
            }
        }
        return false;
    }
}
