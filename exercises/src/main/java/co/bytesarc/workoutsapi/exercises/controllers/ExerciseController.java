package co.bytesarc.workoutsapi.exercises.controllers;

import co.bytesarc.workoutsapi.exercises.models.Exercise;
import co.bytesarc.workoutsapi.exercises.services.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService){
        this.exerciseService = exerciseService;
    }


    @GetMapping("/exercise")
    public ResponseEntity<List<Exercise>> getExercises(@RequestParam int userId){
       List<Exercise> exercises = exerciseService.getExercises();
       return ResponseEntity.ok(exercises);
    }
}
