package advisor.providers;

public class SharedData {
    private String authCode;
    private boolean hasCode = false;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public void setHasCode(boolean hasCode) {
        this.hasCode = hasCode;
    }

    public boolean isHasCode() {
        return hasCode;
    }
}
