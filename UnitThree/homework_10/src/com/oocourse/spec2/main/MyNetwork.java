package com.oocourse.spec2.main;

import com.oocourse.spec2.exceptions.EqualPersonIdException;
import com.oocourse.spec2.exceptions.EqualRelationException;
import com.oocourse.spec2.exceptions.PersonIdNotFoundException;
import com.oocourse.spec2.exceptions.RelationNotFoundException;
import com.oocourse.spec2.exceptions.EqualGroupIdException;
import com.oocourse.spec2.exceptions.GroupIdNotFoundException;

import java.math.BigInteger;
import java.util.HashMap;

public class MyNetwork implements Network {
    private HashMap<Integer,Person> people = new HashMap<>();
    private HashMap<Integer,Integer> fa = new HashMap<>();
    private HashMap<Integer,Integer> size1 = new HashMap<>();
    private HashMap<Integer,Group> group1 = new HashMap<>();

    public MyNetwork(){}

    @Override
    public boolean contains(int id) {
        return people.containsKey(id);
    }

    @Override
    public Person getPerson(int id) {
        if (people.containsKey(id)) { return people.get(id); }
        return null;
    }

    @Override
    public void addPerson(Person person) throws EqualPersonIdException {
        if (people.containsValue(person)) {
            throw new EqualPersonIdException();
        }
        people.put(person.getId(),person);
        ((MyPerson)person).setFa(fa);
        fa.put(person.getId(),person.getId());
        size1.put(person.getId(),1);
    }

    public void merge(int id1,int id2) {
        Person per1 = people.get(id1);
        Person per2 = people.get(id2);
        int fa1 = ((MyPerson)per1).find_fa(id1);
        int fa2 = ((MyPerson)per2).find_fa(id2);
        if (fa1 == fa2) { return; }
        if (size1.get(fa1) <= size1.get(fa2)) {
            fa.put(fa1,fa2);
            int t = size1.get(fa2);
            if (size1.get(fa1) == t) {
                size1.put(fa2, t + 1);
            }
        }
        else {
            fa.put(fa2,fa1);
        }
    }

    @Override
    public void addRelation(int id1, int id2, int value) throws PersonIdNotFoundException,
            EqualRelationException {
        if (!contains(id1) || !contains(id2)) { throw new PersonIdNotFoundException(); }
        if (id1 == id2) { return; }
        if (getPerson(id1).isLinked(getPerson(id2))) {
            throw new EqualRelationException();
        }
        ((MyPerson)getPerson(id1)).addAcquaintance(getPerson(id2),value);
        ((MyPerson)getPerson(id2)).addAcquaintance(getPerson(id1),value);
        merge(id1,id2);
        for (int g : group1.keySet()) {
            Group temp = group1.get(g);
            ((MyGroup)temp).addRelation(id1,id2);
        }
    }

    @Override
    public int queryValue(int id1, int id2) throws PersonIdNotFoundException,
            RelationNotFoundException {
        if (!contains(id1) || !contains(id2)) { throw new PersonIdNotFoundException(); }
        if (!getPerson(id1).isLinked(getPerson(id2))) { throw new RelationNotFoundException(); }
        return getPerson(id1).queryValue(getPerson(id2));
    }

    @Override
    public BigInteger queryConflict(int id1, int id2) throws PersonIdNotFoundException {
        if (!contains(id1) || !contains(id2)) { throw new PersonIdNotFoundException(); }
        return getPerson(id1).getCharacter().xor(getPerson(id2).getCharacter());
    }

    @Override
    public int queryAcquaintanceSum(int id) throws PersonIdNotFoundException {
        if (!contains(id)) { throw new PersonIdNotFoundException(); }
        return getPerson(id).getAcquaintanceSum();
    }

    @Override
    public int compareAge(int id1, int id2) throws PersonIdNotFoundException {
        if (!contains(id1) || !contains(id2)) { throw new PersonIdNotFoundException(); }
        return getPerson(id1).getAge() - getPerson(id2).getAge();
    }

    @Override
    public int compareName(int id1, int id2) throws PersonIdNotFoundException {
        if (!contains(id1) || !contains(id2)) { throw new PersonIdNotFoundException(); }
        return getPerson(id1).getName().compareTo(getPerson(id2).getName());
    }

    @Override
    public int queryPeopleSum() {
        return people.size();
    }

    @Override
    public int queryNameRank(int id) throws PersonIdNotFoundException {
        if (!contains(id)) { throw new PersonIdNotFoundException(); }
        int flag = 1;
        for (int idi : people.keySet()) {
            if (compareName(id,idi) > 0) {
                flag++;
            }
        }
        return flag;
    }

    @Override
    public boolean isCircle(int id1, int id2) throws PersonIdNotFoundException {
        if (!contains(id1) || !contains(id2)) { throw new PersonIdNotFoundException(); }
        if (id1 == id2) { return true; }
        Person per1 = people.get(id1);
        Person per2 = people.get(id2);
        int fa1 = ((MyPerson)per1).find_fa(id1);
        int fa2 = ((MyPerson)per2).find_fa(id2);
        return fa1 == fa2;
    }

    @Override
    public void addGroup(Group group) throws EqualGroupIdException {
        int id = group.getId();
        if (group1.containsKey(id)) {
            throw new EqualGroupIdException();
        }
        group1.put(id,group);
    }

    @Override
    public Group getGroup(int id) {
        return group1.get(id);
    }

    @Override
    public void addtoGroup(int id1, int id2) throws GroupIdNotFoundException,
            PersonIdNotFoundException, EqualPersonIdException {
        if (!group1.containsKey(id2)) {
            throw new GroupIdNotFoundException();
        }
        if (!people.containsKey(id1)) {
            throw new PersonIdNotFoundException();
        }
        if (getGroup(id2).hasPerson(getPerson(id1))) {
            throw new EqualPersonIdException();
        }
        if (((MyGroup)getGroup(id2)).getPeopleSize() < 1111) {
            getGroup(id2).addPerson(getPerson(id1));
        }
    }

    @Override
    public int queryGroupSum() {
        return group1.size();
    }

    @Override
    public int queryGroupPeopleSum(int id) throws GroupIdNotFoundException {
        if (!group1.containsKey(id)) {
            throw new GroupIdNotFoundException();
        }
        return ((MyGroup)getGroup(id)).getPeopleSize();
    }

    @Override
    public int queryGroupRelationSum(int id) throws GroupIdNotFoundException {
        if (!group1.containsKey(id)) {
            throw new GroupIdNotFoundException();
        }
        return group1.get(id).getRelationSum();
    }

    @Override
    public int queryGroupValueSum(int id) throws GroupIdNotFoundException {
        if (!group1.containsKey(id)) {
            throw new GroupIdNotFoundException();
        }
        return group1.get(id).getValueSum();
    }

    @Override
    public BigInteger queryGroupConflictSum(int id) throws GroupIdNotFoundException {
        if (!group1.containsKey(id)) {
            throw new GroupIdNotFoundException();
        }
        return group1.get(id).getConflictSum();
    }

    @Override
    public int queryGroupAgeVar(int id) throws GroupIdNotFoundException {
        if (!group1.containsKey(id)) {
            throw new GroupIdNotFoundException();
        }
        return group1.get(id).getAgeVar();
    }

    @Override
    public int queryGroupAgeMean(int id) throws GroupIdNotFoundException {
        if (!group1.containsKey(id)) {
            throw new GroupIdNotFoundException();
        }
        return group1.get(id).getAgeMean();
    }
}
