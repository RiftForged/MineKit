package com.riftforged.minekit.wrapper;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

/**
 * An immutable wrapper class for the {@link Player} class.
 * This class is used to wrap a Player object and provide additional functionality and optimisations.
 */
public class PlayerWrapper implements Wrapper<Player>
{

    private final Player player;

    /**
     * Creates a new PlayerWrapper for the given Player.
     *
     * @param player The player to wrap.
     */
    public PlayerWrapper(@NotNull Player player)
    {
        this.player = player;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull PlayerWrapper wrap(@NotNull Player player)
    {
        return new PlayerWrapper(player);
    }

    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    @NotNull
    public final String getName()
    {
        return this.player.getName();
    }

    /**
     * Gets the unique identifier of the player.
     *
     * @return The unique identifier of the player.
     */
    @NotNull
    public final UUID getIdentifier()
    {
        return this.player.getUniqueId();
    }

    /**
     * Gets the wrapped Player object.
     *
     * @return The wrapped Player object.
     */
    @NotNull
    public final Player getPlayer()
    {
        return this.player;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof PlayerWrapper that)) return false;

        return Objects.equals(this.player, that.player);
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @NotNull
    @Override
    public Player unwrap()
    {
        return this.getPlayer();
    }
}
