package org.decimalfp.search;

/**
 * @author Dmytro.Sheyko
 */
public class NaiveSearch implements ISubarraySearch {
    public static final ISubarraySearch.IFactory Factory = new Factory();
    private final byte[] subarray_;

    NaiveSearch(byte[] subarray) {
        subarray_ = subarray;
    }

    @Override
    public int firstIndexIn(byte[] array) {
        for (int i = 0, ii = array.length - subarray_.length; i < ii; i += 1) {
            boolean found = true;
            for (int j = 0; j < subarray_.length; j += 1) {
                if (array[i + j] != subarray_[j]) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return i;
            }
        }
        return -1;
    }

    public static class Factory implements ISubarraySearch.IFactory {
        @Override
        public ISubarraySearch prepare(byte[] subarray) {
            return new NaiveSearch(subarray.clone());
        }
    }
}
