package com.riftforged.minekit.wrapper;

import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * An immutable wrapper class for the {@link Location} class.
 * This class is used to wrap a Location object and provide additional functionality and optimisations.
 */
public final class LocationWrapper implements Wrapper<Location>
{

    private final World world;
    private final double x, y, z;
    private final float yaw, pitch;

    /**
     * Creates a new LocationWrapper for the given Location.
     *
     * @param location The location to wrap.
     * @since 1.0.0
     */
    public LocationWrapper(@NotNull Location location)
    {
        World world = location.getWorld();
        if (world == null)
            throw new IllegalArgumentException("Location's world cannot be null.");

        this.world = world;
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = Location.normalizeYaw(location.getYaw());
        this.pitch = Location.normalizePitch(location.getPitch());
    }

    /**
     * Creates a new LocationWrapper for the given World and coordinates.
     *
     * @param world The world the location is in.
     * @param x     The x coordinate of the location.
     * @param y     The y coordinate of the location.
     * @param z     The z coordinate of the location.
     * @since 1.0.0
     */
    public LocationWrapper(@NotNull World world, double x, double y, double z)
    {
        this(world, x, y, z, 0.0F, 0.0F);
    }

    /**
     * Creates a new LocationWrapper for the given World, coordinates, yaw and pitch.
     *
     * @param world The world the location is in.
     * @param x     The x coordinate of the location.
     * @param y     The y coordinate of the location.
     * @param z     The z coordinate of the location.
     * @param yaw   The yaw of the location.
     * @param pitch The pitch of the location.
     * @since 1.0.0
     */
    public LocationWrapper(@NotNull World world, double x, double y, double z, float yaw, float pitch)
    {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = Location.normalizeYaw(yaw);
        this.pitch = Location.normalizePitch(pitch);
    }

    /**
     * Convenience method.
     * Creates a new LocationWrapper for the given {@link Location}.
     * Basically calls the constructor.
     *
     * @param location The location to wrap.
     * @return The LocationWrapper.
     * @since 1.0.0
     */
    public static @NotNull LocationWrapper wrap(@NotNull Location location)
    {
        return new LocationWrapper(location);
    }

    /**
     * Returns the X coordinate of the location.
     *
     * @return The X coordinate.
     * @since 1.0.0
     */
    public double getX()
    {
        return x;
    }

    /**
     * Returns the Y coordinate of the location.
     *
     * @return The Y coordinate.
     * @since 1.0.0
     */
    public double getY()
    {
        return y;
    }

    /**
     * Returns the Z coordinate of the location.
     *
     * @return The Z coordinate.
     * @since 1.0.0
     */
    public double getZ()
    {
        return z;
    }

    /**
     * Returns the yaw of the location.
     *
     * @return The yaw.
     * @since 1.0.0
     */
    public float getYaw()
    {
        return yaw;
    }

    /**
     * Returns the pitch of the location.
     *
     * @return The pitch.
     * @since 1.0.0
     */
    public float getPitch()
    {
        return pitch;
    }

    /**
     * Returns the world of the location.
     *
     * @return The world.
     * @since 1.0.0
     */
    public @NotNull World getWorld()
    {
        return world;
    }

    /**
     * Returns a new {@link Location} object with the same world, x, y, z, yaw and pitch as this wrapper.
     *
     * @return The wrapped Location.
     * @since 1.0.0
     */
    @Contract(value = " -> new", pure = true)
    public @NotNull Location getLocation()
    {
        return new Location(this.world, this.x, this.y, this.z, this.yaw, this.pitch);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof LocationWrapper that)) return false;
        return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0 && Double.compare(that.z, z) == 0 && Float.compare(that.yaw, yaw) == 0 && Float.compare(that.pitch, pitch) == 0 && Objects.equals(world, that.world);
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public @NotNull Location unwrap()
    {
        return this.getLocation();
    }
}
