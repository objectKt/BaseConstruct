package com.android.launcher.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Param {

    @Id
    public Long id ;

    public String paramName ;

    public String paramValue ;

    @Generated(hash = 2002329870)
    public Param() {
    }

    @Generated(hash = 818781200)
    public Param(Long id, String paramName, String paramValue) {
        this.id = id;
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public String toString() {
        return "Param{" +
                "id=" + id +
                ", paramName='" + paramName + '\'' +
                ", paramValue='" + paramValue + '\'' +
                '}';
    }
}
