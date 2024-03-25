package com.android.launcher.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class CheckBoxCarInfo {

    @Id(autoincrement = true)
    public Long id ;

    public String selected ;

    public String checkBoxName ;


    @Generated(hash = 1573874063)
    public CheckBoxCarInfo() {
    }


    @Generated(hash = 1558569317)
    public CheckBoxCarInfo(Long id, String selected, String checkBoxName) {
        this.id = id;
        this.selected = selected;
        this.checkBoxName = checkBoxName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getCheckBoxName() {
        return checkBoxName;
    }

    public void setCheckBoxName(String checkBoxName) {
        this.checkBoxName = checkBoxName;
    }

    @Override
    public String toString() {
        return "CheckBoxCarInfo{" +
                "id=" + id +
                ", selected='" + selected + '\'' +
                ", checkBoxName='" + checkBoxName + '\'' +
                '}';
    }
}
