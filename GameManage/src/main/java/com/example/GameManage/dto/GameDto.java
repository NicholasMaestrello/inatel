package com.example.GameManage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

@Data
public class GameDto implements Serializable {
    private String id;
    private String publisherId;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Map<LocalDate, Integer> timePlayed;
}
