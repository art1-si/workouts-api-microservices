package co.bytesarc.workoutsapi.setentries.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateSetEntryRequest {
    private int userId;
    private int exerciseId;
    private double weight;
    private int reps;
    private String note;
}
