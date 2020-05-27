package service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class AuditService implements Runnable {

    String action;

    public AuditService(String action) {
         this.action = action;
    }

    @Override
    public void run() {
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter("AUDIT", true))
        ) {
            writer.newLine();
            writer.write(action + " " + new Date() + " " + Thread.currentThread().getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
