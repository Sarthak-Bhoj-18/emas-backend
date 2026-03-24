package com.rscoe.emas.service.impl;

import com.rscoe.emas.dto.request.CreateMeetingRequest;
import com.rscoe.emas.entity.Meeting;
import com.rscoe.emas.entity.MeetingAttendance;
import com.rscoe.emas.entity.MeetingAttendanceTemp;
import com.rscoe.emas.repository.MeetingAttendanceRepository;
import com.rscoe.emas.repository.MeetingAttendanceTempRepository;
import com.rscoe.emas.repository.MeetingRepository;
import com.rscoe.emas.repository.UserRepository;
import com.rscoe.emas.service.MeetingService;
import com.rscoe.emas.service.NotificationService;
import com.rscoe.emas.entity.User;

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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public void createMeeting(CreateMeetingRequest request, String adminEmail) {

        Meeting meeting = new Meeting();

        meeting.setTitle(request.getTitle());
        meeting.setDescription(request.getDescription());
        meeting.setMeetingTime(request.getMeetingTime());
        meeting.setCreatedBy(adminEmail);

        meetingRepository.save(meeting);

        List<User> users = userRepository.findAll();
        for (User u : users) {
             if (u.isActive()) {
                 notificationService.sendNotification(u.getEmail(), "New Meeting Scheduled", "A new meeting '" + meeting.getTitle() + "' has been scheduled for " + meeting.getMeetingTime().toString());
             }
        }
    }

    @Override
    public List<com.rscoe.emas.dto.response.MeetingResponse> getAllMeetings() {
        return meetingRepository.findAllByOrderByMeetingTimeDesc().stream().map(m -> {
            com.rscoe.emas.dto.response.MeetingResponse res = new com.rscoe.emas.dto.response.MeetingResponse();
            res.setId(m.getId());
            res.setTitle(m.getTitle());
            res.setDescription(m.getDescription());
            res.setMeetingTime(m.getMeetingTime());
            res.setCreatedBy(m.getCreatedBy());
            return res;
        }).toList();
    }

    @Override
    public List<com.rscoe.emas.dto.response.MeetingAttendeeDto> getMeetingAttendees(Long meetingId) {
        List<MeetingAttendanceTemp> tempList = tempRepository.findByMeetingId(meetingId);
        return tempList.stream().map(temp -> {
            String name = userRepository.findByEmail(temp.getEmployeeEmail())
                    .map(User::getName)
                    .orElse("Unknown");
            return new com.rscoe.emas.dto.response.MeetingAttendeeDto(temp.getEmployeeEmail(), name);
        }).toList();
    }

    @Override
    public void removeProxyAttendance(Long meetingId, String employeeEmail) {
        tempRepository.deleteByMeetingIdAndEmployeeEmail(meetingId, employeeEmail);
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