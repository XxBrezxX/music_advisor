package advisor.models;

public class Playlist {
    private String name;
    private String link;

    public Playlist() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name + "\n" + this.link + "\n";
    }
}
