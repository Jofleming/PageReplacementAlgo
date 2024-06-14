package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the Least Recently Used (LRU) page replacement algorithm.
 * In LRU, the least recently used page in memory is replaced first when a new page needs to be added.
 */
public class LRU extends ReplacementAlgorithm {
    private final int pageFrameCount;
    private final List<Integer> referenceString;
    private int pageFaultCount;
    private final Map<Integer, Integer> pageLastUsedMap;

    /**
     * Constructs a new LRU instance with the specified number of page frames and reference string.
     *
     * @param pageFrameCount   The number of page frames available in memory.
     * @param referenceString  The reference string representing the order of page accesses.
     */
    public LRU(int pageFrameCount, List<Integer> referenceString) {
        super(pageFrameCount);
        this.pageFrameCount = pageFrameCount;
        this.referenceString = referenceString;
        this.pageFaultCount = 0;
        this.pageLastUsedMap = new HashMap<>();
    }

    /**
     * Inserts a new page into memory using the LRU replacement algorithm.
     * If the page is not already in memory, it is added to memory.
     * If the page frame is full, the least recently used page is evicted to make space.
     *
     * @param page The number of the page to be inserted.
     */
    public void insert(int page) {
        if (!isPageInMemory(page)) {
            if (isPageFrameFull()) {
                evictPage(page);
            }
            else {
                pageFaultCount++;
            }
            addPageToMemory(page);
        } else {
            updatePageLastUsed(page);
        }
    }

    /**
     * Checks if a page is currently in memory.
     *
     * @param page The number of the page to check.
     * @return True if the page is in memory, false otherwise.
     */
    private boolean isPageInMemory(int page) {
        return memory.contains(page);
    }

    /**
     * Checks if the page frame is full.
     *
     * @return True if the page frame is full, false otherwise.
     */
    private boolean isPageFrameFull() {
        return memory.size() >= pageFrameCount;
    }

    /**
     * Evicts the least recently used page from memory.
     *
     * @param page The number of the page to be evicted.
     */
    private void evictPage(int page) {
        int index = getLeastRecentlyUsedPageIndex();
        memory.remove(index);
        pageFaultCount++;
    }

    /**
     * Finds the index of the least recently used page in memory.
     *
     * @return The index of the least recently used page.
     */
    private int getLeastRecentlyUsedPageIndex() {
        int minIndex = Integer.MAX_VALUE;
        int minValue = Integer.MAX_VALUE;
        for (int i = 0; i < memory.size(); i++) {
            int page = memory.get(i);
            int lastUsed = pageLastUsedMap.getOrDefault(page, Integer.MIN_VALUE);
            if (lastUsed < minValue) {
                minValue = lastUsed;
                minIndex = i;
            }
        }
        return minIndex;
    }


    /**
     * Adds a page to memory and updates its last used timestamp.
     *
     * @param page The number of the page to add to memory.
     */
    private void addPageToMemory(int page) {
        if (memory.size() >= pageFrameCount) {
            memory.remove(0);
        }
        memory.add(page);
        updatePageLastUsed(page);
    }

    /**
     * Updates the timestamp of the last use of a page.
     *
     * @param page The number of the page whose last use timestamp should be updated.
     */
    private void updatePageLastUsed(int page) {
        pageLastUsedMap.put(page, pageLastUsedMap.size());
    }

    /**
     * Returns the total number of page faults that occurred during the execution of the algorithm.
     *
     * @return The total number of page faults.
     */
    public int getPageFaultCount() {
        return pageFaultCount;
    }

    private final List<Integer> memory = new ArrayList<>();
}