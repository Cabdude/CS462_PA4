package hardware;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PA4
 */
public class SensorLog {


    private File logFile;
    List<SensorReport> reports;

    public SensorLog() {
        long logID = System.currentTimeMillis();
        reports = Collections.synchronizedList(new ArrayList<>());
        this.logFile = new File("SensorLOG" + logID + ".txt");

        try {
            logFile.createNewFile();
        } catch (IOException ioe) {

        }
    }

    public void addReport(SensorReport report){
        synchronized (reports) {
            reports.add(report);
        }
    }


    public synchronized void UpdateLogFile() {


        try {
            if(reports.size() > 5) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(logFile));

                synchronized (reports) {
                    for (SensorReport report :
                            reports) {
                        if (report != null) {
                            bw.write(report.toString());
                            bw.newLine();
                            bw.flush();
                        }
                    }

                    bw.close();
                }
            }

        } catch (IOException ioe) {

        }
    }

}
