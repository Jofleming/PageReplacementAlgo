package src;

/**
 * Represents an abstract class for page replacement algorithms.
 * Subclasses must implement the insert method to define the specific algorithm.
 */
public abstract class ReplacementAlgorithm {
    protected int pageFaultCount;
    protected int pageFrameCount;

    /**
     * Constructs a new ReplacementAlgorithm instance with the specified number of page frames.
     *
     * @param pageFrameCount The number of page frames available in memory.
     * @throws IllegalArgumentException if the page frame count is negative.
     */
    public ReplacementAlgorithm(int pageFrameCount) {
        if (pageFrameCount < 0) throw new IllegalArgumentException();
        this.pageFrameCount = pageFrameCount;
        this.pageFaultCount = 0;
    }

    /**
     * Gets the total number of page faults that occurred during the execution of the algorithm.
     *
     * @return The total number of page faults.
     */
    public int getPageFaultCount() {
        return pageFaultCount;
    }

    /**
     * Inserts a new page into memory according to the specific page replacement algorithm.
     *
     * @param pageNumber The number of the page to be inserted.
     */
    public abstract void insert(int pageNumber);
}