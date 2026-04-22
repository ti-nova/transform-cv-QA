package testSuite;

import org.testng.TestNG;
import testClass.TranformPageTest;

public class Prueba {
    public static void main(String[] args) {
        TestNG testng = new TestNG();

        testng.setTestClasses(new Class[] {
                testClass.LogInPageTest.class,
                TranformPageTest.class
        });

        testng.run();
    }
}