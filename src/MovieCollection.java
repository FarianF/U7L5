import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listTopRatedMovies();
        }
        else if (option.equals("h"))
        {
            listTopRevenueMovies();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast() {
        // Create a set to store unique cast members
        HashSet<String> castMembers = new HashSet<>();

        // Iterate through all movies and add unique cast members to the set
        for (Movie movie : movies) {
            String[] castArray = movie.getCast().split("\\|");
            castMembers.addAll(Arrays.asList(castArray));
        }

        // Convert set to sorted list
        ArrayList<String> sortedCastList = new ArrayList<>(castMembers);
        Collections.sort(sortedCastList);

        // Display cast members
        System.out.println("Cast Members:");
        for (int i = 0; i < sortedCastList.size(); i++) {
            System.out.println((i + 1) + ". " + sortedCastList.get(i));
        }

        System.out.println("Enter the number of the cast member to see movies: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice >= 1 && choice <= sortedCastList.size()) {
            String selectedCast = sortedCastList.get(choice - 1);
            ArrayList<Movie> moviesWithSelectedCast = new ArrayList<>();

            // Find movies with the selected cast member
            for (Movie movie : movies) {
                if (movie.getCast().toLowerCase().contains(selectedCast.toLowerCase())) {
                    moviesWithSelectedCast.add(movie);
                }
            }

            // Sort movies alphabetically by title
            sortResults(moviesWithSelectedCast);

            // Display movies with selected cast member
            System.out.println("Movies with " + selectedCast + ":");
            for (int i = 0; i < moviesWithSelectedCast.size(); i++) {
                System.out.println((i + 1) + ". " + moviesWithSelectedCast.get(i).getTitle());
            }

            System.out.println("Enter the number of the movie to learn more about it: ");
            int movieChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (movieChoice >= 1 && movieChoice <= moviesWithSelectedCast.size()) {
                Movie selectedMovie = moviesWithSelectedCast.get(movieChoice - 1);
                displayMovieInfo(selectedMovie);
            } else {
                System.out.println("Invalid movie choice.");
            }
        } else {
            System.out.println("Invalid cast member choice.");
        }
    }


    private void searchKeywords()
    {
        System.out.print("Enter a keyword: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieKeyword = movies.get(i).getKeywords();
            movieKeyword = movieKeyword.toLowerCase();

            if (movieKeyword.indexOf(searchTerm) != -1)
            {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres() {
        ArrayList<String> genresList = new ArrayList<>();

        // Iterate through all movies and add unique genres to the list
        for (Movie movie : movies) {
            String[] genresArray = movie.getGenres().split("\\|");
            for (String genre : genresArray) {
                if (!genresList.contains(genre)) {
                    genresList.add(genre);
                }
            }
        }

        // Sort the genres list alphabetically
        Collections.sort(genresList);

        // Display genres
        System.out.println("Genres:");
        for (int i = 0; i < genresList.size(); i++) {
            System.out.println((i + 1) + ". " + genresList.get(i));
        }

        // Prompt user to select a genre
        System.out.println("Enter the number of the genre to see movies: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice >= 1 && choice <= genresList.size()) {
            String selectedGenre = genresList.get(choice - 1);
            ArrayList<Movie> moviesInSelectedGenre = new ArrayList<>();

            // Find movies in the selected genre
            for (Movie movie : movies) {
                if (movie.getGenres().toLowerCase().contains(selectedGenre.toLowerCase())) {
                    moviesInSelectedGenre.add(movie);
                }
            }

            // Sort movies alphabetically by title
            sortResults(moviesInSelectedGenre);

            // Display movies in the selected genre
            System.out.println("Movies in the genre \"" + selectedGenre + "\":");
            for (int i = 0; i < moviesInSelectedGenre.size(); i++) {
                System.out.println((i + 1) + ". " + moviesInSelectedGenre.get(i).getTitle());
            }

            // Prompt user to select a movie
            System.out.println("Enter the number of the movie to learn more about it: ");
            int movieChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (movieChoice >= 1 && movieChoice <= moviesInSelectedGenre.size()) {
                Movie selectedMovie = moviesInSelectedGenre.get(movieChoice - 1);
                displayMovieInfo(selectedMovie);
            } else {
                System.out.println("Invalid movie choice.");
            }
        } else {
            System.out.println("Invalid genre choice.");
        }
    }


    private void listTopRatedMovies() {
        // Create a copy of movies list to avoid modifying the original list
        ArrayList<Movie> moviesCopy = new ArrayList<>(movies);

        // Sort movies by user rating in descending order
        Collections.sort(moviesCopy, new Comparator<Movie>() {
            @Override
            public int compare(Movie movie1, Movie movie2) {
                return Double.compare(movie2.getUserRating(), movie1.getUserRating());
            }
        });

        // Display top 50 rated movies
        System.out.println("Top 50 Rated Movies:");
        for (int i = 0; i < Math.min(50, moviesCopy.size()); i++) {
            Movie movie = moviesCopy.get(i);
            System.out.println((i + 1) + ". " + movie.getTitle() + ": " + movie.getUserRating());
        }

        // Prompt user to select a movie
        System.out.println("Enter the number of the movie to learn more about it: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice >= 1 && choice <= Math.min(50, moviesCopy.size())) {
            Movie selectedMovie = moviesCopy.get(choice - 1);
            displayMovieInfo(selectedMovie);
        } else {
            System.out.println("Invalid movie choice.");
        }
    }

    private void listTopRevenueMovies() {
        // Create a copy of movies list to avoid modifying the original list
        ArrayList<Movie> moviesCopy = new ArrayList<>(movies);

        // Sort movies by revenue in descending order
        Collections.sort(moviesCopy, new Comparator<Movie>() {
            @Override
            public int compare(Movie movie1, Movie movie2) {
                return Integer.compare(movie2.getRevenue(), movie1.getRevenue());
            }
        });

        // Display top 50 revenue movies
        System.out.println("Top 50 Highest Revenue Movies:");
        for (int i = 0; i < Math.min(50, moviesCopy.size()); i++) {
            Movie movie = moviesCopy.get(i);
            System.out.println((i + 1) + ". " + movie.getTitle() + ": $" + movie.getRevenue());
        }

        // Prompt user to select a movie
        System.out.println("Enter the number of the movie to learn more about it: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice >= 1 && choice <= Math.min(50, moviesCopy.size())) {
            Movie selectedMovie = moviesCopy.get(choice - 1);
            displayMovieInfo(selectedMovie);
        } else {
            System.out.println("Invalid movie choice.");
        }
    }


    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}