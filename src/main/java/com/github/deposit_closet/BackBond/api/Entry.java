package com.github.deposit_closet.BackBond.api;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    private LocalDate newDate;

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
