package com.example.addiction2dcumpose.StubData

import com.example.addiction2dcumpose.dataClasses.*

object MangaStubData {
    val mangaData = MangaData(
        malId = 68313,
        images = Images(
            JPGImage(
                imageUrl = "https://cdn.myanimelist.net/images/manga/2/169120.jpg",
                smallImageUrl = "https://cdn.myanimelist.net/images/manga/2/169120t.jpg",
                largeImageUrl = "https://cdn.myanimelist.net/images/manga/2/169120l.jpg"
            )
        ),
        title = "Yuuwaku Choukyou Hitorijime: Gibo no Himitsu Menu",
        status = "Finished",
        chapters = 6,
        volumes = 1,
        type = "Light Novel",
        synopsis = "Looking for information on the light novel Yuuwaku Choukyou Hitorijime: Gibo no Himitsu Menu? Find out more with MyAnimeList, the world's most active online anime and manga community and database.",
        genres = listOf(
            MangaItemReceive(id = 12, name = "Hentai"),
            MangaItemReceive(id = 10, name = "Something")
        ),
        authors = listOf(MangaItemReceive(id = 2, name = "Ivan, Ivanov")),
        serializations = null,
        themes = null
    )


}