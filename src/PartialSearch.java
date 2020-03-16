import java.util.ArrayList;

public class PartialSearch extends SearchBuilder{


    private static PartialSearch instance = null;


    /**
     * Private constructor for Singleton
     *
     */
    private PartialSearch()
    {

    }

    /**
     * Singleton method to create one instance of PartialSearch using constructor and return it to the caller. If one instance
     * already exists it returns a reference to that instance instead. Allows only one instance of PartialSearch to exist.
     * @return returns a reference to the one and only instance of PartialSearch.
     */
    public static PartialSearch getInstance()
    {
        if (instance == null)
        {
            instance = new PartialSearch();
        }

        return instance;
    }

    /**
     * Checks to see if m contains the title substring
     * @param title name of movie being searched for
     * @param m movie whose title is being checked
     * @return whether or not the substring is present
     */
    @Override
    protected Boolean matchTitle(String title, Movie m)
    {
        if (m.getTitle().toLowerCase().contains(title.toLowerCase()))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    /**
     * checks to see if the ratings is at or above the cutoff
     * @param rating value being used for comparison
     * @param m movie whose value is being checked
     * @return whether or not the ratings is high enough
     */
    @Override
    protected Boolean matchRating(double rating, Movie m)
    {
        return m.getRating() <= rating - 0.5; //creates a wider window for a match
    }

    /**
     * Checks to see if m contains the director substring
     * @param director name being searched for
     * @param m movie whose director is being checked
     * @return whether or not the substring is present
     */
    @Override
    protected Boolean matchDirector(String director, Movie m)
    {
        return (m.getDirector().toLowerCase().contains(director.toLowerCase()));
    }

    /**
     * Checks to see if years match
     * @param year value being searched for
     * @param m movie whose year value is being checked
     * @return whether or not m's year is within 2 years of the desired year
     */
    @Override
    protected Boolean matchYear(int year, Movie m) {
        boolean conforms = false;
        if (m.getYear() <= year + 2 && m.getYear() >= year - 1) {
            conforms = true;
        }

        return conforms;
    }

    /**
     * Checks to see if m contains the genre substring
     * @param genre name being searched for
     * @param m movie whose genre is being checked
     * @return whether or not the substring is present
     */
    @Override
    protected Boolean matchGenre(String genre, Movie m)
    {
        return (m.getGenre().toLowerCase().contains(genre.toLowerCase()));
    }

    /**
     * Checks to see if m contains the actor substring
     * @param actor name being searched for
     * @param m movie whose ActorList is being checked
     * @return whether or not the actor is present
     */
    @Override
    protected Boolean matchActor(String actor, Movie m)
    {
        boolean conforms = false;

        for (String mActor: m.getActors()) {
            if (mActor.toLowerCase().contains(actor.toLowerCase())) conforms = true;
        }

        return conforms;
    }



}
