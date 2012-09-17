package org.zfun;
/**
 * User: skuo Date: Apr 5, 2011
 */
public class PlaceHolder {
    int id;
    String name;
    String version;

    // represent a new field
    // Required by gson
    public PlaceHolder() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
