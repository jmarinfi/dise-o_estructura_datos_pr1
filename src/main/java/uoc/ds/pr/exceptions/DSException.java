package uoc.ds.pr.exceptions;

import java.io.Serial;

public class DSException extends Exception {

    @Serial
    private static final long serialVersionUID = -2577150645305791318L;

    public DSException() {
        super();
    }

    public DSException(String msg) {
        super(msg);
    }

}
