package com.franchy.lil.demo.controller;

import com.franchy.lil.demo.dto.CustomerDTO;
import com.franchy.lil.demo.exception.ResourceNotFoundException;
import com.franchy.lil.demo.mapper.CustomerMapper;
import com.franchy.lil.demo.model.Customer;
import com.franchy.lil.demo.model.Order;
import com.franchy.lil.demo.request.CustomerRequest;
import com.franchy.lil.demo.response.ApiResponse;
import com.franchy.lil.demo.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Customer Endpoint", description = "Operations related to customers")
@RequestMapping("api/v1/customers")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @Operation(summary = "Retrieve customers by active status", description = "Retrieve a list of customers filtered by their active status.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Found the customers",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDTO.class)) }),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Customers not found",
                    content = @Content) })
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<CustomerDTO>>> getCustmers(
            @Parameter(description = "Active status of the customers to be retrieved")
            @RequestParam(value = "active") Boolean active) {
        logger.debug("Retrieving customers with active status: {}", active);
        List<Customer> customers = new ArrayList<>();

        if (active != null) {
            customers = this.customerService.findCustomerByActive(active);
        }

        logger.debug("Retrieved {} customers", customers.size());
        List<CustomerDTO> customersDTO = CustomerMapper.INSTANCE.toDTO(customers);
        ApiResponse<List<CustomerDTO>> response = new ApiResponse<>(true, "Retrieving all customers", customersDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "Get all active adult customers")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Found the active adult customers",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDTO.class)) }),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Customers not found",
                    content = @Content) })
    @GetMapping("/active/adults")
    public ResponseEntity<ApiResponse<List<CustomerDTO>>> getCustmersActiveAndAdult() {
        logger.debug("Retrieving all customers");
        List<Customer> customers = this.customerService.getAllActiveAndAdultCustomer();
        logger.debug("Retrieved {} customers", customers.size());
        List<CustomerDTO> customersDTO  = CustomerMapper.INSTANCE.toDTO(customers);
        ApiResponse<List<CustomerDTO>> response = new ApiResponse<>(true, "Retrieving all customers", customersDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Save a new customer")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Customer created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDTO.class)) }),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerDTO>> saveCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        logger.debug("Saving customer with request: {}", customerRequest);
        Customer customer = new Customer();
        customer.setName(customerRequest.name());
        customer.setEmail(customerRequest.email());
        customer.setAge(customerRequest.age());
        customer.setActive(true);
        this.customerService.saveCustomer(customer);
        CustomerDTO customerDTO  = CustomerMapper.INSTANCE.toDTO(customer);
        ApiResponse<CustomerDTO> response = new ApiResponse<>(true, "Resource created successfully", customerDTO);
        logger.debug("Customer saved successfully: {}", customerDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @Operation(summary = "Delete a customer by ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Customer deleted successfully",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content)
    })
    @DeleteMapping("{customerID}")
    public ResponseEntity<ApiResponse<Integer>> deleteCustomer(
            @Parameter(description = "ID of the customer to be deleted", example = "123")
            @PathVariable("customerID") Integer customerID) {
        logger.debug("Deleting customer with ID: {}", customerID);
        this.customerService.deleteCustomer(customerID);
        ApiResponse<Integer> response = new ApiResponse<>(true, "Resource deleted successfully", customerID);
        logger.debug("Customer deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update customer")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Found the active adult customers",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerDTO.class)) }),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Customers not found",
                    content = @Content) })
    @PutMapping("{customerID}")
    public ResponseEntity<ApiResponse<CustomerDTO>> updateCustomer(
            @Parameter(description = "ID of the customer to be updated", example = "123")
            @PathVariable("customerID") Integer customerID,
            @Parameter(
                    description = "map of property names and values ",
                    content = @Content(
                            schema = @Schema(
                                    type = "object",
                                    implementation = Map.class)))
                               @RequestBody Map<String, Object> customerMap) throws ResourceNotFoundException {
        logger.debug("Updating customer with ID: {}", customerID);
        Customer customer = this.customerService.updateCustomer(customerID, customerMap);
        logger.debug("Customer updated successfully");
        CustomerDTO customerDTO  = CustomerMapper.INSTANCE.toDTO(customer);
        ApiResponse<CustomerDTO> response = new ApiResponse<>(true, "Resource was successfully updated.", customerDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
