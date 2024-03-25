package dc.library.auto.event

data class EventModel(
    var tag: String = "tag"
)

object EventTag {
    const val BLUETOOTH_PERMISSION_HANDLE = "bluetooth"
}