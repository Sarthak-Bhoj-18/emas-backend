package com.rscoe.emas.service.impl;

import com.rscoe.emas.dto.request.CreatePolicyRequest;
import com.rscoe.emas.dto.response.PolicyResponse;
import com.rscoe.emas.entity.Policy;
import com.rscoe.emas.entity.PolicyApproval;
import com.rscoe.emas.enums.PolicyApprovalStatus;
import com.rscoe.emas.repository.PolicyApprovalRepository;
import com.rscoe.emas.repository.PolicyRepository;
import com.rscoe.emas.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private PolicyApprovalRepository policyApprovalRepository;

    @Override
    public PolicyResponse createPolicy(CreatePolicyRequest request, String adminEmail) {
        Policy policy = new Policy();
        policy.setTitle(request.getTitle());
        policy.setContent(request.getContent());
        policy.setAdminSignature(request.getAdminSignature());
        policy.setCreatedBy(adminEmail);
        Policy savedPolicy = policyRepository.save(policy);
        return mapToResponse(savedPolicy, adminEmail);
    }

    @Override
    public List<PolicyResponse> getAllPolicies(String employeeEmail) {
        return policyRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(policy -> mapToResponse(policy, employeeEmail))
                .collect(Collectors.toList());
    }

    @Override
    public PolicyResponse getPolicyById(Long id, String employeeEmail) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found"));
        return mapToResponse(policy, employeeEmail);
    }

    @Override
    public void acknowledgePolicy(Long id, String employeeEmail) {
        PolicyApproval approval = policyApprovalRepository.findByPolicyIdAndEmployeeEmail(id, employeeEmail)
                .orElse(new PolicyApproval());
        
        approval.setPolicyId(id);
        approval.setEmployeeEmail(employeeEmail);
        approval.setStatus(PolicyApprovalStatus.ACKNOWLEDGED);
        approval.setAcknowledgedAt(LocalDateTime.now());
        
        policyApprovalRepository.save(approval);
    }

    private PolicyResponse mapToResponse(Policy policy, String employeeEmail) {
        PolicyResponse response = new PolicyResponse();
        response.setId(policy.getId());
        response.setTitle(policy.getTitle());
        response.setContent(policy.getContent());
        response.setAdminSignature(policy.getAdminSignature());
        response.setCreatedBy(policy.getCreatedBy());
        response.setCreatedAt(policy.getCreatedAt());

        Optional<PolicyApproval> approval = policyApprovalRepository.findByPolicyIdAndEmployeeEmail(policy.getId(), employeeEmail);
        if (approval.isPresent()) {
            response.setStatus(approval.get().getStatus());
            response.setAcknowledgedAt(approval.get().getAcknowledgedAt());
        } else {
            response.setStatus(PolicyApprovalStatus.PENDING);
        }

        return response;
    }
}
