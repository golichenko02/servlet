package org.hillel.persistence.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "stop")
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@NamedQueries(value = {
        @NamedQuery(name = "findAllStopEntity", query = "select s from StopEntity s")
})
public class StopEntity extends AbstractModifyEntity<Long> {

    @Embedded
    private CommonInfo commonInfo;

    @OneToOne(mappedBy = "stopEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private StopAdditionalInfoEntity additionalInfo;

    @ManyToMany(mappedBy = "stops", cascade = CascadeType.MERGE)
    List<JourneyEntity> journeys = new ArrayList<>();

    @Transient
    private boolean applyToJourneyBuild;


    public void addStopAdditionalInfo(StopAdditionalInfoEntity stopAdditionalInfo) {
        if (stopAdditionalInfo == null) {
            this.additionalInfo = null;
            return;
        }
        stopAdditionalInfo.setStopEntity(this);
        this.additionalInfo = stopAdditionalInfo;
    }

    public void addJourney(JourneyEntity journey) {
        if (Objects.isNull(journey)) throw new IllegalArgumentException("StopEntity must be set");
        if (journeys == null) journeys = new ArrayList<>();
        this.journeys.add(journey);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StopEntity)) return false;
        StopEntity stopEntity = (StopEntity) o;
        return getId() != null && Objects.equals(stopEntity.getId(), getId());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public void removeJourney() {
        if (CollectionUtils.isEmpty(journeys)) return;
        journeys.forEach(journeyEntity -> journeyEntity.getStops().remove(this));
        journeys = null;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", StopEntity.class.getSimpleName() + "[", "]")
                .add("id=" + getId())
                .add("commonInfo=" + commonInfo)
                .add("additionalInfo=" + additionalInfo)
                .toString();
    }
}

