package h12.export;

import h12.template.fsm.Fsm;

import java.io.IOException;

/**
 * Interface which can be used to export a {@link Fsm}
 */
public interface FsmExporter {

    /**
     * Exports a {@link  Fsm}
     * @param fsm Automata which gets exported
     * @throws IOException Exception can be thrown when accessing Files
     */
    void export(Fsm fsm) throws IOException;
}
