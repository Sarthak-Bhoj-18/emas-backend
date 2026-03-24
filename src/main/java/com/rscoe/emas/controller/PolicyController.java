package com.rscoe.emas.controller;

import com.rscoe.emas.dto.request.CreatePolicyRequest;
import com.rscoe.emas.dto.response.PolicyResponse;
import com.rscoe.emas.service.PdfGeneratorService;
import com.rscoe.emas.service.PolicyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private static final Logger logger = LoggerFactory.getLogger(PolicyController.class);

    @Autowired
    private PolicyService policyService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PolicyResponse> createPolicy(@RequestBody CreatePolicyRequest request, Authentication authentication) {
        logger.info("Received request to create policy: {} by user: {}", request.getTitle(), authentication.getName());
        PolicyResponse response = policyService.createPolicy(request, authentication.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PolicyResponse>> getAllPolicies(Authentication authentication) {
        return ResponseEntity.ok(policyService.getAllPolicies(authentication.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolicyResponse> getPolicyById(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(policyService.getPolicyById(id, authentication.getName()));
    }

    @PostMapping("/{id}/acknowledge")
    public ResponseEntity<String> acknowledgePolicy(@PathVariable Long id, Authentication authentication) {
        policyService.acknowledgePolicy(id, authentication.getName());
        return ResponseEntity.ok("Policy acknowledged successfully");
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id, Authentication authentication) {
        logger.info("Received request to download PDF for policy: {} by user: {}", id, authentication.getName());
        PolicyResponse policy = policyService.getPolicyById(id, authentication.getName());
        
        Map<String, Object> data = new HashMap<>();
        data.put("policy", policy);
        data.put("employeeEmail", authentication.getName());
        
        byte[] pdfBytes = pdfGeneratorService.generatePdfFromHtml("policy-template", data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=policy_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
