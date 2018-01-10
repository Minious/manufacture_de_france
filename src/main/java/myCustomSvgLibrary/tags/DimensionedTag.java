package myCustomSvgLibrary.tags;

public abstract class DimensionedTag extends PositionnedTag {

    public DimensionedTag(boolean isAutoClosing) {
        super(isAutoClosing);
    }

    public void width(Double value){
        this.putAtt("width", value.toString());
    }

    public void height(Double value){
        this.putAtt("height", value.toString());
    }
}
