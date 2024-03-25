package com.android.launcher.entity;

import java.math.BigDecimal;

public class Can105MessageEntity {
    BigDecimal fromd;
    BigDecimal tod;
    Long ll;
    public Can105MessageEntity(BigDecimal fromd,
                               BigDecimal tod,
                               Long ll){
        this.fromd = fromd;
        this.tod = tod;
        this.ll = ll;
    }

    public BigDecimal getFromd() {
        return fromd;
    }

    public void setFromd(BigDecimal fromd) {
        this.fromd = fromd;
    }

    public BigDecimal getTod() {
        return tod;
    }

    public void setTod(BigDecimal tod) {
        this.tod = tod;
    }

    public Long getLl() {
        return ll;
    }

    public void setLl(Long ll) {
        this.ll = ll;
    }
}
