package standard;

import java.util.ArrayList;

/**
 * the QuickSot class implements the quick sort algorithm as a static function
 * the quick sort algorithm is used in sorting the result of each trial
 * 
 * @author Rentian Dong
 */
public class QuickSort {
	
	/**
	 * Recursively sort a ordered list using quick sort algorithm
	 * 
	 * @param toSort the list ot be sorted
	 * @return the sorted list
	 */
	public static ArrayList<String> quickSort(ArrayList<String> toSort, int start, int end) {
		
		int mid; // middle point of certain interval returned from partition()
		
		// if toSort is not completely sorted
		if (start < end) {
			mid = partition(toSort, start, end); // sort current range
			// recursively sort temp and right sub arrays
			quickSort(toSort, start, mid - 1);
			quickSort(toSort, mid + 1, end);
		}
		
		return toSort;
	}
	
	/**
	 * Helper method for quick sort. Within the given range [start, end], this
	 * function moves all elements smaller than or equal to the last one to the
	 * left and all elements larget than the last one to the right. The last
	 * element is inserted in between such left and right subarrays at last.
	 *  
	 * @param toSort the array to be sorted
	 * @param start lower bound of range to sort
	 * @param end upper bound of range to sort
	 * @return index at which the last element was placed
	 */
	private static int partition(ArrayList<String> toSort, int start, int end) {
		String lastElement = toSort.get(end); // last element of list
		String temp; // temporary variable to swap elements
		int nextSmall = start - 1; // index for element smaller than lastElement
		
		// iterate through all elements in list except the last one
		for (int index = start; index < end; index++) {
			
			// if found an element smaller than last element
			if (toSort.get(index).compareTo(lastElement) <= 0) {
				nextSmall++;
				// exchange toSort[nextSmall] and toSort[index]
				temp = toSort.get(nextSmall);
				toSort.set(nextSmall, toSort.get(index));
				toSort.set(index, temp);
			}
		}
		
		// insert last element into the middle at the end
		temp = toSort.get(nextSmall + 1);
		toSort.set(nextSmall + 1, lastElement);
		toSort.set(end, temp);
		
		// return index of middle point for recursion
		return nextSmall + 1;
	}
}

