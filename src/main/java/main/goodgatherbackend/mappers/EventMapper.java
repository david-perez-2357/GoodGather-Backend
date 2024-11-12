package main.goodgatherbackend.mappers;

import main.goodgatherbackend.dtos.EventDTO;
import main.goodgatherbackend.models.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public abstract class EventMapper {
    @Mapping(target = "id", source = "id")
    public abstract EventDTO toDTO(Event event);

    @Mapping(target = "id", ignore = true)
    public abstract Event toModel(EventDTO eventDTO);

    public abstract List<EventDTO> toDTOList(List<Event> events);
    public abstract List<Event> toModelList(List<EventDTO> eventDTOs);
}
