package com.example.employeemanagementrestapis.models;

import com.example.employeemanagementrestapis.models.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "assignment")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by", nullable = false)
    private Employee assignedBy;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime assignedAt;
}
