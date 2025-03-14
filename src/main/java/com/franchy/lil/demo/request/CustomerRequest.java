package com.franchy.lil.demo.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CustomerRequest(
        @Schema(example = "John Doe")
        String name,

        @Schema(example = "john.doe@example.com")
        String email,

        @Schema(example = "30")
        Integer age) {

}
