package com.rscoe.emas.service.impl;

import com.rscoe.emas.dto.request.CreateMeetingRequest;
import com.rscoe.emas.entity.Meeting;
import com.rscoe.emas.entity.MeetingAttendance;
import com.rscoe.emas.entity.MeetingAttendanceTemp;
import com.rscoe.emas.repository.MeetingAttendanceRepository;
import com.rscoe.emas.repository.MeetingAttendanceTempRepository;
import com.rscoe.emas.repository.MeetingRepository;
import com.rscoe.emas.service.MeetingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingAttendanceTempRepository tempRepository;

    @Autowired
    private MeetingAttendanceRepository finalRepository;

    @Override
    public void createMeeting(CreateMeetingRequest request, String adminEmail) {

        Meeting meeting = new Meeting();

        meeting.setTitle(request.getTitle());
        meeting.setDescription(request.getDescription());
        meeting.setMeetingTime(request.getMeetingTime());
        meeting.setCreatedBy(adminEmail);

        meetingRepository.save(meeting);
    }

    @Override
    public void markPresent(Long meetingId, String employeeEmail) {

        MeetingAttendanceTemp temp = new MeetingAttendanceTemp();

        temp.setMeetingId(meetingId);
        temp.setEmployeeEmail(employeeEmail);

        tempRepository.save(temp);
    }

    @Override
    public String confirmAttendance(Long meetingId) {

        List<MeetingAttendanceTemp> tempList =
                tempRepository.findByMeetingId(meetingId);

        for(MeetingAttendanceTemp temp : tempList){

            MeetingAttendance finalRecord = new MeetingAttendance();

            finalRecord.setMeetingId(temp.getMeetingId());
            finalRecord.setEmployeeEmail(temp.getEmployeeEmail());

            finalRepository.save(finalRecord);
        }

        tempRepository.deleteAll(tempList);

        return "Meeting attendance confirmed";
    }
}