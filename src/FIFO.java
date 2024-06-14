package src;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents the First-In-First-Out (FIFO) page replacement algorithm.
 * In FIFO, the oldest page in memory is replaced first when a new page needs to be added.
 */
public class FIFO extends ReplacementAlgorithm {
    private Queue<Integer> queue;

    /**
     * Constructs a new FIFO instance with the specified number of page frames.
     *
     * @param pageFrameCount The number of page frames available in memory.
     */
    public FIFO(int pageFrameCount) {
        super(pageFrameCount);
        this.queue = new LinkedList<>();
    }

    /**
     * Inserts a new page into memory using the FIFO replacement algorithm.
     * If the page is not already in memory, it is added to the queue.
     * If the queue is full, the oldest page (the first one added) is removed to make space.
     *
     * @param pageNumber The number of the page to be inserted.
     */
    @Override
    public void insert(int pageNumber) {
        if (!queue.contains(pageNumber)) {
            if (queue.size() >= pageFrameCount) {
                queue.poll();
            }
            queue.add(pageNumber);
            pageFaultCount++;
        }
    }
}
