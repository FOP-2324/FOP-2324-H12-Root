package h12;

import h12.h1.FileSystemIOFactoryTransformer;
import h12.h1.TutorTests_H1_1_FileSystemIOFactoryTest;
import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;
import org.sourcegrade.jagr.api.testing.RubricConfiguration;

import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.criterion;

public class H12_RubricProvider implements RubricProvider {

    public static final Rubric RUBRIC = Rubric.builder()
        .title("H12 | Automaten parsen")
        .addChildCriteria(
            Criterion.builder()
                .shortDescription("H1 | Dateien lesbar machen")
                .addChildCriteria(
                    criterion(
                        "Die Methoden createReader() und supportsReader() sind vollständig korrekt.",
                        JUnitTestRef.ofMethod(() -> TutorTests_H1_1_FileSystemIOFactoryTest.class.getMethod("testFileSystemIOFactoryReader"))
                    ),
                    criterion(
                        "Die Methoden createWriter() und supportsWriter() sind vollständig korrekt.",
                        JUnitTestRef.ofMethod(() -> TutorTests_H1_1_FileSystemIOFactoryTest.class.getMethod("testFileSystemIOFactoryWriter"))
                    )
                )
                .build(),
            Criterion.builder()
                .shortDescription("H2 | Kommentare entfernen")
                .addChildCriteria(
                    Criterion.builder()
                        .shortDescription("H2.1 | Lookahead")
                        .addChildCriteria(
                            criterion(
                                "Das Einlesen einer Zeile ohne Kommentar in Puffer funktioniert korrekt."
                            ),
                            criterion(
                                "Der Sonderfall, dass eine Zeile nur aus Kommentaren besteht wird korrekt behandelt."
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H2.2 | Zeichenstrom nutzen")
                        .addChildCriteria(
                            criterion(
                                "Die Methoden hasNext() und peek() sind vollständig korrekt."
                            ),
                            criterion(
                                "Die Methode read() ist vollständig korrekt."
                            )
                        )
                        .build()
                )
                .build(),
            Criterion.builder()
                .shortDescription("H3 | Wörter erkennen ")
                .addChildCriteria(
                    criterion(
                        "Die Methode hasNext() ist vollständig korrekt."
                    ),
                    criterion(
                        "Ein Aufruf der Methode scan() liefert korrekt das aktuelle Zeichen zurück."
                    ),
                    criterion(
                        "Beim Aufruf der Methode scan() wird das nächste Zeichen korrekt eingelesen."
                    ),
                    criterion(
                        "Das Ende der Datei wird korrekt behandelt."
                    )
                )
                .build(),
            Criterion.builder()
                .shortDescription("H4 | Parser")
                .addChildCriteria(
                    Criterion.builder()
                        .shortDescription("H4.1 | Header parsen")
                        .addChildCriteria(
                            criterion(
                                "Die Methoden parseOutputWidth(), parseNumberOfTerms() und parseNumberOfStates() sind vollständig korrekt (abgesehen von Exceptionwurf)."
                            ),
                            criterion(
                                "Die Methode parseInitialState() ist vollständig korrekt."
                            ),
                            criterion(
                                "Eine BadNumberException bzw. BadIDentifierException wird korrekt geworfen, wenn dies gefordert ist."
                            ),
                            criterion(
                                "Die Methode parseHeader() ruft jeweils korrekt die entsprechende Untermethode zum erhaltenen Token auf."
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H4.2 | Term parsen")
                        .addChildCriteria(
                            criterion(
                                "Die Methode parseTerm() kann einen Übergang korrekt parsen."
                            ),
                            criterion(
                                "Die Methode parseTerms() ist vollständig korrekt."
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H4.3 | Fsm parsen ")
                        .addChildCriteria(
                            criterion(
                                "Die Methode parseFsm() ist vollständig korrekt."
                            )
                        )
                        .build()
                )
                .build(),
            Criterion.builder()
                .shortDescription("H5 | Builder")
                .addChildCriteria(
                    Criterion.builder()
                        .shortDescription("H5.1 | Header verstehen")
                        .addChildCriteria(
                            criterion(
                                "Die Methoden set{OutputSize, NumberOfTerms, NumberOfStates}() sind vollständig korrekt. (inkl. ParameterAlreadySpecified Exceptionwurf)"
                            ),
                            criterion(
                                "Die Methoden setInitialState ist vollständig korrekt."
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H5.2 | Terms verarbeiten")
                        .addChildCriteria(
                            criterion(
                                "Die Methode addTerm ist vollständig korrekt."
                            ),
                            criterion(
                                "StateFactory wird genutzt"
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H5.3 | finishHeader und finishFsm")
                        .addChildCriteria(
                            criterion(
                                "Die Methoden finishHeader() und finishFsm() sind vollständig korrekt."
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H5.4 | getFsm")
                        .addChildCriteria(
                            criterion(
                                "Die Fsm wird zurückgegeben wenn die Flag gesetzt ist, andernfalls wird eine Exception geworfen."
                            )
                        )
                        .build()
                )
                .build(),
            Criterion.builder()
                .shortDescription("H6 | Kiss2 Exporter")
                .addChildCriteria(
                    criterion(
                        "Alle Header-Parameter werden korrekt geschrieben. (Abgesehen vom Initial State)"
                    ),
                    criterion(
                        "Der Initial State Parameter wird korrekt in Header geschrieben."
                    ),
                    criterion(
                        "Ein Term ist korrekt formatiert."
                    ),
                    criterion(
                        "Der exporter ist vollständig korrekt."
                    )
                )
                .build(),
            Criterion.builder()
                .shortDescription("H7 | Verilog Exporter")
                .addChildCriteria(
                    Criterion.builder()
                        .shortDescription("H7.1 | ??")
                        .addChildCriteria(
                            criterion(
                                "Die geforderte funktionalität wurde korrekt umgesetzt."
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H7.2 | ??")
                        .addChildCriteria(
                            criterion(
                                "Die geforderte funktionalität wurde korrekt umgesetzt."
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H7.3 | ??")
                        .addChildCriteria(
                            criterion(
                                "Die geforderte funktionalität wurde korrekt umgesetzt."
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H7.4 | ??")
                        .addChildCriteria(
                            criterion(
                                "Die Methode generateConditionsHeader() generiert eine korrekte Zeile."
                            ),
                            criterion(
                                "Die Methode generateConditionsHeader() wird korrekt mit allen Übergängen aufgerufen."
                            )
                        )
                        .build()
                )
                .build()
        )
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }

    @Override
    public void configure(RubricConfiguration configuration) {
        configuration.addTransformer(new FileSystemIOFactoryTransformer());
    }
}
