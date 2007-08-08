package edu.utexas.barad.agent.swt.tester;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * University of Texas at Austin
 * Barad Project, Jul 26, 2007
 */
public class ItemPath implements Iterable<String> {
    public static final String DEFAULT_DELIMITER = "/";

    private final String[] segments;

    public ItemPath(String path) {
        this(path, DEFAULT_DELIMITER);
    }

    public ItemPath(String path, String delimiter) {
        if (path == null)
            throw new IllegalArgumentException("path is null");
        if (delimiter == null)
            throw new IllegalArgumentException("delimiter is null");

        StringTokenizer tokenizer = new StringTokenizer(path, delimiter);
        segments = new String[tokenizer.countTokens()];
        for (int i = 0; i < segments.length; i++) {
            segments[i] = tokenizer.nextToken();
        }
    }

    public ItemPath(String[] segments) {
        if (segments == null)
            throw new IllegalArgumentException("segments is null");
        int i = Arrays.asList(segments).indexOf(null);
        if (i != -1)
            throw new IllegalArgumentException("segments[" + i + "] is null");

        this.segments = new String[segments.length];
        System.arraycopy(segments, 0, this.segments, 0, segments.length);
    }

    public ItemPath(String[] segments, int fromIndex, int toIndex) {
        if (segments == null)
            throw new IllegalArgumentException("segments is null");
        if (fromIndex < 0 || toIndex > segments.length || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();
        int i = Arrays.asList(segments).subList(fromIndex, toIndex).indexOf(null);
        if (i != -1)
            throw new IllegalArgumentException("segments[" + (i + fromIndex) + "] is null");

        this.segments = new String[toIndex - fromIndex];
        System.arraycopy(segments, fromIndex, this.segments, 0, this.segments.length);
    }

    public ItemPath(List<String> segments) {
        this.segments = segments.toArray(new String[segments.size()]);
    }

    public ItemPath subPath(int fromIndex, int toIndex) {
        return new ItemPath(segments, fromIndex, toIndex);
    }

    public ItemPath subPath(int fromIndex) {
        return subPath(fromIndex, segmentCount());
    }

    public String getSegment(int index) {
        return segments[index];
    }

    public int segmentCount() {
        return segments.length;
    }

    public String toString() {
        return toString(DEFAULT_DELIMITER);
    }

    public String toString(String delimiter) {
        StringBuilder builder = new StringBuilder();
        for (String segment : segments) {
            if (builder.length() > 0)
                builder.append(delimiter);
            builder.append(segment);
        }
        return builder.toString();
    }

    public Iterator<String> iterator() {
        return new ItemPathIterator();
    }

    private class ItemPathIterator implements Iterator<String> {
        private int i = 0;

        public boolean hasNext() {
            return i < segments.length;
        }

        public String next() {
            return segments[i++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}