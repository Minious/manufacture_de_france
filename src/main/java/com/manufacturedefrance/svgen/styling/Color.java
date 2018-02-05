package com.manufacturedefrance.svgen.styling;

public class Color {
    private int red;
    private int green;
    private int blue;

    public static Color BLACK = new Color(0, 0, 0);
    public static Color RED = new Color(255, 0, 0);
    public static Color GREEN = new Color(0, 255, 0);
    public static Color BLUE = new Color(0, 0, 255);
    public static Color WHITE = new Color(255, 255, 255);

    public Color(Color clone){
        this(clone.red, clone.green, clone.blue);
    }

    public Color(int red, int green, int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Color(int grey){
        this(grey, grey, grey);
    }

    public String getHex(){
        String red = Integer.toHexString(this.red);
        String green = Integer.toHexString(this.green);
        String blue = Integer.toHexString(this.blue);

        StringBuilder sb = new StringBuilder();
        sb.append("#");
        sb.append(red.length() == 1 ? "0"+red : red);
        sb.append(green.length() == 1 ? "0"+green : green);
        sb.append(blue.length() == 1 ? "0"+blue : blue);

        return sb.toString();
    }
}
