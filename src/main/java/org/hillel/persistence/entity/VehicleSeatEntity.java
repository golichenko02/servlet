package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hillel.persistence.entity.util.YesNoConverter;

import javax.persistence.*;
import java.util.StringJoiner;

@Entity
@Table(name = "vehicle_seat")
@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@Check(constraints = "seat_number > 0")
@NamedQueries(value = {
        @NamedQuery(name = "findAllVehicleSeatEntity", query = "select vs from VehicleSeatEntity vs")
})
public class VehicleSeatEntity extends AbstractModifyEntity<Long> {

    @Column(name = "seat_number", nullable = false)
    private int seatNumber;

    @Column(name = "booked")
    @Convert(converter = YesNoConverter.class)
    private boolean isBooked = false;

    @ManyToOne(/*cascade = {CascadeType.PERSIST},*/ fetch = FetchType.LAZY)
    @JoinColumn(name = "journey_id")
    private JourneyEntity journey;

    @ManyToOne(/*cascade = {CascadeType.PERSIST},*/ fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private VehicleEntity vehicle;

    @Override
    public String toString() {
        return new StringJoiner(", ", VehicleSeatEntity.class.getSimpleName() + "[", "]")
                .add("id=" + getId())
                .add("seatNumber=" + seatNumber)
                .add("isBooked=" + isBooked)
                .toString();
    }
}

