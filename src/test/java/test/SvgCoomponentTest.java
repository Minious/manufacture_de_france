package test;

import org.junit.Test;

import static com.manufacturedefrance.svgen.SvgComponent.DOUBLE_FORMAT;
import static org.junit.Assert.assertEquals;

public class SvgCoomponentTest {

    @Test
    public void formatterTest(){
        double myNumber = -01234.56789;
        assertEquals(DOUBLE_FORMAT.format(myNumber), "-1234.568");
    }

}
