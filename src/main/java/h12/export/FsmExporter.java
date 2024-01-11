package h12.export;

import h12.template.fsm.Fsm;

import java.io.IOException;

public interface FsmExporter {
    void export(Fsm fsm) throws IOException;
}
