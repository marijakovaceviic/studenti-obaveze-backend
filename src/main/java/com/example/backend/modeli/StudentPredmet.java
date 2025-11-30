package com.example.backend.modeli;

import java.util.List;

public class StudentPredmet {
    private List<Long> izabraniPredmeti;
    private Long studentId;

     public StudentPredmet() {
    }

    public StudentPredmet(List<Long> izabraniPredmeti, Long studentId) {
        this.izabraniPredmeti = izabraniPredmeti;
        this.studentId = studentId;
    }

    public List<Long> getIzabraniPredmeti() {
        return izabraniPredmeti;
    }

    public void setIzabraniPredmeti(List<Long> izabraniPredmeti) {
        this.izabraniPredmeti = izabraniPredmeti;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
