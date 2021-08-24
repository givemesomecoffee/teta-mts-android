package ru.givemesomecoffee.remotedata

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.givemesomecoffee.remotedata.tmdb.MoviesApiService
import ru.givemesomecoffee.remotedata.tmdb.RemoteDatasourceImpl
import javax.inject.Singleton

@Module(includes = [RemoteDataBindings::class])
class RemoteDataModule {

    @Provides
    @Singleton
    internal fun provideMoviesApiService(): MoviesApiService {
        return MoviesApiService.create()
    }

}

@Module
internal interface RemoteDataBindings {

    @Binds
    @Suppress("UNUSED")
    fun bind_RemoteDatasourceImpl_to_RemoteDatasource(remoteDatasourceImpl: RemoteDatasourceImpl): RemoteDatasource
}