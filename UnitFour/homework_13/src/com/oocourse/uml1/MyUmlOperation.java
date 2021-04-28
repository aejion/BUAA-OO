package com.oocourse.uml1;

import com.oocourse.uml1.interact.common.OperationQueryType;
import com.oocourse.uml1.models.common.Direction;
import com.oocourse.uml1.models.common.Visibility;
import com.oocourse.uml1.models.elements.UmlOperation;
import com.oocourse.uml1.models.elements.UmlParameter;

public class MyUmlOperation {
    private UmlOperation umlele;
    private int allele = 0;
    private int hasreturn = 0;
    private int para = 0;

    MyUmlOperation(UmlOperation op) {
        umlele = op;
    }

    public void addparameter(UmlParameter t) {
        if (t.getDirection() == Direction.IN) {
            para++;
        }
        if (t.getDirection() == Direction.RETURN) {
            hasreturn = 1;
        }
    }

    public Boolean canfit(OperationQueryType[] queryTypes) {
        Boolean flag = true;
        for (OperationQueryType t : queryTypes) {
            if (t == OperationQueryType.NON_PARAM) {
                if (para != 0) {
                    flag = false;
                    break;
                }
            } else if (t == OperationQueryType.PARAM) {
                if (para == 0) {
                    flag = false;
                    break;
                }
            } else if (t == OperationQueryType.RETURN) {
                if (hasreturn != 1) {
                    flag = false;
                    break;
                }
            } else if (t == OperationQueryType.NON_RETURN) {
                if (hasreturn != 0) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    public String getName() {
        return umlele.getName();
    }

    public Visibility getVi() {
        return umlele.getVisibility();
    }
}
