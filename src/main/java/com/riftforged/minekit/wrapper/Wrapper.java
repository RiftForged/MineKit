package com.riftforged.minekit.wrapper;

import org.jetbrains.annotations.NotNull;

/**
 * An interface for a wrapper class.
 * This interface is used to wrap an object and provide additional functionality and optimisations.
 * The wrapped object can be retrieved using the {@link #unwrap()} method.
 *
 * @param <T> The type of the wrapped object.
 */
public interface Wrapper<T>
{

    /**
     * Returns the wrapped object.
     *
     * @return The wrapped object.
     * @since 1.0.0
     */
    @NotNull
    T unwrap();
}
