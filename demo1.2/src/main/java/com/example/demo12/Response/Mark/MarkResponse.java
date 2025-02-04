package com.example.demo12.Response.Mark;

import com.example.demo12.Model.Mark.Marks;

import java.util.List;

public class MarkResponse {
    private String msg;
    private List<Marks> marksList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public List<Marks> getMarksList() {
        return marksList;
    }

    public void setMarksList(List<Marks> marksList) {
        this.marksList = marksList;
    }


}
