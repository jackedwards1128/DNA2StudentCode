import org.junit.runner.Computer;

import static java.lang.Math.*;

/**
 * DNA
 * <p>
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *</p>
 * <p>
 * Completed by: Jack Edwards
 *</p>
 */

public class DNA {

    public static final int RADIX = 4;
    public static int RABIN_KARP_FACTOR = 0;

    public static int STRCount(String sequence, String STR) {

        // This is used to prevent the recalculation of R^(m-1) over and over
        RABIN_KARP_FACTOR = (int)(pow(RADIX, STR.length() - 1));

        long hash = hash(sequence, 0, STR.length());
        long STRHash = hash(STR, 0, STR.length());

        // These counts are used to keep track of the current streak and the longest streak
        int subcount = 0;
        int mostCount = 0;

        // Loop through entire sequence
        for (int i = 0; i <= sequence.length() - STR.length();) {
            // If we have a match, slide forward by a full STR-length
            if (hash == STRHash) {
                subcount++;
                i += STR.length();

                // Contingency to ensure no out-of-bounds
                if (i+STR.length() < sequence.length())
                    hash = hash(sequence, i, i + STR.length());
            } else {
                // If we don't have a match, but we did before, back up because there might've been the start to a new
                // chain of STRs
                if (subcount > 0) {
                    i -= STR.length();
                    i += 2;
                    mostCount = max(mostCount, subcount);
                    hash = hash(sequence, i, i + STR.length());
                } else {
                    // Otherwise, just slide forward one space
                    i++;
                    if (i+STR.length() < sequence.length())
                        hash = slideHash(hash, sequence, STR.length(), i);
                }
                subcount = 0;
            }
        }

        mostCount = max(mostCount, subcount);

        return mostCount;
    }

    // Used to calculate a hash from scratch
    public static long hash(String text, int start, int end) {
        // Horner's Method: loop through the sequence and add in new terms while multiplying the whole hash by the radix
        long hash = 0;
        for(int i = start; i < end; i++) {
            hash = ((hash * RADIX) + letterToBaseFour(text.charAt(i)));
        }
        return hash;

    }

    // Used to slide the hash when the hash only moves by a single index
    public static long slideHash(long hash, String seq, int length, int index) {
        // Cut out the front term
        // Since m-1 doesn't change as you're sliding, we can just calculate what R^(m-1) is at the start
        // and save it into a variable. This is the purpose of RABIN_KARP_FACTOR
        long newHash = (long)((hash) - (letterToBaseFour(seq.charAt(index-1)) * RABIN_KARP_FACTOR));


        // Scroll all the exponents up (by multiplying by R), and then add the new term
        hash = ((newHash * RADIX) + letterToBaseFour(seq.charAt(index + length - 1)));

        return hash;
    }

    // This function converts a char letter into base-4
    public static int letterToBaseFour(char letter) {
        // Clean data by converting to uppercase
        letter = Character.toUpperCase(letter);
        if (letter == 'A') {
            return 0;
        } else if (letter == 'C') {
            return 1;
        } else if (letter == 'G') {
            return 2;
        } else {
            return 3;
        }
    }
}
