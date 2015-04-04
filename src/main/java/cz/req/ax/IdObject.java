package cz.req.ax;

import java.io.Serializable;

public interface IdObject<T extends Serializable> extends Serializable {

    T getId();

}
