package com.rscoe.emas.service;

import com.rscoe.emas.dto.request.CreateMeetingRequest;

public interface MeetingService {

    void createMeeting(CreateMeetingRequest request, String adminEmail);

    void markPresent(Long meetingId, String employeeEmail);

    java.util.List<com.rscoe.emas.dto.response.MeetingResponse> getAllMeetings();

    java.util.List<com.rscoe.emas.dto.response.MeetingAttendeeDto> getMeetingAttendees(Long meetingId);

    void removeProxyAttendance(Long meetingId, String employeeEmail);

    String confirmAttendance(Long meetingId);

}