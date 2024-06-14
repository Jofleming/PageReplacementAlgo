package src;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Optimal (OPT) page replacement algorithm.
 * In OPT, the page that will not be used for the longest period of time in the future is replaced first when a new page needs to be added.
 */
public class OPT extends ReplacementAlgorithm {
    private final int pageFrameCount;
    private final List<Integer> referenceString;
    private int pageFaultCount;

    /**
     * Constructs a new OPT instance with the specified number of page frames and reference string.
     *
     * @param pageFrameCount   The number of page frames available in memory.
     * @param referenceString  The reference string representing the order of page accesses.
     */
    public OPT(int pageFrameCount, List<Integer> referenceString) {
        super(pageFrameCount);
        this.pageFrameCount = pageFrameCount;
        this.referenceString = referenceString;
        this.pageFaultCount = 0;
    }

    /**
     * Inserts a new page into memory using the OPT replacement algorithm.
     * If the page is not already in memory, it is added to memory.
     * If the page frame is full, the page that will not be used for the longest period of time in the future is evicted to make space.
     *
     * @param page The number of the page to be inserted.
     */
    public void insert(int page) {
        if (!isPageInMemory(page)) {
            if (isPageFrameFull()) {
                int nextAppearanceIndex = getNextAppearanceIndex(page, referenceString, 0);
                evictPage(page, nextAppearanceIndex);
            } else {
                pageFaultCount++;
            }
            addPageToMemory(page);
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
     * Evicts the page that will not be used for the longest period of time in the future from memory.
     *
     * @param page               The number of the page to be evicted.
     * @param nextAppearanceIndex The index of the next appearance of the page in the reference string.
     */
    private void evictPage(int page, int nextAppearanceIndex) {
        int furthestIndex = -1;
        int maxDistance = -1;

        for (int i = 0; i < memory.size(); i++) {
            int currentPage = memory.get(i);
            int distance = getNextAppearanceIndex(currentPage, referenceString, nextAppearanceIndex) - nextAppearanceIndex;
            if (distance == -1) {
                furthestIndex = i;
                break;
            }
            if (distance > maxDistance) {
                maxDistance = distance;
                furthestIndex = i;
            }
        }

        memory.remove(furthestIndex);
        memory.add(furthestIndex, page);
        pageFaultCount++;
    }

    /**
     * Finds the index of the next appearance of a page in the reference string.
     *
     * @param page        The number of the page to find the next appearance index for.
     * @param referenceString The reference string representing the order of page accesses.
     * @param currentIndex    The current index in the reference string from which to start searching.
     * @return The index of the next appearance of the page in the reference string, or the length of the reference string if the page is not found.
     */
    private int getNextAppearanceIndex(int page, List<Integer> referenceString, int currentIndex) {
        for (int i = currentIndex; i < referenceString.size(); i++) {
            if (page == referenceString.get(i)) {
                return i;
            }
        }
        return referenceString.size();
    }

    /**
     * Adds a page to memory.
     * If the page frame is full, the least recently used page is evicted to make space.
     *
     * @param page The number of the page to add to memory.
     */
    private void addPageToMemory(int page) {
        if (memory.size() >= pageFrameCount) {
            memory.remove(0);
        }
        memory.add(page);
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
