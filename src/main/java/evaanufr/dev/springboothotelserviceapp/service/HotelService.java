package evaanufr.dev.springboothotelserviceapp.service;

import evaanufr.dev.springboothotelserviceapp.domain.mapper.Mapper;
import evaanufr.dev.springboothotelserviceapp.repository.HotelRepository;
import evaanufr.dev.springboothotelserviceapp.api.dto.*;
import evaanufr.dev.springboothotelserviceapp.domain.HotelEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final Mapper mapper;

    public HotelService(HotelRepository hotelRepository, Mapper mapper) {
        this.hotelRepository = hotelRepository;
        this.mapper = mapper;
    }

    @Transactional
    public HotelSummaryResponse createHotel(CreateHotelRequest request) {
        HotelEntity hotel = new HotelEntity();
        hotel.setName(request.name());
        hotel.setDescription(request.description());
        hotel.setBrand(request.brand());
        hotel.setAddress(mapper.toAddress(request.address()));
        hotel.setContacts(mapper.toContacts(request.contacts()));
        hotel.setArrivalTime(mapper.toArrivalTime(request.arrivalTime()));

        HotelEntity saved = hotelRepository.save(hotel);
        return mapper.toSummaryResponse(saved);
    }

    public List<HotelSummaryResponse> getListHotelsSummary() {
        return hotelRepository.findAll().stream().map(mapper::toSummaryResponse).toList();
    }

    @Transactional(readOnly = true)
    public HotelDetailsResponse getHotelDetails(Long id) {
        HotelEntity hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Hotel not found: " + id));
        return mapper.toDetailsResponse(hotel);
    }

    public List<HotelSummaryResponse> searchHotelsSummary(SearchCriteria searchCriteria) {
        int pageSize = searchCriteria.pageSize() != null ? searchCriteria.pageSize() : 10;
        int pageNumber = searchCriteria.pageNumber() != null ? searchCriteria.pageNumber() : 0;
        var pageable = Pageable.ofSize(pageSize).withPage(pageNumber);
        var allHotels=hotelRepository.searchAllByFilters(searchCriteria.name(), searchCriteria.brand(),searchCriteria.city(),searchCriteria.country(), searchCriteria.amenities(),pageable);
        return allHotels.stream().map(mapper::toSummaryResponse).toList();
    }

    @Transactional
    public void addAmenitiesToHotel(Long id, List<String> amenities) {
        var hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Hotel not found: " + id));
        if (amenities == null || amenities.isEmpty()) {
            return;
        }
        List<String> existing = hotel.getAmenities();
        for (String amenity : amenities) {
            if (amenity != null && !existing.contains(amenity)) {
                existing.add(amenity);
            }
        }
    }

    public Map<String, Long> getHotelsHistogram(String param) {
        return switch (param){
            case "city"->mapper.toMap(hotelRepository.getHistogramByCity());
            case "brand" -> mapper.toMap(hotelRepository.getHistogramByBrand());
            case "country" -> mapper.toMap(hotelRepository.getHistogramByCountry());
            case "amenities" -> mapper.toMap(hotelRepository.getHistogramByAmenities());
            default -> throw new IllegalArgumentException(
                    "Unsupported histogram param: " + param
            );
        };
    }


}
