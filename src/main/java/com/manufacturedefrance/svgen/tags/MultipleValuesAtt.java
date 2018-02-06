package com.manufacturedefrance.svgen.tags;

import com.manufacturedefrance.svgen.SvgComponent;

import java.util.ArrayList;

public abstract class MultipleValuesAtt<T> extends AbstractAtt {

    private ArrayList<Value> values;
    private boolean isInParentheses;
    private boolean hasSemiColon;

    protected MultipleValuesAtt(String name) {
        super(name);
        this.values = new ArrayList<>();
        this.isInParentheses = false;
        this.hasSemiColon = false;
    }

    protected void isInPrentheses(){
        this.isInParentheses = true;
    }

    protected void hasSemiColon(){
        this.hasSemiColon = true;
    }

    public void addValue(T type, Object... values){
        String typeStr = getTypeStr(type);
        if(typeStr != null)
            this.values.add(new Value(typeStr, values));
    }

    protected abstract String getTypeStr(T type);

    @Override
    public String getValue() {
        StringBuilder strBld = new StringBuilder();
        for(Value value : this.values)
            strBld.append(value.render()+" ");
        strBld.deleteCharAt(strBld.length() - 1);
        return strBld.toString();
    }

    private class Value {
        String type;
        Object[] values;

        public Value(String type, Object[] values){
            this.type = type;
            this.values = values;
        }

        public String render(){
            StringBuilder strBldValues = new StringBuilder();
            for(Object value : this.values) {
                String valueStr;
                if(value instanceof Double)
                    valueStr = SvgComponent.DOUBLE_FORMAT.format(value);
                else
                    valueStr = value.toString();
                strBldValues.append(valueStr + " ");
            }
            if(strBldValues.length() > 0)
                strBldValues.deleteCharAt(strBldValues.length() - 1);

            StringBuilder strBld = new StringBuilder();
            strBld.append(this.type);
            if(isInParentheses) {
                strBld.append("(");
                strBld.append(strBldValues);
                strBld.append(")");
            } else if(hasSemiColon) {
                strBld.append(": ");
                strBld.append(strBldValues);
                strBld.append(";");
            } else {
                strBld.append(" ");
                strBld.append(strBldValues);
            }
            return strBld.toString();
        }
    }
}
