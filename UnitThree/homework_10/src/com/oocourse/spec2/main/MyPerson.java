package com.oocourse.spec2.main;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;

public class MyPerson implements Person {
    private int id;
    private String name;
    private BigInteger character;
    private int age;
    private HashSet<Person> acquaintance = new HashSet<>();
    private HashMap<Person,Integer> value = new HashMap<>();
    private HashMap<Integer,Integer> fa = new HashMap<>();

    public MyPerson(int id, String name, BigInteger character, int age) {
        this.id = id;
        this.name = name;
        this.character = character;
        this.age = age;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BigInteger getCharacter() {
        return character;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public boolean isLinked(Person person) {
        if (person.getId() == id) { return true; }
        return acquaintance.contains(person);
    }

    @Override
    public int queryValue(Person person) {
        if (value.containsKey(person)) {
            return value.get(person);
        }
        return 0;
    }

    @Override
    public int getAcquaintanceSum() {
        return acquaintance.size();
    }

    @Override
    public int compareTo(Person p2) {
        return name.compareTo(p2.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Person)) { return false; }
        return ((Person) o).getId() == id;
    }

    public void addAcquaintance(Person per, int value1) {
        acquaintance.add(per);
        value.put(per,value1);
    }

    public void setFa(HashMap<Integer, Integer> fa) {
        this.fa = fa;
    }

    public int find_fa(int id) {
        if (fa.get(id) == id) { return id; }
        int ffa = find_fa(fa.get(id));
        fa.put(id,ffa);
        return ffa;
    }
}
