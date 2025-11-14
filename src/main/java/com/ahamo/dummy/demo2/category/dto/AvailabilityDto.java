package com.ahamo.dummy.demo2.category.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AvailabilityDto(String onlineStock, String deliveryLeadTime) {}
