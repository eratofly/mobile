import com.example.movies.MovieData
import kotlin.random.Random

object Storage {
    val movie by lazy {
        val result = mutableListOf<MovieData>()

        movieNames.mapIndexedTo(result) { index, name ->
            MovieData(
                name = name,
                annotation = movieDescription[index],
                rating = Random.nextDouble(0.0, 5.0),
                previewURL = moviePreviewUrl[index]
            )
        }

        result.shuffle()

        return@lazy result
    }

    private val movieNames = listOf(
        "Margherita",
        "Pepperoni",
        "Hawaiian",
        "Four Cheese",
        "Meat Lover's",
        "Vegetarian",
    )
    private val movieDescription = listOf(
        "Classic pizza with tomato sauce, mozzarella, and fresh basil.",
        "Topped with spicy pepperoni slices and mozzarella cheese.",
        "A mix of ham, pineapple, and cheese for a sweet and savory flavor.",
        "A rich blend of mozzarella, gorgonzola, parmesan, and goat cheese.",
        " Lover's - Loaded with pepperoni, sausage, bacon, and ham.",
        "A colorful assortment of fresh vegetables and cheese.",
    )

    private val moviePreviewUrl = listOf(
        "https://avatars.mds.yandex.net/i?id=eae1d6e1e18e29179faf8bb209f496d7_l-5235076-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=eae1d6e1e18e29179faf8bb209f496d7_l-5235076-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=eae1d6e1e18e29179faf8bb209f496d7_l-5235076-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=eae1d6e1e18e29179faf8bb209f496d7_l-5235076-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=eae1d6e1e18e29179faf8bb209f496d7_l-5235076-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=eae1d6e1e18e29179faf8bb209f496d7_l-5235076-images-thumbs&n=13",
    )
}