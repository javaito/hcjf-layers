package org.hcjf.layers.crud;

import org.hcjf.layers.LayerInterface;
import org.hcjf.layers.query.Queryable;

import java.util.Collection;

/**
 * @author javaito
 */
public interface UpdateLayerInterface<O extends Object> extends LayerInterface {

    /**
     * This method implements the update of the resource.
     * @param object Instance of the resource that going to be updated.
     *               This instance must have an id to identify the updatable data.
     * @return The instance updated.
     */
    default O update(O object) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method implements the update for many object in the same call.
     * @param objects Objects to update.
     * @return The instances updated.
     */
    default Collection<O> update(Collection<O> objects) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method implements the update operation over an add of the instances,
     * this instances are selected using the query like a match.
     * @param queryable Instance that contains all the information to evaluate a query.
     * @param object Contains the values for all the instances that found the query evaluation.
     * @return Return the instances updated.
     */
    default Collection<O> update(Queryable queryable, O object) {
        throw new UnsupportedOperationException();
    }

}
