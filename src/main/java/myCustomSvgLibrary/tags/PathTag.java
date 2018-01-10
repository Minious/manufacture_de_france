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
        ((PathDescAtt) getAtt("d")).addPathInstruction(type, values);
    }

    @Override
    protected String getTagName() {
        return "path";
    }

    private enum PathInstructionType{
        MOVETO, LINETO, MOVETORELA, LINETORELA, ARCTO, CLOSEPATH
    }

    class PathDescAtt extends AbstractAtt {
        private ArrayList<PathInstruction> pathInstructions;

        public PathDescAtt() {
            super("d");
            this.pathInstructions = new ArrayList<>();
        }

        public void addPathInstruction(PathInstructionType type, double... values){
            String pathInstructionTypeStr = null;
            switch(type){
                case MOVETO:
                    pathInstructionTypeStr = "M";
                    break;
                case LINETO:
                    pathInstructionTypeStr = "L";
                    break;
                case MOVETORELA:
                    pathInstructionTypeStr = "m";
                    break;
                case LINETORELA:
                    pathInstructionTypeStr = "l";
                    break;
                case ARCTO:
                    pathInstructionTypeStr = "A";
                    break;
                case CLOSEPATH:
                    pathInstructionTypeStr = "Z";
                    break;
            }
            if(pathInstructionTypeStr != null)
                this.pathInstructions.add(new PathInstruction(pathInstructionTypeStr, values));
        }

        @Override
        public String getValue() {
            StringBuilder strBld = new StringBuilder();
            for(PathInstruction pathInstruction : this.pathInstructions)
                strBld.append(pathInstruction.render()+" ");
            strBld.deleteCharAt(strBld.length() - 1);
            return strBld.toString();
        }

        class PathInstruction {
            String type;
            double[] values;

            public PathInstruction(String type, double[] values){
                this.type = type;
                this.values = values;
            }

            public String render(){
                StringBuilder strBld = new StringBuilder();
                strBld.append(this.type+" ");
                for(double value : this.values)
                    strBld.append(SvgComponent.DOUBLE_FORMAT.format(value)+" ");
                strBld.deleteCharAt(strBld.length() - 1);
                strBld.append(" ");
                return strBld.toString();
            }
        }
    }
}
