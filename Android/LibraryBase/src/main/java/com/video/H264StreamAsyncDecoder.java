package com.video;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.view.Surface;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;

public class H264StreamAsyncDecoder {
    private MediaFormat mediaFormat;
    private MediaCodec mediaCodec;
    private static final int MAX_QUEUE_SIZE = 31;
    private final ArrayBlockingQueue<byte[]> queue = new ArrayBlockingQueue<>(MAX_QUEUE_SIZE);
    private Surface surface;
    private final int frameRate = 30;// 视频帧率最低设置为24帧，这和人眼的反应速度有关，当帧率低于这个数值时，人眼就会感觉到明显的卡顿。通常视频的帧率设置为30FPS

    public void createDecoder() {
        try {
            pollAllData();
            mediaCodec = MediaCodec.createDecoderByType(MediaFormat.MIMETYPE_VIDEO_AVC);
            int width = 720, height = 1080;
            mediaFormat = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, width, height);
            mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, frameRate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putData(byte[] data) {
        if (queue.size() >= MAX_QUEUE_SIZE - 1) {
            queue.poll();
        }
        queue.add(data.clone());
    }

    public void pollAllData() {
        queue.clear();
    }

    public void setSurface(Surface surface) {
        this.surface = surface;
    }

    public void decode() {
        mediaCodec.setCallback(new MediaCodec.Callback() {
            @Override
            public void onInputBufferAvailable(@NonNull MediaCodec codec, int index) {
                ByteBuffer decoderInputBuffer = mediaCodec.getInputBuffer(index);
                assert decoderInputBuffer != null;
                decoderInputBuffer.clear();
                byte[] input = queue.poll();
                if (input != null) {
                    decoderInputBuffer.limit(input.length);
                    decoderInputBuffer.put(input, 0, input.length);
                    mediaCodec.queueInputBuffer(index, 0, input.length, computePresentationTime(index), 0);
                } else {
                    mediaCodec.queueInputBuffer(index, 0, 0, computePresentationTime(index), 0);
                }
            }

            @Override
            public void onOutputBufferAvailable(@NonNull MediaCodec codec, int index, @NonNull MediaCodec.BufferInfo info) {
                mediaCodec.releaseOutputBuffer(index, true);
            }

            @Override
            public void onError(@NonNull MediaCodec codec, @NonNull MediaCodec.CodecException e) {
                mediaCodec.reset();
            }

            @Override
            public void onOutputFormatChanged(@NonNull MediaCodec codec, @NonNull MediaFormat format) {
            }
        });
        mediaCodec.configure(mediaFormat, surface, null, 0);
        mediaCodec.start();
    }

    private long computePresentationTime(long frameIndex) {
        return 132 + frameIndex * 1000000 / frameRate;
    }

    public void stop() {
        mediaCodec.stop();
        mediaCodec.release();
        mediaFormat = null;
        mediaCodec = null;
    }
}