import java.util.ArrayList;

public class SearchBuilder {

    //ATTRIBUTES
    private static SearchBuilder   instance = null; //reference to itself



    //METHODS
    /**
     * Private Constructor. Call by getInstance().
     */
    protected SearchBuilder()
    {

    }

    /**
     * Singleton method to create one instance of SearchBuilder using constructor and return it to the caller. If one instance
     * already exists it returns a reference to that instance instead. Allows only one instance of SearchBuilder to exist.
     * @return returns a reference to the one and only instance of SearchBuilder.
     */
    public static SearchBuilder getInstance()
    {
        if (instance == null)
        {
            instance = new SearchBuilder();
        }

        return instance;
    }

    /**
     * Checks to see if titles match
     * @param title name of movie being searched for
     * @param m movie whose title is being checked
     * @return whether or not the titles match
     */
    protected Boolean matchTitle(String title, Movie m)
    {
        return m.getTitle().toLowerCase().equals(title.toLowerCase());
    }


    /**
     * checks to see if the ratings is at or above the cutoff
     * @param rating value being used for comparison
     * @param m movie whose value is being checked
     * @return whether or not the ratings is high enough
     */
    protected Boolean matchRating(double rating, Movie m)
    {
        return m.getRating() <= rating;
    }


    /**
     * Checks to see if directors match
     * @param director name being searched for
     * @param m movie whose director is being checked
     * @return whether or not the titles match
     */
    protected Boolean matchDirector(String director, Movie m)
    {
        return m.getDirector().toLowerCase().equals(director.toLowerCase());
    }

    /**
     * Checks to see if years match
     * @param year value being searched for
     * @param m movie whose year value is being checked
     * @return whether or not the years match
     */
    protected Boolean matchYear(int year, Movie m)
    {
        return m.getYear().equals(year);
    }

    /**
     * checks to see if the genres match
     * @param genre genre being searched for
     * @param m movie whose genre is being checked
     * @return whether or not the genres match
     */
    protected Boolean matchGenre(String genre, Movie m)
    {
        return m.getGenre().toLowerCase().equals(genre.toLowerCase());
    }

    /**
     * checks to see if a given movie contains a given actor
     * @param actor name being searched for
     * @param m movie whose ActorList is being checked
     * @return whether or not actor exists within m's ActorList
     */
    protected Boolean matchActor(String actor, Movie m)
    {
        boolean match = false;
        for (String mActor: m.getActors()) {
            if (mActor.toLowerCase().equals(actor.toLowerCase())) match = true;
        }

        return match;
    }

    /**
     * Searches through FilmFinder's MasterList of Movies according to the search constraints contained inside a single
     * Movie object's attributes. It sets the ResultsList inside of FilmFinder to whatever search finds, and
     * finally calls FilmFinder.passResultsList() to send results to ResultsView. Assumes all fields in constraints are
     * initialized to something, not NULL.
     *@param constraints: a new Movie object that holds the GUI SearchView results of a search
     */
    public void search(Movie constraints)
    {
        FilmFinder filmFinder = FilmFinder.getInstance(); //used to access Master Movie list
        filmFinder.getResultsList().clear(); //clears all previous search results

        //Checks if constraint attribute has any valid values in it
        Boolean titleEmpty = true;
        Boolean ratingEmpty = true;
        Boolean directorEmpty = true;
        Boolean yearEmpty = true;
        Boolean genreEmpty = true;
        Boolean actorEmpty = true;

        //STAGE 1: setting flags----------------------------------------------------------
        if(!constraints.getTitle().isEmpty()) titleEmpty = false;
        if(constraints.getRating().doubleValue() >= 0.0) ratingEmpty = false;
        if(!constraints.getDirector().isEmpty()) directorEmpty = false;
        if(constraints.getYear().intValue() > -1) yearEmpty = false;
        if(!constraints.getGenre().isEmpty()) genreEmpty = false;
        if(!constraints.getActors().isEmpty()) actorEmpty = false;

        //STAGE2 searching FilmFinder.MasterList and Adding to results List---------------------------------------------------------------

        ArrayList<Movie> resultsList = new ArrayList<Movie>();

        //If title is not empty, search by title
        //Else, search by description
        if (!titleEmpty) {
            //For each movie in FilmFinder, if movie's title matches constraints, add movie to resultsList
            for (Movie m : FilmFinder.getInstance().getMasterList()) {
                if (matchTitle(constraints.getTitle(), m)) resultsList.add(m);
            }
        }
        else {
            //For each movie in FilmFinder, if movie's description conforms to constraints, add movie to resultsList
            for (Movie m: FilmFinder.getInstance().getMasterList()) {
                if (!ratingEmpty){
                    //If FilmFinder movie does not conform, continue on to next movie
                    if (matchRating(constraints.getRating(), m)) continue;
                }
                if (!directorEmpty){
                    //If FilmFinder movie does not conform, continue on to next movie
                    if (!matchDirector(constraints.getDirector(), m)) continue;
                }
                if (!yearEmpty){
                    //If FilmFinder movie does not conform, continue on to next movie
                    if (!matchYear(constraints.getYear(), m)) continue;
                }
                if (!genreEmpty){
                    //If FilmFinder movie does not conform, continue on to next movie
                    if (!matchGenre(constraints.getGenre(), m)) continue;
                }
                if (!actorEmpty){
                    //If FilmFinder movie does not conform, continue on to next movie
                    //Iterate through each actor in movie.
                    //If no actors match the constraint, continue
                    //Else, add movie to list
                    if (!matchActor(constraints.getActors().get(0), m)) continue;
                }
                //If FilmFinder movie matches constraints or no constraints are entered, add the movie
                resultsList.add(m);
            }
        }

        //Nothing was found
        if(resultsList.isEmpty())
        {
            //Create a movie object just for carrying a message.
            ArrayList<String> emptyActorList = new ArrayList<String>();
            Movie message = new Movie("No movies found with that search. Try again!\n", -1, "", emptyActorList, -1.0, "", false);
            filmFinder.getResultsList().add(message);
            filmFinder.passResultsList(); //pass message
        }
        else //Some movies were found! Copies results into FilmFinder's ResultsList attribute.
        {
            for(Movie m : resultsList)
            {
                filmFinder.getResultsList().add(m);
            }
            filmFinder.passResultsList(); //pass found movies to ResultsView
        }
        System.out.println("SearchBuilder: Search finished!");
    }

}
