package de.aasvogel.spiral.terrain;

public class TestTilePositionUtil
{

    private static final TilePosition CENTER = new TilePosition(0, 0);

    public void testAll(){
        testGetAllNeighbours();
        testIterator();
        testStream();
    }

    public void testGetAllNeighbours()
    {
        long count = TilePositionUtil.getAllNeighbours(CENTER).peek(System.out::println).count();

        if (count != 8)
            throw new IllegalStateException("Center with less than 8 Neighbours! " + count);
    }

    public void testIterator()
    {
        TilePositionUtil.TilePositionIterator iterator = new TilePositionUtil.TilePositionIterator(CENTER);

        for (int i = 0; i < 4; i++)
        {
            TilePosition next = iterator.next();
            System.out.println(next);
            asscertainDistance(CENTER, next, 0.5);
        }

        for (int i = 0; i < 4; i++)
        {
            TilePosition next = iterator.next();
            System.out.println(next);
            asscertainDistance(CENTER, next, 0.70);
        }

        for (int i = 0; i < 4; i++)
        {
            TilePosition next = iterator.next();
            System.out.println(next);
            asscertainDistance(CENTER, next, 1);
        }

        for (int i = 0; i < 4 ; i++){
            TilePosition next = iterator.next();
            System.out.println(next);
            asscertainDistance(CENTER, next, 1.12);
        }

    }

    public void testStream()
    {
        TilePositionUtil.getClosestPositions(CENTER)//
            .limit(4) //
            .peek(System.out::println) //
            .forEach(p -> asscertainDistance(CENTER, p, 0.5));//

        TilePositionUtil.getClosestPositions(CENTER)//
            .skip(4)//
            .limit(4) //
            .peek(System.out::println) //
            .forEach(p -> asscertainDistance(CENTER, p, 0.70));//

//        TilePositionUtil.getClosestPositions(CENTER)//
//            .skip(8)//
//            .limit(4) //
//            .peek(System.out::println) //
//            .forEach(p -> asscertainDistance(CENTER, p, 1));//
//
//        TilePositionUtil.getClosestPositions(CENTER)//
//            .skip(12)//
//            .limit(4) //
//            .peek(System.out::println) //
//            .forEach(p -> asscertainDistance(CENTER, p, 1));//
    }

    private void asscertainDistance(TilePosition center, TilePosition other, double expected)
    {
        double actual = center.getDistanceTo_m(other);

        if (actual < 0)
            throw new IllegalStateException("Negative Distance not possible! " + actual);

        double epsilon = 0.05; // 5 cm.
        if (Math.abs(actual - expected) > epsilon)
        {
            throw new IllegalStateException(
                "Unexpected Distance! expected: " + expected + " actual: " + actual);
        }
    }
}
