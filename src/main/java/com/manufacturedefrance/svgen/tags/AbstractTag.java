package com.manufacturedefrance.svgen.tags;

import com.manufacturedefrance.svgen.styling.Color;
import com.manufacturedefrance.svgen.styling.Font;
import com.manufacturedefrance.svgen.styling.Stroke;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractTag {
    HashMap<String, AbstractAtt> hashAttrs;
    ArrayList<AbstractAtt> attrs;
    private boolean isAutoClosing;
    private boolean forceRendering = false;

    AbstractTag(boolean isAutoClosing){
        hashAttrs = new HashMap<>();
        attrs = new ArrayList<>();
        this.isAutoClosing = isAutoClosing;
    }

    void putAtt(String name, String value){
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

    private void transform(TransformType type, Double... values) {
        String transformTagName = "transform";
        if (!this.hashAttrs.containsKey(transformTagName)) {
            TransformAtt transformAtt = new TransformAtt();
            hashAttrs.put(transformAtt.getName(), transformAtt);
            attrs.add(transformAtt);
        }
        ((TransformAtt) getAtt(transformTagName)).addValue(type, values);
    }
/*
    public void style(String value) {
        this.putAtt("style", value);
    }*/

    public void stroke(Stroke stroke, Color strokeColor) {
        this.strokeWidth(stroke.getWidth());
        this.stroke(strokeColor);
        this.strokeLineCap(stroke.getCap());
        this.strokeLineJoin(stroke.getJoin());
        if(stroke.isDashed()) {
            this.strokeDashArray(stroke.getDashArray());
            this.strokeDashOffset(stroke.getDashOffset());
        }
    }

    public void shape(Color shapeColor) {
        this.fill(shapeColor);
    }

    public void font(Font font, Color fontColor) {
        this.fill(fontColor);
        this.fontFamily(font.getName());
        this.fontSize(font.getSize());
        this.fontWeight(font.isBold());
    }

    private void strokeWidth(double strokeWidth){
        this.style(StyleAttType.STROKE_WIDTH, strokeWidth);
    }

    private void stroke(Color color){
        this.style(StyleAttType.STROKE, color.getHex());
    }

    private void strokeLineCap(Stroke.CAP cap){
        String capStr;

        switch(cap){
            case BUTT:
                capStr = "butt";
                break;
            case ROUND:
                capStr = "round";
                break;
            case SQUARE:
                capStr = "square";
                break;
            default:
                throw new IllegalArgumentException();
        }

        this.style(StyleAttType.STROKE_LINECAP, capStr);
    }

    private void strokeLineJoin(Stroke.JOIN join){
        String joinStr;

        switch(join){
            case ROUND:
                joinStr = "round";
                break;
            case BEVEL:
                joinStr = "bevel";
                break;
            case MITER:
                joinStr = "miter";
                break;
            default:
                throw new IllegalArgumentException();
        }

        this.style(StyleAttType.STROKE_LINEJOIN, joinStr);
    }

    private void strokeDashArray(double[] dashArray){
        if(dashArray.length != 0) {
            StringBuilder dashArraySb = new StringBuilder();
            for (int i = 0; i < dashArray.length; i++) {
                dashArraySb.append(dashArray[i]);
                if (i < dashArray.length - 1)
                    dashArraySb.append(" ");
            }

            this.style(StyleAttType.STROKE_DASHARRAY, dashArraySb);
        }
    }

    private void strokeDashOffset(double dashOffset){
        if(dashOffset != 0)
            this.style(StyleAttType.STROKE_DASHOFFSET, dashOffset);
    }

    private void fill(Color color){
        if(color == null)
            this.style(StyleAttType.FILL, "none");
        else
            this.style(StyleAttType.FILL, color.getHex());
    }

    private void fontFamily(String fontFamily){
        this.style(StyleAttType.FONT_FAMILY, fontFamily);
    }

    private void fontSize(double fontSize){
        this.style(StyleAttType.FONT_SIZE, fontSize);
    }

    private void fontWeight(boolean bold){
        this.style(StyleAttType.FONT_WEIGHT, bold ? "bold" : "normal");
    }

    private void style(StyleAttType type, Object... values){
        String styleTagName = "style";
        if (!this.hashAttrs.containsKey(styleTagName)) {
            StyleAtt styleAtt = new StyleAtt();
            hashAttrs.put(styleAtt.getName(), styleAtt);
            attrs.add(styleAtt);
        }
        ((StyleAtt) getAtt(styleTagName)).addValue(type, values);
    }

    AbstractAtt getAtt(String name){
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

        TransformAtt() {
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

    enum StyleAttType{
        STROKE_WIDTH,
        STROKE,
        STROKE_LINECAP,
        STROKE_LINEJOIN,
        STROKE_DASHARRAY,
        STROKE_DASHOFFSET,
        FILL,
        FONT_FAMILY,
        FONT_SIZE,
        FONT_WEIGHT
    }

    public class StyleAtt extends MultipleValuesAtt<StyleAttType> {

        StyleAtt() {
            super("style");
            this.hasSemiColon();
        }

        protected String getTypeStr(StyleAttType type){
            switch(type){
                case STROKE_WIDTH:
                    return "stroke-width";
                case STROKE:
                    return "stroke";
                case STROKE_LINECAP:
                    return "stroke-linecap";
                case STROKE_LINEJOIN:
                    return "stroke-linejoin";
                case STROKE_DASHARRAY:
                    return "stroke-dasharray";
                case STROKE_DASHOFFSET:
                    return "stroke-dashoffset";
                case FILL:
                    return "fill";
                case FONT_FAMILY:
                    return "font-family";
                case FONT_SIZE:
                    return "font-size";
                case FONT_WEIGHT:
                    return "font-weight";
            }
            return null;
        }
    }

    private class Att extends AbstractAtt {
        String value;

        Att(String name, String value){
            super(name);
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }
    }
}
