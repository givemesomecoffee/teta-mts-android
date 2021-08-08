package ru.givemesomecoffee.tetamtsandroid.data.local.db.assets

import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.Actor
import ru.givemesomecoffee.tetamtsandroid.data.local.db.entity.ActorsToMovies

class Actors {

    val actors = listOf(
        Actor(
            "Jason Statham",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/lldeQ91GwIVff43JBrpdbAAeYWj.jpg",
            0
        ),
        Actor(
            "Holt McCallany",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/8NvOcP35qv5UHWEdpqAvQrKnQQz.jpg",
            1
        ),
        Actor(
            "Rocci Williams",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/e5GWh54fUmbupb5DKsSNU5axEx2.jpg",
            2
        ),
        Actor(
            "Lewis Tan",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/lkW8gh20BuwzHecXqYH1eRVuWpb.jpg",
            3
        ),
        Actor(
            "Jessica McNamee",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/aAfaMEEqD8syHv5bLi5B3sccrM2.jpg",
            4
        ),
        Actor(
            "Josh Lawson",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/Am9vM77uZd9bGODugwmWtOfzx6E.jpg",
            5
        ),
        Actor("Max Carolan", "", 6),
        Actor("Dermot Magennis", "", 7),
        Actor(
            "Tara Flynn",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/17gBs4aux2NcnMvf3DK5UKUFttn.jpg",
            8
        ),
        /*    Actor("", "", 9),
            Actor("", "", 10),
            Actor("", "", 11),*/
        Actor(
            "6ix9ine",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/xAlvyeC9zLbygGMxmmyTHymwuZP.jpg",
            12
        ),
        /*      Actor("", "", 13),
              Actor("", "", 14),*/
        Actor(
            "Benson Jack Anthony",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/aVfEldX1ksEMrx45yNBAf9MAIDZ.jpg",
            15
        ),
        Actor(
            "Frances Berry",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/qCp0psD5qzguABpRxWmMuC04kcl.jpg",
            16
        ),
        Actor(
            "Christian Charisiou",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/8OpoYvO1QqBYRAp1LxxUIiRdQG0.jpg",
            17
        ),
        Actor(
            "Emma Stone",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/2hwXbPW2ffnXUe1Um0WXHG0cTwb.jpg",
            18
        ),
        Actor(
            "Emma Thompson",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/xr8Ki3CIqweWWqS5q0kUYdiK6oQ.jpg",
            19
        ),
        Actor(
            "Joel Fry",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/4nEKEWJpaTHncCTv6zeP98V0qGI.jpg",
            20
        ),
        Actor(
            "Scarlett Johansson",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/6NsMbJXRlDZuDzatN2akFdGuTvx.jpg",
            21
        ),
        Actor(
            "Florence Pugh",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/75PvULemW8BvheSKtPMoBBsvPLh.jpg",
            22
        ),
        Actor(
            "Rachel Weisz",
            "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/msTqKPA33ryVtcVNgOdeaJGYq16.jpg",
            23
        ),


        )

    val actorsToMovies = listOf(
        ActorsToMovies(0, 0),
        ActorsToMovies(0, 1),
        ActorsToMovies(0, 2),
        //
        ActorsToMovies(1, 3),
        ActorsToMovies(1, 4),
        ActorsToMovies(1, 5),
        //
        ActorsToMovies(2, 6),
        ActorsToMovies(2, 7),
        ActorsToMovies(2, 8),
        //
        /*      ActorsToMovies(3, 9),
              ActorsToMovies(3, 10),
              ActorsToMovies(3, 11),*/
        //
        ActorsToMovies(4, 12),
        /*      ActorsToMovies(4, 13),
              ActorsToMovies(4, 14),*/
        //
        ActorsToMovies(5, 15),
        ActorsToMovies(5, 16),
        ActorsToMovies(5, 17),
        //
        ActorsToMovies(6, 18),
        ActorsToMovies(6, 19),
        ActorsToMovies(6, 20),
        //
        ActorsToMovies(7, 21),
        ActorsToMovies(7, 22),
        ActorsToMovies(7, 23),
    )


}