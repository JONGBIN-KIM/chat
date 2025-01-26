package com.makestar.chat.infrastructure.adapter;

import com.makestar.chat.application.port.out.PopularRoomStatePort;
import com.makestar.chat.domain.ChatRoomPopularity;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryPopularRoomCacheAdapter implements PopularRoomStatePort {

    private final Map<Long, ChatRoomPopularity> roomPopularityMap = new ConcurrentHashMap<>();

    @Override
    public void setRoomPopularity(Long roomId, ChatRoomPopularity level) {
        roomPopularityMap.put(roomId, level);
    }

    @Override
    public ChatRoomPopularity getRoomPopularity(Long roomId) {
        return roomPopularityMap.getOrDefault(roomId, ChatRoomPopularity.NORMAL);
    }

    @Override
    public void removeRoomPopularity(Long roomId) {
        roomPopularityMap.remove(roomId);
    }

    @Override
    public boolean isPopular(Long roomId) {
        ChatRoomPopularity state = getRoomPopularity(roomId);
        return state == ChatRoomPopularity.POPULAR || state == ChatRoomPopularity.SUPER;
    }

    @Override
    public boolean isSuperPopular(Long roomId) {
        return getRoomPopularity(roomId) == ChatRoomPopularity.SUPER;
    }
}
