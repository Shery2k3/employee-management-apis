package com.example.employeemanagementrestapis.services;

import com.example.employeemanagementrestapis.models.Employee;
import com.example.employeemanagementrestapis.models.LeaveBalance;
import com.example.employeemanagementrestapis.models.enums.LeaveType;
import com.example.employeemanagementrestapis.repositories.LeaveBalanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.Map;

@Service
public class LeaveBalanceService {
	private static final Map<LeaveType, Integer> DEFAULT_ALLOWANCES = new EnumMap<>(LeaveType.class);

	static {
		DEFAULT_ALLOWANCES.put(LeaveType.ANNUAL, 14);
		DEFAULT_ALLOWANCES.put(LeaveType.SICK, 10);
		DEFAULT_ALLOWANCES.put(LeaveType.CASUAL, 10);
		DEFAULT_ALLOWANCES.put(LeaveType.UNPAID, 365);
		DEFAULT_ALLOWANCES.put(LeaveType.MATERNITY, 90);
	}

	private final LeaveBalanceRepository leaveBalanceRepository;

	public LeaveBalanceService(LeaveBalanceRepository leaveBalanceRepository) {
		this.leaveBalanceRepository = leaveBalanceRepository;
	}

	@Transactional
	public LeaveBalance getOrCreateBalance(Employee employee, LeaveType leaveType) {
		return leaveBalanceRepository.findByEmployeeIdAndLeaveType(employee.getId(), leaveType)
				.orElseGet(() -> leaveBalanceRepository.save(
						LeaveBalance.builder()
								.employee(employee)
								.leaveType(leaveType)
								.totalAllowed(DEFAULT_ALLOWANCES.getOrDefault(leaveType, 0))
								.usedDays(0)
								.build()
				));
	}

}
