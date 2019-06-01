package com.example.shrijhajhra.careergo;

import java.util.List;

/**
 * Created by shri jhajhra on 13/03/2018.
 */

public class test_item {
    private String question;
    private String op1,op2,op3;
    public List<String> ans_list;
    public List<String> catg_option;

    public String toString() {
        return question;
    }

    public String getQuestion() {
        return question;
    }

    public String getOp1() {
        return op1;
    }

    public void setOp1(String op1) {
        this.op1 = op1;
    }

    public String getOp2() {
        return op2;
    }

    public void setOp2(String op2) {
        this.op2 = op2;
    }

    public String getOp3() {
        return op3;
    }

    public void setOp3(String op3) {
        this.op3 = op3;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getAns_list() {
        return ans_list.size();
    }

    public String getans_atIndex(int i) {
        return ans_list.get(i);
    }

    public void setAns_list(List<String> ans_list) {
        this.ans_list = ans_list;
    }


    public test_item() {
    }

    public void setcategory_option(List<String> category_option) {
        this.catg_option = category_option;
    }

    public String getCatg_atIndex(int i) {
        return catg_option.get(i);
    }
}



