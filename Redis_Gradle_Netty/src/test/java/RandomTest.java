import java.util.ArrayList;
import java.util.List;

public class RandomTest {

    public static void main(String[] args) {
        String wdf = new String("aaaaaaaaa");
        List<String> li = new ArrayList<>();
        li.add(wdf);
        wdf = null;
        System.out.println(li);
    }
}
