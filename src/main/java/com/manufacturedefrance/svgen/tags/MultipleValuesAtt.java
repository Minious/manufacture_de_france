package com.manufacturedefrance.svgen.tags;

import com.manufacturedefrance.svgen.SvgComponent;

import java.util.ArrayList;

public abstract class MultipleValuesAtt<Type> extends AbstractAtt {

    private ArrayList<Value> values;
    private boolean isInParentheses;

    protected MultipleValuesAtt(String name) {
        super(name);
        this.values = new ArrayList<>();
        this.isInParentheses = false;
    }

    protected void isInPrentheses(){
        this.isInParentheses = true;
    }

    public void addValue(Type type, double... values){
        String typeStr = getTypeStr(type);
        if(typeStr != null)
            this.values.add(new Value(typeStr, values));
    }

    protected abstract String getTypeStr(Type type);

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
        double[] values;

        public Value(String type, double[] values){
            this.type = type;
            this.values = values;
        }

        public String render(){
            StringBuilder strBldValues = new StringBuilder();
            for(double value : this.values)
                strBldValues.append(SvgComponent.DOUBLE_FORMAT.format(value)+" ");
            if(strBldValues.length() > 0)
                strBldValues.deleteCharAt(strBldValues.length() - 1);

            StringBuilder strBld = new StringBuilder();
            strBld.append(this.type);
            if(isInParentheses) {
                strBld.append("(");
                strBld.append(strBldValues.toString());
                strBld.append(")");
            } else {
                strBld.append(" ");
                strBld.append(strBldValues.toString());
            }
            return strBld.toString();
        }
    }
}
