package evaanufr.dev.springboothotelserviceapp.api;

import evaanufr.dev.springboothotelserviceapp.api.dto.CreateHotelRequest;
import evaanufr.dev.springboothotelserviceapp.api.dto.HotelDetailsResponse;
import evaanufr.dev.springboothotelserviceapp.api.dto.HotelSummaryResponse;
import evaanufr.dev.springboothotelserviceapp.api.dto.SearchCriteria;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/property-view")
@AllArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping("/hotels")
    public ResponseEntity<HotelSummaryResponse> createHotel(@Valid @RequestBody CreateHotelRequest createHotelRequest) {
        var newHotel = hotelService.createHotel(createHotelRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newHotel);
    }

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelSummaryResponse>> getListHotelsSummary() {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getListHotelsSummary());
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<HotelDetailsResponse> getHotelDetails(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getHotelDetails(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<HotelSummaryResponse>> searchHotelsSummary(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String amenities,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageNumber
    ) {
        var searchFilters = new SearchCriteria(name, brand, city, country, amenities, pageSize, pageNumber);
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.searchHotelsSummary(searchFilters));
    }
    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<Void> addAmenitiesToHotel(@PathVariable Long id,@RequestBody List<String> amenities) {
        hotelService.addAmenitiesToHotel(id,amenities);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
