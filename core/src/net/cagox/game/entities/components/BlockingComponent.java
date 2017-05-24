package net.cagox.game.entities.components;

import com.badlogic.ashley.core.Component;

/**
 *  A Component that identifies the Entity that blocks movement into its space.
 *<p>
 * Primarily this is to block movement through things like walls, but it may also eventually be
 * added to NPCs and the like.
 *
 * @author      Kenneth M. Burling (burlingk) <burlingk@cagox.net>
 * @version     0.1
 * @since       0.1
 */
public class BlockingComponent implements Component {
    public boolean isBlocking = true;
}
