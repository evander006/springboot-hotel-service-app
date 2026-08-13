package evaanufr.dev.springboothotelserviceapp.repository;

import evaanufr.dev.springboothotelserviceapp.domain.HotelEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Long> {

    @Query("""
            select h from HotelEntity h
            where (:name is null or lower(h.name) = lower(:name))
              and (:brand is null or lower(h.brand) = lower(:brand))
              and (:city is null or lower(h.address.city) = lower(:city))
              and (:country is null or lower(h.address.country) = lower(:country))
              and (:amenities is null or :amenities member of h.amenities)
            """)
    List<HotelEntity> searchAllByFilters(
            @Param("name") String name,
            @Param("brand") String brand,
            @Param("city") String city,
            @Param("country") String country,
            @Param("amenities") String amenities,
            Pageable pageable
    );
    @Query("select h.brand, count(h) from HotelEntity h group by h.brand")
    List<Object[]> getHistogramByBrand();
    @Query("select h.address.city, count(h) from HotelEntity h group by h.address.city")
    List<Object[]> getHistogramByCity();
    @Query("select h.address.country, count(h) from HotelEntity h group by h.address.country")
    List<Object[]> getHistogramByCountry();
    @Query("select a, count(h) from HotelEntity h join h.amenities a group by a")
    List<Object[]> getHistogramByAmenities();
}
