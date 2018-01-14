package com.manufacturedefrance.svgen.tags;

public class LineTag extends AbstractTag {

    public LineTag() {
        super(true);
    }

    public void x1(Double value){
        this.putAtt("x1", value.toString());
    }

    public void y1(Double value){
        this.putAtt("y1", value.toString());
    }

    public void x2(Double value){
        this.putAtt("x2", value.toString());
    }

    public void y2(Double value){
        this.putAtt("y2", value.toString());
    }

    @Override
    protected String getTagName() {
        return "line";
    }
}
