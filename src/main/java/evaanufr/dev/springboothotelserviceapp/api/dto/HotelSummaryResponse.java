package evaanufr.dev.springboothotelserviceapp.api.dto;

public record HotelSummaryResponse(
        Long id,
        String name,
        String description,
        String address,
        String phone
) {
}
