package com.example.task;

public class Task {
    String titledoes;
    String adddate2;
    String descdoes;
    String keydoes;

    public Task() {
    }

    public Task(String titledoes, String adddate2, String descdoes, String keydoes) {
        this.titledoes = titledoes;
        this.adddate2 = adddate2;
        this.descdoes = descdoes;
        this.keydoes = keydoes;
    }

    public String getKeydoes() {
        return keydoes;
    }

    public void setKeydoes(String keydoes) {
        this.keydoes = keydoes;
    }

    public String getTitledoes() {
        return titledoes;
    }

    public void setTitledoes(String titledoes) {
        this.titledoes = titledoes;
    }

    public String getAdddate2() { return adddate2; }

    public void setAdddate2(String adddate2) { this.adddate2 = adddate2; }

    public String getDescdoes() {
        return descdoes;
    }

    public void setDescdoes(String descdoes) {
        this.descdoes = descdoes;
    }
}
