package K2LJ.WelCheck_Backend.facilitypackage.service;

import K2LJ.WelCheck_Backend.facilitypackage.domain.Facility;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FacilityService {
    @Transactional(readOnly = false)
    Long registrationFacility(Facility facility);

    void validateDuplicateFacility(Facility facility);

    List<Facility> findAll();

    Facility findOne(Long facilityId);

    void update(Long id, String fcltCd);
}
