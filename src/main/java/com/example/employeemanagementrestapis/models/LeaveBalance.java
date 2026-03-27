package com.example.employeemanagementrestapis.models;

import com.example.employeemanagementrestapis.models.enums.LeaveType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "leave_balance", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"employee_id", "leave_type"})
})
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Enumerated(EnumType.STRING)
    @Column(name = "leave_type", nullable = false)
    private LeaveType leaveType;

    @Column(name = "total_allowed", nullable = false)
    private int totalAllowed;

    @Column(name = "used_days", nullable = false)
    private int usedDays = 0;

    public int getRemainingDays() {
        return totalAllowed - usedDays;
    }
}
