package evaanufr.dev.springboothotelserviceapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArrivalTime {

    @Column(name = "check_in")
    private String checkIn;

    @Column(name = "check_out")
    private String checkOut;
}
