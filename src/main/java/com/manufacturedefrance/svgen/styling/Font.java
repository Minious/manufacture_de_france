package com.manufacturedefrance.svgen.styling;

import java.util.Arrays;
import java.util.List;

public class Font {
    private String name;
    private int size;
    private boolean bold;

    public static String ARIAL = "Arial";
    public static String CENTURY_GOTHIC = "Century Gothic";
    private static List<String> FONTS = Arrays.asList(
        ARIAL,
        CENTURY_GOTHIC
    );

    private static String DEFAULT_NAME = ARIAL;
    private static int DEFAULT_SIZE = 12;

    public Font(Font clone){
        this(clone.name, clone.size, clone.bold);

    }

    public Font(String name, int size, boolean bold){
        this.name = name;
        this.size = size;
        this.bold = bold;
    }

    public Font(String name, int size){
        this(name, size, false);
    }

    public Font(String name){
        this(name, DEFAULT_SIZE);
    }

    public Font(){
        this(DEFAULT_NAME);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(!FONTS.contains(name))
            throw new IllegalArgumentException();
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    public boolean isBold() {
    	return this.bold;
    }
    
    public void setBold(boolean bold) {
    	this.bold = bold;
    }
}
