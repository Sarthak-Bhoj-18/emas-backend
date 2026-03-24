package com.rscoe.emas.service;

import com.rscoe.emas.dto.request.CreatePolicyRequest;
import com.rscoe.emas.dto.response.PolicyResponse;
import java.util.List;

public interface PolicyService {
    PolicyResponse createPolicy(CreatePolicyRequest request, String adminEmail);
    List<PolicyResponse> getAllPolicies(String employeeEmail);
    PolicyResponse getPolicyById(Long id, String employeeEmail);
    void acknowledgePolicy(Long id, String employeeEmail);
}
