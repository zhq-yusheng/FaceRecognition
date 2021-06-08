package com.xmkj.face.pojo;

public class Person {
    private int id;
    private int code;

    public Person(int currentNumber, int i) {
        this.id = currentNumber;
        this.code = i;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", code=" + code +
                '}';
    }
}
