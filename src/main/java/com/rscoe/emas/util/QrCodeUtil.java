package com.rscoe.emas.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QrCodeUtil {

    public static void generateQRCode(String text, String filePath) throws Exception {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(
                text,
                BarcodeFormat.QR_CODE,
                300,
                300
        );

        Path path = FileSystems.getDefault().getPath(filePath);

        com.google.zxing.client.j2se.MatrixToImageWriter.writeToPath(
                bitMatrix,
                "PNG",
                path
        );
    }
}