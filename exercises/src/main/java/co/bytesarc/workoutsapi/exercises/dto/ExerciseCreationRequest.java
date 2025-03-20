package co.bytesarc.workoutsapi.exercises.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExerciseCreationRequest {
    private int userId;
    private String name;
    private String type;
    private String description;
}
