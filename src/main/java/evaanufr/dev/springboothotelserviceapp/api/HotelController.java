package evaanufr.dev.springboothotelserviceapp.api;

import evaanufr.dev.springboothotelserviceapp.api.dto.CreateHotelRequest;
import evaanufr.dev.springboothotelserviceapp.api.dto.HotelDetailsResponse;
import evaanufr.dev.springboothotelserviceapp.api.dto.HotelSummaryResponse;
import evaanufr.dev.springboothotelserviceapp.api.dto.SearchCriteria;
import evaanufr.dev.springboothotelserviceapp.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/property-view")
@AllArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @Operation(summary = "Create hotel")
    @ApiResponse(responseCode = "201", description = "Hotel was created")
    @PostMapping("/hotels")
    public ResponseEntity<HotelSummaryResponse> createHotel(@Valid @RequestBody CreateHotelRequest createHotelRequest) {
        var newHotel = hotelService.createHotel(createHotelRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newHotel);
    }
    @Operation(summary = "Get summaries")
    @ApiResponse(responseCode = "200", description = "Short summaries of hotels are found")
    @GetMapping("/hotels")
    public ResponseEntity<List<HotelSummaryResponse>> getListHotelsSummary() {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getListHotelsSummary());
    }
    @Operation(summary = "Get hotel details")
    @ApiResponse(responseCode = "200", description = "Hotel details were found by id")
    @GetMapping("/hotels/{id}")
    public ResponseEntity<HotelDetailsResponse> getHotelDetails(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getHotelDetails(id));
    }
    @Operation(summary = "Search hotels by filters")
    @ApiResponse(responseCode = "200", description = "Hotels were found by filters")
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
    @Operation(summary = "Add amenities to hotel")
    @ApiResponse(responseCode = "201", description = "Amenities were added")
    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<Void> addAmenitiesToHotel(@PathVariable Long id,@RequestBody List<String> amenities) {
        hotelService.addAmenitiesToHotel(id,amenities);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(summary = "Get hotels' histogram")
    @ApiResponse(responseCode = "200", description = "Hotels' histogram was found")
    @GetMapping("/histogram/{param}")
    public ResponseEntity<Map<String, Long>> getHotelsHistogram(@PathVariable String param) {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getHotelsHistogram(param));
    }
}
