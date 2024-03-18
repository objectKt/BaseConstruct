package com.android.launcher.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

@Entity
public class CheckBoxEntity implements Serializable {

    public static final long serialVersionUID = 1L ;

    @Id(autoincrement = true)
    public Long id ;

    public String selected ;

    public String checkBoxName ;

    @Generated(hash = 2083221173)
    public CheckBoxEntity() {
    }

    @Generated(hash = 1517895664)
    public CheckBoxEntity(Long id, String selected, String checkBoxName) {
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
        return "CheckBoxEntity{" +
                "id=" + id +
                ", selected='" + selected + '\'' +
                ", checkBoxName='" + checkBoxName + '\'' +
                '}';
    }
}
