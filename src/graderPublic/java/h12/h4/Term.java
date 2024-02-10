package h12.h4;

import com.fasterxml.jackson.databind.JsonNode;
import h12.template.errors.BadBitfieldException;
import h12.template.fsm.BitField;

public class Term {
    public BitField input;
    public String start;
    public String next;
    public BitField output;

    public Term(BitField input, String start, String next, BitField output) {
        this.input = input;
        this.start = start;
        this.next = next;
        this.output = output;
    }

    public static Term fromJson(final JsonNode jsonNode)  {
        try {
            return new Term(
                new BitField(jsonNode.get("input").asText()),
                jsonNode.get("start").asText(),
                jsonNode.get("next").asText(),
                new BitField(jsonNode.get("output").asText())
            );
        } catch (BadBitfieldException e) {
            throw new RuntimeException(e);
        }
    }
}
