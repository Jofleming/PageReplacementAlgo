# Page Replacement Algorithms

## Project Summary
This project implements three page replacement algorithms: FIFO, LRU, and OPT (Optimal). The algorithms are tested using specific reference strings and random page-reference strings with varying page frame counts.


## Results
The optimal algorithm (OPT) consistently performed the best with the fewest page faults. This is expected since OPT is designed to minimize faults by predicting future page references. FIFO had the most page faults due to its lack of consideration for recent page usage, while LRU performed better than FIFO but not as well as OPT.

## Big O Runtime Analysis
- FIFO: O(1) for each page reference, resulting in O(n) for n references.
- LRU: O(n) for updating the page reference list, resulting in O(n) for n references.
- OPT: O(n) for finding the furthest page in the future list, resulting in O(n^2) for n references.

## Conclusion
The OPT algorithm is the most efficient in terms of minimizing page faults, but it is not practical for real-time systems due to its need for future knowledge. LRU offers a good balance between performance and implementation complexity, while FIFO is the simplest but least efficient.
