package com.example.projectdemo;

public class Field {
    int quality = HelloApplication.rand.nextInt(5)+1;
    int workToBeDone = 6-quality;

    public Field() {
    }
}
