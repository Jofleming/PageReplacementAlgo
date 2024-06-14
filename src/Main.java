package src;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<List<Integer>> referenceStrings = Arrays.asList(
                Arrays.asList(7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1),
                Arrays.asList(8, 1, 0, 7, 3, 0, 3, 4, 5, 3, 5, 2, 0, 6, 8, 4, 8, 1, 5, 3),
                Arrays.asList(4, 6, 4, 8, 6, 3, 6, 0, 5, 9, 2, 1, 0, 4, 6, 3, 0, 6, 8, 4)
        );

        int[] pageFrameCounts = {3, 5, 7};

        for (int pageFrameCount : pageFrameCounts) {
            for (List<Integer> referenceString : referenceStrings) {
                System.out.println("Page Frame Count: " + pageFrameCount + " Reference String: " + referenceString);

                ReplacementAlgorithm fifo = new FIFO(pageFrameCount);
                ReplacementAlgorithm lru = new LRU(pageFrameCount, referenceString);
                ReplacementAlgorithm opt = new OPT(pageFrameCount, referenceString);

                for (int page : referenceString) {
                    fifo.insert(page);
                    lru.insert(page);
                    opt.insert(page);
                }

                System.out.println("FIFO Page Faults: " + fifo.getPageFaultCount());
                System.out.println("LRU Page Faults: " + lru.getPageFaultCount());
                System.out.println("OPT Page Faults: " + opt.getPageFaultCount());
                System.out.println();
            }
        }
    }
}
