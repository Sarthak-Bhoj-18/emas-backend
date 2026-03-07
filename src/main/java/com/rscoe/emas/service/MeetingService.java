package com.rscoe.emas.service;

import com.rscoe.emas.dto.request.CreateMeetingRequest;

public interface MeetingService {

    void createMeeting(CreateMeetingRequest request, String adminEmail);

    void markPresent(Long meetingId, String employeeEmail);

    String confirmAttendance(Long meetingId);

}