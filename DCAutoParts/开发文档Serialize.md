# Serialize 用法

标准序列化工具
基于腾讯 MMKV 实现, 其比SharePreference/SQLite速度快, 可以有效解决ANR

## === 序列化字段 ===
```html
//创建一个变量, 对该变量读写都会自动映射到本地磁盘, 即序列化字段
@SerializeConfig(mmapID = "app_config") // 指定mmapID可以避免字段名重复情况下导致的错误
object AppConfig {
    var name: String by serial()
    var simple: String by serial("默认值", "自定义键名")
}
//之后该字段读写都会自动读取和写入到本地磁盘
AppConfig.name = "名称" // 写入到本地磁盘
AppConfig.name // 读取自本地磁盘
```

### 懒加载
```html
//即只在第一次读取字段的时候才会从本地读取, 后续从内存读取
var model: ModelSerializable by serialLazy() // 懒加载
//零耗时，彻底解决反复读取磁盘造成耗时
```
### 动态键名
```html
//动态键名使用{}函数回调返回值
var userId: String = "0123"
var balance: String by serial("0.0", { "${userId}:balance" })
```

## === 序列化配置 ===

### 单例配置
```html
//为类添加注解@SerializeConfig可指定其所有serialXX()字段的MMKV实例配置
@SerializeConfig(mmapID = "app_config")
object AppConfig {
    var isFirstLaunch: Boolean by serial()
}
```

### 序列化LiveData
```html
//使用serialLiveData创建LiveData
val liveData by serialLiveData("默认值")
//每次写入都回调观察者observe
liveData.observe(this) {
    toast("观察到本地数据: $it")
}
```

## === 页面参数 ===
```html
//快速在Activity/Fragment中传递和接受参数
//Activity
openActivity<TestActivity>(
    "parcelize" to ModelParcelable(),
    "name" to "名称",
    "age" to 34
)
//Fragment
MyFragment().withArguments(
    "parcelize" to ModelParcelable(),
    "name" to "名称",
    "age" to 34
)
//读取参数
    private val parcelize: ModelParcelable by bundle()
    private val name:String by bundle()
    private val age:Int by bundle()
```

## === 常见问题 ===
```html
// 要求修改UserData字段, 然后保存到本地
object UserConfig {
    var userData:UserData by serial()
}

//错误示例：
UserConfig.userData.name = "new name"
UserConfig.userData = UserConfig.userData // 修改无效，修改变量里面的字段并不会更新本地数据, 下次读取的还是旧的值

//如果使用 by serialLazy 以上方式有效(因为他自带临时变量)

//正确示例：使用临时变量
val userData = UserConfig.userData
userData.name = "new name"
UserConfig.userData = userData
```