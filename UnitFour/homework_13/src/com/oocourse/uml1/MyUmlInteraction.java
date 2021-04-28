package com.oocourse.uml1;

import com.oocourse.uml1.interact.common.AttributeClassInformation;
import com.oocourse.uml1.interact.common.AttributeQueryType;
import com.oocourse.uml1.interact.common.OperationQueryType;
import com.oocourse.uml1.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml1.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml1.interact.exceptions.user.ConflictQueryTypeException;
import com.oocourse.uml1.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml1.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml1.interact.format.UmlInteraction;
import com.oocourse.uml1.models.common.Visibility;
import com.oocourse.uml1.models.elements.UmlAssociation;
import com.oocourse.uml1.models.elements.UmlAssociationEnd;
import com.oocourse.uml1.models.elements.UmlAttribute;
import com.oocourse.uml1.models.elements.UmlParameter;
import com.oocourse.uml1.models.elements.UmlGeneralization;
import com.oocourse.uml1.models.elements.UmlInterfaceRealization;
import com.oocourse.uml1.models.elements.UmlElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyUmlInteraction implements UmlInteraction {
    private HashMap<String,MyUmlClass> toid = new HashMap<>();
    private HashMap<String, UmlAssociation> ion = new HashMap<>();
    private HashMap<String, UmlAssociationEnd> end = new HashMap<>();
    private HashMap<String, UmlAttribute> bute = new HashMap<>();
    private HashMap<String,MyUmlOperation> tion = new HashMap<>();
    private HashMap<String, UmlParameter> ter = new HashMap<>();
    private HashMap<String, UmlGeneralization> ation = new HashMap<>();
    private HashMap<String,UmlInterfaceRealization> zation = new HashMap<>();
    private HashMap<String,MyUmlInterface> face = new HashMap<>();
    private HashMap<String,MyUmlClass> toname = new HashMap<>();
    private HashMap<String,Integer> namenum = new HashMap<>();
    private MyInitClass my = new MyInitClass();
    private MyHandler handler = new MyHandler();

    public MyUmlInteraction(UmlElement[] elements) {
        my.setAtion(ation);
        my.setBute(bute);
        my.setEnd(end);
        my.setFace(face);
        my.setIon(ion);
        my.setNamenum(namenum);
        my.setTer(ter);
        my.setTion(tion);
        my.setToid(toid);
        my.setToname(toname);
        my.setZation(zation);
        handler.setToid(toid);
        handler.setBute(bute);
        handler.setFace(face);
        int size = elements.length;
        for (int i = 0; i < size; i++) {
            my.before(elements[i]);
        }
        for (int i = 0; i < size; i++) {
            my.dowith(elements[i]);
        }
    }

    @Override
    public int getClassCount() {
        return toid.size();
    }

    @Override
    public int getClassOperationCount(String className, OperationQueryType[] queryTypes) throws
            ClassNotFoundException, ClassDuplicatedException, ConflictQueryTypeException {
        if (!toname.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (namenum.get(className) > 1) {
            throw new ClassDuplicatedException(className);
        }
        if (handler.conflict(queryTypes)) {
            throw new ConflictQueryTypeException();
        }
        MyUmlClass temp = toname.get(className);
        ArrayList<String> temp1 = temp.getOpid();
        int ans = 0;
        for (String s : temp1) {
            if (tion.get(s).canfit(queryTypes)) {
                ans++;
            }
        }
        return ans;
    }

    @Override
    public int getClassAttributeCount(String className, AttributeQueryType queryType) throws
            ClassNotFoundException, ClassDuplicatedException {
        if (!toname.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (namenum.get(className) > 1) {
            throw new ClassDuplicatedException(className);
        }
        MyUmlClass temp = toname.get(className);
        if (queryType == AttributeQueryType.SELF_ONLY) {
            return temp.getSelfattr();
        }
        else if (queryType == AttributeQueryType.ALL) {
            return handler.queryAllAttr(temp);
        }
        return 0;
    }

    @Override
    public int getClassAssociationCount(String className) throws
            ClassNotFoundException, ClassDuplicatedException {
        if (!toname.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (namenum.get(className) > 1) {
            throw new ClassDuplicatedException(className);
        }
        MyUmlClass temp = toname.get(className);
        return handler.queryAllAsso(temp);
    }

    @Override
    public List<String> getClassAssociatedClassList(String className) throws
            ClassNotFoundException, ClassDuplicatedException {
        if (!toname.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (namenum.get(className) > 1) {
            throw new ClassDuplicatedException(className);
        }
        MyUmlClass temp = toname.get(className);
        List<String> temp1 = handler.queryAllAssoset(temp);
        List<String> ans = new ArrayList<>();
        for (String ss : temp1) {
            if (toid.containsKey(ss)) {
                ans.add(toid.get(ss).getName());
            }
        }
        return ans;
    }

    @Override
    public Map<Visibility, Integer> getClassOperationVisibility(String className,
                                                                String operationName)
            throws ClassNotFoundException, ClassDuplicatedException {
        if (!toname.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (namenum.get(className) > 1) {
            throw new ClassDuplicatedException(className);
        }
        MyUmlClass temp = toname.get(className);
        ArrayList<String> temp1 = temp.getOpid();
        Map<Visibility, Integer> ans = new HashMap<>();
        for (String ss : temp1) {
            MyUmlOperation temp3 = tion.get(ss);
            if (temp3.getName().equals(operationName)) {
                Visibility temp2 = temp3.getVi();
                int temp4 = 0;
                if (ans.containsKey(temp2)) {
                    temp4 = ans.get(temp2);
                }
                ans.put(temp2,temp4 + 1);
            }
        }
        return ans;
    }

    @Override
    public Visibility getClassAttributeVisibility(String className, String attributeName) throws
            ClassNotFoundException, ClassDuplicatedException,
            AttributeNotFoundException, AttributeDuplicatedException {
        if (!toname.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (namenum.get(className) > 1) {
            throw new ClassDuplicatedException(className);
        }
        MyUmlClass temp = toname.get(className);
        int num = handler.queryHasSame(temp,attributeName);
        if (num == 0) {
            throw new AttributeNotFoundException(className,attributeName);
        }
        if (num > 1) {
            throw new AttributeDuplicatedException(className,attributeName);
        }
        return handler.queryVi(temp,attributeName);
    }

    @Override
    public String getTopParentClass(String className) throws
            ClassNotFoundException, ClassDuplicatedException {
        if (!toname.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (namenum.get(className) > 1) {
            throw new ClassDuplicatedException(className);
        }
        MyUmlClass temp = toname.get(className);
        return handler.queryTop(temp);
    }

    @Override
    public List<String> getImplementInterfaceList(String className) throws
            ClassNotFoundException, ClassDuplicatedException {
        if (!toname.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (namenum.get(className) > 1) {
            throw new ClassDuplicatedException(className);
        }
        MyUmlClass temp = toname.get(className);
        List<String> temp1 = handler.queryInterfaceList(temp);
        List<String> ans = new ArrayList<>();
        for (String ss : temp1) {
            if (face.containsKey(ss)) {
                ans.add(face.get(ss).getName());
            }
        }
        return ans;
    }

    @Override
    public List<AttributeClassInformation> getInformationNotHidden(String className) throws
            ClassNotFoundException, ClassDuplicatedException {
        if (!toname.containsKey(className)) {
            throw new ClassNotFoundException(className);
        }
        if (namenum.get(className) > 1) {
            throw new ClassDuplicatedException(className);
        }
        MyUmlClass temp = toname.get(className);
        List<String> temp1 = handler.queryHidden(temp);
        List<AttributeClassInformation> ans = new ArrayList<>();
        for (String ss : temp1) {
            String name = bute.get(ss).getName();
            String classna = toid.get(bute.get(ss).getParentId()).getName();
            AttributeClassInformation temp3 = new AttributeClassInformation(name,classna);
            ans.add(temp3);
        }
        return ans;
    }
}
