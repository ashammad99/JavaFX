/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Ahmed Hammad
 */
public class FileStorage {

    public static String dir = System.getProperty("user.dir");

    public static File logFile = new File(dir + "\\src\\log.txt");

    public static void writeLog(String sqlStatment) {
        java.sql.Timestamp timestamp = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
        String logEntry = sqlStatment + "       [" + timestamp + "]\n";
        try {
            FileWriter myWriter = new FileWriter(logFile,true);
            myWriter.append(logEntry);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
