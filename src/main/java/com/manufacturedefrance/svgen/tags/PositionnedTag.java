package com.manufacturedefrance.svgen.tags;

public abstract class PositionnedTag extends AbstractTag {

    public PositionnedTag(boolean isAutoClosing) {
        super(isAutoClosing);
    }

    public void x(Double value){
        this.putAtt("x", value.toString());
    }

    public void y(Double value){
        this.putAtt("y", value.toString());
    }
}
