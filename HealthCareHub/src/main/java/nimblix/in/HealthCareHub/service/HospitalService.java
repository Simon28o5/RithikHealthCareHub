package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.model.Hospital;
import nimblix.in.HealthCareHub.request.HospitalRegistrationRequest;
import nimblix.in.HealthCareHub.response.HospitalResponse;

import java.util.List;

public interface HospitalService {

    String registerHospital(HospitalRegistrationRequest request);

    HospitalResponse<Hospital> getTopRatedHospitals();
}
