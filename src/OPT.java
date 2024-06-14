package src;

import java.util.ArrayList;
import java.util.List;


public class OPT extends ReplacementAlgorithm {
    private final int pageFrameCount;
    private final List<Integer> referenceString;
    private int pageFaultCount;

    public OPT(int pageFrameCount, List<Integer> referenceString) {
        super(pageFrameCount);
        this.pageFrameCount = pageFrameCount;
        this.referenceString = referenceString;
        this.pageFaultCount = 0;
    }

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


    private boolean isPageInMemory(int page) {
        return memory.contains(page);
    }

    private boolean isPageFrameFull() {
        return memory.size() >= pageFrameCount;
    }

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

    private int getNextAppearanceIndex(int page, List<Integer> referenceString, int currentIndex) {
        for (int i = currentIndex; i < referenceString.size(); i++) {
            if (page == referenceString.get(i)) {
                return i;
            }
        }
        return referenceString.size();
    }

    private void addPageToMemory(int page) {
        if (memory.size() >= pageFrameCount) {
            memory.remove(0);
        }
        memory.add(page);
    }

    public int getPageFaultCount() {
        return pageFaultCount;
    }

    private final List<Integer> memory = new ArrayList<>();
}
