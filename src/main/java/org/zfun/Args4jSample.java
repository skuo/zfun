package org.zfun;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * User: skuo
 * Date: May 3, 2011
 */
public class Args4jSample {
   
    @Option(name="-name",usage="sets a name")
    public String name = "default";
   
    @Option(name="-maxNum",usage="specifies max num of profiles to be processed")
    public long maxNum;
    
    @Option(name="-disableTdbReport", usage="skips tdb report task")
    public boolean disableTdbReport = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(long maxNum) {
        this.maxNum = maxNum;
    }

    public boolean isDisableTdbReport() {
        return disableTdbReport;
    }

    public void setDisableTdbReport(boolean disableTdbReport) {
        this.disableTdbReport = disableTdbReport;
    }

    public void exec() {
        System.out.format("name=%s, maxNum=%d, disableTdbReport=%b\n", name, maxNum, disableTdbReport);
    }
    
    public static void main(String[] args) {
        Args4jSample sample = new Args4jSample();
        CmdLineParser parser = new CmdLineParser(sample);
        try {
            parser.parseArgument(args);
            sample.exec();
        } catch (CmdLineException e) {
            // handling of wrong arguments
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
        }
    }
    
}
