package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// TODO: Provide Impl
@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingProvider, TrainingService {

    private final TrainingRepository trainingRepository;

    @Override
    public Optional<User> getTraining(final Long trainingId) {
        throw new UnsupportedOperationException("Not finished yet");
    }

    @Override
    public List<Training> findAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> getTrainingByUserId(Long userId) {
        return trainingRepository.findByUserId(userId);
    }

    @Override
    public List<Training> findTrainingByActivity(ActivityType activity) {
        return trainingRepository.findByActivity(activity);
    }

    @Override
    public Training createTraining(Training training) {
        log.info("Creating Training {}", training);
        if (training.getId() != null) {
            throw new IllegalArgumentException("Training has already DB ID, update is not permitted!");
        }
        return trainingRepository.save(training);
    }

    private void deleteTraining(final Long trainingId) {
        log.info("Deleting Training {}", trainingId);
        final Optional<Training> training = trainingRepository.findById(trainingId);
        training.ifPresent(trainingRepository::delete);
    }

    @Override
    public Training updateTraining(final Long trainingId, final Training updatedTraining) {

        final Optional<Training> training = trainingRepository.findById(trainingId);
        log.info("Updating Training {}", training);
        if(training.isPresent()) {
            deleteTraining(trainingId);
            createTraining(updatedTraining);
            return updatedTraining;
        }
        else{
            throw new IllegalArgumentException("User does not exist");
        }
    }


}
