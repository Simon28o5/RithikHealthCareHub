package nimblix.in.HealthCareHub.controller;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.model.Hospital;
import nimblix.in.HealthCareHub.request.HospitalRegistrationRequest;
import nimblix.in.HealthCareHub.response.HospitalResponse;
import nimblix.in.HealthCareHub.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hospital")
@RequiredArgsConstructor
public class HospitalController {

    @Autowired
    private final HospitalService hospitalService;

    @PostMapping("/register")
    public String registerHospital(@RequestBody HospitalRegistrationRequest request) {
        return hospitalService.registerHospital(request);
    }

    @GetMapping("/top-rated")
    public HospitalResponse<Hospital> getTopRatedHospitals(){
        return hospitalService.getTopRatedHospitals();
    }

    @PatchMapping("{id}/{rating}")
    public HospitalResponse updateRating(@PathVariable Long id, @PathVariable Double Rating){
        return hospitalService.updateRating(id, Rating);
    }
}
