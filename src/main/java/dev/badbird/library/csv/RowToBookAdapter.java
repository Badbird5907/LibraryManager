package dev.badbird.library.csv;

import dev.badbird.library.Main;
import dev.badbird.library.object.Book;
import dev.badbird.library.object.Category;
import dev.badbird.library.object.Genre;
import dev.badbird.library.util.SearchUtils;
import dev.badbird.library.util.Utils;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RowToBookAdapter {
    public static Set<Book> fromRows(Row[] rows) throws ParseException, RuntimeException {
        Set<Book> set = new HashSet<>();
        Set<Long> usedISBNs = new HashSet<>();
        for (Row row : rows) {
            Map<String, String> values = row.getValues();
            long isbn = Long.parseLong(values.get("ISBN"));
            if (!Utils.isValidISBN(isbn)) {
                throw new RuntimeException("Found invalid ISBN: " + isbn + "!");
            }
            if (usedISBNs.contains(isbn)) {
                throw new RuntimeException("Duplicate ISBN: " + isbn);
            }
            usedISBNs.add(isbn);
            // Book Title,Author,Publisher,Published Date,Copyright Date,Price,Genre,Fiction/Non-Fiction
            String title = values.get("Book Title"), author = values.get("Author"),
                    publisher = values.get("Publisher");
            String publishedDateStr = values.get("Published Date");
            Date publishedDate = (publishedDateStr.contains("/") ? Main.DATE_FORMAT : Main.CSV_DATE_FORMAT)
                    .parse(publishedDateStr);
            String copyrightDateStr = values.get("Copyright Date");
            Date copyrightDate = (copyrightDateStr.contains("/") ? Main.DATE_FORMAT : Main.CSV_DATE_FORMAT)
                    .parse(copyrightDateStr);
            double price = Double.parseDouble(values.get("Price").substring(1));
            SearchUtils.EnumSearchResult<Genre> gse = SearchUtils.findEnumByName(values.get("Genre"), Genre.class);
            if (gse == null) throw new RuntimeException("Invalid genre: " + values.get("Genre"));
            Genre genre = gse.value(); // coalesce to nearest genre and category
            SearchUtils.EnumSearchResult<Category> cse = SearchUtils.findEnumByName(values.get("Fiction/Non-Fiction"), Category.class);
            if (cse == null) throw new RuntimeException("Invalid category: " + values.get("Fiction/Non-Fiction"));
            Category category = cse.value();
            Book book = new Book(isbn, title, author, publisher, copyrightDate, publishedDate, price, genre, category);
            set.add(book);
        }
        return set;
    }
}
