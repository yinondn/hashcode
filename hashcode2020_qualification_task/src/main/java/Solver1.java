import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solver1 {
    public Solution solve(File fileIn) throws IOException {
        ProblemParser parser = new ProblemParser();
        Problem problem1 = parser.parse(fileIn);
        return solve(problem1);
    }

    private Solution solve(Problem problem) {
        Solution solution = new Solution(problem);
        assign(problem, solution);
        return solution;
    }

    private void assign(Problem problem, Solution solution) {
        List<Library> librariesProblem = problem.libraries;
        List<Library> librariesSolution = solution.libraries;
        librariesSolution.clear();
        int daysRemaining = problem.D;
        Collection<Book> booksScanned = new ArrayList<>();
        Library library;
        Comparator<Library> sorter = new SortLibraries1();
        int size = librariesProblem.size();

        while (size > 0) {
            for (int i = 0; i < size; i++) {
                library = librariesProblem.get(i);
                library.books.removeAll(booksScanned);
                library.init(daysRemaining);
            }
            Collections.sort(librariesProblem, sorter);
            library = librariesProblem.remove(0);
            size--;
            booksScanned.clear();
            library.scan(daysRemaining);
            if (library.booksToScan.size() > 0) {
                booksScanned.addAll(library.booksToScan);
                librariesSolution.add(library);
                daysRemaining -= library.T;
                if (daysRemaining <= 0) break;
            }
        }
    }
}
