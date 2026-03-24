package com.rscoe.emas.repository;

import com.rscoe.emas.entity.PolicyApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface PolicyApprovalRepository extends JpaRepository<PolicyApproval, Long> {
    Optional<PolicyApproval> findByPolicyIdAndEmployeeEmail(Long policyId, String employeeEmail);
    List<PolicyApproval> findByEmployeeEmail(String employeeEmail);
}
