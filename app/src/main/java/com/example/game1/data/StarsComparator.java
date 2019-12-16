package com.example.game1.data;

import com.example.game1.presentation.model.User;

import java.util.Comparator;

/**
 * A comparator for comparing User objects in terms of their top number of stars.
 */
public class StarsComparator implements Comparator<User> {

    /**
     * Compares its two arguments for order.
     *
     * <p>Returns a negative integer, zero, or a positive integer as user1 is less than, equal to, or
     * greater than user2 in terms of the top number of stars in their history.
     *
     * @param user1 the first User to compare
     * @param user2 the second User to compare
     * @return a negative integer, zero, or a positive integer as user1 is less than, equal to, or
     * greater than user2 in terms of the top number of stars in their history
     */
    @Override
    public int compare(User user1, User user2) {
        return user1.getTopIndividualStats().getStars() - user2.getTopIndividualStats().getStars();
    }
}
