package com.example.demo12.Response.Details;

import com.example.demo12.Model.Mark.Marks;

import java.util.List;

public class LoginResponse {

    private String msg;
    private String email;
    private String password;
    private List<String> ids;
    private List<Marks> Mark;
//    private String code;
//
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }

    public List<Marks> getMark() {
        return Mark;
    }

    public void setMark(List<Marks> mark) {
        Mark = mark;
    }

        public List<String> getIds() {
            return ids;
        }

        public void setIds(List<String> ids) {
            this.ids = ids;
        }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
