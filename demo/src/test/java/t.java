import org.junit.jupiter.api.Test;

import com.example.Main;

import org.junit.jupiter.api.Assertions;

public class t {
    @Test
    void testMaior() {
        Assertions.assertEquals(3, maior(1, 2, 3));
        Assertions.assertEquals(3, maior(3, 2, 1));
        Assertions.assertEquals(3, maior(1, 3, 2));
    }

    @Test
    void testMenor() {
        Assertions.assertEquals(1, menor(1, 2, 3));
        Assertions.assertEquals(1, menor(3, 2, 1));
        Assertions.assertEquals(1, menor(2, 3, 1));
    }

    @Test
    void testMeio() {
        Assertions.assertEquals(2, meio(1, 2, 3));
        Assertions.assertEquals(2, meio(3, 2, 1));
        Assertions.assertEquals(2, meio(1, 3, 2));
    }

    public static int maior(int n1, int n2, int n3) {
        if (n1 > n2 && n1 > n3) {
            return n1;
        } else if (n2 > n1 && n2 > n3) {
            return n2;
        } else {
            return n3;
        }
    }

    public static int menor(int n1, int n2, int n3) {
        if (n1 < n2 && n1 < n3) {
            return n1;
        } else if (n2 < n1 && n2 < n3) {
            return n2;
        } else {
            return n3;
        }
    }

    public static int meio(int num1, int num2, int num3) {
        int meio;
        int maior = maior(num1, num2, num3);
        int menor = menor(num1, num2, num3);
        meio = num1;
        if (meio != maior) {
            if (meio != menor) {
                return meio;
            }
        }
        meio = num2;
        if (meio != maior) {
            if (meio != menor) {
                return meio;
            }
        }
        meio = num3;
        if (meio != maior) {
            if (meio != menor) {
                return meio;
            }
        }
        return 0;
    }
}
