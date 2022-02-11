import java.util.*;
/**
 * @author Xhuljo Balli
 * */
public class QuickSort {
    public void runQuickSort(ArrayList<Integer> nums, Graph graph, Visualization Visualization) throws InterruptedException {
        quickSort(nums, graph,0, nums.size() - 1);
        Visualization.needRefresh = true;
    }
    public int partition(ArrayList<Integer> subArray, Graph graph, int low, int high) throws InterruptedException {
        int i = low;
        int pivot = subArray.get(high);

        for (int j = low; j < high; j++) {
            if (subArray.get(j) < pivot) {
                Collections.swap(subArray, i, j);

                graph.updateArray(subArray);
                graph.paintImmediately(0,30,1190,632);
                Thread.sleep(50);
                i++;
            }
        }
        Collections.swap(subArray, i, high);

        graph.updateArray(subArray);
        graph.paintImmediately(0,30,1190,630);
        Thread.sleep(50);
        return i;
    }
    public void quickSort(ArrayList<Integer> array, Graph graph, int low, int high) throws InterruptedException {
        if (low < high) {
            int div = partition(array, graph, low, high);

            quickSort(array, graph, low,div - 1);
            quickSort(array, graph, div + 1, high);
        }
    }
}