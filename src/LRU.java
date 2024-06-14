package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LRU extends ReplacementAlgorithm {
    private final int pageFrameCount;
    private final List<Integer> referenceString;
    private int pageFaultCount;
    private final Map<Integer, Integer> pageLastUsedMap;

    public LRU(int pageFrameCount, List<Integer> referenceString) {
        super(pageFrameCount);
        this.pageFrameCount = pageFrameCount;
        this.referenceString = referenceString;
        this.pageFaultCount = 0;
        this.pageLastUsedMap = new HashMap<>();
    }

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

    private boolean isPageInMemory(int page) {
        return memory.contains(page);
    }

    private boolean isPageFrameFull() {
        return memory.size() >= pageFrameCount;
    }

    private void evictPage(int page) {
        int index = getLeastRecentlyUsedPageIndex();
        memory.remove(index);
        pageFaultCount++;
    }

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

    private void addPageToMemory(int page) {
        if (memory.size() >= pageFrameCount) {
            memory.remove(0);
        }
        memory.add(page);
        updatePageLastUsed(page);
    }

    private void updatePageLastUsed(int page) {
        pageLastUsedMap.put(page, pageLastUsedMap.size());
    }

    public int getPageFaultCount() {
        return pageFaultCount;
    }

    private final List<Integer> memory = new ArrayList<>();
}