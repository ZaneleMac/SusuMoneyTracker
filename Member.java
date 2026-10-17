
public class Member {

    private String id;
    private String name;
    private boolean hasReceivedPot;

    public Member(String id, String name) {
        this.id = id;
        this.name = name;
        this.hasReceivedPot = false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isHasReceivedPot() {
        return hasReceivedPot;
    }

    public void setHasReceivedPot(boolean hasReceivedPot) {
        this.hasReceivedPot = hasReceivedPot;
    }
}
