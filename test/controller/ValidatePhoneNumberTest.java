package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author HieuNV183534
 */
class ValidatePhoneNumberTest {

	private PlaceOrderController placeOrderController;

	@BeforeEach
	void setUp() throws Exception {
		placeOrderController = new PlaceOrderController();
	}


	@ParameterizedTest
	@CsvSource({
			"0971883025, true",
			"976342342, false",
			"1234567890, false",
			"0123456789, true"
	})


	@Test
	public void test(String phoneNumber, boolean expected) {
		boolean isValid = placeOrderController.validatePhoneNumber(phoneNumber);
		assertEquals(expected, isValid);
	}

}
