package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ValidateNameTest {
    private PlaceOrderController placeOrderController;

    @BeforeEach
    void setUp() throws Exception {
        placeOrderController = new PlaceOrderController();
    }


    @ParameterizedTest
    @CsvSource({
            "Nguyễn Văn Hiếu, true",
            "Andrew, true",
            "_#hello , false",
            "Bob, true"
    })


    @Test
    public void test(String name, boolean expected) {
        boolean isValid = placeOrderController.validatePhoneNumber(name);
        assertEquals(expected, isValid);
    }
}
