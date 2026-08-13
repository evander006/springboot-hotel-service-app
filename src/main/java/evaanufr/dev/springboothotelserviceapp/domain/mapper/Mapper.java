package evaanufr.dev.springboothotelserviceapp.domain.mapper;

import evaanufr.dev.springboothotelserviceapp.api.dto.*;
import evaanufr.dev.springboothotelserviceapp.domain.Address;
import evaanufr.dev.springboothotelserviceapp.domain.ArrivalTime;
import evaanufr.dev.springboothotelserviceapp.domain.Contacts;
import evaanufr.dev.springboothotelserviceapp.domain.HotelEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public Address toAddress(AddressDto dto) {
        if (dto == null) {
            return null;
        }
        return new Address(
                dto.houseNumber(),
                dto.street(),
                dto.city(),
                dto.country(),
                dto.postCode()
        );
    }

    public AddressDto toAddressDto(Address address) {
        if (address == null) {
            return null;
        }
        return new AddressDto(
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getCountry(),
                address.getPostCode()
        );
    }

    public Contacts toContacts(ContactsDto dto) {
        if (dto == null) {
            return null;
        }
        return new Contacts(dto.phone(), dto.email());
    }

    public ContactsDto toContactsDto(Contacts contacts) {
        if (contacts == null) {
            return null;
        }
        return new ContactsDto(contacts.getPhone(), contacts.getEmail());
    }

    public ArrivalTime toArrivalTime(ArrivalTimeDto dto) {
        if (dto == null) {
            return null;
        }
        return new ArrivalTime(dto.checkIn(), dto.checkOut());
    }

    public ArrivalTimeDto toArrivalTimeDto(ArrivalTime arrivalTime) {
        if (arrivalTime == null) {
            return null;
        }
        return new ArrivalTimeDto(arrivalTime.getCheckIn(), arrivalTime.getCheckOut());
    }

    public HotelSummaryResponse toSummaryResponse(HotelEntity hotel) {
        return new HotelSummaryResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                formatAddress(hotel.getAddress()),
                hotel.getContacts() != null ? hotel.getContacts().getPhone() : null
        );
    }

    public HotelDetailsResponse toDetailsResponse(HotelEntity hotel) {
        List<String> amenities = hotel.getAmenities() != null
                ? List.copyOf(hotel.getAmenities())
                : List.of();

        return new HotelDetailsResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                hotel.getBrand(),
                toAddressDto(hotel.getAddress()),
                toContactsDto(hotel.getContacts()),
                toArrivalTimeDto(hotel.getArrivalTime()),
                amenities
        );
    }

    public String formatAddress(Address address) {
        if (address == null) {
            return null;
        }
        return "%d %s, %s, %s, %s".formatted(
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getPostCode(),
                address.getCountry()
        );
    }
    public Map<String, Long> toMap(List<Object[]> listObj) {
        return listObj.stream().collect(Collectors.toMap(
                row->(String) row[0],
                row->(Long) row[1]
        ));
    }
}
