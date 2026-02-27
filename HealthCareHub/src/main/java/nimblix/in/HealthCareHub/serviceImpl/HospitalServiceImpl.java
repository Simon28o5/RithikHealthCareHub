package nimblix.in.HealthCareHub.serviceImpl;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.model.Hospital;
import nimblix.in.HealthCareHub.repository.HospitalRepository;
import nimblix.in.HealthCareHub.request.HospitalRegistrationRequest;
import nimblix.in.HealthCareHub.response.HospitalResponse;
import nimblix.in.HealthCareHub.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class HospitalServiceImpl implements HospitalService {

    static Map<Long, List<Double>> ratingMap = new HashMap<>();

    @Autowired
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
        List<Hospital> hospitals = hospitalRepository.findTop5ByOrderByRatingDesc();
        HospitalResponse<Hospital> response = new HospitalResponse<>();
        response.setObject(hospitals);
        response.setMessage(HealthCareConstants.SUCESSS_MESSAGE);
        return response;
    }

    @Override
    public HospitalResponse<Hospital> updateRating(Long id, Double rating) {

        Optional<Hospital> hosp = hospitalRepository.findById(id);

        HospitalResponse<Hospital> response = new HospitalResponse<>();

        if (hosp.isEmpty()) {
            response.setMessage(HealthCareConstants.NO_HOSPITAL_FOUND);
            return response;
        } else {
            Hospital h = hosp.get();

            if (!ratingMap.containsKey(id))
                ratingMap.put(id, new ArrayList<>());

            ratingMap.get(id).add(rating);
            double avg = ratingMap.get(id).stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            h.setRating(avg);
            hospitalRepository.save(h);

            response.setMessage(HealthCareConstants.REVIEWS_SUBMITTED);
            response.setObject(h);

            return response;
        }
    }
}
