package co.bytesarc.workoutsapi.setentries.models;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SetEntry {
    private int id;
    private int userId;
    private int exerciseId;
    private double weight;
    private int reps;
    private Instant createdAt;
    private Instant updatedAt;
}
