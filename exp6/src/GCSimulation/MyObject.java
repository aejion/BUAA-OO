package GCSimulation;

public class MyObject implements Comparable<MyObject> {
    private static int totalId = 0;
    private int id;
    private boolean referenced;
    private int age;

    MyObject() {
        id = totalId;
        totalId++;
        referenced = true;
        age = 0;
    }

    public void setAge(int age) {
        this.age = age;
    }

    //@ ensures \result == age;
    public /*@pure@*/ int getAge() {
        return age;
    }

    //@ ensures \result == id;
    public /*@pure@*/int getId() {
        return id;
    }

    public void setReferenced(boolean referenced) {
        this.referenced = referenced;
    }

    //@ ensures \result == referenced;
    public /*@pure@*/ boolean getReferenced() {
        return referenced;
    }

    /*@ public normal_behavior
      @ requires object != null
      @ ensures ( (\result == -1) ==> (age < object.age) || (age == object.age && id < object.id) )
      @ ensures ( (\result ==  1) ==> (age > object.age) || (age == object.age && id >= object.id) )
      @
      @ also 
      @ public exceptional_behavior
      @ requires object == null
      @ assignable \nothing;
      @ signals (NullPointerException e) object == null;
      @*/
    public /*@pure@*/ int compareTo(MyObject object) {
        /* Your Code Here */
        if (object == null) {
            throw new NullPointerException();
        }
        if (age < object.getAge() || (age == object.getAge() && id < object.getId())) {
            return -1;
        }
        else {
            return 1;
        }
    }
}
