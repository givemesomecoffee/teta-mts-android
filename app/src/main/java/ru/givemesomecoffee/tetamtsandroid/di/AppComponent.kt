package ru.givemesomecoffee.tetamtsandroid.di

import dagger.*
import ru.givemesomecoffee.data.repository.Repository
import ru.givemesomecoffee.data.repository.UserRepository
import ru.givemesomecoffee.tetamtsandroid.domain.cases.MovieCase
import ru.givemesomecoffee.tetamtsandroid.domain.cases.MoviesListCases
import ru.givemesomecoffee.tetamtsandroid.domain.cases.UserCase
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.MovieDetailsFragment
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.MoviesListFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun repository(repository: Repository): Builder

        @BindsInstance
        fun userRepository(repository: UserRepository): Builder

        fun build(): AppComponent
    }

    fun userCase(): UserCase
    fun moviesListCase(): MoviesListCases
    fun inject(fragment: MovieDetailsFragment)
    fun inject(fragment: MoviesListFragment)
}

@Module()
object AppModule {

    @Reusable
    @Provides
    fun provideMovieListCases(
        repository: Repository
    ): MoviesListCases {
        return MoviesListCases(repository =  repository )
    }

    @Reusable
    @Provides
    fun provideMovieCase(
        repository: Repository
    ): MovieCase {
        return MovieCase(repository = repository)
    }

    @Reusable
    @Provides
    fun provideUserCase(
        userRepository: UserRepository
    ): UserCase{
        return UserCase(repository = userRepository)
    }

}
