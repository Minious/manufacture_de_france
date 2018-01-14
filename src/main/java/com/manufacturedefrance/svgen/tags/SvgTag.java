package com.manufacturedefrance.svgen.tags;

public class SvgTag extends DimensionedTag {
    private static final String XMLNS = "http://www.w3.org/2000/svg";

    public SvgTag(){
        super(false);
        this.xmlns();
    }

    private void xmlns(){
        this.putAtt("xmlns", SvgTag.XMLNS);
    }

    @Override
    public String getTagName() {
        return "svg";
    }
}
