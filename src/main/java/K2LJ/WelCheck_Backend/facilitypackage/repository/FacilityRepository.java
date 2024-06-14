package K2LJ.WelCheck_Backend.facilitypackage.repository;


import K2LJ.WelCheck_Backend.facilitypackage.domain.Facility;

import java.util.List;

public interface FacilityRepository {
    void save(Facility facility);

    Facility findOne(Long id);

    List<Facility> findAll();

    List<Facility> findbyFcltCd(String fcltcd);
}
