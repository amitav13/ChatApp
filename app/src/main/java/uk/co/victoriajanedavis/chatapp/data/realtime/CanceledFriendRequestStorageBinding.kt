package uk.co.victoriajanedavis.chatapp.data.realtime

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import uk.co.victoriajanedavis.chatapp.data.mappers.CanceledFriendRequestWsFriendshipDbMapper
import uk.co.victoriajanedavis.chatapp.data.model.db.FriendshipDbModel
import uk.co.victoriajanedavis.chatapp.data.realtime.fcm.FirebaseMessagingStreams
import uk.co.victoriajanedavis.chatapp.data.realtime.websocket.WebSocketStreams
import uk.co.victoriajanedavis.chatapp.data.repositories.store.ReceivedFriendRequestStore
import uk.co.victoriajanedavis.chatapp.domain.ReactiveStore
import java.util.UUID
import javax.inject.Inject
import javax.inject.Named

class CanceledFriendRequestStorageBinding @Inject constructor(
    private val webSocketStreams: WebSocketStreams,
    private val firebaseMessagingStreams: FirebaseMessagingStreams,
    @Named(ReceivedFriendRequestStore) private val friendStore: ReactiveStore<UUID, FriendshipDbModel>
) {
    private val canceledFriendRequestMapper = CanceledFriendRequestWsFriendshipDbMapper()

    fun subscribeToCanceledFriendRequestsStream(): Disposable {
        return Flowable.merge(
            webSocketStreams.canceledFriendRequestStream(),
            firebaseMessagingStreams.canceledFriendRequestStream())
            .doOnNext { Log.d("CanceledFriendReq", "WebSocket Emitted") }
            .publish().autoConnect()
            .map(canceledFriendRequestMapper::mapFrom)
            .flatMapCompletable(friendStore::storeSingular)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}