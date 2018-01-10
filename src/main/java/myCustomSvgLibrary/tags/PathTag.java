package myCustomSvgLibrary.tags;

public class PathTag extends AbstractTag {

    public PathTag() {
        super(true);
    }

    public void d(String value){
        this.putAtt("d", value);
    }

    @Override
    protected String getTagName() {
        return "path";
    }
}
