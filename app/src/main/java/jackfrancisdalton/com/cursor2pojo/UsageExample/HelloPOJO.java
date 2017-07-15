package jackfrancisdalton.com.cursor2pojo.UsageExample;

import jackfrancisdalton.com.cursor2pojo.C2PColumnInfo;

/**
 * Created by Jack F. Dalton on 0015 15 07 2017.
 */

public class HelloPOJO {

    @C2PColumnInfo(name = "name_column")
    private String name;

    @C2PColumnInfo(name = "name_column")
    private int value;

    @C2PColumnInfo(name = "name_column")
    private boolean predicate;

    public HelloPOJO(String name, int value, boolean predicate) {
        this.name = name;
        this.value = value;
        this.predicate = predicate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isPredicate() {
        return predicate;
    }

    public void setPredicate(boolean predicate) {
        this.predicate = predicate;
    }
}
