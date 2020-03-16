# FilmFinder

# The original program was submitted as a group project for CS-321 on 12/5/19.
# My teammates were Bradley Bowen, Erik Failing, and Matt Fletcher.

# The purpose of FilmFinder is to allow the user to view all the movie data stored in a .json file, perform searches on that list of movies, and save movies to a WatchList that is saved whenever the program closes.

# Changes that have been made independently of the group since 12/5/19 include the following
# -The program is now centered in the screen
# -A JOptionPane now appears to allow the user to enter the name of the file they would like to use (FEATURE REPLACED)
# -The search boxes now have KeyListeners so that a search can be done by hitting "Enter"
# -MainView now takes user to a dropdown menu with the files they can choose from, while DBWizard gives the user the option to create a new file instead.
# -FuzzySearch toggle in SearchView has been shifted
# -DBWizard now uses a loop to create a user-specified number of movies
# -PartialSearch now includes modified comparisons for all fields.

# Features to be added:
# -Merge PartialSearch and FuzzySearch classes to have both substring search and Levenshtein distance calculation.
# -Implement GUI for DBWizard
