package com.example.employeemanagement.data.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime signIn;
    private LocalDateTime signOut;
    private LocalDateTime sickLeave;
    private LocalDateTime absence;
    @ManyToOne
    @JoinColumn
    private Employee employee;
}
