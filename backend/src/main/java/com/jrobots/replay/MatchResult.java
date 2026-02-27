package com.jrobots.replay;

import java.util.List;

/**
 * MatchResult is the final outcome of a match which will be returned to the API layer.
 * <p>
 *     It wraps:
 *     <ul>
 *         <li>Arena metadata.</li>
 *         <li>Total tick executed.</li>
 *         <li>Winner id (or -1 in case of a draw).</li>
 *         <li>Replay snapshots.</li>
 *     </ul>
 * </p>
 */
public record MatchResult(
        int arenaWidth,
        int arenaHeight,
        int totalTicks,
        int winnerId,
        List<Snapshot> replay
) {}