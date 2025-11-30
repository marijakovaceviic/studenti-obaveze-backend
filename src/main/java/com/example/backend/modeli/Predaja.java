package com.example.backend.modeli;

import java.time.LocalDateTime;

public class Predaja {
    private Long studentId;
    private String putanja;
    private String nazivPredmeta;
    private String nazivObaveze;
    private LocalDateTime rok;
    private Long obavezaId;

    public Predaja() {
    }

    public Predaja(Long studentId, String putanja, String nazivPredmeta, String nazivObaveze, LocalDateTime rok) {
        this.studentId = studentId;
        this.putanja = putanja;
        this.nazivPredmeta = nazivPredmeta;
        this.nazivObaveze = nazivObaveze;
        this.rok = rok;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getPutanja() {
        return putanja;
    }

    public void setPutanja(String putanja) {
        this.putanja = putanja;
    }

    public String getNazivPredmeta() {
        return nazivPredmeta;
    }

    public void setNazivPredmeta(String nazivPredmeta) {
        this.nazivPredmeta = nazivPredmeta;
    }

    public String getNazivObaveze() {
        return nazivObaveze;
    }

    public void setNazivObaveze(String nazivObaveze) {
        this.nazivObaveze = nazivObaveze;
    }

    public LocalDateTime getRok() {
        return rok;
    }

    public void setRok(LocalDateTime rok) {
        this.rok = rok;
    }

    public Long getObavezaId() {
        return obavezaId;
    }

    public void setObavezaId(Long obavezaId) {
        this.obavezaId = obavezaId;
    }
}
