package edu.utexas.barad.common.testcase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * University of Texas at Austin
 * Barad Project, Jul 30, 2007
 */
public class TestCase implements Cloneable, Serializable {
    private static final long serialVersionUID = -7425656005894856047L;

    private List<TestStep> steps = new ArrayList<TestStep>();

    public boolean add(TestStep step) {
        return steps.add(step);
    }

    public boolean remove(TestStep step) {
        return steps.remove(step);
    }

    public List<TestStep> getSteps() {
        return Collections.unmodifiableList(steps);
    }

    public String toString() {
        return "TestCase{" +
                "steps=" + steps +
                '}';
    }

    public String toHTML() {
        StringBuffer buffer = new StringBuffer();
        int size = steps.size();
        for (int i = 0; i < size; ++i) {
            TestStep testStep = steps.get(i);
            buffer.append(testStep.toHTML());
            if (i + 1 < size) {
                buffer.append("<br/>");
            }
        }
        return buffer.toString();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        TestCase clone = (TestCase) super.clone();
        clone.steps = new ArrayList<TestStep>(this.steps);
        return clone;
    }

    @Override
    public int hashCode() {
        return steps.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof TestCase) {
            TestCase another = (TestCase) object;
            return another.steps.equals(this.steps);
        }
        return false;
    }
}