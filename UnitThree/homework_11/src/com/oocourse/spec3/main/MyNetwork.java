package com.oocourse.spec3.main;

import com.oocourse.spec3.exceptions.EqualPersonIdException;
import com.oocourse.spec3.exceptions.EqualRelationException;
import com.oocourse.spec3.exceptions.PersonIdNotFoundException;
import com.oocourse.spec3.exceptions.RelationNotFoundException;
import com.oocourse.spec3.exceptions.EqualGroupIdException;
import com.oocourse.spec3.exceptions.GroupIdNotFoundException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class MyNetwork implements Network {
    private HashMap<Integer,Person> people = new HashMap<>();
    private ArrayList<Person> peopleset = new ArrayList<>();
    private HashMap<Integer,Integer> fa = new HashMap<>();
    private HashMap<Integer,Integer> size1 = new HashMap<>();
    private HashMap<Integer,Group> group1 = new HashMap<>();
    private HashMap<Integer,Integer> money = new HashMap<>();
    private ArrayList<ArrayList<Integer>> bcc;
    private int numofbcc = 0;
    private int num = 0;
    private HashMap<Integer,Integer> pre = new HashMap<>();
    private int id1fa = 0;
    private Stack<MyPair> ss = new Stack<>();
    private HashMap<Integer,Integer> bccno = new HashMap<>();

    public MyNetwork() {
        bcc = new ArrayList<>();
        for (int i = 0; i < 3010; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            bcc.add(temp);
        }
    }

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
        peopleset.add(person);
        money.put(person.getId(),0);
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

    @Override
    public void delFromGroup(int id1, int id2) throws GroupIdNotFoundException,
            PersonIdNotFoundException, EqualPersonIdException {
        if (!group1.containsKey(id2)) {
            throw new GroupIdNotFoundException();
        }
        if (!people.containsKey(id1)) {
            throw new PersonIdNotFoundException();
        }
        if (!getGroup(id2).hasPerson(getPerson(id1))) {
            throw new EqualPersonIdException();
        }
        getGroup(id2).delPerson(getPerson(id1));
    }

    @Override
    public int queryMinPath(int id1, int id2) throws PersonIdNotFoundException {
        if (!people.containsKey(id1) || !people.containsKey(id2)) {
            throw new PersonIdNotFoundException();
        }
        if (id1 == id2) { return 0; }
        if (!isCircle(id1,id2)) { return -1; }
        return ((MyPerson)people.get(id1)).find_min_loop((MyPerson)people.get(id1),
                (MyPerson)people.get(id2));
    }

    int dfs(int id1,int id2) {
        int lowu = ++num;
        pre.put(id1,num);
        Person now = people.get(id1);
        ArrayList<Person> nowlink = ((MyPerson)now).getAcquaintance();
        for (Person ver : nowlink) {
            MyPair e = new MyPair(id1, ver.getId());
            if (!pre.containsKey(ver.getId())) {
                ss.push(e);
                int lowv = dfs(ver.getId(), id1);
                lowu = Math.min(lowu, lowv);
                if (lowv >= pre.get(id1)) {
                    ArrayList<Integer> tempbcc = new ArrayList<>();
                    while (true) {
                        MyPair tempp = ss.pop();
                        if (!bccno.containsKey(tempp.getId1())) {
                            tempbcc.add(tempp.getId1());
                            bccno.put(tempp.getId1(), numofbcc);
                        } else if (bccno.get(tempp.getId1()) != numofbcc) {
                            tempbcc.add(tempp.getId1());
                            bccno.put(tempp.getId1(), numofbcc);
                        }
                        if (!bccno.containsKey(tempp.getId2())) {
                            tempbcc.add(tempp.getId2());
                            bccno.put(tempp.getId2(), numofbcc);
                        } else if (bccno.get(tempp.getId2()) != numofbcc) {
                            tempbcc.add(tempp.getId2());
                            bccno.put(tempp.getId2(), numofbcc);
                        }
                        if (tempp.getId1() == id1 && tempp.getId2() == ver.getId()) {
                            break;
                        }
                    }
                    bcc.set(numofbcc, tempbcc);
                    numofbcc++;
                }
            } else if (pre.get(ver.getId()) < pre.get(id1) && ver.getId() != id2) {
                ss.push(e);
                lowu = Math.min(lowu, pre.get(ver.getId()));
            }
        }
        return lowu;
    }

    @Override
    public boolean queryStrongLinked(int id1, int id2) throws PersonIdNotFoundException {
        if (!people.containsKey(id1) || !people.containsKey(id2)) {
            throw new PersonIdNotFoundException();
        }
        if (id1 == id2) { return true; }
        if (!isCircle(id1,id2)) { return false; }
        numofbcc = 0;
        num = 0;
        pre.clear();
        ss.clear();
        bccno.clear();
        for (int i = 0; i < 3010; i++) {
            if (!people.containsKey(i)) {
                id1fa = i;
                break;
            }
        }
        dfs(id1,id1fa);
        for (int i = 0; i < numofbcc; i++) {
            ArrayList<Integer> temp = bcc.get(i);
            //System.out.println(temp);
            if (temp.contains(id1) && temp.contains(id2) && temp.size() > 2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int queryAgeSum(int l, int r) {
        int ans = 0;
        for (Person per : peopleset) {
            if (per.getAge() >= l && per.getAge() <= r) {
                ans++;
            }
        }
        return ans;
    }

    @Override
    public int queryBlockSum() {
        HashMap<Integer,Integer> all = new HashMap<>();
        for (Person per : peopleset) {
            int tempfa = ((MyPerson)per).find_fa(per.getId());
            if (!all.containsKey(tempfa)) {
                all.put(tempfa,1);
            }
        }
        return all.size();
    }

    @Override
    public int queryMoney(int id) throws PersonIdNotFoundException {
        if (!people.containsKey(id)) {
            throw new PersonIdNotFoundException();
        }
        return money.get(id);
    }

    @Override
    public void borrowFrom(int id1, int id2, int value) throws PersonIdNotFoundException,
            EqualPersonIdException {
        if (!people.containsKey(id1) || !people.containsKey(id2)) {
            throw new PersonIdNotFoundException();
        }
        if (id1 == id2) {
            throw new EqualPersonIdException();
        }
        int temp1 = money.get(id1) - value;
        money.put(id1,temp1);
        temp1 = money.get(id2) + value;
        money.put(id2,temp1);
    }
}

