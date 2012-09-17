package org.zfun;

import java.util.ArrayList;
import java.util.List;

/**
 * User: skuo
 * Date: Oct 6, 2011
 */
public class PlaceHolders {
    private List<PlaceHolder> holders = new ArrayList<PlaceHolder>();

    public List<PlaceHolder> getHolders() {
        return holders;
    }

    public void setHolders(List<PlaceHolder> holders) {
        this.holders = holders;
    }
    
    public void add(PlaceHolder holder) {
        holders.add(holder);
    }
}
