package org.decimalfp.search;

/**
 * @author Dmytro.Sheyko
 */
public interface ISubarraySearch {
    int firstIndexIn(byte[] array);

    interface IFactory {
        ISubarraySearch prepare(byte[] subarray);
    }
}
