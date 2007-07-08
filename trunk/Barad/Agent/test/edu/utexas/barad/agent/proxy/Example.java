package edu.utexas.barad.agent.proxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 4, 2007
 *
 *
 */
public class Example {
    private String data;
    private Example example;
    private static final Example DEFAULT_EXAMPLE = new Example();

    public int x = 42;
    public static String y = "Test";

    public Example(String data) {
        setData(data);
    }

    public Example() {
        // Default constructor.
    }

    public static Example getDefault() {
        return DEFAULT_EXAMPLE;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Example getTest() {
        return example;
    }

    public void setTest(Example example) {
        this.example = example;
    }

    public Example[] getArray() {
        return new Example[] { example };
    }
}