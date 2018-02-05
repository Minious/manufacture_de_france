package com.manufacturedefrance.svgen.styling;

public class Stroke {
    private double width;
    private CAP cap;
    private JOIN join;
    private double miterLimit;
    private boolean isDashed;
    private double[] dashArray;
    private double dashOffset;
    private double opacity;

    private static double DEFAULT_WIDTH = 1;
    private static double DEFAULT_MITER_LIMIT = 10;
    private static double DEFAULT_OPACITY = 1;

    public Stroke(Stroke clone) {
        this(clone.width, clone.cap, clone.join, clone.miterLimit, clone.dashArray, clone.dashOffset, clone.opacity);
    }

    public Stroke(double width, CAP cap, JOIN join, double miterLimit, double[] dashArray, double dashOffset, double opacity){
        this.width = width;
        this.cap = cap;
        this.join = join;
        this.miterLimit = miterLimit;
        this.setDashArray(dashArray);
        this.dashOffset = dashOffset;
        this.opacity = opacity;
    }

    public Stroke(double width, CAP cap, JOIN join, double miterLimit, double[] dash, double dashOffset){
        this(width, cap, join, miterLimit, dash, dashOffset, DEFAULT_OPACITY);
    }

    public Stroke(double width, CAP cap, JOIN join, double miterLimit, double[] dash){
        this(width, cap, join, miterLimit, dash, 0);
    }

    public Stroke(double width, CAP cap, JOIN join, double miterLimit){
        this(width, cap, join, miterLimit, new double[]{});
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
        if(miterLimit < 1)
            throw new IllegalArgumentException();
        this.miterLimit = miterLimit;
    }

    public boolean isDashed() {
        return isDashed;
    }

    public void setDashArray(double[] dashArray){
        if(dashArray.length % 2 != 0)
            throw new IllegalArgumentException();
        this.dashArray = dashArray;
        if(dashArray.length >= 2)
            this.isDashed = true;
    }

    public double[] getDashArray(){
        return this.dashArray;

    }

    public void removeDashArray(){
        this.dashArray = new double[]{};
        this.isDashed = false;
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
