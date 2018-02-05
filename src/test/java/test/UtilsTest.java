package test;

import com.manufacturedefrance.techdrawgen.Utils;
import com.manufacturedefrance.utils.MyPoint2D;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void getAngle2Test(){
        MyPoint2D[] p1s = new MyPoint2D[]{
            new MyPoint2D(0, 0),
            new MyPoint2D(0, 0),
            new MyPoint2D(0, 0),
            new MyPoint2D(0, 0),
            new MyPoint2D(0, 0),
            new MyPoint2D(1, 1),
            new MyPoint2D(2, 2),
            new MyPoint2D(0, 0)
        };
        MyPoint2D[] p2s = new MyPoint2D[]{
            new MyPoint2D(1, 1),
            new MyPoint2D(0, 1),
            new MyPoint2D(1, 0),
            new MyPoint2D(-1, 0),
            new MyPoint2D(0, -1),
            new MyPoint2D(2, 2),
            new MyPoint2D(1, 1),
            new MyPoint2D(1/2d, Math.sqrt(3) / 2)
        };
        double[] angles = new double[]{
            Math.PI / 4,
            Math.PI / 2,
            0,
            Math.PI,
            -Math.PI / 2,
            Math.PI / 4,
            -3 * Math.PI / 4,
            Math.PI / 3
        };

        for(int i=0;i<angles.length;i++)
            assertEquals(angles[i], Utils.getAngle(p1s[i], p2s[i]), 0.001);
    }
}
