package dc.library.auto.database

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * 数据库插件 ObjectBox 的使用示例：
 * 每个 Entity 必须有一个 id 属性，类型必须是 Long
 * 每当创建或修改一个 Entity data class. 需要 【 Build > Make Project 】
 * 从而自动生成 the classes required to use ObjectBox,
 * 同时 app 目录下自动更新文件 objectbox-models > default.json
 */
@Entity
data class EntitySampleUseObjectBox(
    @Id
    var id: Long = 0,
    var name: String? = null
)