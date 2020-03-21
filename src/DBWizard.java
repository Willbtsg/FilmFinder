import java.awt.*;
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;

public class DBWizard
{

    //Set file name 
    public static String DBNAME = "data/";

    private static DBWizard instance;

    private DBWizard()
    {

    }

    public static DBWizard getInstance()
    {
        if (instance == null)
        {
            instance = new DBWizard();
        }
        return instance;
    }

    public static boolean inputFileName()
    {
        DBNAME += JOptionPane.showInputDialog(null,"What would you like to name your file?\n(Please include '.json' at the end of the name)", "Create a new file", JOptionPane.QUESTION_MESSAGE);

        return !DBNAME.contains("null");
    }

    public static boolean chooseFileName()
    {
        String files[];
        File data = new File("data");

        files = data.list();

        DBNAME += JOptionPane.showInputDialog(null, "Please select a file to use.","Select a file", JOptionPane.PLAIN_MESSAGE, null, files, files[0]);

        return !DBNAME.contains("null");
    }

    public static boolean setFileName()
    {
        int choice;

        String[] options = {"Select a file.", "Create a new file."};

        choice = JOptionPane.showOptionDialog(null, "Would you like to edit a pre-existing file, or create a new one?", "File Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == 0) { return chooseFileName(); }
        else if (choice == 1) { return inputFileName(); }
        else { return false; }
    }


    //Don't shoot me for this.
    //https://stackoverflow.com/a/53226346/5763413
    //Stack overflow said to do it
    @SuppressWarnings("unchecked")


    /**
     * Writes the list of movies out to the master database. 
     * @param The list of movies to be written to the database.
     * @return Void
     */
    public static void writeDB(ArrayList<Movie> ml)
    {
        JSONArray list = new JSONArray();
        JSONObject obj = new JSONObject();

        for (Movie m:ml)
        {
            list.add(m.toJSON());
        }

        obj.put("MovieList", list);

        try(FileWriter file =  new FileWriter(DBNAME))
        {
            file.write(obj.toString());
            file.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Reads the Master list of movies from the database
     * @return An ArrayList of Movie objects
     *
     */

    public static ArrayList<Movie> readDB() {
        JSONParser parser = new JSONParser();

        ArrayList<Movie> theList = new ArrayList<Movie>();

        try {
            Object obj = parser.parse(new FileReader(DBNAME));
            //
            //Read JSON file
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray movieList = (JSONArray) jsonObject.get("MovieList");

            for (Object j : movieList) {
                theList.add(new Movie((JSONObject) j));
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return theList;
    }



    /**
     * Main function for testing
     */
    public static void main(String args[])
    {

        if (!setFileName()) { return; }

        ArrayList<Movie> MovieList = readDB();
        ArrayList<Movie> NewMovies = new ArrayList();

        /*
        System.out.println("\nPRINTING MOVIE LIST");
        for (Movie m: MovieList)
        {
            System.out.println(m);
            System.out.println(); 
        }
        System.out.println("END MOVIE LIST");
        */

        //create a JFrame to display the movies already in the chosen database
        JFrame showMovies = new JFrame();
        showMovies.setTitle("Film Finder");
        showMovies.setLayout(null);
        showMovies.add(ResultsView.getInstance());
        ResultsView.getInstance().setBounds(0, 0, 521, 450); //override default bounds for ResultsView
        ResultsView.getInstance().setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); //override ResultsView's default border color so it blends with the background
        showMovies.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Change appearance of JFrame
        showMovies.setSize(521,450);//800 width and 500 height
        ResultsView.getInstance().showMoviesText(MovieList, 1);
        showMovies.setLocationRelativeTo(null);
        showMovies.setVisible(true);//making the frame visible

        //use a JOptionPane to see how many movies the user wants to create
        showMovies.setTitle("DBWizard");
        showMovies.setLayout(null);
        Integer choices[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        Integer numMovies  = (Integer) JOptionPane.showInputDialog(null, "How many movies would you like to add?", "FilmFinder", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);

        if (numMovies == null) { return; }
        System.out.println("Generating new movie(s)");

        for (int i = 0; i < numMovies; i++)
        {
            NewMovies.add(Movie.createMovie());
        }

        System.out.println("Generated movie(s):");

        for (Movie temp : NewMovies)
        {
            System.out.println(temp);
            MovieList.add(temp);
        }

        writeDB(MovieList);

        return;
    }
}
