package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.StringJoiner;

@Entity
@Table(name = "stop_additional_info")
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
public class StopAdditionalInfoEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "build_date", nullable = false)
    private LocalDate buildDate;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @OneToOne
    @MapsId
    @JoinColumn(name = "stop_id")
    private StopEntity stopEntity;

    @Override
    public String toString() {
        return new StringJoiner(", ", StopAdditionalInfoEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("longitude=" + longitude)
                .add("latitude=" + latitude)
                .add("buildDate=" + buildDate)
                .add("city='" + city + "'")
                .toString();
    }
}
