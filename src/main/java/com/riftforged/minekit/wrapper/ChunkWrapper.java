package com.riftforged.minekit.wrapper;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * An immutable wrapper class for the {@link Chunk} class.
 * This class is used to wrap a Chunk object and provide additional functionality and optimisations.
 * Note: This class is lazily loaded and calling the {@link #getChunk()} method for the first time may call {@link World#getChunkAt(int, int)}.
 */
public final class ChunkWrapper implements Wrapper<Chunk>
{

    private final World world;
    private final int x, z;

    private Chunk chunk; // Lazy load

    /**
     * Creates a new ChunkWrapper for the given Chunk.
     *
     * @param chunk The chunk to wrap.
     * @since 1.0.0
     */
    public ChunkWrapper(@NotNull Chunk chunk)
    {
        this(chunk.getWorld(), chunk.getX(), chunk.getZ());

        this.chunk = chunk;
    }

    /**
     * Creates a new ChunkWrapper for the given World and chunk coordinates.
     * Note: This constructor doesn't check if the chunk actually exists in the world.
     *
     * @param world The world the chunk is in.
     * @param x     The x coordinate of the chunk.
     * @param z     The z coordinate of the chunk.
     * @since 1.0.0
     */
    public ChunkWrapper(@NotNull World world, int x, int z)
    {
        this.world = world;
        this.x = x;
        this.z = z;
    }

    /**
     * Convenience method.
     * Creates a new ChunkWrapper for the given {@link Chunk}.
     * Basically calls the constructor.
     *
     * @param chunk The chunk to wrap.
     * @return The ChunkWrapper.
     * @since 1.0.0
     */
    @Contract("_ -> new")
    public static @NotNull ChunkWrapper wrap(@NotNull Chunk chunk)
    {
        return new ChunkWrapper(chunk);
    }

    /**
     * Convenience method.
     * Creates a new ChunkWrapper using the {@link Chunk} the given {@link Entity} resides in.
     *
     * @param entity The entity to get the chunk from.
     * @return The ChunkWrapper.
     * @since 1.0.0
     */
    @Contract("_ -> new")
    public static @NotNull ChunkWrapper wrap(@NotNull Entity entity)
    {
        return new ChunkWrapper(entity.getLocation().getChunk());
    }

    /**
     * Convenience method.
     * Creates a new ChunkWrapper using the {@link Chunk} the given {@link Block} resides in.
     *
     * @param block The block to get the chunk from.
     * @return The ChunkWrapper.
     * @since 1.0.0
     */
    @Contract("_ -> new")
    public static @NotNull ChunkWrapper wrap(@NotNull Block block)
    {
        return new ChunkWrapper(block.getChunk());
    }

    /**
     * Convenience method.
     * Creates a new ChunkWrapper using the {@link Chunk} the given {@link org.bukkit.Location} resides in.
     *
     * @param location The location to get the chunk from.
     * @return The ChunkWrapper.
     * @since 1.0.0
     */
    @Contract("_ -> new")
    public static @NotNull ChunkWrapper wrap(@NotNull Location location)
    {
        return new ChunkWrapper(location.getChunk());
    }

    /**
     * Returns the Chunk object for this ChunkWrapper.
     * Note: This method is lazily loaded and calling it for the first time may call {@link World#getChunkAt(int, int)}.
     *
     * @return The Chunk object.
     * @since 1.0.0
     */
    public @NotNull Chunk getChunk()
    {
        if (this.chunk == null)
        {
            this.chunk = this.world.getChunkAt(this.x, this.z);
        }

        return this.chunk;
    }

    /**
     * Returns the 8 ChunkWrappers adjacent to this ChunkWrapper.
     * The list doesn't include this ChunkWrapper, only the ones adjacent to it.
     * Note: These ChunkWrappers are all lazily loaded and calling the {@link #getChunk()}
     * method for the first time will call {@link World#getChunkAt(int, int)}.
     *
     * @return The 8 adjacent ChunkWrappers.
     * @since 1.0.0
     */
    @UnmodifiableView
    public @NotNull List<ChunkWrapper> getAdjacentChunks()
    {
        List<ChunkWrapper> adjacent = new ArrayList<>(8);
        World world = this.getWorld();
        int x = this.getX();
        int z = this.getZ();

        int[] xOffsetArray = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] zOffsetArray = {-1, -1, -1, 0, 0, 1, 1, 1};

        for (int i = 0; i < 8; i++)
        {
            int surroundingX = x + xOffsetArray[i];
            int surroundingZ = z + zOffsetArray[i];

            ChunkWrapper wrapper = new ChunkWrapper(world, surroundingX, surroundingZ);
            adjacent.add(wrapper);
        }

        return Collections.unmodifiableList(adjacent);
    }

    /**
     * Returns the {@link World} object for this ChunkWrapper.
     *
     * @return The World object.
     * @since 1.0.0
     */
    public @NotNull World getWorld()
    {
        return this.world;
    }

    /**
     * Returns the x coordinate of this ChunkWrapper.
     *
     * @return The x coordinate.
     * @since 1.0.0
     */
    public int getX()
    {
        return this.x;
    }

    /**
     * Returns the z coordinate of this ChunkWrapper.
     *
     * @return The z coordinate.
     * @since 1.0.0
     */
    public int getZ()
    {
        return this.z;
    }

    /**
     * Returns a list of all the entities in this chunk.
     *
     * @return A list of all the entities in this chunk.
     * @since 1.0.0
     */
    @UnmodifiableView
    public List<Entity> getEntities()
    {
        return List.of(this.getChunk().getEntities());
    }

    /**
     * Returns a list of all the players in this chunk.
     *
     * @return A list of all the players in this chunk.
     * @since 1.0.0
     */
    @UnmodifiableView
    public List<Player> getPlayers()
    {
        return this.getEntities()
                .stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof ChunkWrapper that)) return false;
        return this.getX() == that.getX() && this.getZ() == that.getZ() && Objects.equals(this.getWorld(), that.getWorld());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.getWorld(), this.getX(), this.getZ());
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public @NotNull Chunk unwrap()
    {
        return this.getChunk();
    }
}
