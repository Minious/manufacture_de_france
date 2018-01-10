package myCustomSvgLibrary.tags;

public class GTag extends AbstractTag {

    public GTag() {
        super(false);
    }

    @Override
    protected String getTagName() {
        return "g";
    }
}
