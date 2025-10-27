package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    private int callsMade = 0;
    private BreedFetcher fetcher;
    private Hashtable<String, List<String>> cache;

    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.fetcher = fetcher;
        this.cache = new Hashtable<>();
    }

    @Override
    public List<String> getSubBreeds(String breed) {
        if (this.cache.containsKey(breed)){
            return cache.get(breed);
        } else {
            try{
                this.callsMade += 1;
                List<String> subBreeds = this.fetcher.getSubBreeds(breed);
                this.cache.put(breed, subBreeds);
                return subBreeds;
            } catch (BreedNotFoundException bfe){
                throw new BreedNotFoundException(breed);
            }
        }
    }
        // return statement included so that the starter code can compile and run.

    public int getCallsMade() {
        return callsMade;
    }
}