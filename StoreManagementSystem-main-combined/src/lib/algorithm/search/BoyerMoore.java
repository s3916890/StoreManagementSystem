package lib.algorithm.search;

public class BoyerMoore{
    private int radix;
    private int[] rightMost;
    private String pattern;

    /**
     * Preprocesses the pattern string.
     * @param pattern the pattern string to look for
     */

    public BoyerMoore(String pattern) {
        this.radix = 256;
        this.pattern = pattern;

        this.rightMost = new int[radix];

        for(int character = 0; character < radix; character++){
            rightMost[character] = -1;
        }

        for(int j = 0; j < pattern.length(); j++){
            rightMost[pattern.charAt(j)] = j;
        }
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }


    /**
     * @function This function could search the given pattern with the comparison text
     * @param  text: the comparison text of string
     * @param  pattern: the given search pattern
     * @return the boolean result searching the pattern
     */
    public boolean boyerMooreSearch(String text, String pattern){
        int lengthOfText = text.length(), lengthOfPattern = pattern.length();

        int numOfSkip;

        for(int i = 0; i <= lengthOfText - lengthOfPattern; i += numOfSkip){
            numOfSkip = 0;
            for(int j = lengthOfPattern - 1; j >= 0; j--){
                if(pattern.charAt(j) != text.charAt(i + j)){
                    numOfSkip = Math.max(1, j - this.rightMost[text.charAt(i + j)]);
                    break;
                }
            }
            if(numOfSkip == 0) return true;
        }

        return false;
    }

    public int getRadix() {
        return radix;
    }

    public int[] getRight() {
        return rightMost;
    }

    public void setRight(int[] right) {
        this.rightMost = right;
    }
}
