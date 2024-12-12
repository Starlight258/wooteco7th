package pairmatching.domain.pair;

import static pairmatching.exception.ErrorMessage.PAIR_MATCH_ORDER_FAILED;

import java.util.List;
import pairmatching.domain.order.Level;
import pairmatching.domain.order.PairOrder;
import pairmatching.exception.CustomIllegalArgumentException;
import pairmatching.exception.ErrorMessage;

public class PairHistory {

    private final List<PairResult> results;

    public PairHistory(final List<PairResult> results) {
        this.results = results;
    }

    public void add(final PairResult pairResult) {
        PairOrder pairOrder = pairResult.getPairOrder();
        if (isExists(pairOrder)) {
            PairResult samePairOrderResult = findSamePairOrderResult(pairOrder);
            results.remove(samePairOrderResult);
        }
        results.add(pairResult);
    }

    public PairResult findSamePairOrderResult(final PairOrder compared) {
        return results.stream()
                .filter(pairResult -> pairResult.getPairOrder().equals(compared))
                .findAny()
                .orElseThrow(() -> new CustomIllegalArgumentException(PAIR_MATCH_ORDER_FAILED));
    }

    public boolean isExists(final PairOrder compared) {
        return results.stream()
                .map(PairResult::getPairOrder)
                .anyMatch(pairOrder -> pairOrder.equals(compared));
    }

    public boolean hasSameLevelPair(final Pair pair, final Level level) {
        return results.stream()
                .filter(result -> result.hasSameLevel(level))
                .anyMatch(result -> result.hasSamePair(pair));
    }

    public PairResult inquire(final PairOrder compared) {
        return results.stream()
                .filter(result -> result.getPairOrder().equals(compared))
                .findAny()
                .orElseThrow(() -> new CustomIllegalArgumentException(ErrorMessage.INVALID_NO_HISTORY));
    }

    public void clear() {
        results.clear();
    }
}