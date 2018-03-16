package com.study.beans;

/**
 * Created by 苏晓伟 on 2018/3/14.
 */

public class MTagEntity {

    private String text;
    private boolean isChecked;

    public MTagEntity(String text, boolean isChecked) {
        this.text = text;
        this.isChecked = isChecked;
    }

    public MTagEntity() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
