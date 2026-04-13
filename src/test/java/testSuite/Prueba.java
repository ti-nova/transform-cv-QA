package testSuite;

import org.testng.TestNG;

public class Prueba {
    public static void main(String[] args) {
        TestNG testng = new TestNG();

        testng.setPreserveOrder(true);

        testng.setTestClasses(new Class[] {
                testClass.LogInPageTest.class,
                testClass.HomePageTest.class
        });

        testng.run();
    }
}