package main.goodgatherbackend.services;

import lombok.AllArgsConstructor;
import main.goodgatherbackend.dtos.CauseDTO;
import main.goodgatherbackend.mappers.CauseMapper;
import main.goodgatherbackend.models.Cause;
import main.goodgatherbackend.models.Event;
import main.goodgatherbackend.models.Ticket;
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

    public Double getCauseFunds(Integer id) {
        Cause cause = causeRepository.findById(id).orElseThrow();
        List<Ticket> tickets = cause.getEvents().stream().map(Event::getTickets).flatMap(List::stream).toList();
        return tickets.stream().mapToDouble(Ticket::getPrice).sum();
    }

    public List<CauseDTO> getCausesInUsersRange(Integer userId) {
        List<Cause> causes = causeRepository.getCausesInUsersRange(userId);
        return causeMapper.toDTOList(causes);
    }

    public CauseDTO createCause(CauseDTO causeDTO) {
        Cause cause = causeMapper.toModel(causeDTO);
        cause = causeRepository.save(cause);
        return causeMapper.toDTO(cause);
    }
}
