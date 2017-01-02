package org.decimalfp.search;

/**
 * @author Dmytro.Sheyko
 */
public class HorspoolSearch implements ISubarraySearch {
    public static final ISubarraySearch.IFactory Factory = new Factory();
    private final int[] table_ = new int[256];
    private final byte[] subarray_;

    HorspoolSearch(byte[] subarray) {
        subarray_ = subarray;
        int length = subarray_.length;
        for (int i = 0; i < table_.length; i += 1) {
            table_[i] = length;
        }
        for (int i = 0, ii = length - 1; i < ii; i += 1) {
            table_[subarray_[i] & 0xff] = length - 1 - i;
        }
    }

    @Override
    public int firstIndexIn(byte[] array) {
        int lenm1 = subarray_.length - 1;
        for (int i = 0, ii = array.length - subarray_.length; i < ii; i += table_[array[i + lenm1] & 0xff]) {
            for (int j = lenm1; array[i + j] == subarray_[j]; j -= 1) {
                if (j == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static class Factory implements ISubarraySearch.IFactory {
        @Override
        public ISubarraySearch prepare(byte[] subarray) {
            return new HorspoolSearch(subarray.clone());
        }
    }
}
