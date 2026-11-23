import java.util.ArrayList;
import java.util.Scanner;

public class MovieTracker {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<Movie> movies = new ArrayList<>();

    public static void main(String[] args) {
        boolean running = true;
        System.out.println("=== MovieTracker ===");

        while (running) {
            System.out.println("\n1. Add movie\n2. View movies\n3. Rate a movie\n4. Remove movie\n5. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": addMovie(); break;
                case "2": viewMovies(); break;
                case "3": rateMovie(); break;
                case "4": removeMovie(); break;
                case "5": running = false; System.out.println("Thanks for using MovieTracker!"); break;
                default: System.out.println("Invalid option. Please choose 1-5.");
            }
        }
        scanner.close();
    }


    private static void addMovie() {
        System.out.print("Enter movie title: ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }
        System.out.print("Enter release year: ");
        String yearInput = scanner.nextLine().trim();
        int year;
        try {
            year = Integer.parseInt(yearInput);
            if (year < 1888 || year > 2100) {
                System.out.println("Please enter a reasonable year (1888-2100).");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid year. Movie not added.");
            return;
        }
        movies.add(new Movie(title, year));
        System.out.println("Movie added successfully!");
    }

    private static void viewMovies() {
        if (movies.isEmpty()) {
            System.out.println("No movies tracked yet.");
            return;
        }
        System.out.println("\n--- Your Movie List ---");
        for (int i = 0; i < movies.size(); i++) {
            System.out.println((i + 1) + ". " + movies.get(i));
        }
    }

    private static void rateMovie() {
        if (movies.isEmpty()) {
            System.out.println("Add a movie before rating it.");
            return;
        }
        viewMovies();
        System.out.print("Enter movie number to rate: ");
        int index = readMovieIndex();
        if (index == -1) return;

        System.out.print("Enter rating (1-5 stars): ");
        try {
            int rating = Integer.parseInt(scanner.nextLine().trim());
            if (rating < 1 || rating > 5) {
                System.out.println("Rating must be between 1 and 5.");
                return;
            }
            movies.get(index).setRating(rating);
            System.out.println("Rating saved!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid rating.");
        }
    }

    private static void removeMovie() {
        if (movies.isEmpty()) {
            System.out.println("Your movie list is empty.");
            return;
        }
        viewMovies();
        System.out.print("Enter movie number to remove: ");
        int index = readMovieIndex();
        if (index == -1) return;
        Movie removed = movies.remove(index);
        System.out.println("Removed: " + removed.getTitle());
    }

    private static int readMovieIndex() {
        try {
            int number = Integer.parseInt(scanner.nextLine().trim());
            if (number < 1 || number > movies.size()) {
                System.out.println("Invalid movie number.");
                return -1;
            }
            return number - 1;
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return -1;
        }
    }
}


class Movie {
    private final String title;
    private final int year;
    private int rating; // 0 means not rated yet


    public Movie(String title, int year) {
        this.title = title;
        this.year = year;
        this.rating = 0;
    }

    public String getTitle() { return title; }
    public void setRating(int rating) { this.rating = rating; }

    @Override
    public String toString() {
        String ratingText = rating == 0 ? "Not rated" : rating + "/5 stars";
        return title + " (" + year + ") - " + ratingText;
    }
}
