package myCustomSvgLibrary.tags;

public class TextTag extends PositionnedTag {

    public TextTag(){
        super(false);
    }

    @Override
    public String getTagName() {
        return "text";
    }
}
