package com.oocourse.uml3;

import com.oocourse.uml3.models.common.ElementType;
import com.oocourse.uml3.models.elements.UmlAssociationEnd;
import com.oocourse.uml3.models.elements.UmlAssociation;
import com.oocourse.uml3.models.elements.UmlAttribute;
import com.oocourse.uml3.models.elements.UmlParameter;
import com.oocourse.uml3.models.elements.UmlGeneralization;
import com.oocourse.uml3.models.elements.UmlInterfaceRealization;
import com.oocourse.uml3.models.elements.UmlMessage;
import com.oocourse.uml3.models.elements.UmlLifeline;
import com.oocourse.uml3.models.elements.UmlRegion;
import com.oocourse.uml3.models.elements.UmlElement;
import com.oocourse.uml3.models.elements.UmlOperation;
import com.oocourse.uml3.models.elements.UmlState;
import com.oocourse.uml3.models.elements.UmlPseudostate;
import com.oocourse.uml3.models.elements.UmlFinalState;
import com.oocourse.uml3.models.elements.UmlTransition;
import com.oocourse.uml3.models.elements.UmlClass;
import com.oocourse.uml3.models.elements.UmlInterface;
import com.oocourse.uml3.models.elements.UmlInteraction;
import com.oocourse.uml3.models.elements.UmlStateMachine;
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
    private HashMap<String,MyUmlinteraction> ction = new HashMap<>();
    private HashMap<String,MyUmlinteraction> intoname = new HashMap<>();
    private HashMap<String,Integer> innamenum = new HashMap<>();
    private HashMap<String, UmlMessage> ssage = new HashMap<>();
    private HashMap<String,UmlLifeline> line = new HashMap<>();
    private HashMap<String,MyUmlStateMachine> chine = new HashMap<>();
    private HashMap<String,MyUmlStateMachine> inetoname = new HashMap<>();
    private HashMap<String,Integer> inenamenum = new HashMap<>();
    private HashMap<String,UmlRegion> gion = new HashMap<>();

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
                toid.get(end1.getReference()).addend(end2);
            }
            if (face.containsKey(end1.getReference())) {
                face.get(end1.getReference()).addselfasso(end2.getReference());
            }
            if (toid.containsKey(end2.getReference())) {
                toid.get(end2.getReference()).addselfasso(end1.getReference());
                toid.get(end2.getReference()).addend(end1);
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
        dowithtwo(ele);
    }

    private void dowithtwo(UmlElement ele) {
        if (ele.getElementType() == ElementType.UML_INTERFACE_REALIZATION) {
            UmlInterfaceRealization temp = (UmlInterfaceRealization) ele;
            MyUmlClass source = toid.get(temp.getSource());
            source.addExAndIm(temp.getTarget());
        }
        if (ele.getElementType() == ElementType.UML_LIFELINE) {
            ction.get(ele.getParentId()).addlife((UmlLifeline) ele);
        }
        if (ele.getElementType() == ElementType.UML_MESSAGE) {
            ction.get(ele.getParentId()).addmess((UmlMessage) ele);
        }
        if (ele.getElementType() == ElementType.UML_STATE) {
            UmlRegion temp = gion.get(ele.getParentId());
            chine.get(temp.getParentId()).addallstate((UmlState) ele);
        }
        if (ele.getElementType() == ElementType.UML_PSEUDOSTATE) {
            UmlRegion temp = gion.get(ele.getParentId());
            chine.get(temp.getParentId()).setInitstate((UmlPseudostate) ele);
        }
        if (ele.getElementType() == ElementType.UML_FINAL_STATE) {
            UmlRegion temp = gion.get(ele.getParentId());
            chine.get(temp.getParentId()).setFinalstate((UmlFinalState) ele);
        }
        if (ele.getElementType() == ElementType.UML_TRANSITION) {
            UmlRegion temp = gion.get(ele.getParentId());
            chine.get(temp.getParentId()).addtran((UmlTransition) ele);
        }
    }

    public void before(UmlElement ele) {
        if (ele.getElementType() == ElementType.UML_CLASS) {
            MyUmlClass temp = new MyUmlClass((UmlClass) ele);
            temp.setToid(toid);
            temp.setFace(face);
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
        } else if (ele.getElementType() == ElementType.UML_INTERACTION) {
            MyUmlinteraction temp = new MyUmlinteraction((UmlInteraction) ele);
            ction.put(ele.getId(),temp);
            intoname.put(ele.getName(),temp);
            int temp1 = 0;
            if (innamenum.containsKey(ele.getName())) {
                temp1 = innamenum.get(ele.getName());
            }
            innamenum.put(ele.getName(),temp1 + 1);
        } else if (ele.getElementType() == ElementType.UML_MESSAGE) {
            ssage.put(ele.getId(), (UmlMessage) ele);
        } else if (ele.getElementType() == ElementType.UML_LIFELINE) {
            line.put(ele.getId(), (UmlLifeline) ele);
        } else if (ele.getElementType() == ElementType.UML_STATE_MACHINE) {
            MyUmlStateMachine temp = new MyUmlStateMachine((UmlStateMachine) ele);
            chine.put(ele.getId(),temp);
            inetoname.put(ele.getName(),temp);
            int temp1 = 0;
            if (inenamenum.containsKey(ele.getName())) {
                temp1 = inenamenum.get(ele.getName());
            }
            inenamenum.put(ele.getName(),temp1 + 1);
        } else if (ele.getElementType() == ElementType.UML_REGION) {
            gion.put(ele.getId(), (UmlRegion) ele);
        }
    }

    public void setBute(HashMap<String, UmlAttribute> bute) {
        this.bute = bute;
    }

    public void setFace(HashMap<String, MyUmlInterface> face) {
        this.face = face;
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

    public void setCtion(HashMap<String, MyUmlinteraction> ction) {
        this.ction = ction;
    }

    public void setInnamenum(HashMap<String, Integer> innamenum) {
        this.innamenum = innamenum;
    }

    public void setIntoname(HashMap<String, MyUmlinteraction> intoname) {
        this.intoname = intoname;
    }

    public void setChine(HashMap<String, MyUmlStateMachine> chine) {
        this.chine = chine;
    }

    public void setInenamenum(HashMap<String, Integer> inenamenum) {
        this.inenamenum = inenamenum;
    }

    public void setInetoname(HashMap<String, MyUmlStateMachine> inetoname) {
        this.inetoname = inetoname;
    }
}
