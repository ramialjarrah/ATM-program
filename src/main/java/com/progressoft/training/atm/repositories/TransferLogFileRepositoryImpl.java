package com.progressoft.training.atm.repositories;

import java.io.*;
import java.math.BigDecimal;

public class TransferLogFileRepositoryImpl implements TransferLogFileRepository {

    private final String filePath;

    public TransferLogFileRepositoryImpl() {

        this.filePath = "/home/ramialjarrah/IdeaProjects/training-session3/src/main/resources/atm/transfer-log.txt";

    }


    public void addTransfer(String senderPin, String receiverPin, BigDecimal amount) {

        String castedAmount = amount.toString();

        String str = senderPin + " Transfers " + castedAmount + " JOD to " + receiverPin;

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true));

            bufferedWriter.write(str+ "\n");

            System.out.print(str);

            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }
}
