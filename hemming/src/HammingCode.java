import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class HammingCode {
    public static int[] generateHammingCode(int[] data) {
        int n = data.length;
        int r = 0;

        while (Math.pow(2, r) < (n + r + 1)) {
            r++;
        }

        int[] hammingCode = new int[n + r];

        int dataPos = 0;
        for (int i = 0; i < hammingCode.length; i++) {
            if ((i + 1 & i) == 0) {
                hammingCode[i] = 0;
            } else {
                hammingCode[i] = data[dataPos++];
            }
        }

        // проверочные биты
        for (int i = 0; i < r; i++) {
            int parityPos = (int) Math.pow(2, i) - 1;
            int parity = 0;

            for (int j = parityPos; j < hammingCode.length; j += 2 * (parityPos + 1)) {
                for (int k = 0; k <= parityPos && j + k < hammingCode.length; k++) {
                    parity ^= hammingCode[j + k];
                }
            }

            hammingCode[parityPos] = parity;
        }

        return hammingCode;
    }

    public static int detectAndCorrectError(int[] hammingCode) {
        int errorPos = 0;

        for (int i = 0; i < Math.log(hammingCode.length) / Math.log(2); i++) {
            int parityPos = (int) Math.pow(2, i) - 1;
            int parity = 0;

            for (int j = parityPos; j < hammingCode.length; j += 2 * (parityPos + 1)) {
                for (int k = 0; k <= parityPos && j + k < hammingCode.length; k++) {
                    parity ^= hammingCode[j + k];
                }
            }

            if (parity != 0) {
                errorPos += parityPos + 1;
            }
        }

        // Если ошибка есть, исправляем
        if (errorPos > 0 && errorPos <= hammingCode.length) {
            hammingCode[errorPos - 1] ^= 1;
        }

        return errorPos;
    }

    public static int[] decodeHammingCode(int[] hammingCode) {
        int n = hammingCode.length;
        int r = (int) Math.ceil(Math.log(n + 1) / Math.log(2));
        int[] data = new int[n - r];

        int dataPos = 0;
        for (int i = 0; i < hammingCode.length; i++) {
            if ((i + 1 & i) != 0) { // Пропускаем позиции степеней двойки
                data[dataPos++] = hammingCode[i];
            }
        }

        return data;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Введите количество бит данных: ");
        int n = Integer.parseInt(reader.readLine());

        int[] data = new int[n];
        System.out.print("Введите данные побитно (0 или 1): ");
        String input = reader.readLine().trim();

        // формат ввода: с пробелами или без
        if (input.contains(" ")) {
            String[] inputData = input.split(" ");
            for (int i = 0; i < n; i++) {
                data[i] = Integer.parseInt(inputData[i]);
            }
        } else {
            for (int i = 0; i < n; i++) {
                data[i] = Character.getNumericValue(input.charAt(i));
            }
        }

        int[] hammingCode = generateHammingCode(data);
        System.out.println("Сгенерированный код Хэмминга: " + Arrays.toString(hammingCode));

        System.out.print("Введите код Хэмминга для декодирования: ");
        String inputHamming = reader.readLine().trim();

        // ввод в массив целых чисел (с пробелами или без)
        int[] inputHammingCode;
        if (inputHamming.contains(" ")) {
            String[] inputData = inputHamming.split(" ");
            inputHammingCode = Arrays.stream(inputData).mapToInt(Integer::parseInt).toArray();
        } else {
            inputHammingCode = new int[inputHamming.length()];
            for (int i = 0; i < inputHamming.length(); i++) {
                inputHammingCode[i] = Character.getNumericValue(inputHamming.charAt(i));
            }
        }

        int detectedErrorPosition = detectAndCorrectError(inputHammingCode);

        if (detectedErrorPosition == 0) {
            System.out.println("Ошибок не обнаружено. Код корректен.");
        } else {
            System.out.println("Обнаружена ошибка в позиции: " + detectedErrorPosition);
            System.out.println("Исправленный код Хэмминга: " + Arrays.toString(inputHammingCode));
        }

        int[] decodedData = decodeHammingCode(inputHammingCode);
        System.out.println("Декодированные данные: " + Arrays.toString(decodedData));
    }
}
