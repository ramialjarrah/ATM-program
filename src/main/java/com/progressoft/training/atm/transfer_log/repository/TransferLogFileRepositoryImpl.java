package com.progressoft.training.atm.transfer_log.repository;

import com.progressoft.training.atm.bank.service.request.TransferRequest;

import java.io.*;
import java.math.BigDecimal;

public class TransferLogFileRepositoryImpl implements TransferLogFileRepository {

    private final String filePath;

    public TransferLogFileRepositoryImpl() {

        this.filePath = "/home/ramialjarrah/IdeaProjects/training-session3/src/main/resources/atm/transfer-log.txt";

    }


    @Override
    public void addTransfer(TransferRequest transferRequest) {

        String castedAmount = transferRequest.amount().toString();

        String str = transferRequest.senderPin() + " Transfers " + castedAmount + " JOD to " + transferRequest.receiverPin();

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true));

            bufferedWriter.write(str + "\n");

            System.out.print(str);

            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }
}
