package com.oocourse.spec2.main;

import java.math.BigInteger;
import java.util.HashMap;

public class MyGroup implements Group {
    private int id;
    private HashMap<Person,Integer> people = new HashMap<>();
    private HashMap<Integer,Person> people1 = new HashMap<>();
    private int valuesum = 0;
    private int relationsum = 0;
    private BigInteger conflictsum = BigInteger.valueOf(0);
    private int agesum = 0;
    private int agesum2 = 0;

    public MyGroup(int id1) {
        id = id1;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Group)) { return false; }
        return ((Group) obj).getId() == id;
    }

    @Override
    public void addPerson(Person person) {
        for (Person per : people.keySet()) {
            if (person.isLinked(per)) {
                valuesum += 2 * person.queryValue(per);
                relationsum += 2;
            }
        }
        relationsum += 1;
        if (people.isEmpty()) { conflictsum = person.getCharacter(); }
        else { conflictsum = conflictsum.xor(person.getCharacter()); }
        agesum += person.getAge();
        agesum2 += person.getAge() * person.getAge();
        people.put(person,person.getId());
        people1.put(person.getId(),person);
    }

    @Override
    public boolean hasPerson(Person person) {
        return people.containsKey(person);
    }

    @Override
    public int getRelationSum() {
        return relationsum;
    }

    @Override
    public int getValueSum() {
        return valuesum;
    }

    @Override
    public BigInteger getConflictSum() {
        return conflictsum;
    }

    @Override
    public int getAgeMean() {
        int tempsize = people.size();
        if (tempsize == 0) { return 0; }
        return agesum / tempsize;
    }

    @Override
    public int getAgeVar() {
        int ans = 0;
        int tempsize = people.size();
        if (tempsize == 0) { return 0; }
        int ans1 = getAgeMean();
        ans += agesum2 - 2 * ans1 *  agesum + ans1 * ans1 * tempsize;
        return ans / tempsize;
    }

    public int getPeopleSize() {
        return people.size();
    }

    public void addRelation(int id1,int id2) {
        if (people.containsValue(id1) && people.containsValue(id2)) {
            relationsum += 2;
            valuesum += 2 * people1.get(id1).queryValue(people1.get(id2));
        }
    }
}
