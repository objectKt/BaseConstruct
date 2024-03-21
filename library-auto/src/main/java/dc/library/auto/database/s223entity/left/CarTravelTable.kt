package dc.library.auto.database.s223entity.left

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/* 行程
 * 平均油耗 = 总消耗的燃料量 / 总行驶距离
 * 平均时速 = 总行驶距离 / 总行驶时间
 */
@Entity
data class CarTravelTable(
    @Id
    var id: Long = 0,
    var userMile: Float = 0f, // 用户行驶的里程，可以重置为0
    var totalMile: Float = 0f,// 总行驶的里程
    var subtotalMile: Float = 0f,// 小计里程，达到一百公里，计算油耗，把值累加到totalMile和userMile，然后重置为0
    var subtotalConsumeOil: Float = 0f,// 小计消耗的汽油，计算一次油耗，把值累加到userConsumeOil和totalConsumeOil，然后重置
    var userConsumeOil: Float = 0f,// 用户消耗的汽油量
    var totalConsumeOil: Float = 0f,// 总的消耗的汽油
    var subtotalAverageSpeed: Float = 0f,// 小计平均时速, 每一百公里更新一次值
    var userAverageSpeed: Float = 0f,
    var totalAverageSpeed: Float = 0f,
    var totalRunTime: Float = 0f,
    var userRunTime: Float = 0f,
    var subtotalRuntime: Float = 0f, // 小计，汽车运行时间，达到一百公里后，重置为0
    var startComputeTime: Float = 0f,
    var currentQtrip: Float = 0f, // 当前油耗
    var userAverageQtrip: Float = 0f, // 用户平均油耗
    var totalAverageQtrip: Float = 0f, // 总的平均油耗
    var data1: String = "",
    var data2: String = "",
    var data3: String = ""
) {
    companion object {
        fun updateData(): CarTravelTable {
            return CarTravelTable()
        }
    }
}
