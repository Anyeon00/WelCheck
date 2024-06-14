package K2LJ.WelCheck_Backend.facilitypackage.api;


import K2LJ.WelCheck_Backend.facilitypackage.domain.Facility;
import K2LJ.WelCheck_Backend.facilitypackage.service.FacilityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Transactional
public class FacilityApiController {
    private final FacilityService facilityService;

    @PostMapping("/facilitys")
    public CreateFacilityResponse createFacility(@RequestBody @Valid CreateFacilityRequest request) {
        Facility facility = new Facility();
        facility.setFcltCd(request.getFcltCd());

        Long id = facilityService.registrationFacility(facility);
        return new CreateFacilityResponse(id);
    }

    @PutMapping("/facilitys/{id}")
    public UpdateFacilityResponse updateFacility(@PathVariable("id") Long id, @RequestBody @Valid UpdateFacilitysRequest request) {
        facilityService.update(id, request.getFcltCd());
        Facility findFacility = facilityService.findOne(id);
        return new UpdateFacilityResponse(findFacility.getId(), findFacility.getFcltCd());
    }

    @GetMapping("/facilitys")
    public Result facilitys(){
        List<FacilityDto> collect = facilityService.findAll().stream()
                .map(f -> new FacilityDto(f.getFcltCd()))
                .collect(Collectors.toList());
        //for사용해도 무방

        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class FacilityDto{
        String fcltCd;
    }

    @Data
    static class CreateFacilityRequest {
        String fcltCd;
    }
    @Data
    @AllArgsConstructor
    static class CreateFacilityResponse {
        Long id;
    }

    @Data
    static class UpdateFacilitysRequest {
        String fcltCd;
    }
    @Data
    @AllArgsConstructor
    static class UpdateFacilityResponse {
        Long id;
        String fcltCd;
    }
}
