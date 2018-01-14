package com.manufacturedefrance.svgen.tags;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractTag {
    protected HashMap<String, AbstractAtt> hashAttrs;
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
        ((TransformAtt) getAtt("transform")).addValue(type, values);
    }

    public void style(String value){
        this.putAtt("style", value);
    }

    protected AbstractAtt getAtt(String name){
        return hashAttrs.get(name);
    }

    private static String tabuler(String input){
        StringBuilder output = new StringBuilder();
        for(String line : input.split("\n"))
            output.append("\t" + line + "\n");
        return output.toString();
    }

    protected void forceRender(){
        this.forceRendering = true;
    }

    public String render() {
        return this.render(null);
    }

    public String render(String content) {
        StringBuilder strBld = new StringBuilder();

        if(this.forceRendering || !this.attrs.isEmpty()) {
            strBld.append("<" + this.getTagName() + " ");
            for (AbstractAtt att : this.attrs)
                strBld.append(att.getName() + "=\"" + att.getValue() + "\" ");
            strBld.deleteCharAt(strBld.length() - 1);
            if (this.isAutoClosing)
                strBld.append("/>");
            else {
                strBld.append(">\n");
                if(content != null)
                    strBld.append(AbstractTag.tabuler(content));
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

    class TransformAtt extends MultipleValuesAtt<TransformType> {

        public TransformAtt() {
            super("transform");
            this.isInPrentheses();
        }

        @Override
        protected String getTypeStr(TransformType type) {
            switch(type){
                case TRANSLATION:
                    return "translate";
                case ROTATION:
                    return "rotate";
                case MATRIX:
                    return "matrix";
            }
            return null;
        }
    }

    private class Att extends AbstractAtt {
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
}
