package controller;

import controller.PlaceOrderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidateAddressTest {

    private PlaceOrderController placeOrderController;

    @BeforeEach
    void setUp() throws Exception {
        placeOrderController = new PlaceOrderController();
    }


    @ParameterizedTest
    @CsvSource({
            "null, false",
            "Lê Thanh Nghị, true",
            "@hanoi, false",
            "Ha Tinh, true"
    })


    @Test
    public void test(String address, boolean expected) {
        boolean isValid = placeOrderController.validatePhoneNumber(address);
        assertEquals(expected, isValid);
    }
}
