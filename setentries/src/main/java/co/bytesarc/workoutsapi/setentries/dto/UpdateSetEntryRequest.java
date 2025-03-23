package co.bytesarc.workoutsapi.setentries.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateSetEntryRequest {
    private Double weight;
    private Integer reps;
}
