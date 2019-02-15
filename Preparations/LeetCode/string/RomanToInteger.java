package string;

@SuppressWarnings("unused")
public class RomanToInteger {

    public int romanToInt2(String s) {
        char[] cArr = s.toCharArray();
        int tail = cArr.length - 1, result = 0, current, next;

        for (int i = 0; i < tail; i++) {
            current = convert(cArr[i]);
            next = convert(cArr[i + 1]);
            result = current < next ? result - current : result + current;
        }
        result += convert(cArr[tail]);
        return result;
    }


    public int romanToInt(String s) {
        char[] cArr = s.toCharArray();
        int size = cArr.length, result = 0;

        for (int check = 1; check < size; check++) {
            result += subSum(cArr[check - 1], cArr[check]);
        }

        for (int index = 0; index < size; index++) {
            result += convert(cArr[index]);
        }

        return result;
    }

    private int subSum(char lead, char follow) {
        String pair = new StringBuilder().append(lead).append(follow).toString();
        switch (pair) {
            // IV should be 4, we put -2 here thus we can do 1 + 5 - 2 = 4 later
            // Range is 1 to 3999, and note that IVIV would be VIII, IXIX would be XVIII
            // Which means this "IV" syntax would only happen once
            case "IV":
                return -2;
            case "IX":
                return -2;
            case "XL":
                return -20;
            case "XC":
                return -20;
            case "CD":
                return -200;
            case "CM":
                return -200;
            default:
                return 0;
        }
    }

    private int convert(char roman) {
        switch (roman) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }
}
