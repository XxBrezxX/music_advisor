package advisor.utils;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class ProgramArguments {
    @Option(name = "-access", usage = "Spotify Server Path")
    private String serverPath = "https://accounts.spotify.com";
    @Option(name = "-resource", usage = "Spotify API Path")
    private String apiPath = "https://api.spotify.com";
    @Option(name = "-page", usage = "PageSize")
    private Integer pageSize = 5;

    public String getApiPath() {
//        System.out.println(apiPath);
        return apiPath;
    }

    public String getServerPath() {
        return serverPath;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }


    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
