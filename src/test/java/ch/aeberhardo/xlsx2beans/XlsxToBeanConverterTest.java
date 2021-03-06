package ch.aeberhardo.xlsx2beans;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.aeberhardo.xlsx2beans.converter.beans.DateBean;
import ch.aeberhardo.xlsx2beans.converter.beans.DoublePrecisionBean;
import ch.aeberhardo.xlsx2beans.converter.beans.FormulaBean;
import ch.aeberhardo.xlsx2beans.converter.beans.NumberAsTextBean;
import ch.aeberhardo.xlsx2beans.converter.beans.TestBean1;

public class XlsxToBeanConverterTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test_convert() throws ParseException {

		XlsxToBeanConverter converter = new XlsxToBeanConverter();

		List<TestBean1> beans = converter.convert(getClass().getResourceAsStream("/test-valid.xlsx"), 0, TestBean1.class);

		assertEquals(2, beans.size());

		assertEquals("ABC", beans.get(0).getMyString1());
		assertEquals("This is my string 1", beans.get(0).getMyString2());
		assertEquals(Integer.valueOf(123), beans.get(0).getMyInteger());
		assertEquals(Double.valueOf(7.89), beans.get(0).getMyDouble());
		assertEquals(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("12.01.2013 14:16:23"), beans.get(0).getMyDate());

		assertEquals("DEF", beans.get(1).getMyString1());
		assertEquals("This is my string 2", beans.get(1).getMyString2());
		assertEquals(Integer.valueOf(456), beans.get(1).getMyInteger());
		assertEquals(Double.valueOf(9.87), beans.get(1).getMyDouble());
		assertEquals(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("20.06.2011 23:50:33"), beans.get(1).getMyDate());
	}

	@Test
	public void test_convertWithEmptyCells() throws ParseException {

		XlsxToBeanConverter converter = new XlsxToBeanConverter();

		List<TestBean1> beans = converter.convert(getClass().getResourceAsStream("/test-valid-empty_cells.xlsx"), 0, TestBean1.class);

		assertEquals(2, beans.size());

		assertEquals("ABC", beans.get(0).getMyString1());
		assertEquals(null, beans.get(0).getMyString2());
		assertEquals(Integer.valueOf(123), beans.get(0).getMyInteger());
		assertEquals(Double.valueOf(7.89), beans.get(0).getMyDouble());
		assertEquals(null, beans.get(0).getMyDate());

		assertEquals("DEF", beans.get(1).getMyString1());
		assertEquals("This is my string 2", beans.get(1).getMyString2());
		assertEquals(Integer.valueOf(456), beans.get(1).getMyInteger());
		assertEquals(null, beans.get(1).getMyDouble());
		assertEquals(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse("20.06.2011 23:50:33"), beans.get(1).getMyDate());
	}

	@Test
	public void test_convert_dates() throws ParseException {

		XlsxToBeanConverter converter = new XlsxToBeanConverter();

		List<DateBean> beans = converter.convert(getClass().getResourceAsStream("/test-valid-dates.xlsx"), 0, DateBean.class);

		assertEquals(2, beans.size());

		assertEquals("13.11.1987 15:26:36", new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(beans.get(0).getTimestamp()));
		assertEquals("12.10.2010", new SimpleDateFormat("dd.MM.yyyy").format(beans.get(0).getDate()));
		assertEquals("11:20:39", new SimpleDateFormat("HH:mm:ss").format(beans.get(0).getTime()));

		assertEquals("03.06.2013 06:07:08", new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(beans.get(1).getTimestamp()));
		assertEquals("03.04.2006", new SimpleDateFormat("dd.MM.yyyy").format(beans.get(1).getDate()));
		assertEquals("09:01:02", new SimpleDateFormat("HH:mm:ss").format(beans.get(1).getTime()));
	}

	@Test
	public void test_convert_formula() throws ParseException {

		XlsxToBeanConverter converter = new XlsxToBeanConverter();

		List<FormulaBean> beans = converter.convert(getClass().getResourceAsStream("/test-valid-formula.xlsx"), 0, FormulaBean.class);

		assertEquals(2, beans.size());

		assertEquals(Integer.valueOf(4), beans.get(0).getMyFormulaInteger());
		assertEquals("26.08.2012 13:37:36", new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(beans.get(0).getMyFormulaTimestamp()));

		assertEquals(Integer.valueOf(2), beans.get(1).getMyFormulaInteger());
		assertEquals("15.12.2001 05:06:00", new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(beans.get(1).getMyFormulaTimestamp()));
	}

	@Test
	public void test_doublePrecision() {

		XlsxToBeanConverter converter = new XlsxToBeanConverter();

		List<DoublePrecisionBean> beans = converter.convert(getClass().getResourceAsStream("/test-valid-double_precision.xlsx"), 0, DoublePrecisionBean.class);

		assertEquals(1, beans.size());

		assertEquals(Double.valueOf(8.2), beans.get(0).getMyDecimal());
		assertEquals(Integer.valueOf(820), beans.get(0).getMyInteger());
		assertEquals(Integer.valueOf(820), beans.get(0).getMyCalculatedInteger());

	}

	@Test
	public void test_numberAsText() {

		XlsxToBeanConverter converter = new XlsxToBeanConverter();

		List<NumberAsTextBean> beans = converter.convert(getClass().getResourceAsStream("/test-valid-number_as_text.xlsx"), 0, NumberAsTextBean.class);

		assertEquals(4, beans.size());

		assertEquals("Foo", beans.get(0).getMyString());
		assertEquals("1234", beans.get(1).getMyString());
		assertEquals("4567", beans.get(2).getMyString());
		assertEquals("Bar", beans.get(3).getMyString());

	}

}
