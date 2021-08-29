package ru.givemesomecoffee.tetamtsandroid.di

import androidx.work.WorkerFactory
import dagger.*
import ru.givemesomecoffee.data.repository.Repository
import ru.givemesomecoffee.data.repository.UserRepository
import ru.givemesomecoffee.tetamtsandroid.App
import ru.givemesomecoffee.tetamtsandroid.domain.cases.MovieCase
import ru.givemesomecoffee.tetamtsandroid.domain.cases.MoviesListCases
import ru.givemesomecoffee.tetamtsandroid.domain.cases.UserCase
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.MovieDetailsFragment
import ru.givemesomecoffee.tetamtsandroid.presentation.ui.MoviesListFragment
import ru.givemesomecoffee.tetamtsandroid.service.DaggerRefreshDataWorkerFactory
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
    fun inject(app: App)

}

@Module()
object AppModule {

    @Singleton
    @Provides
    fun provideWorkerFactory(
        domain: MoviesListCases
    ): WorkerFactory {
        return DaggerRefreshDataWorkerFactory(domain)
    }

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
