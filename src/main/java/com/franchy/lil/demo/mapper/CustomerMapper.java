package com.franchy.lil.demo.mapper;

import com.franchy.lil.demo.domain.Customer;
import com.franchy.lil.demo.dto.CustomerDTO;
import com.franchy.lil.demo.model.CustomerModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    List<CustomerDTO> toDTO(List<Customer> customers);

    CustomerDTO toDTO(Customer customer);

    Customer toDomain(CustomerModel customer);

    CustomerModel toModel(Customer customer);
}
