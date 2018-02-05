package com.manufacturedefrance.svgen.styling;

public class Stroke {
    private double width;
    private CAP cap;
    private JOIN join;
    private double miterLimit;
    private double[] dashArray;
    private double dashOffset;

    private static double DEFAULT_WIDTH = 1;
    private static double DEFAULT_MITER_LIMIT = 10;

    public Stroke(Stroke clone) {
        this(clone.width, clone.cap, clone.join, clone.miterLimit, clone.dashArray, clone.dashOffset);
    }

    public Stroke(double width, CAP cap, JOIN join, double miterLimit, double[] dashArray, double dashOffset){
        this.width = width;
        this.cap = cap;
        this.join = join;
        this.miterLimit = miterLimit;
        this.dashArray = dashArray;
        this.dashOffset = dashOffset;
    }

    public Stroke(double width, CAP cap, JOIN join, double miterLimit, double[] dash){
        this(width, cap, join, miterLimit, dash, 0);
    }

    public Stroke(double width, CAP cap, JOIN join, double miterLimit){
        this(width, cap, join, miterLimit, null);
    }

    public Stroke(double width, CAP cap, JOIN join){
        this(width, cap, join, DEFAULT_MITER_LIMIT);
    }

    public Stroke(double width, CAP cap){
        this(width, cap, JOIN.MITER);
    }

    public Stroke(double width){
        this(width, CAP.SQUARE);
    }

    public Stroke(){
        this(DEFAULT_WIDTH);
    }

    public double getWidth(){
        return this.width;
    }

    public void setWidth(double width){
        this.width = width;
    }

    public CAP getCap() {
        return cap;
    }

    public void setCap(CAP cap) {
        this.cap = cap;
    }

    public JOIN getJoin() {
        return join;
    }

    public void setJoin(JOIN join) {
        this.join = join;
    }

    public double getMiterLimit() {
        return miterLimit;
    }

    public void setMiterLimit(double miterLimit) {
        this.miterLimit = miterLimit;
    }

    public void setDashArray(double[] dashArray){
        this.dashArray = dashArray;
    }

    public double[] getDashArray(){
        return this.dashArray;
    }

    public void removeDashArray(){
        this.dashArray = null;
    }

    public double getDashOffset() {
        return dashOffset;
    }

    public void setDashOffset(double dashOffset) {
        this.dashOffset = dashOffset;
    }

    public enum CAP { BUTT, ROUND, SQUARE }

    public enum JOIN { ROUND, BEVEL, MITER }
}
