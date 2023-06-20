package advisor.models;

public class Featured {
    private String name;
    private String link;

    public Featured() {
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name + "\n" + this.link + "\n";
    }
}
