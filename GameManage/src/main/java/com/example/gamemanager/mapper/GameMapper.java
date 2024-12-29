package com.example.gamemanager.mapper;

import com.example.gamemanager.dto.GameDto;
import com.example.gamemanager.entity.GameEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GameMapper {

    GameDto mapEntityToDto(GameEntity game);

    @Mapping(target = "timePlayed", source = "timePlayed", qualifiedByName = "timePlayedNamed")
    GameEntity mapDtoToEntity(GameDto gameDto);

    @Named("timePlayedNamed")
    default Map<LocalDate, Integer> timePlayedNamed(Map<LocalDate, Integer> timePlayed) {
        return timePlayed.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
