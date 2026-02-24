package nimblix.in.HealthCareHub.response;

import lombok.Data;
import nimblix.in.HealthCareHub.model.Hospital;

import java.util.List;

@Data
public class HospitalResponse<Hospital> {

    private String Message;
    private List<Hospital> hospitals;
}
