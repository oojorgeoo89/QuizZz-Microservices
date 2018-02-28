package jorge.rv.quizzz.userservice.utils;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import jorge.rv.quizzz.userservice.service.utils.DateHelper;
import jorge.rv.quizzz.userservice.service.utils.DateHelperImpl;

public class DateHelperTests {

	private static Date FIXED_DATE = new Date(123456);
	private static long VALID_OFFSET = 5;
	private static long NEGATIVE_OFFSET = -5;

	private DateHelper dateHelper;

	@Before
	public void before() {
		dateHelper = new DateHelperImpl();
	}

	@Test
	public void calculateValidExpirationDate() {
		Date expirationDate = dateHelper.getExpirationDate(FIXED_DATE, (int) VALID_OFFSET);

		assertEquals(VALID_OFFSET * 1000 * 60, expirationDate.getTime() - FIXED_DATE.getTime());
	}

	@Test
	public void calculateNegativeExpirationDate_shouldReturnFromDate() {
		Date expirationDate = dateHelper.getExpirationDate(FIXED_DATE, (int) NEGATIVE_OFFSET);

		assertEquals(0, expirationDate.getTime() - FIXED_DATE.getTime());
	}

}
