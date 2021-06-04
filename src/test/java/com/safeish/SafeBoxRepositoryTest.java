package com.safeish;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safeish.safebox.Customer;
import com.safeish.safebox.api.CustomerRepository;

@SpringBootTest
public class SafeBoxRepositoryTest {

    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Test
    public void whenFindingCustomerById_thenCorrect() {
        Customer c = customerRepository.save(new Customer("John", "john@domain.com"));
        Optional<Customer> c2 = customerRepository.findById(c.getId());
        assertEquals(c2.get(),c);
    }
    
//    @Test
//    public void whenFindingAllCustomers_thenCorrect() {
//        customerRepository.save(new Customer("John", "john@domain.com"));
//        customerRepository.save(new Customer("Julie", "julie@domain.com"));
//        assertThat(customerRepository.findAll()).isInstanceOf(List.class);
//    }
//    
//    
//    @Test
//    public void whenSavingCustomer_thenCorrect() {
//        customerRepository.save(new Customer("Bob", "bob@domain.com"));
//        Customer customer = customerRepository.findById(1L).orElseGet(() 
//          -> new Customer("john", "john@domain.com"));
//        assertThat(customer.getName()).isEqualTo("Bob");
//    }

}
