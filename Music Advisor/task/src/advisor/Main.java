package advisor;

import advisor.controllers.MenuApp;
import advisor.utils.ProgramArguments;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

public class Main {
    public static void main(String[] args) {
        ProgramArguments programArguments = new ProgramArguments();
        CmdLineParser cmdLineParser = new CmdLineParser(programArguments);
        try{
            cmdLineParser.parseArgument(args);
            MenuApp menuApp = new MenuApp(programArguments);
            menuApp.startUI();
        } catch (CmdLineException e) {
            throw new RuntimeException(e);
        }
    }
}

