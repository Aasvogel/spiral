package de.aasvogel.spiral.terrain;

/**
 * A Tile represents a Square of 0,5m length.
 * <p>
 * It has a material, like Soil, Stone, Sand, Water...
 * <p>
 * It has mineral content.
 * <p>
 * It can have a cover.
 * <p>
 * It can be rendered.
 */
public class Tile
{
    private final Material material;

    public Tile(Material material)
    {
        this.material = material;
    }


}
