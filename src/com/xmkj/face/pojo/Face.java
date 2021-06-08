package com.xmkj.face.pojo;

import java.util.Arrays;

public class Face {
    private int id;
    private String name;
    private byte[] faceEngine;

    public Face(int id, String name, byte[] faceEngine) {
        this.id = id;
        this.name = name;
        this.faceEngine = faceEngine;
    }

    public Face() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFaceEngine() {
        return faceEngine;
    }

    public void setFaceEngine(byte[] faceEngine) {
        this.faceEngine = faceEngine;
    }

    @Override
    public String toString() {
        return "Face{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", faceEngine=" + Arrays.toString(faceEngine) +
                '}';
    }
}