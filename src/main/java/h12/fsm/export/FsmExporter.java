package h12.fsm.export;

import h12.fsm.Fsm;

import java.io.IOException;

public interface FsmExporter {
    void export(Fsm fsm) throws IOException;
}
