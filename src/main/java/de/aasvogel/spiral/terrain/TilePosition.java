package de.aasvogel.spiral.terrain;

/**
 * TilePosition marks the Position of a {@link Tile} on the {@link Grid}.
 * <p>
 * It contains a few Methods for easily finding Tiles in the proximity.
 */
public class TilePosition
{
    /**
     * [Z.B. 123° East]
     * <p>
     * Nothing to do with Geography. Denotes merely the distance to the Center of the Grid along the
     * "X-Axis". With each "point" being 0.5 m.
     */
    private final long longitude;
    /**
     * [Z.B. 15° North]
     * <p>
     * Nothing to do with Geography. Denotes merely the distance to the Center of the Grid along the
     * "Y-Axis". With each "point" being 0.5 m.
     */
    private final long latitude;

    public TilePosition(long longitude, long latitude)
    {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public long getLongitude()
    {
        return longitude;
    }

    public long getLatitude()
    {
        return latitude;
    }

    public double getDistanceTo_m(TilePosition other)
    {
        double a = Math.abs(other.longitude - this.longitude);
        double b = Math.abs(other.latitude - this.latitude);
        return Math.sqrt(a * a + b * b) /2;
    }

    // ***********************************************************
    // generated Methods
    // ***********************************************************

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TilePosition that = (TilePosition)o;

        if (longitude != that.longitude)
            return false;
        if (latitude != that.latitude)
            return false;

        return true;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("TilePosition{");
        sb.append("longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode()
    {
        int result = (int)(longitude ^ (longitude >>> 32));
        result = 31 * result + (int)(latitude ^ (latitude >>> 32));
        return result;
    }
}
