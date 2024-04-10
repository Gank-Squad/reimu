package com.git.ganksquad.arithmetic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.git.ganksquad.App;
import com.git.ganksquad.exceptions.ReimuRuntimeException;

public class IntegerArithmeticTest {

	public boolean codePassed(String code) {

		try {
			App.eval(code);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

    @Test
    public void integerAdditionTest()
    {
    	assertTrue(codePassed("0 + 0;"));
    	assertTrue(codePassed("0 + 1;"));
    	assertTrue(codePassed("1 + 0;"));
    	assertTrue(codePassed("0 + -1;"));
    	assertTrue(codePassed("-1 + 0;"));
    }

    @Test
    public void integerSubtractionTest()
    {
    	assertTrue(codePassed("0 - 0;"));
    	assertTrue(codePassed("0 - 1;"));
    	assertTrue(codePassed("1 - 0;"));
    	assertTrue(codePassed("0 - -1;"));
    	assertTrue(codePassed("-1 - 0;"));
    }

    @Test
    public void integerMultiplicationTest()
    {
    	assertTrue(codePassed("0 * 0;"));
    	assertTrue(codePassed("0 * 1;"));
    	assertTrue(codePassed("1 * 0;"));
    	assertTrue(codePassed("0 * -1;"));
    	assertTrue(codePassed("-1 * 0;"));
    }

    @Test
    public void integerDivisionTest()
    {
    	assertFalse(codePassed("0 / 0;"));
    	assertTrue(codePassed("0 / 1;"));
    	assertFalse(codePassed("1 / 0;"));
    	assertTrue(codePassed("0 / -1;"));
    	assertFalse(codePassed("-1 / 0;"));
    }
}
