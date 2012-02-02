package com.bigaloncode.core.observer;

/**
 * Handles instantiating new
 * 
 * @author alanr
 * 
 */
public interface FilterFactory {

    public Filter getFilter(final Class<? extends Filter> type);
}
