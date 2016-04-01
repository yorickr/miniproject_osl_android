package space.imegumii.lichtapp;

/**
 * Created by imegumii on 4/1/16.
 */
public class Kaku {
    private String group;
    private String name;
    private String channel;

    private boolean state = false;

    public Kaku(String grp, String nm, String ch) {
        group = grp;
        name = nm;
        channel = ch;
    }

    public void sync() {
        MainActivity.api.updateKaku(this);
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public String getChannel() {
        return channel;
    }

    public boolean getState() {
        return state;
    }
}
