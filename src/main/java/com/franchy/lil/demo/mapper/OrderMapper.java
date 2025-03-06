package com.franchy.lil.demo.mapper;

import com.franchy.lil.demo.dto.OrderDTO;
import com.franchy.lil.demo.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "customer.name", target = "customerName")
    @Mapping(source = "customer.email", target = "customerEmail")
    OrderDTO toDTO(Order order);

    List<OrderDTO> toDTOList(List<Order> orders);

}
