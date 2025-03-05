package co.bytesarc.workoutsapi.exercises.services;

import co.bytesarc.workoutsapi.exercises.models.Exercise;
import co.bytesarc.workoutsapi.exercises.repositories.ExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {

    private  final ExerciseRepository exerciseRepository;


    public ExerciseService(ExerciseRepository exerciseRepository){
        this.exerciseRepository = exerciseRepository;
    }

    public List<Exercise> getExercises(){
        return exerciseRepository.getExercises();
    }

}
