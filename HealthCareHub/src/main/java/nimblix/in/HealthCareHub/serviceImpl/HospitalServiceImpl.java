package nimblix.in.HealthCareHub.serviceImpl;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.model.Hospital;
import nimblix.in.HealthCareHub.repository.HospitalRepository;
import nimblix.in.HealthCareHub.request.HospitalRegistrationRequest;
import nimblix.in.HealthCareHub.response.HospitalResponse;
import nimblix.in.HealthCareHub.service.HospitalService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;

    @Override
    public String registerHospital(HospitalRegistrationRequest request) {

        // Check if hospital already exists
        if (hospitalRepository.findByName(request.getName()).isPresent()) {
            return "Hospital already exists";
        }

        Hospital hospital = Hospital.builder()
                .name(request.getName())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .phone(request.getPhone())
                .email(request.getEmail())
                .totalBeds(request.getTotalBeds())
                .build();

        hospitalRepository.save(hospital);

        return "Hospital Registered Successfully";
    }

    @Override
    public HospitalResponse<Hospital> getTopRatedHospitals() {
        List<Hospital> hospitals= hospitalRepository.findTop5ByOrderByRatingDesc();
        HospitalResponse<Hospital> response=new HospitalResponse<>();
        response.setObject(hospitals);
        response.setMessage(HealthCareConstants.SUCESSS_MESSAGE);
        return response;
    }

    @Override
    public HospitalResponse<Hospital> updateRating(Long id,Double rating) {
        List<Double> ratingList=new ArrayList<>();
        Optional<Hospital> hosp=hospitalRepository.findById(id);

        HospitalResponse<Hospital> response =new HospitalResponse<>();
        if(hosp.isPresent()){
            Hospital h=hosp.get();
            response.setMessage(HealthCareConstants.SUCESSS_MESSAGE);
//            if it is first Review
            if(ratingList.isEmpty()){
                ratingList.add(rating);
                h.setRating(rating);
            }
//            if review already exists
            else {
                double avg = ratingList.stream().mapToDouble(Double::doubleValue).average().orElseThrow(() ->new IllegalStateException(HealthCareConstants.NO_REVIEWS_SUBMITTED));
                h.setRating(avg);
            }

            response.setObject(h);
            hospitalRepository.save(h);
        }
        return response;
    }
}
