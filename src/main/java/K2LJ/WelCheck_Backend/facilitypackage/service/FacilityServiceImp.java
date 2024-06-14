package K2LJ.WelCheck_Backend.facilitypackage.service;

import K2LJ.WelCheck_Backend.facilitypackage.domain.Facility;
import K2LJ.WelCheck_Backend.facilitypackage.repository.FacilityRepositoryImp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FacilityServiceImp implements FacilityService{

    private final FacilityRepositoryImp facilityRepostiory;

    @Override
    public Long registrationFacility(Facility facility) {
        validateDuplicateFacility(facility);
        facilityRepostiory.save(facility);
        return facility.getId();
    }

    @Override
    public void validateDuplicateFacility(Facility facility)  {
        List<Facility> facilities = facilityRepostiory.findbyFcltCd(facility.getFcltCd());
        if (!facilities.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 시설입니다. ");
        }
    }

    @Override
    public List<Facility> findAll(){
        return facilityRepostiory.findAll();
    }

    @Override
    public Facility findOne(Long facilityId){
        return facilityRepostiory.findOne(facilityId);
    }

    @Override
    @Transactional
    public void update(Long id, String fcltCd) {
        Facility facility = facilityRepostiory.findOne(id);
        facility.setFcltCd(fcltCd);
    }

}
