package ru.givemesomecoffee.tetamtsandroid.di

import android.app.Application
import androidx.room.Room
import dagger.*
import ru.givemesomecoffee.data.repository.Repository
import ru.givemesomecoffee.localdata.LocalDatasource
import ru.givemesomecoffee.localdata.db.AppDatabase
import ru.givemesomecoffee.localdata.db.LocalDatasourceImpl
import ru.givemesomecoffee.remotedata.RemoteDatasource
import ru.givemesomecoffee.remotedata.tmdb.MoviesApiService
import ru.givemesomecoffee.remotedata.tmdb.RemoteDatasourceImpl
import ru.givemesomecoffee.tetamtsandroid.data.repository.UserRepository
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
        fun application(application: Application): Builder



        fun build(): AppComponent
    }

    fun userCase(): UserCase
    fun moviesListCase(): MoviesListCases
    fun inject(fragment: MovieDetailsFragment)
    fun inject(fragment: MoviesListFragment)
}

@Module(includes = [NetworkModule::class, DatabaseModule::class, AppBindModule::class])
object AppModule {

    @Reusable
    @Provides
    fun provideMovieListCases(
        repository: Repository
    ): MoviesListCases {
        return MoviesListCases(repository = repository)
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
        repository: UserRepository
    ): UserCase{
        return UserCase(repository = repository)
    }

}

@Module
class NetworkModule {

/*    @Provides
    @Reusable
    fun provideRemoteDatasource(apiService: MoviesApiService): RemoteDatasource {
        return RemoteDatasourceImpl(apiService)
    }*/


    @Provides
    @Singleton
    fun provideMoviesApiService(): MoviesApiService {
        return MoviesApiService.create()
    }
}


@Module
class DatabaseModule{

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            AppDatabase::class.java,
            "DATABASE_NAME"
        )
            .fallbackToDestructiveMigration()
            .build()
    }


}


@Module
interface AppBindModule {

   @Binds
    fun bind_LocalDatasourceImpl_to_LocalDatasource(localDatasourceImpl: LocalDatasourceImpl): LocalDatasource

    @Binds
    fun bind_RemoteDatasourceImpl_to_RemoteDatasource(remoteDatasourceImpl: RemoteDatasourceImpl): RemoteDatasource

}


