import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
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

    public static void inputFileName()
    {
        DBNAME += JOptionPane.showInputDialog(null,"What would you like to name your file?\n(Please include '.json' at the end of the name)", "Create a new file", JOptionPane.QUESTION_MESSAGE);

        return;
    }

    public static void chooseFileName()
    {
        String files[];
        File data = new File("data");

        files = data.list();

        DBNAME += JOptionPane.showInputDialog(null, "Please select a file to use.","Select a file", JOptionPane.PLAIN_MESSAGE, null, files, files[0]);

        return;

    }

    public static void setFileName()
    {
        int choice;

        String[] options = {"Select a file.", "Create a new file."};

        choice = JOptionPane.showOptionDialog(null, "Would you like to edit a pre-existing file, or create a new one?", "File Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == 0) chooseFileName();
        else if (choice == 1) inputFileName();

        return;
    }

    private DBWizard()
    {

    };

    public static DBWizard getInstance()
    {
        if (instance == null)
        {
            instance = new DBWizard();
        }
        return instance;
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

        setFileName();

        if (DBNAME.equals("data/null") || DBNAME.equals("data/")) return;

        Scanner reader = new Scanner(System.in);

        System.out.println("##########\nReading DB");
        ArrayList<Movie> MovieList = readDB();
        ArrayList<Movie> NewMovies = new ArrayList();

        System.out.println("\nPRINTING MOVIE LIST");
        for (Movie m: MovieList)
        {
            System.out.println(m);
            System.out.println(); 
        }
        System.out.println("END MOVIE LIST");

        System.out.println("How many movies would you like to add?");
        int numMovies  = reader.nextInt();

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
