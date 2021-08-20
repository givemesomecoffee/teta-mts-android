/*
package ru.givemesomecoffee.data

import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.givemesomecoffee.data.repository.Repository
import ru.givemesomecoffee.localdata.LocalDatasource
import ru.givemesomecoffee.localdata.db.LocalDatasourceImpl
import ru.givemesomecoffee.remotedata.RemoteDatasource
import ru.givemesomecoffee.remotedata.tmdb.RemoteDatasourceImpl

@Component(modules = [DataModule::class, AppBindModule::class])
interface DataComponent {


}

@Module
class DataModule{

    @Provides
    fun provideRepository(localDatasource: LocalDatasource, remoteDatasource: RemoteDatasource): Repository{
       return Repository(localDatasource = localDatasource, remoteDatasource = remoteDatasource)
    }

}

@Module
interface AppBindModule {

    @Binds
    fun bind_LocalDatasourceImpl_to_LocalDatasource(localDatasourceImpl: LocalDatasourceImpl): LocalDatasource

    @Binds
    fun bind_RemoteDatasourceImpl_to_RemoteDatasource(remoteDatasourceImpl: RemoteDatasourceImpl): RemoteDatasource

}

*/
