package two;

public class TestStatic {
	static int i = 10; // variable for the class
	
	@SuppressWarnings("static-access")
	public static void main(String[] args){
		i ++;
		TestStatic.i ++;
		TestStatic a = new TestStatic();
		TestStatic b = new TestStatic();
		System.out.println(a.i);
		b.i ++;
		System.out.println(b.i);
		Increase.inc();
		System.out.println(i);
		Increase inc1 = new Increase();
		Increase inc2 = new Increase();
		System.out.println("------------------------");
		System.out.println(inc1.share);
		inc1.share++;
		System.out.println(inc2.share);
	}
}

class Increase{
	static int share = 47; // variable for the class
	static void inc(){
		TestStatic.i++;
	} // method for the class
}
