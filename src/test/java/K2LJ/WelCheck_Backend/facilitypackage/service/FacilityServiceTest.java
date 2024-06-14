package K2LJ.WelCheck_Backend.facilitypackage.service;


import K2LJ.WelCheck_Backend.facilitypackage.domain.Facility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class FacilityServiceTest {
    @Autowired
    private FacilityServiceImp facilityService;
    @Test
    void registrationFacility() {
        //given
        Facility facility = new Facility();
        //when
        Long findId = facilityService.registrationFacility(facility);
        //then
        assertThat(facility).isEqualTo(facilityService.findOne(findId));
    }

    @Test
    void validateDuplicateFacility() {
        //given
        Facility facility1 = new Facility();
        facility1.setFcltCd("시설");
        Facility facility2 = new Facility();
        facility2.setFcltCd("시설");

        //when
        facilityService.registrationFacility(facility1);

        //then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            facilityService.registrationFacility(facility2);
        });
    }

    @Test
    void findFacilitys() {
        //given
        Facility facility1 = new Facility();
        facility1.setFcltCd("시설1");
        facilityService.registrationFacility(facility1);

        Facility facility2 = new Facility();
        facility2.setFcltCd("시설2");
        facilityService.registrationFacility(facility2);

        //when
        List<Facility> facilitys = facilityService.findAll();

        assertThat(facilitys).size().isEqualTo(2);
        /*assertThat(facilitys).contains(facility1);
        assertThat(facilitys).contains(facility2);*/
    }
}