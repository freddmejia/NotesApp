package botix.gamer.notesapp.di

import android.content.Context
import android.content.SharedPreferences
import botix.gamer.notesapp.R
import botix.gamer.notesapp.data.repository.NoteRepositoryImplementation
import botix.gamer.notesapp.data.reposuserLoginory.UserRepositoryImplementation
import botix.gamer.notesapp.domain.note.NoteCreateUseCase
import botix.gamer.notesapp.domain.note.NoteUpdateUseCase
import botix.gamer.notesapp.domain.user.LoginUseCase
import botix.gamer.notesapp.domain.user.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun providerSharedPreferences(@ApplicationContext appContext: Context) : SharedPreferences =
        appContext.applicationContext.getSharedPreferences(appContext.getString(R.string.shared_preferences), Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideAdminSharedPreference(@ApplicationContext appContext: Context):
            AdminSharedPreference = AdminSharedPreference(appContext)

    @Singleton
    @Provides
    fun provideApiGraph() = AdminApolloClient()

    //REGISTER AND LOGIN
    @Singleton
    @Provides
    fun provideUserRepositoryImplementation(adminApolloClient: AdminApolloClient, adminSharedPreference: AdminSharedPreference) = UserRepositoryImplementation(adminApolloClient, adminSharedPreference)

    @Singleton
    @Provides
    fun provideLoginUseCase(userRepositoryImplementation: UserRepositoryImplementation) = LoginUseCase(userRepositoryImplementation)


    @Singleton
    @Provides
    fun provideRegisterUseCase(userRepositoryImplementation: UserRepositoryImplementation) = RegisterUseCase(userRepositoryImplementation)
    //


    //NOTE, CRUD
    @Singleton
    @Provides
    fun provideNoteRepositoryImplementation(adminApolloClient: AdminApolloClient) = NoteRepositoryImplementation(adminApolloClient)


    @Singleton
    @Provides
    fun provideNoteCreateUseCase(noteRepositoryImplementation: NoteRepositoryImplementation) = NoteCreateUseCase(noteRepositoryImplementation)


    @Singleton
    @Provides
    fun provideNoteUpdateUseCase(noteRepositoryImplementation: NoteRepositoryImplementation) = NoteUpdateUseCase(noteRepositoryImplementation)
    //
}