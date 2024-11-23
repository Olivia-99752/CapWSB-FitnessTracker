package com.capgemini.wsb.fitnesstracker.training.internal;



import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingServiceImpl trainingService;

    private final TrainingMapper trainingMapper;

    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings()
                .stream()
                .map(trainingMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public List<TrainingDto> getTrainingByUserId(@PathVariable Long id) {
        return trainingService.getTrainingByUserId(id).stream().map(trainingMapper::toDto).toList();
    }

    @GetMapping("/activityType")
    public List<TrainingDto> getTrainingByUserId(@RequestParam ActivityType activity) {
        return trainingService.findTrainingByActivity(activity).stream().map(trainingMapper::toDto).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Training addTraining(@RequestBody TrainingDto trainingDto) throws InterruptedException {
        Training training = trainingMapper.toEntity(trainingDto);
        return trainingService.createTraining(training);
    }

    @PutMapping("/{id}")
    public Training updateTraining(@PathVariable Long id, @RequestBody TrainingDto trainingDto) {
        Training training = trainingMapper.toEntity(trainingDto);
        return trainingService.updateTraining(id, training);
    }

}
