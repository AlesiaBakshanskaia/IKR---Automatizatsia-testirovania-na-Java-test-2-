import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class MathServiceTest {
    static MathService mathService;

    @BeforeAll
    static void init() {
        mathService = new MathService();
    }

    @Test
    void checkPairToString() {
        Pair pair = new Pair(2.568, 1.0);
        Assertions.assertEquals("Answer{first=2.568, second=1.0}", pair.toString());
    }

    @ParameterizedTest
    @CsvSource({"2, 10, 1, 92", "1, -10, 1, 96", "-2, 10, 1, 108", "2, 10, -1, 108", "-1, -10, -1, 96",
            "2, 4, 2, 0", "2, 4, 2, 0", "10, 10, 10, -300"})
    void checkGetD(int a, int b, int c, int result) {
        int d = mathService.getD(a, b, c);
        Assertions.assertEquals(result, d);
    }

    @ParameterizedTest
    @CsvSource({"1, -7, 12, 4.0, 3.0", "2, 10, 1, -0.10208423834364044, -4.89791576165636"})
    void checkGetAnswerDiscriminantOverZero(int a, int b, int c, Double x1, Double x2) throws NotFoundAnswerException {
        Pair pair = mathService.getAnswer(a, b, c);
        String result = "Answer{" + "first=" + x1 + ", second=" + x2 + '}';
        Assertions.assertAll(() -> Assertions.assertEquals(x1, pair.first),
                () ->Assertions.assertEquals(x2, pair.second),
                () ->Assertions.assertEquals(result, pair.toString()));
    }

    @ParameterizedTest
    @CsvSource({"2, 4, 2, -1.0", "32, 16, 2, 0.25"})
    void checkGetAnswerDiscriminantEqualZero(int a, int b, int c, Double x1) throws NotFoundAnswerException {
        Pair pair = mathService.getAnswer(a, b, c);
        String result = "Answer{" + "first=" + x1 + ", second=" + x1 + '}';
        Assertions.assertAll(() -> Assertions.assertEquals(x1, pair.first),
                () ->Assertions.assertEquals(x1, pair.second),
                () ->Assertions.assertEquals(result, pair.toString()));
    }

    @Test
    void checkExceptionInGetAnswerDiscriminantLessZero() {
        String message = "";
        Assertions.assertThrows(NotFoundAnswerException.class, () -> mathService.getAnswer(8, 2, 1));
        try {
            Pair pair = mathService.getAnswer(8, 2, 1);
        } catch (NotFoundAnswerException e) {
            message = e.getMessage();
        }
        Assertions.assertEquals("Корни не могут быть найдены", message);
    }

    @Test
    void checkGetAnswerWithIntOverFlow()  {
        Assertions.assertThrows(NotFoundAnswerException.class, () -> mathService.getAnswer(1, 1, 2147483647+1));
    }
    @Test
    void checkGetAnswerWithAEqualZero()  {
        Assertions.assertThrows(NotFoundAnswerException.class, () -> mathService.getAnswer(0, 1, 2));
    }


}
