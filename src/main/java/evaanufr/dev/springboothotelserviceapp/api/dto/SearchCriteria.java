package evaanufr.dev.springboothotelserviceapp.api.dto;

public record SearchCriteria(
        String name,
        String brand,
        String city,
        String country,
        String amenities,
        Integer pageSize,
        Integer pageNumber
) {
}
