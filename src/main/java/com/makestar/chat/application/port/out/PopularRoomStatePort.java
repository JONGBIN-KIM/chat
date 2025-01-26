package com.makestar.chat.application.port.out;

import com.makestar.chat.domain.ChatRoomPopularity;

public interface PopularRoomStatePort {

    void setRoomPopularity(Long roomId, ChatRoomPopularity level);

    ChatRoomPopularity getRoomPopularity(Long roomId);

    void removeRoomPopularity(Long roomId);

    boolean isPopular(Long roomId);

    boolean isSuperPopular(Long roomId);
}
