package dc.library.decode

import android.util.Log
import dc.library.utils.XXByteArray
import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufAllocator
import io.netty.buffer.ByteBufUtil

/**
 * [ ttys1 解码协议 ]
 * AA BB 06 AA 00 00 08 00 00 01 E5 28 28 BF 42 BB 41 6E CF CC DD
 * AA BB 22 17 10 98 70
 * 74 02 4C CD D0
 * @author hf
 */
object SerialPortTTYS1Decoder {
    private var byteBuf: ByteBuf = ByteBufAllocator.DEFAULT.buffer()
    private const val FRAME_SIZE_AT_LEAST: Int = 3

    fun decodeBytes(bytes: ByteArray): String {
        if (bytes.isEmpty()) {
            return ""
        }
        byteBuf.writeBytes(bytes)
        while (byteBuf.readableBytes() > 0) {
            val findBytesStart = byteBuf.getUnsignedShort(byteBuf.readerIndex())
            if (String.format("%02X", findBytesStart) != "AABB") {
                if (byteBuf.readerIndex() < byteBuf.writerIndex()) {
                    byteBuf.skipBytes(1)
                }
            } else {
                val startIndex = byteBuf.readerIndex()
                if (byteBuf.readableBytes() < FRAME_SIZE_AT_LEAST) {
                    break
                } else {
                    var endIndex = startIndex + 2
                    val writingIndex = byteBuf.writerIndex()
                    while (endIndex < writingIndex) {
                        val findTailByte = byteBuf.getUnsignedShort(endIndex)
                        if (String.format("%02X", findTailByte) == "CDD0") {
                            break
                        } else {
                            endIndex += 1
                        }
                    }

                    if (endIndex >= writingIndex) {
                        break
                    }
                    val length = endIndex - startIndex + 2
                    val frameBuf = byteBuf.slice(startIndex, length).retain()
                    val frameBytes: ByteArray = ByteBufUtil.getBytes(frameBuf)
                    byteBuf.readerIndex(byteBuf.writerIndex())
                    byteBuf.clear()
                    val frameHex = XXByteArray.toHeX(frameBytes)
                    Log.i("dc-auto-parts", "ttys1 解码完整结果：$frameHex")
                    return frameHex
                }
            }
        }
        return ""
    }
}