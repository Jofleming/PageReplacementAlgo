package Tests;

import org.junit.jupiter.api.Test;
import src.FIFO;
import src.LRU;
import src.OPT;
import src.ReplacementAlgorithm;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class TestPageReplacement {

    @Test
    public void testFIFO() {
        ReplacementAlgorithm fifo = new FIFO(3);
        List<Integer> referenceString = Arrays.asList(7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1);
        for (int page : referenceString) {
            fifo.insert(page);
        }
        assertEquals(15, fifo.getPageFaultCount());
    }

    @Test
    public void testOPT() {
        List<Integer> referenceString = Arrays.asList(2, 3, 2, 1, 5, 2, 4, 5, 3, 2, 5, 2);
        int pageFrameCount = 3;

        OPT opt = new OPT(pageFrameCount, referenceString);

        opt.insert(2);
        opt.insert(3);
        opt.insert(2);
        opt.insert(1);
        opt.insert(5);
        opt.insert(2);
        opt.insert(4);
        opt.insert(5);
        opt.insert(3);
        opt.insert(2);
        opt.insert(5);
        opt.insert(2);

        assertEquals(9, opt.getPageFaultCount());
    }

    @Test
    void testLRU() {
        List<Integer> referenceString = Arrays.asList(1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5);
        ReplacementAlgorithm lru = new LRU(3, referenceString);
        for (int page : referenceString) {
            lru.insert(page);
        }
        assertEquals(10, lru.getPageFaultCount());
    }
}