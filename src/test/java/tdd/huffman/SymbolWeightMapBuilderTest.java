package tdd.huffman;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SymbolWeightMapBuilderTest {

    private SymbolWeightMapBuilder symbolWeightMapBuilder = new SymbolWeightMapBuilder();
    private InputStream inputStream;
    private Map<Byte, Long> builtMap;

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(new ByteArrayInputStream("abracadabra".getBytes()),
                        ImmutableMap.of(
                                (byte) 'a', 5L,
                                (byte) 'b', 2L,
                                (byte) 'c', 1L,
                                (byte) 'd', 1L,
                                (byte) 'r', 2L)
                ),
                Arguments.of(new ByteArrayInputStream("To Be Or Not To Be".getBytes()),
                        ImmutableMap.builder()
                                .put((byte) 'T', 2L)
                                .put((byte) 'o', 3L)
                                .put((byte) 'B', 2L)
                                .put((byte) 'e', 2L)
                                .put((byte) 'O', 1L)
                                .put((byte) 'r', 1L)
                                .put((byte) 'N', 1L)
                                .put((byte) 't', 1L)
                                .put((byte) ' ', 5L)
                                .build()),
                Arguments.of(new ByteArrayInputStream("The quick brown fox jumps over the lazy dog".getBytes()),
                        ImmutableMap.builder()
                                .put((byte) ' ', 8L)
                                .put((byte) 'o', 4L)
                                .put((byte) 'e', 3L)
                                .put((byte) 'h', 2L)
                                .put((byte) 'u', 2L)
                                .put((byte) 'r', 2L)
                                .put((byte) 'T', 1L)
                                .put((byte) 'q', 1L)
                                .put((byte) 'i', 1L)
                                .put((byte) 'c', 1L)
                                .put((byte) 'k', 1L)
                                .put((byte) 'b', 1L)
                                .put((byte) 'w', 1L)
                                .put((byte) 'n', 1L)
                                .put((byte) 'f', 1L)
                                .put((byte) 'x', 1L)
                                .put((byte) 'j', 1L)
                                .put((byte) 'm', 1L)
                                .put((byte) 'p', 1L)
                                .put((byte) 's', 1L)
                                .put((byte) 'v', 1L)
                                .put((byte) 't', 1L)
                                .put((byte) 'l', 1L)
                                .put((byte) 'a', 1L)
                                .put((byte) 'z', 1L)
                                .put((byte) 'y', 1L)
                                .put((byte) 'd', 1L)
                                .put((byte) 'g', 1L)
                                .build()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    void shouldBuildSymbolToWeightMap(InputStream inputStream, Map<Byte, Long> expectedSymbolWeightMap) throws IOException {
        givenInput(inputStream);
        whenBuildingSymbolWeightMap();
        thenBuiltSymbolWeightMapIs(expectedSymbolWeightMap);
    }

    private void givenInput(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private void thenBuiltSymbolWeightMapIs(Map<Byte, Long> expectedSymbolWeightMap) {
        assertThat(builtMap).isEqualTo(expectedSymbolWeightMap);
    }

    private void whenBuildingSymbolWeightMap() throws IOException {
        builtMap = symbolWeightMapBuilder.build(inputStream);
    }
}