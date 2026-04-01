package com.example.employeemanagementrestapis.repositories;

import com.example.employeemanagementrestapis.models.LeaveRequest;
import com.example.employeemanagementrestapis.models.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
	@Query("""
			select count(lr) > 0
			from LeaveRequest lr
			where lr.employee.id = :employeeId
			  and lr.leaveStatus in :statuses
			  and lr.startDate <= :endDate
			  and lr.endDate >= :startDate
			""")
	boolean existsOverlappingRequest(
			@Param("employeeId") Long employeeId,
			@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate,
			@Param("statuses") Collection<LeaveStatus> statuses
	);
}
