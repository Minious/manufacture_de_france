package myCustomSvgLibrary.tags;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractTag {
    private HashMap<String, AbstractAtt> hashAttrs;
    protected ArrayList<AbstractAtt> attrs;
    private boolean isAutoClosing;
    private boolean forceRendering = false;

    public AbstractTag(boolean isAutoClosing){
        hashAttrs = new HashMap<>();
        attrs = new ArrayList<>();
        this.isAutoClosing = isAutoClosing;
    }

    protected void putAtt(String name, String value){
        Att att = new Att(name, value);
        hashAttrs.put(name, att);
        attrs.add(att);
    }

    public void rotate(double rotation){
        if(rotation != 0)
            this.transform(TransformType.ROTATION, rotation);
    }

    public void translate(double tx, double ty){
        if(tx != 0 || ty != 0)
            this.transform(TransformType.TRANSLATION, tx, ty);
    }

    private void transform(TransformType type, double... values) {
        if (!this.hashAttrs.containsKey("transform")) {
            TransformAtt transformAtt = new TransformAtt();
            hashAttrs.put(transformAtt.getName(), transformAtt);
            attrs.add(transformAtt);
        }
        ((TransformAtt) getAtt("transform")).addTransform(type, values);
    }

    public void style(String value){
        this.putAtt("style", value);
    }

    protected AbstractAtt getAtt(String name){
        return hashAttrs.get(name);
    }

    static private String tabuler(String input){
        String output = "";
        for(String line : input.split("\n"))
            output += "\t" + line + "\n";
        return output;
    }

    protected void forceRender(){
        this.forceRendering = true;
    }

    public String render() {
        return this.render(null);
    }

    public String render(String content) {
        StringBuilder strBld = new StringBuilder();

        if(this.forceRendering || this.attrs.size() > 0) {
            strBld.append("<" + this.getTagName() + " ");
            for (AbstractAtt att : this.attrs)
                strBld.append(att.getName() + "=\"" + att.getValue() + "\" ");
            strBld.deleteCharAt(strBld.length() - 1);
            if (isAutoClosing)
                strBld.append("/>");
            else {
                strBld.append(">\n");
                if(content != null)
                    strBld.append(AbstractTag.tabuler(content) + "\n");
                strBld.append("</" + this.getTagName() + ">");
            }
        } else
            if(content != null)
                strBld.append(content);

        return strBld.toString();
    }

    protected abstract String getTagName();

    private enum TransformType{
        TRANSLATION, ROTATION, MATRIX;
    }

    class TransformAtt extends AbstractAtt {
        private ArrayList<Transform> transforms;

        public TransformAtt() {
            super("transform");
            this.transforms = new ArrayList<>();
        }

        public void addTransform(TransformType type, double... values){
            String typeStr = null;
            switch(type){
                case TRANSLATION:
                    typeStr = "translate";
                    break;
                case ROTATION:
                    typeStr = "rotate";
                    break;
                case MATRIX:
                    typeStr = "matrix";
                    break;
            }
            if(typeStr != null)
                this.transforms.add(new Transform(typeStr, values));
        }

        @Override
        public String getValue() {
            StringBuilder strBld = new StringBuilder();
            for(Transform transform : this.transforms)
                strBld.append(transform.render()+" ");
            strBld.deleteCharAt(strBld.length() - 1);
            return strBld.toString();
        }

        class Transform {
            String type;
            double[] values;

            public Transform(String type, double[] values){
                this.type = type;
                this.values = values;
            }

            public String render(){
                StringBuilder strBld = new StringBuilder();
                strBld.append(this.type+"(");
                for(double value : this.values)
                    strBld.append(value+" ");
                strBld.deleteCharAt(strBld.length() - 1);
                strBld.append(")");
                return strBld.toString();
            }
        }
    }

    class Att extends AbstractAtt {
        String value;

        public Att(String name, String value){
            super(name);
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }
    }

    abstract class AbstractAtt {
        String name;

        public AbstractAtt(String name){
            this.name = name;
        }

        public String getName(){
            return this.name;
        }

        public String render(){
            return this.getName()+"=\""+this.getValue()+"\"";
        }

        abstract public String getValue();
    }
}
