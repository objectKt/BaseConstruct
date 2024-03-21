package dc.library.utils.decoder

import dc.library.utils.XXByteArray
import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufAllocator
import io.netty.buffer.ByteBufUtil

/**
 * @author HuFei
 * [ RS485 解码协议 ]
 * DD 03 00 1B 09EA000002B207D000002D8800000000000022220307020BBC0BC2 FACE 77
 * 解码RS485接收的数据
 * DD
 * 03           // 03 读取基本信息及状态 04 电池单体电压 05 读取保护板硬件版本号
 * 00           // 状态 0 表示正确
 * 1B           // 表示长度值 1b = 27
 * 17 00 00 00 02 D0 03 E8 00 00 20 78 00 00 00 00 00 00 10 48 03 0F 02 0B 76 0B 82 // 内容
 * FB FF        // 校验
 * 77           // 帧尾
 *              // 最少长度：1 + 1 + 1 + 1 + 0 + 2 + 1 = 7 个字节
 */
object SerialPortRs485Decoder
{
    private var byteBuf : ByteBuf = ByteBufAllocator.DEFAULT.buffer()
    private const val leastSizeOfFrame = 7

    fun controlBuf(bytes : ByteArray) : String
    {
        byteBuf.writeBytes(bytes)
        while (byteBuf.readableBytes() > 0)
        {
            val findByte = byteBuf.getByte(byteBuf.readerIndex())
            if (String.format("%02X" , findByte) != "DD")
            {
                if (byteBuf.readerIndex() < byteBuf.writerIndex())
                {
                    byteBuf.skipBytes(1)
                }
            } else
            {
                val startIndex = byteBuf.readerIndex()
                if (byteBuf.readableBytes() < leastSizeOfFrame)
                {
                    break
                } else
                {
                    val bodyLengthByte = byteBuf.getByte(startIndex + 3)
                    val bodyLengthHex = String.format("%02X" , bodyLengthByte)
                    val bodyLengthInt = XXByteArray.hexOf2BytesToInt(bodyLengthHex)
                    var endIndex = startIndex + 4
                    val writingIndex = byteBuf.writerIndex()
                    while (endIndex < writingIndex)
                    {
                        val findTailByte = byteBuf.getByte(endIndex)
                        if (String.format("%02X" , findTailByte) == "77")
                        {
                            break
                        } else
                        {
                            endIndex += 1
                        }
                    }

                    if (endIndex >= writingIndex)
                    {
                        val length = endIndex - startIndex
                        if (length > bodyLengthInt + 7)
                        {
                            byteBuf.slice(startIndex , length).retain()
                            byteBuf.readerIndex(writingIndex - 1)
                            byteBuf.clear()
                        }
                        break
                    }
                    val length = endIndex - startIndex + 1
                    val frameBuf = byteBuf.slice(startIndex , length).retain()
                    val frameBytes : ByteArray = ByteBufUtil.getBytes(frameBuf)
                    byteBuf.readerIndex(byteBuf.writerIndex())
                    byteBuf.clear()
                    val frameHex = XXByteArray.feChangeFromHex(XXByteArray.bytesToSpaceHex(frameBytes))
                    //XXFunction.i("解码完整结果：$frameHex")
                    return frameHex
                }
            }
        }
        return ""
    }
}