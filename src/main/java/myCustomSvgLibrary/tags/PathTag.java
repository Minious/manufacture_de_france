package myCustomSvgLibrary.tags;

import myCustomSvgLibrary.SvgComponent;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class PathTag extends AbstractTag {

    public PathTag() {
        super(true);
    }

    public void d(String value){
        this.putAtt("d", value);
    }

    public void moveto(double x, double y){
        this.addPathInstruction(PathInstructionType.MOVETO, x, y);
    }

    public void lineto(double x, double y){
        if(x != 0 || y != 0)
            this.addPathInstruction(PathInstructionType.LINETO, x, y);
    }

    public void arcto(double width, double height, boolean largeArcFlag, boolean sweepFlag, double xEnd, double yEnd){
        this.addPathInstruction(PathInstructionType.ARCTO, width, height, 0, largeArcFlag ? 1 : 0, sweepFlag ? 1 : 0, xEnd, yEnd);
    }

    public void closePath(){
        this.addPathInstruction(PathInstructionType.CLOSEPATH);
    }

    private void addPathInstruction(PathInstructionType type, double... values) {
        if (!this.hashAttrs.containsKey("d")) {
            PathDescAtt pathDescAtt = new PathDescAtt();
            hashAttrs.put(pathDescAtt.getName(), pathDescAtt);
            attrs.add(pathDescAtt);
        }
        ((PathDescAtt) getAtt("d")).addValue(type, values);
    }

    @Override
    protected String getTagName() {
        return "path";
    }

    private enum PathInstructionType{
        MOVETO, LINETO, MOVETORELA, LINETORELA, ARCTO, CLOSEPATH
    }

    class PathDescAtt extends MultipleValuesAtt<PathInstructionType> {

        public PathDescAtt() {
            super("d");
        }

        @Override
        protected String getTypeStr(PathInstructionType type) {
            switch(type){
                case MOVETO:
                    return "M";
                case LINETO:
                    return "L";
                case MOVETORELA:
                    return "m";
                case LINETORELA:
                    return "l";
                case ARCTO:
                    return "A";
                case CLOSEPATH:
                    return "Z";
            }
            return null;
        }
    }
}
