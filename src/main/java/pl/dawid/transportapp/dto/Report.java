package pl.dawid.transportapp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public abstract class Report {
    private LocalDate start;
    private LocalDate end;
}
