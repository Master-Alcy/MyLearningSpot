public class I_UseStaticFactoryConstructor {

    private static I_UseStaticFactoryConstructor INSTANCE1;

    public static I_UseStaticFactoryConstructor getInstance1() {
        return new I_UseStaticFactoryConstructor();
    }
    
    private I_UseStaticFactoryConstructor() {

    }
}
