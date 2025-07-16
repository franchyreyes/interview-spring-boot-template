package com.franchy.lil.demo.mapper;

import com.franchy.lil.demo.domain.Order;
import com.franchy.lil.demo.dto.OrderDTO;
import com.franchy.lil.demo.dto.OrderNumberDTO;
import com.franchy.lil.demo.model.OrderModel;
import com.franchy.lil.demo.model.OrderRedis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order toDomain(OrderModel order);
    OrderModel toModel(Order order);

    @Mapping(source = "customer.name", target = "customerName")
    @Mapping(source = "customer.email", target = "customerEmail")
    OrderDTO toDTO(Order order);

    @Mapping(source = "orderNumber", target = "orderNumber")
    OrderNumberDTO toOrderNumberDTO(Order order);
    OrderDTO toDTO(OrderRedis orderRedis);

    List<OrderDTO> toDTO(List<Order> orders);

    List<OrderRedis> toOrderRedis(List<OrderDTO> orders);

    List<OrderDTO> orderRedisListToOrderDTOList(List<OrderRedis> orders);
}
