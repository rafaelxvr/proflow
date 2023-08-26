package dev.ravel.proflow.domain.services;

import dev.ravel.proflow.infrastructure.model.Subtask;
import dev.ravel.proflow.infrastructure.repository.SubtaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtaskServiceImpl implements SubtaskService {
    private final SubtaskRepository subtaskRepository;

    @Autowired
    public SubtaskServiceImpl(SubtaskRepository subtaskRepository) {
        this.subtaskRepository = subtaskRepository;
    }

    @Override
    public Subtask addSubtask(Subtask subtask) {
        return subtaskRepository.save(subtask);
    }

    @Override
    public Subtask getSubtaskById(int id) {
        return subtaskRepository.findById(id).orElse(null);
    }

    @Override
    public List<Subtask> getSubtasksByTaskId(int taskId) {
        return subtaskRepository.findByTaskId(taskId);
    }

    @Override
    public void deleteSubtask(int id) {
        subtaskRepository.deleteById(id);
    }

    @Override
    public Subtask updateSubtask(Subtask subtask) {
        return subtaskRepository.save(subtask);
    }
}
