package com.example.demo12.Model.Mark;

import jakarta.persistence.*;

@Entity
@Table(name="marks")
public class marks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mark_id;

    private Long mark;
    private String subject;
    private String edit_date;
    private String stdent_id;
    private String tran_id;

    public Long getMark_id() {
        return mark_id;
    }

    public void setMark_id(Long mark_id) {
        this.mark_id = mark_id;
    }

    public Long getMark() {
        return mark;
    }

    public void setMark(Long mark) {
        this.mark = mark;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEdit_date() {
        return edit_date;
    }

    public void setEdit_date(String edit_date) {
        this.edit_date = edit_date;
    }

    public String getStdent_id() {
        return stdent_id;
    }

    public void setStdent_id(String stdent_id) {
        this.stdent_id = stdent_id;
    }



    public String getTran_id() {
        return tran_id;
    }

    public void setTran_id(String tran_id) {
        this.tran_id = tran_id;
    }


}
