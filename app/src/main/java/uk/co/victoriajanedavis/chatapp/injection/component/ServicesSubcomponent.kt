package uk.co.victoriajanedavis.chatapp.injection.component

import dagger.Subcomponent
import dagger.android.AndroidInjector
import uk.co.victoriajanedavis.chatapp.presentation.fcm.MyFirebaseService
import uk.co.victoriajanedavis.chatapp.presentation.notifications.message.ReplyActionService
import uk.co.victoriajanedavis.chatapp.presentation.ui.main.SyncService


@Subcomponent
interface MyFirebaseServiceSubcomponent : AndroidInjector<MyFirebaseService> {
    @Subcomponent.Builder abstract class Builder : AndroidInjector.Builder<MyFirebaseService>()
}


@Subcomponent
interface SyncServiceSubcomponent : AndroidInjector<SyncService> {
    @Subcomponent.Builder abstract class Builder : AndroidInjector.Builder<SyncService>()
}


@Subcomponent
interface ReplyActionServiceSubcomponent : AndroidInjector<ReplyActionService> {
    @Subcomponent.Builder abstract class Builder : AndroidInjector.Builder<ReplyActionService>()
}