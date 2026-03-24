package com.rscoe.emas.repository;

import com.rscoe.emas.entity.MeetingAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingAttendanceRepository extends JpaRepository<MeetingAttendance, Long> {
    java.util.List<MeetingAttendance> findByMeetingId(Long meetingId);
    java.util.Optional<MeetingAttendance> findByMeetingIdAndEmployeeEmail(Long meetingId, String employeeEmail);
}