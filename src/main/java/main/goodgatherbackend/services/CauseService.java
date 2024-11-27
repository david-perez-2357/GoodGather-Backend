package main.goodgatherbackend.services;

import lombok.AllArgsConstructor;
import main.goodgatherbackend.dtos.CauseDTO;
import main.goodgatherbackend.mappers.CauseMapper;
import main.goodgatherbackend.models.Cause;
import main.goodgatherbackend.repositories.CauseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CauseService {
    private CauseRepository causeRepository;
    private CauseMapper causeMapper;

    public List<CauseDTO> getAll() {
        List<Cause> causes = causeRepository.findAll();
        return causeMapper.toDTOList(causes);
    }

    public CauseDTO getById(Integer id) {
        Cause cause = causeRepository.findById(id).orElseThrow();
        return causeMapper.toDTO(cause);
    }
}
