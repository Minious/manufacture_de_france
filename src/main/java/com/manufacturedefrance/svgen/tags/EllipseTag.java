package com.manufacturedefrance.svgen.tags;

public class EllipseTag extends AbstractTag {

    public EllipseTag() {
        super(true);
    }

    public void cx(Double value){
        this.putAtt("cx", value.toString());
    }

    public void cy(Double value){
        this.putAtt("cy", value.toString());
    }

    public void rx(Double value){
        this.putAtt("rx", value.toString());
    }

    public void ry(Double value){
        this.putAtt("ry", value.toString());
    }

    @Override
    protected String getTagName() {
        return "ellipse";
    }
}
