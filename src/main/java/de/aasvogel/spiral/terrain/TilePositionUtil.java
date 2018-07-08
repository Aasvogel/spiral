package de.aasvogel.spiral.terrain;

import java.io.Serializable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This Class contains a few utility-Methods for {@link TilePosition}. All of them static.
 */
public class TilePositionUtil
{
    public static TilePosition getNeighbour(TilePosition source, CardinalDiraction direction)
    {
        switch (direction)
        {
        case EAST:
            return new TilePosition(source.getLongitude() + 1, source.getLatitude());
        case NORTHEAST:
            return new TilePosition(source.getLongitude() + 1, source.getLatitude() + 1);
        case NORTH:
            return new TilePosition(source.getLongitude(), source.getLatitude() + 1);
        case NORTHWEST:
            return new TilePosition(source.getLongitude() - 1, source.getLatitude() + 1);
        case WEST:
            return new TilePosition(source.getLongitude() - 1, source.getLatitude());
        case SOUTHWEST:
            return new TilePosition(source.getLongitude() - 1, source.getLatitude() - 1);
        case SOUTH:
            return new TilePosition(source.getLongitude(), source.getLatitude() - 1);
        case SOUTHEAST:
            return new TilePosition(source.getLongitude() + 1, source.getLatitude() - 1);
        default:
            throw new IllegalArgumentException("Unknown CardinalDirection: " + direction);
        }
    }

    public static Stream<TilePosition> getAllNeighbours(TilePosition source)
    {
        return Arrays.stream(CardinalDiraction.values())//
            .map(d -> getNeighbour(source, d));
    }

    public static Stream<TilePosition> getClosestPositions(TilePosition center)
    {
        TilePositionIterator tilePositionIterator = new TilePositionIterator(center);

        int characteristics = Spliterator.ORDERED;
        Spliterator<TilePosition> tilePositionSpliterator =
            Spliterators.spliteratorUnknownSize(tilePositionIterator, characteristics);

        return StreamSupport.stream(tilePositionSpliterator, false);
    }
    // ***********************************************************
    // The Iterator
    // ***********************************************************

    /**
     * Iterator over the Tiles. Tries to get the closest ones first...
     */
    static class TilePositionIterator implements Iterator<TilePosition>
    {
        private final TilePosition startingPoint;
        private final DistancComparator comparator = new DistancComparator();

        private final List<TilePosition> possibilities = new ArrayList<>();
        private final Set<TilePosition> handeled = new HashSet<>();

        TilePositionIterator(TilePosition startingPoint)
        {
            this.startingPoint = startingPoint;
            handleTilePosition(startingPoint);
        }

        /**
         * there is always an next Position!
         *
         * @return
         */
        @Override
        public boolean hasNext()
        {
            return true;
        }

        @Override
        public TilePosition next()
        {
            possibilities.sort(comparator);
            TilePosition next = possibilities.remove(0);

            handleTilePosition(next);
            return next;
        }

        /**
         * The neighbours of the given {@link TilePosition} are added to the possibilities (if not already
         * handeled.
         *
         * @param tilePosition
         */
        private void handleTilePosition(TilePosition tilePosition)
        {
            TilePositionUtil.getAllNeighbours(tilePosition)//
                .filter(p -> !possibilities.contains(p))//
                .filter(p -> !handeled.contains(p))//
                .forEach(possibilities::add); // expensive if posibilities already big.

            handeled.add(tilePosition);
        }

        /**
         * Comparator prefering Tiles closer to StartingPoint. (Precisicion 1/1000)
         */
        private class DistancComparator implements Comparator<TilePosition>, Serializable
        {
            private static final long serialVersionUID = -1764058134431595406L;

            @Override
            public int compare(TilePosition o1, TilePosition o2)
            {
                double difference = o1.getDistanceTo_m(startingPoint) - o2.getDistanceTo_m(startingPoint);

                return (int)(difference * 1000);
            }
        }
    }

    // ***********************************************************
    // private Constructor
    // ***********************************************************
    private TilePositionUtil(){ }

}
