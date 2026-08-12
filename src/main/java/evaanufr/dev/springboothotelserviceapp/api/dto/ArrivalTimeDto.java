package evaanufr.dev.springboothotelserviceapp.api.dto;

import jakarta.validation.constraints.NotBlank;

public record ArrivalTimeDto(
        @NotBlank String checkIn,
        String checkOut
) {
}
