package h12.fsm;

public interface StateFactory {
    State get(String name);
    int size();
}
