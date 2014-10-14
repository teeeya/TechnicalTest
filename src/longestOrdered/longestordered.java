package longestOrdered;
/**
 * @author Fathea Chowdhury
 *
 */
public class Longestordered {
	
	public static void main(String[] params) {
		int[] sequence = { 0,1,2,3,4,5,6,100,2,3,3,3};
		System.out.println(longestSubstring(sequence));
	}
	public static int longestSubstring(int [] sequence){
		int longestSubstring = 0;
		int counter = 1;
		for (int i = 0; i < sequence.length; i++) {
			if (i < sequence.length - 1) {
				if (sequence[i] <= sequence[i + 1]) {
					counter++;
				} else {
					if (longestSubstring < counter) {// counter is larger than
														// current longest
														// substring
						longestSubstring = counter;
						counter = 1;
					} else { // the longest substring is larger - reset counter
						counter = 1;
					}
				}
			}
			// Executes when end of array is reached
			else if (i == sequence.length - 1) {
				if (sequence[i] >= sequence[i - 1]) {// counter is larger than
													// current longest substring
					counter++;
					if (longestSubstring < counter) {
						longestSubstring = counter;
					}
				} else {
					counter = 1;
				}
			}
		}
		return longestSubstring;
	}

}
