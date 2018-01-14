package com.manufacturedefrance.svgen.tags;

public class RectTag extends DimensionedTag {
    public RectTag(){
        super(true);
    }

    @Override
    public String getTagName() {
        return "rect";
    }
}
