package com.rscoe.emas.util;

import com.rscoe.emas.service.QrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QrScheduler {

    @Autowired
    private QrService qrService;

    @Scheduled(fixedRate = 30000) // every 30 sec
    public void generateQrAutomatically() {
        qrService.generateNewQr();
        System.out.println("New QR generated");
    }
}