package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import streash.vars.Number;

class NumberTest {

	@Test
	void testGetValue() {
		Number goodNumber = new Number(10);
		Number badNumber = new Number(12, 7);
		assertEquals(10, goodNumber.getValue());
		assertThrows(IllegalStateException.class, () -> {
			badNumber.getValue();
		});
	}
	
	@Test
	void testFloatingValue() {
		Number number = new Number(12, 7);
		assertEquals((float) 12/7, number.getFloatingValue());
	}
	
	@Test
	void testParse() {
		String goodStr = "12/7";
		String badStr = "12/7/3";
		Number expected = new Number(12, 7);
		assertEquals(expected, Number.parse(goodStr));
		assertThrows(IllegalArgumentException.class, () -> {
			Number.parse(badStr);
		});
	}
	
	@Test
	void testAdd() {
		Number n1 = new Number(5, 4);
		Number n2 = new Number(12, 8);
		Number expected = new Number(22, 8);
		assertEquals(expected, n1.add(n2));
	}
	
	@Test
	void testSub() {
		Number n1 = new Number(5, 4);
		Number n2 = new Number(12, 8);
		Number expected = new Number(1, 4);
		assertEquals(expected, n2.sub(n1));
	}
	
	@Test
	void testMul() {
		Number n1 = new Number(5, 4);
		Number n2 = new Number(5, 2);
		Number expected = new Number(25, 8);
		assertEquals(expected, n2.mul(n1));
	}
	
	@Test
	void testDiv() {
		Number n1 = new Number(5, 4);
		Number n2 = new Number(5, 2);
		Number badIdea = new Number(0);
		Number expected = new Number(1, 2);
		assertEquals(expected, n1.div(n2));
		assertThrows(ArithmeticException.class, () -> {
			n1.div(badIdea);
		});
	}
	
	@Test
	void testIsInteger() {
		Number number = new Number(12);
		assertTrue(number.isInteger());
	}
}
