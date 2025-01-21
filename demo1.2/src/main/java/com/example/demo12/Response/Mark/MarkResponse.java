package com.example.demo12.Response.Mark;

import com.example.demo12.Model.Mark.marks;

import java.util.List;

public class MarkResponse {
    private String msg;
    private List<marks> marksList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public List<marks> getMarksList() {
        return marksList;
    }

    public void setMarksList(List<marks> marksList) {
        this.marksList = marksList;
    }


}
