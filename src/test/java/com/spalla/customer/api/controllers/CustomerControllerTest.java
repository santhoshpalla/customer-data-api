package com.spalla.customer.api.controllers;

import com.spalla.customer.api.entity.Customer;
import com.spalla.customer.api.exceptions.CustomerNotFoundException;
import com.spalla.customer.api.services.CustomerDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CustomerDataService customerDataService;

    @BeforeEach
    void setUp(){
        List<Customer> customerList = new ArrayList<>();
        Customer mockCustomer = new Customer("Mr.", null, "John",
                null, "Doe", "johndoe@gmail.com", "(888)956-1234");
        customerList.add(mockCustomer);
        mockCustomer = new Customer("Mrs.", "II", "Jane",
                null, "Doe", "janedoe@gmail.com", "+1(777)956-1234");

        Customer createCustomer = new Customer("Mrs.", null, "Mary",
                null, "Mc", "marymc@outlook.com", "+1(568)503-3621");

        Mockito.when(customerDataService.getAllCustomers()).thenReturn(customerList);
        Mockito.when(customerDataService.getCustomerByEmailId("janedoe@gmail.com")).thenReturn(mockCustomer);
        Mockito.when(customerDataService.getCustomerByEmailId("abc@gmail.com")).thenThrow(new CustomerNotFoundException("abc@gmail.com"));
        Mockito.when(customerDataService.getCustomerById(2L)).thenReturn(mockCustomer);
        Mockito.when(customerDataService.getCustomerById(99L)).thenThrow(new CustomerNotFoundException(99L));
        Mockito.when(customerDataService.createCustomer(createCustomer)).thenReturn(createCustomer);
        Mockito.when(customerDataService.updateCustomer(createCustomer, 2L)).thenReturn(createCustomer);
        Mockito.doNothing().when(customerDataService).deleteCustomer(Mockito.anyLong());
    }

    @Test
    void testListAllCustomers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void testGetCustomerByEmailId() throws Exception {
        mockMvc.perform((MockMvcRequestBuilders.get("/api/v1/customers/customer/details?emailId=janedoe@gmail.com"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Jane"));
    }

    @Test
    void testGetCustomerByEmailId_NotFound() throws Exception {
        mockMvc.perform((MockMvcRequestBuilders.get("/api/v1/customers/customer/details?emailId=abc@gmail.com"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetCustomerById() throws Exception {
        mockMvc.perform((MockMvcRequestBuilders.get("/api/v1/customers/2"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Jane"));
    }

    @Test
    void testGetCustomerById_NotFound() throws Exception {
        mockMvc.perform((MockMvcRequestBuilders.get("/api/v1/customers/99"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"prefix\": \"Mrs.\",\"suffix\": null,\"firstName\": \"Mary\"," +
                                "\"middleName\": null,\"lastName\": \"Mc\"," +
                                "\"emailId\": \"marymc@outlook.com\",\"phoneNumber\": \"+1(568)503-3621\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Mary"));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customers/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"prefix\": \"Mrs.\",\"suffix\": null,\"firstName\": \"Mary\"," +
                                "\"middleName\": null,\"lastName\": \"Mc\"," +
                                "\"emailId\": \"marymc@outlook.com\",\"phoneNumber\": \"+1(568)503-3621\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Mary"));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/2"))
                .andExpect(status().isOk());
    }


}
