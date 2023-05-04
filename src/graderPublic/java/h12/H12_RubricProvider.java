package h12;

import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;

public class H12_RubricProvider implements RubricProvider {

    public static final Rubric RUBRIC = Rubric.builder()
        .title("H12")
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
