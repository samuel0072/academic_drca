package br.ufal.ic.academic.util;

import lombok.Getter;

@Getter
public class SystemResponse {

    private boolean error;
    private Object object;

    public void error(Object message) {
        this.error = true;
        this.object = message;
    }

    public void ok(Object message) {
        this.error = false;
        this.object = message;
    }
}
