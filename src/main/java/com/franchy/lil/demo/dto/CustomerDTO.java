package com.franchy.lil.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CustomerDTO(

        @Schema(example = "10") Integer id,

        @Schema(example = "Franchy Reyes") String name, @Schema(example = "franchy@gmail.com") String email,
        @Schema(example = "50") Integer age) {
}
