package com.spalla.customer.api.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerDataUtilsTest {

    @Test
    void testMaskNameField(){
        Assertions.assertEquals("J**n", CustomerDataUtils.maskNameField("John"));
    }

    @Test
    void testMaskEmailField(){
        Assertions.assertEquals("J******@gmail.com", CustomerDataUtils.maskEmailField("Johndoe@gmail.com"));
    }

    @Test
    void testMaskPhoneField(){
        Assertions.assertEquals("+**********-9902", CustomerDataUtils.maskPhoneField("+1(800) 345-9902"));
    }

    @Test
    void testIsValidEmailId(){
        Assertions.assertTrue(CustomerDataUtils.isValidEmailId("abc@gmail.com"));
        Assertions.assertFalse(CustomerDataUtils.isValidEmailId("abc-gmail.com"));
        Assertions.assertFalse(CustomerDataUtils.isValidEmailId("abc.gmail@com"));
    }
}
