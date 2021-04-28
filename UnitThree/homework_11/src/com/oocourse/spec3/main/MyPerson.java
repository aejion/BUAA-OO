package com.oocourse.spec3.main;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class MyPerson implements Person {
    private int id;
    private String name;
    private BigInteger character;
    private int age;
    private ArrayList<Person> acquaintance = new ArrayList<>();
    private HashMap<Person,Integer> value = new HashMap<>();
    private HashMap<Integer,Integer> fa = new HashMap<>();

    public MyPerson(int id, String name, BigInteger character, int age) {
        this.id = id;
        this.name = name;
        this.character = character;
        this.age = age;
    }

    public ArrayList<Person> getAcquaintance() {
        return acquaintance;
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
        if (!(o instanceof Person)) { return false; }
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

    public int find_min_loop(MyPerson per1,MyPerson per2) {
        HashMap<MyPerson,Integer> d = new HashMap<>();
        PriorityQueue<MyData> q = new PriorityQueue<>();
        d.put(per1,0);
        MyData temp1 = new MyData(per1,0);
        q.add(temp1);
        while (!q.isEmpty()) {
            MyData temp2 = q.poll();
            //System.out.println(temp2.getMinlen()+" "+temp2.getMy().getId());
            MyPerson temp3 = temp2.getMy();
            int minlen = temp2.getMinlen();
            if (d.get(temp3) < minlen) { continue; }
            if (temp3.equals(per2)) { break; }
            ArrayList<Person> temp4 = temp3.getAcquaintance();
            for (Person person : temp4) {
                MyPerson temp5 = (MyPerson) person;
                int templen = temp3.queryValue(temp5);
                if (!d.containsKey(temp5)) {
                    d.put(temp5, templen + minlen);
                    q.add(new MyData(temp5, templen + minlen));
                } else {
                    int nowlen = d.get(temp5);
                    if (templen + minlen < nowlen) {
                        d.put(temp5, templen + minlen);
                        q.add(new MyData(temp5, templen + minlen));
                    }
                }
            }
        }
        return d.get(per2);
    }
}