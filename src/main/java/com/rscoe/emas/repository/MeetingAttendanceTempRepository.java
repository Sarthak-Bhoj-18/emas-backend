package com.rscoe.emas.repository;

import com.rscoe.emas.entity.MeetingAttendanceTemp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingAttendanceTempRepository extends JpaRepository<MeetingAttendanceTemp, Long> {

    List<MeetingAttendanceTemp> findByMeetingId(Long meetingId);

    java.util.Optional<MeetingAttendanceTemp> findByMeetingIdAndEmployeeEmail(Long meetingId, String employeeEmail);

    @org.springframework.transaction.annotation.Transactional
    void deleteByMeetingIdAndEmployeeEmail(Long meetingId, String employeeEmail);

}