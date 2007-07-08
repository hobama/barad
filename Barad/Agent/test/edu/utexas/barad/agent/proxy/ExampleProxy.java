package edu.utexas.barad.agent.proxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 4, 2007
 *
 *
 */
public interface ExampleProxy {
    public ExampleProxy getDefault();

    public String getData();

    public void setData(String data);

    public ExampleProxy getTest();

    public void setTest(ExampleProxy example);

    public int __fieldGetx();

    public void __fieldSetx(int x);

    public String __fieldGety();

    public void __fieldSety(String y);

    public ExampleProxy[] getArray();

    public int __fieldGetinvalid(); // Should fail.

    public int getInvalidMethod(); // Should fail.
}