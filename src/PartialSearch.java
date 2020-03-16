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
     * Compare titles
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

    @Override
    protected Boolean matchDirector(String director, Movie m)
    {
        return (m.getDirector().toLowerCase().contains(director.toLowerCase()));
    }

    @Override
    protected Boolean matchGenre(String genre, Movie m)
    {
        return (m.getGenre().toLowerCase().contains(genre.toLowerCase()));
    }

    @Override
    protected Boolean matchActor(String actor, Movie m)
    {
        boolean conforms = false;

        for (String mActor: m.getActors()) {
            if (mActor.toLowerCase().contains(actor.toLowerCase())) conforms = true;
        }

        return conforms;
    }

    @Override
    protected Boolean matchYear(int year, Movie m) {
        boolean conforms = false;
        if (m.getYear() <= year + 2 && m.getYear() >= year - 1) {
            conforms = true;
        }

        return conforms;
    }
    

}
