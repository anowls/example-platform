package org.zongzi.platform.operation.socket;

public interface TySocketConstant {

    int PORT = 8889;

    /**
     * maxFrameLength：解码的帧的最大长度
     */
    int MAX_FRAME_LENGTH = 1024 * 1024 * 10;
    /**
     * lengthFieldOffset ：长度属性的起始位（偏移位），包中存放有整个大数据包长度的字节，这段字节的其实位置
     */
    int LENGTH_FIELD_OFFSET = 2;
    /**
     * lengthFieldLength：长度属性的长度，即存放整个大数据包长度的字节所占的长度
     */
    int LENGTH_FIELD_LENGTH = 4;
    /**
     * lengthAdjustmen：长度调节值，在总长被定义为包含包头长度时，修正信息长度。
     */
    int LENGTH_ADJUSTMENT = 0;
    /**
     * initialBytesToStrip：跳过的字节数，根据需要我们跳过lengthFieldLength个字节，以便接收端直接接受到不含“长度属性”的内容
     */
    int INITIAL_BYTES_TO_STRIP = 0;

    boolean FAIL_FAST = false;
}