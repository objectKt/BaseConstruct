# ChannelEvent 用法
可以在代码任何地方发送和接收任何对象:
```html
// 创建一个事件类，如果类型重复, 可以通过加标签来区分事件
data class UserInfoEvent(val name:String, val age:Int)
// 发送
sendEvent(UserInfoEvent("新的姓名", 24), "tag_change_name")
// 接收
receiveEvent<UserInfoEvent>("tag_change_name", "tag_change_username") {
    tv.text = it.name // it 即为UserInfoEvent
}
```
```html
// Channel.receiveEvent支持自动注销, 默认在 onDestroy 销毁,
// 也可以指定其参数支持手动注销(可以不用考虑)
receiveEvent<String>(lifeEvent = Lifecycle.Event.ON_PAUSE) { tv.text = it }
// 使用receiveEventHandler可返回用于手动取消事件的对象
val receiver = receiveEventHandler<String> { tv.text = it }
receiver.cancel() // 手动调用函数注销
```
```html
// 可以在任何线程发送事件, 可以在任何线程接收事件, 回调永远在主线程
// 使用协程函数可以切换回调的线程(也被称为调度器)
receiveEvent<String> {
    tv.text = it // 当前主线程
    launch(Dispatchers.IO) { } // 执行异步任务
}
// 具备返回值回调的切换
receiveEvent<String> {
    // 当前主线程
    tv.text = withContext(Dispatchers.IO){ "新的姓名" } // 切换到IO线程, 返回结果到主线程
}
```