package com.oocourse.uml2;

import com.oocourse.uml2.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.ConflictQueryTypeException;
import com.oocourse.uml2.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.InteractionNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.InteractionDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.LifelineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.LifelineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.StateDuplicatedException;
import com.oocourse.uml2.interact.format.UmlGeneralInteraction;
import com.oocourse.uml2.interact.common.AttributeClassInformation;
import com.oocourse.uml2.interact.common.AttributeQueryType;
import com.oocourse.uml2.interact.common.OperationQueryType;
import com.oocourse.uml2.models.common.Visibility;
import com.oocourse.uml2.models.elements.UmlAttribute;
import com.oocourse.uml2.models.elements.UmlParameter;
import com.oocourse.uml2.models.elements.UmlElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyUmlGeneralInteraction implements UmlGeneralInteraction {
    private HashMap<String, MyUmlClass> toid = new HashMap<>();
    private HashMap<String, MyUmlOperation> tion = new HashMap<>();
    private HashMap<String, UmlAttribute> bute = new HashMap<>();
    private HashMap<String, UmlParameter> ter = new HashMap<>();
    private HashMap<String, MyUmlInterface> face = new HashMap<>();
    private HashMap<String, MyUmlClass> toname = new HashMap<>();
    private HashMap<String, Integer> namenum = new HashMap<>();
    private HashMap<String, MyUmlinteraction> ction = new HashMap<>();
    private HashMap<String, MyUmlinteraction> intoname = new HashMap<>();
    private HashMap<String, Integer> innamenum = new HashMap<>();
    private HashMap<String, MyUmlStateMachine> chine = new HashMap<>();
    private HashMap<String, MyUmlStateMachine> inetoname = new HashMap<>();
    private HashMap<String, Integer> inenamenum = new HashMap<>();
    private MyHandler handler = new MyHandler();

    private MyInitClass my = new MyInitClass();

    public MyUmlGeneralInteraction(UmlElement[] elements) {
        my.setBute(bute);
        my.setFace(face);
        my.setNamenum(namenum);
        my.setTer(ter);
        my.setTion(tion);
        my.setToid(toid);
        my.setToname(toname);
        my.setCtion(ction);
        my.setInnamenum(innamenum);
        my.setIntoname(intoname);
        my.setInenamenum(inenamenum);
        my.setChine(chine);
        my.setInetoname(inetoname);
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
        } else if (namenum.get(className) > 1) {
            throw new ClassDuplicatedException(className);
        } else if (handler.conflict(queryTypes)) {
            throw new ConflictQueryTypeException();
        }
        MyUmlClass temp = toname.get(className);
        ArrayList<String> temp1 = temp.getOpid();
        int ans = 0;
        for (String s : temp1) {
            if (tion.get(s).canfit(queryTypes)) { ans++; }
        }
        return ans;
    }

    @Override
    public int getClassAttributeCount(String className, AttributeQueryType queryType) throws
            ClassNotFoundException, ClassDuplicatedException {
        if (!toname.containsKey(className)) {
            throw new ClassNotFoundException(className);
        } else if (namenum.get(className) > 1) {
            throw new ClassDuplicatedException(className);
        }
        MyUmlClass temp = toname.get(className);
        if (queryType == AttributeQueryType.SELF_ONLY) {
            return temp.getSelfattr();
        } else if (queryType == AttributeQueryType.ALL) {
            return handler.queryAllAttr(temp);
        }
        return 0;
    }

    @Override
    public int getClassAssociationCount(String className) throws
            ClassNotFoundException, ClassDuplicatedException {
        if (!toname.containsKey(className)) {
            throw new ClassNotFoundException(className);
        } else if (namenum.get(className) > 1) {
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
        } else if (namenum.get(className) > 1) {
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
        } else if (namenum.get(className) > 1) {
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
                ans.put(temp2, temp4 + 1);
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
        } else if (namenum.get(className) > 1) {
            throw new ClassDuplicatedException(className);
        }
        MyUmlClass temp = toname.get(className);
        int num = handler.queryHasSame(temp, attributeName);
        if (num == 0) {
            throw new AttributeNotFoundException(className, attributeName);
        }
        if (num > 1) {
            throw new AttributeDuplicatedException(className, attributeName);
        }
        return handler.queryVi(temp, attributeName);
    }

    @Override
    public String getTopParentClass(String className) throws
            ClassNotFoundException, ClassDuplicatedException {
        if (!toname.containsKey(className)) {
            throw new ClassNotFoundException(className);
        } else if (namenum.get(className) > 1) {
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
        } else if (namenum.get(className) > 1) {
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
        } else if (namenum.get(className) > 1) {
            throw new ClassDuplicatedException(className);
        }
        MyUmlClass temp = toname.get(className);
        List<String> temp1 = handler.queryHidden(temp);
        List<AttributeClassInformation> ans = new ArrayList<>();
        for (String ss : temp1) {
            String name = bute.get(ss).getName();
            String classna = toid.get(bute.get(ss).getParentId()).getName();
            AttributeClassInformation temp3 = new AttributeClassInformation(name, classna);
            ans.add(temp3);
        }
        return ans;
    }

    @Override
    public int getParticipantCount(String interactionName) throws
            InteractionNotFoundException, InteractionDuplicatedException {
        if (!intoname.containsKey(interactionName)) {
            throw new InteractionNotFoundException(interactionName);
        } else if (innamenum.get(interactionName) > 1) {
            throw new InteractionDuplicatedException(interactionName);
        }
        return intoname.get(interactionName).getallife();
    }

    @Override
    public int getMessageCount(String interactionName) throws
            InteractionNotFoundException, InteractionDuplicatedException {
        if (!intoname.containsKey(interactionName)) {
            throw new InteractionNotFoundException(interactionName);
        } else if (innamenum.get(interactionName) > 1) {
            throw new InteractionDuplicatedException(interactionName);
        }
        return intoname.get(interactionName).getallmess();
    }

    @Override
    public int getIncomingMessageCount(String interactionName, String lifelineName) throws
            InteractionNotFoundException, InteractionDuplicatedException,
            LifelineNotFoundException, LifelineDuplicatedException {
        if (!intoname.containsKey(interactionName)) {
            throw new InteractionNotFoundException(interactionName);
        } else if (innamenum.get(interactionName) > 1) {
            throw new InteractionDuplicatedException(interactionName);
        }
        MyUmlinteraction temp = intoname.get(interactionName);
        if (temp.find(lifelineName) == 0) {
            throw new LifelineNotFoundException(interactionName, lifelineName);
        } else if (temp.find(lifelineName) > 1) {
            throw new LifelineDuplicatedException(interactionName, lifelineName);
        }
        return temp.getnum(lifelineName);
    }

    @Override
    public int getStateCount(String stateMachineName) throws
            StateMachineNotFoundException, StateMachineDuplicatedException {
        if (!inetoname.containsKey(stateMachineName)) {
            throw new StateMachineNotFoundException(stateMachineName);
        } else if (inenamenum.get(stateMachineName) > 1) {
            throw new StateMachineDuplicatedException(stateMachineName);
        }
        return inetoname.get(stateMachineName).countstate();
    }

    @Override
    public int getTransitionCount(String stateMachineName) throws
            StateMachineNotFoundException, StateMachineDuplicatedException {
        if (!inetoname.containsKey(stateMachineName)) {
            throw new StateMachineNotFoundException(stateMachineName);
        } else if (inenamenum.get(stateMachineName) > 1) {
            throw new StateMachineDuplicatedException(stateMachineName);
        }
        return inetoname.get(stateMachineName).getNumtran();
    }

    @Override
    public int getSubsequentStateCount(String stateMachineName, String stateName) throws
            StateMachineNotFoundException, StateMachineDuplicatedException,
            StateNotFoundException, StateDuplicatedException {
        if (!inetoname.containsKey(stateMachineName)) {
            throw new StateMachineNotFoundException(stateMachineName);
        } else if (inenamenum.get(stateMachineName) > 1) {
            throw new StateMachineDuplicatedException(stateMachineName);
        }
        MyUmlStateMachine temp = inetoname.get(stateMachineName);
        if (temp.find(stateName) == 0) {
            throw new StateNotFoundException(stateMachineName,stateName);
        } else if (temp.find(stateName) > 1) {
            throw new StateDuplicatedException(stateMachineName,stateName);
        }
        return temp.findcanget(stateName);
    }
}