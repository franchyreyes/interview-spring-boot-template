package com.franchy.lil.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record OrderNumberDTO(@Schema(example = "50")
                              String orderNumber) {
}
