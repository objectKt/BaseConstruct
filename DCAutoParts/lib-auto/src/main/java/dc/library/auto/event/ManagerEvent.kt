package dc.library.auto.event

import com.drake.channel.sendEvent
import javax.annotation.Nonnull

object ManagerEvent {

    fun sendTagEvent(@Nonnull onlyTag: String) {
        sendEvent(EventModel(), onlyTag)
    }

}