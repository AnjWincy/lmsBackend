package com.example.demo12.Response.Mark;

import com.example.demo12.Model.Mark.CalendarModel;

import java.util.List;
import java.util.Map;

public class CalendarResponse {
    public CalendarResponse(String message) {
        this.message = message;
    }

    public String message;
    private Map<String,Long> res;
    private Map<String, List<CalendarModel>> res1;


    public Map<String, Long> getRes() {
        return res;
    }

    public void setRes(Map<String, Long> res) {
        this.res = res;
    }




    public Map<String, List<CalendarModel>> getRes1() {
        return res1;
    }

    public void setRes1(Map<String, List<CalendarModel>> res1) {
        this.res1 = res1;
    }



}
