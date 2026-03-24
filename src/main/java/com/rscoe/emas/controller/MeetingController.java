package com.rscoe.emas.controller;

import com.rscoe.emas.dto.request.CreateMeetingRequest;
import com.rscoe.emas.service.MeetingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public String createMeeting(@RequestBody CreateMeetingRequest request,
                                Authentication authentication){

        meetingService.createMeeting(request, authentication.getName());

        return "Meeting created";
    }

    @GetMapping
    public java.util.List<com.rscoe.emas.dto.response.MeetingResponse> getAllMeetings() {
        return meetingService.getAllMeetings();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{meetingId}/attendees")
    public java.util.List<com.rscoe.emas.dto.response.MeetingAttendeeDto> getAttendees(@PathVariable Long meetingId) {
        return meetingService.getMeetingAttendees(meetingId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{meetingId}/attendees/{email}")
    public String removeProxy(@PathVariable Long meetingId, @PathVariable String email) {
        meetingService.removeProxyAttendance(meetingId, email);
        return "Removed successfully";
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/{meetingId}/present")
    public String markPresent(@PathVariable Long meetingId,
                              Authentication authentication){

        meetingService.markPresent(meetingId, authentication.getName());

        return "Presence recorded";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{meetingId}/confirm")
    public String confirm(@PathVariable Long meetingId){

        return meetingService.confirmAttendance(meetingId);
    }
}