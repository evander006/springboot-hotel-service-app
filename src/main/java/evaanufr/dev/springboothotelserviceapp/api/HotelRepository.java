package evaanufr.dev.springboothotelserviceapp.api;

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
}
