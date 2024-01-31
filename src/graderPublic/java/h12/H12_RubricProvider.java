package h12;

import h12.h1.FileSystemIOFactoryTransformer;
import h12.h1.TutorTests_H1_1_FileSystemIOFactoryTest;
import h12.h2.H2_1_Tests;
import h12.h2.H2_2_Tests;
import h12.h3.H3_Tests;
import h12.h4.H4_1_Tests;
import h12.h4.H4_2_Tests;
import h12.h5.H5_1_Tests;
import h12.h5.H5_2_Tests;
import h12.h5.H5_3_Tests;
import h12.h5.H5_4_Tests;
import h12.h6.H6_Tests;
import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;
import org.sourcegrade.jagr.api.testing.RubricConfiguration;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;

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
                        JUnitTestRef.ofMethod(() -> TutorTests_H1_1_FileSystemIOFactoryTest.class.getMethod("testFileSystemIOFactoryReader")),
                        JUnitTestRef.ofMethod(() -> TutorTests_H1_1_FileSystemIOFactoryTest.class.getMethod("testSupportsReader"))
                    ),
                    criterion(
                        "Die Methoden createWriter() und supportsWriter() sind vollständig korrekt.",
                        JUnitTestRef.ofMethod(() -> TutorTests_H1_1_FileSystemIOFactoryTest.class.getMethod("testFileSystemIOFactoryWriter")),
                        JUnitTestRef.ofMethod(() -> TutorTests_H1_1_FileSystemIOFactoryTest.class.getMethod("testSupportsWriter"))
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
                                "Das Einlesen einer Zeile ohne Kommentar in Puffer funktioniert korrekt.",
                                JUnitTestRef.ofMethod(() -> H2_1_Tests.class.getMethod("testNoComments", JsonParameterSet.class)),
                                JUnitTestRef.ofMethod(() -> H2_1_Tests.class.getMethod("testContentPlusComments", JsonParameterSet.class))
                            ),
                            criterion(
                                "Der Sonderfall, dass eine Zeile nur aus Kommentaren besteht wird korrekt behandelt.",
                                JUnitTestRef.ofMethod(() -> H2_1_Tests.class.getMethod("testCommentsOnly", JsonParameterSet.class))
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H2.2 | Zeichenstrom nutzen")
                        .addChildCriteria(
                            criterion(
                                "Die Methoden hasNext() und peek() sind vollständig korrekt.",
                                JUnitTestRef.ofMethod(() -> H2_2_Tests.class.getMethod("testHasNextEmptyLookAhead")),
                                JUnitTestRef.ofMethod(() -> H2_2_Tests.class.getMethod("testHasNextNonEmptyLookAhead")),
                                JUnitTestRef.ofMethod(() -> H2_2_Tests.class.getMethod("testPeek"))
//                                JUnitTestRef.ofMethod(() -> H2_2_Tests.class.getMethod("testHasNext", JsonParameterSet.class))
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
                        "Die Methode hasNext() ist vollständig korrekt.",
                        JUnitTestRef.ofMethod(() -> H3_Tests.class.getMethod("testHasNextFalse")),
                        JUnitTestRef.ofMethod(() -> H3_Tests.class.getMethod("testHasNextTrue", String.class))
                    ),
                    criterion(
                        "Ein Aufruf der Methode scan() liefert korrekt das aktuelle Token zurück.",
                        JUnitTestRef.ofMethod(() -> H3_Tests.class.getMethod("testScanReturn"))
                    ),
                    criterion(
                        "Beim Aufruf der Methode scan() wird das nächste Token korrekt eingelesen.",
                        JUnitTestRef.ofMethod(() -> H3_Tests.class.getMethod("testScanReadsNextToken", JsonParameterSet.class))
                    ),
                    criterion(
                        "Das Ende der Datei wird korrekt behandelt.",
                        JUnitTestRef.ofMethod(() -> H3_Tests.class.getMethod("testEof", JsonParameterSet.class))
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
                                "Die Methoden parseOutputWidth(), parseNumberOfTerms() und parseNumberOfStates() sind vollständig korrekt (abgesehen von Exceptionwurf).",
                                JUnitTestRef.ofMethod(() -> H4_1_Tests.class.getMethod("testParseOutputWidth")),
                                JUnitTestRef.ofMethod(() -> H4_1_Tests.class.getMethod("testParseNumberOfTerms")),
                                JUnitTestRef.ofMethod(() -> H4_1_Tests.class.getMethod("testParseNumberOfStates"))
                            ),
                            criterion(
                                "Die Methode parseInitialState() ist vollständig korrekt.",
                                JUnitTestRef.ofMethod(() -> H4_1_Tests.class.getMethod("testParseInitialState"))
                            ),
                            criterion(
                                "Eine BadNumberException bzw. BadIdentifierException wird korrekt geworfen, wenn dies gefordert ist.",
                                JUnitTestRef.ofMethod(() -> H4_1_Tests.class.getMethod("testParseOutputWidthException")),
                                JUnitTestRef.ofMethod(() -> H4_1_Tests.class.getMethod("testParseNumberOfTermsException")),
                                JUnitTestRef.ofMethod(() -> H4_1_Tests.class.getMethod("testParseNumberOfStatesException")),
                                JUnitTestRef.ofMethod(() -> H4_1_Tests.class.getMethod("testParseInitialStateException"))
                            ),
                            criterion(
                                "Die Methode parseHeader() ruft jeweils korrekt die entsprechende Untermethode zum erhaltenen Token auf.",
                                JUnitTestRef.ofMethod(() -> H4_1_Tests.class.getMethod("testParseHeader"))
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H4.2 | Term parsen")
                        .addChildCriteria(
                            criterion(
                                "Die Methode parseTerm() kann einen Übergang korrekt parsen.",
                                JUnitTestRef.ofMethod(() -> H4_2_Tests.class.getMethod("testParseTerm"))
                            ),
                            criterion(
                                "Die Methode parseTerms() ist vollständig korrekt.",
                                JUnitTestRef.ofMethod(() -> H4_2_Tests.class.getMethod("testParseTerms", JsonParameterSet.class))
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
                                "Die Methoden set{OutputSize, NumberOfTerms, NumberOfStates}() sind vollständig korrekt. (inkl. ParameterAlreadySpecified Exceptionwurf)",
                                JUnitTestRef.ofMethod(() -> H5_1_Tests.class.getMethod("testSetOutputSize")),
                                JUnitTestRef.ofMethod(() -> H5_1_Tests.class.getMethod("testSetOutputSizeException")),
                                JUnitTestRef.ofMethod(() -> H5_1_Tests.class.getMethod("testSetNumberOfTerms")),
                                JUnitTestRef.ofMethod(() -> H5_1_Tests.class.getMethod("testSetNumberOfTermsException"))
                            ),
                            criterion(
                                "Die Methode setInitialState ist vollständig korrekt.",
                                JUnitTestRef.ofMethod(() -> H5_1_Tests.class.getMethod("testSetInitialState")),
                                JUnitTestRef.ofMethod(() -> H5_1_Tests.class.getMethod("testSetInitialStateException"))
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H5.2 | Terms verarbeiten")
                        .addChildCriteria(
                            criterion(
                                "Die Methode addTerm ist vollständig korrekt.",
                                JUnitTestRef.ofMethod(() -> H5_2_Tests.class.getMethod("testInvalidBitFieldWidths")),
                                JUnitTestRef.ofMethod(() -> H5_2_Tests.class.getMethod("testAddTermToFsm"))
                            ),
                            criterion(
                                "StateFactory wird genutzt",
                                JUnitTestRef.ofMethod(() -> H5_2_Tests.class.getMethod("testStateFactoryUsed"))
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H5.3 | finishHeader und finishFsm")
                        .addChildCriteria(
                            criterion(
                                "Die Methoden finishHeader() und finishFsm() sind vollständig korrekt.",
                                JUnitTestRef.ofMethod(() -> H5_3_Tests.class.getMethod("testFinishHeaderAllParametersSet")),
                                JUnitTestRef.ofMethod(() -> H5_3_Tests.class.getMethod("testFinishHeaderMissingInputSize")),
                                JUnitTestRef.ofMethod(() -> H5_3_Tests.class.getMethod("testFinishHeaderMissingOutputSize")),
                                JUnitTestRef.ofMethod(() -> H5_3_Tests.class.getMethod("testFinishHeaderMissingNumberOfTerms")),
                                JUnitTestRef.ofMethod(() -> H5_3_Tests.class.getMethod("testFinishHeaderMissingNumberOfStates")),
                                JUnitTestRef.ofMethod(() -> H5_3_Tests.class.getMethod("testFinishFsmValid")),
                                JUnitTestRef.ofMethod(() -> H5_3_Tests.class.getMethod("testFinishFsmStateMismatch")),
                                JUnitTestRef.ofMethod(() -> H5_3_Tests.class.getMethod("testFinishFsmTermMismatch"))
                            )
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H5.4 | getFsm")
                        .addChildCriteria(
                            criterion(
                                "Die Fsm wird zurückgegeben wenn die Flag gesetzt ist, andernfalls wird eine Exception geworfen.",
                                JUnitTestRef.ofMethod(() -> H5_4_Tests.class.getMethod("testGetFsmUnfinished")),
                                JUnitTestRef.ofMethod(() -> H5_4_Tests.class.getMethod("testGetFsmFinished"))
                            )
                        )
                        .build()
                )
                .build(),
            Criterion.builder()
                .shortDescription("H6 | Kiss2 Exporter")
                .addChildCriteria(
                    criterion(
                        "Alle Header-Parameter werden korrekt geschrieben. (Abgesehen vom Initial State)",
                        JUnitTestRef.ofMethod(() -> H6_Tests.class.getMethod("testHeaders"))
                    ),
                    criterion(
                        "Der Initial State Parameter wird korrekt in Header geschrieben.",
                        JUnitTestRef.ofMethod(() -> H6_Tests.class.getMethod("testInitialState"))
                    ),
                    criterion(
                        "Ein Term ist korrekt formatiert.",
                        JUnitTestRef.ofMethod(() -> H6_Tests.class.getMethod("testSingleTerm"))
                    ),
                    criterion(
                        "Der exporter ist vollständig korrekt.",
                        JUnitTestRef.ofMethod(() -> H6_Tests.class.getMethod("testFsmWithInitialState"))
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
