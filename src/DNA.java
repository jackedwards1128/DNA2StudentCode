/**
 * DNA
 * <p>
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *</p>
 * <p>
 * Completed by: [YOUR NAME HERE]
 *</p>
 */

public class DNA {

    /**
     * TODO: Complete this function, STRCount(), to return longest consecutive run of STR in sequence.
     */
    public static int STRCount(String sequence, String STR) {

        int highestCount = 0;
        int subcount = 0;
        int STRLetter = 0;

        System.out.println(STR);

        for (int i = 0; i < sequence.length(); i++) {
            if (sequence.charAt(i) == STR.charAt(STRLetter)) {
                STRLetter++;
            } else {
                STRLetter = 0;
                if (subcount > highestCount)
                    highestCount = subcount;
                subcount = 0;
            }
            if(STRLetter == STR.length()) {
                STRLetter = 0;
                subcount++;
            }
        }
        return highestCount;
    }
}
