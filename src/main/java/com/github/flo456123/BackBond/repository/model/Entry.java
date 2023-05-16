package com.github.flo456123.BackBond.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_entry")
public class Entry {

    @Id
    @GeneratedValue
    private Integer id;

    private LocalDateTime newDate;

    private double BC_1MONTH;
    private double BC_2MONTH;
    private double BC_3MONTH;
    private double BC_4MONTH;
    private double BC_6MONTH;
    private double BC_1YEAR;
    private double BC_2YEAR;
    private double BC_3YEAR;
    private double BC_5YEAR;
    private double BC_7YEAR;
    private double BC_10YEAR;
    private double BC_20YEAR;
    private double BC_30YEAR;

}
