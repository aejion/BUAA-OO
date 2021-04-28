package main;

import exceptions.EqualPersonIdException;
import exceptions.EqualRelationException;
import exceptions.PersonIdNotFoundException;
import exceptions.RelationNotFoundException;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class MyNetwork implements Network {
    private HashMap<Integer,Person> people = new HashMap<>();
    private HashMap<Integer,Integer> fa = new HashMap<>();
    private HashMap<Integer,Integer> size1 = new HashMap<>();

    public MyNetwork(){}

    @Override
    public boolean contains(int id) {
        if (people.containsKey(id)) { return true; }
        return false;
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
        fa.put(person.getId(),person.getId());
        size1.put(person.getId(),1);
    }

    public int find_fa(int id) {
        if (fa.get(id) == id) { return id; }
        int ffa = find_fa(fa.get(id));
        fa.put(id,ffa);
        return ffa;
    }

    public void merge(int id1,int id2){
        int fa1 = find_fa(id1);
        int fa2 = find_fa(id2);
        if (fa1==fa2) { return; }
        if (size1.get(fa1)<=size1.get(fa2)) {
            fa.put(fa1,fa2);
            int t =size1.get(fa2);
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
        if (find_fa(id1) == find_fa(id2)) {
            return true;
        }
        return false;
    }
}
