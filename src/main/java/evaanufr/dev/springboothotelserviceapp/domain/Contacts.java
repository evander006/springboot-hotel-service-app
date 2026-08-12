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
public class Contacts {

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;
}
