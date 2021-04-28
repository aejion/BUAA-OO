package com.oocourse.uml1;

import com.oocourse.uml1.models.common.ElementType;
import com.oocourse.uml1.models.elements.UmlAssociation;
import com.oocourse.uml1.models.elements.UmlAssociationEnd;
import com.oocourse.uml1.models.elements.UmlAttribute;
import com.oocourse.uml1.models.elements.UmlParameter;
import com.oocourse.uml1.models.elements.UmlGeneralization;
import com.oocourse.uml1.models.elements.UmlInterfaceRealization;
import com.oocourse.uml1.models.elements.UmlClass;
import com.oocourse.uml1.models.elements.UmlElement;
import com.oocourse.uml1.models.elements.UmlOperation;
import com.oocourse.uml1.models.elements.UmlInterface;

import java.util.HashMap;

public class MyInitClass {
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

    public void dowith(UmlElement ele) {
        if (ele.getElementType() == ElementType.UML_ATTRIBUTE) {
            UmlAttribute temp = (UmlAttribute) ele;
            String par = temp.getParentId();
            if (toid.containsKey(par)) {
                toid.get(par).addselfattr(temp);
            }
            if (face.containsKey(par)) {
                face.get(par).addselfattr(temp);
            }
        }
        if (ele.getElementType() == ElementType.UML_OPERATION) {
            UmlOperation temp = (UmlOperation) ele;
            String par = temp.getParentId();
            if (toid.containsKey(par)) {
                toid.get(par).addoperation(temp);
            }
            if (face.containsKey(par)) {
                face.get(par).addoperation(temp);
            }
        }
        if (ele.getElementType() == ElementType.UML_PARAMETER) {
            UmlParameter temp = (UmlParameter) ele;
            tion.get(temp.getParentId()).addparameter(temp);
        }
        if (ele.getElementType() == ElementType.UML_ASSOCIATION) {
            UmlAssociation temp = (UmlAssociation) ele;
            UmlAssociationEnd end1 = end.get(temp.getEnd1());
            UmlAssociationEnd end2 = end.get(temp.getEnd2());
            if (toid.containsKey(end1.getReference())) {
                toid.get(end1.getReference()).addselfasso(end2.getReference());
            }
            if (face.containsKey(end1.getReference())) {
                face.get(end1.getReference()).addselfasso(end2.getReference());
            }
            if (toid.containsKey(end2.getReference())) {
                toid.get(end2.getReference()).addselfasso(end1.getReference());
            }
            if (face.containsKey(end2.getReference())) {
                face.get(end2.getReference()).addselfasso(end1.getReference());
            }
        }
        if (ele.getElementType() == ElementType.UML_GENERALIZATION) {
            UmlGeneralization temp = (UmlGeneralization) ele;
            if (toid.containsKey(temp.getSource())) {
                MyUmlClass source = toid.get(temp.getSource());
                source.addExAndIm(temp.getTarget());
            } else if (face.containsKey(temp.getSource())) {
                MyUmlInterface source = face.get(temp.getSource());
                source.addExAndIm(temp.getTarget());
            }
        }
        if (ele.getElementType() == ElementType.UML_INTERFACE_REALIZATION) {
            UmlInterfaceRealization temp = (UmlInterfaceRealization) ele;
            MyUmlClass source = toid.get(temp.getSource());
            source.addExAndIm(temp.getTarget());
        }
    }

    public void before(UmlElement ele) {
        if (ele.getElementType() == ElementType.UML_CLASS) {
            MyUmlClass temp = new MyUmlClass((UmlClass) ele);
            toname.put(ele.getName(),temp);
            toid.put(ele.getId(),temp);
            int temp1 = 0;
            if (namenum.containsKey(ele.getName())) {
                temp1 = namenum.get(ele.getName());
            }
            namenum.put(ele.getName(),temp1 + 1);
        } else if (ele.getElementType() == ElementType.UML_ASSOCIATION) {
            ion.put(ele.getId(),(UmlAssociation) ele);
        } else if (ele.getElementType() == ElementType.UML_ASSOCIATION_END) {
            end.put(ele.getId(),(UmlAssociationEnd) ele);
        } else if (ele.getElementType() == ElementType.UML_ATTRIBUTE) {
            bute.put(ele.getId(),(UmlAttribute) ele);
        } else if (ele.getElementType() == ElementType.UML_OPERATION) {
            MyUmlOperation temp = new MyUmlOperation((UmlOperation) ele);
            tion.put(ele.getId(),temp);
        } else if (ele.getElementType() == ElementType.UML_PARAMETER) {
            ter.put(ele.getId(),(UmlParameter) ele);
        } else if (ele.getElementType() == ElementType.UML_GENERALIZATION) {
            ation.put(ele.getId(),(UmlGeneralization) ele);
        } else if (ele.getElementType() == ElementType.UML_INTERFACE_REALIZATION) {
            zation.put(ele.getId(),(UmlInterfaceRealization) ele);
        } else if (ele.getElementType() == ElementType.UML_INTERFACE) {
            MyUmlInterface temp = new MyUmlInterface((UmlInterface) ele);
            face.put(ele.getId(),temp);
        }
    }

    public void setAtion(HashMap<String, UmlGeneralization> ation) {
        this.ation = ation;
    }

    public void setBute(HashMap<String, UmlAttribute> bute) {
        this.bute = bute;
    }

    public void setEnd(HashMap<String, UmlAssociationEnd> end) {
        this.end = end;
    }

    public void setFace(HashMap<String, MyUmlInterface> face) {
        this.face = face;
    }

    public void setIon(HashMap<String, UmlAssociation> ion) {
        this.ion = ion;
    }

    public void setNamenum(HashMap<String, Integer> namenum) {
        this.namenum = namenum;
    }

    public void setTer(HashMap<String, UmlParameter> ter) {
        this.ter = ter;
    }

    public void setTion(HashMap<String, MyUmlOperation> tion) {
        this.tion = tion;
    }

    public void setToid(HashMap<String, MyUmlClass> toid) {
        this.toid = toid;
    }

    public void setToname(HashMap<String, MyUmlClass> toname) {
        this.toname = toname;
    }

    public void setZation(HashMap<String, UmlInterfaceRealization> zation) {
        this.zation = zation;
    }
}
