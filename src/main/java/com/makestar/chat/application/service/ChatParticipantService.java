package com.makestar.chat.application.service;

import com.makestar.chat.application.port.in.GetRoomParticipantsUseCase;
import com.makestar.chat.application.port.in.JoinChatRoomUseCase;
import com.makestar.chat.application.port.out.ChatParticipantPersistencePort;
import com.makestar.chat.application.port.out.PopularRoomStatePort;
import com.makestar.chat.domain.ChatParticipant;
import com.makestar.chat.domain.ChatRoomPopularity;
import com.makestar.chat.infrastructure.config.PopularityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatParticipantService implements JoinChatRoomUseCase, GetRoomParticipantsUseCase {

    private final ChatParticipantPersistencePort participantPersistencePort;
    private final PopularRoomStatePort popularRoomStatePort;

    private final PopularityProperties popularityProps;

    @Override
    public ChatParticipant join(Long roomId, String nickname) {

        ChatParticipant p = ChatParticipant.builder()
                .roomId(roomId)
                .nickname(nickname)
                .build();
        ChatParticipant saved = participantPersistencePort.save(p);

        int count = participantPersistencePort.findByRoomId(roomId).size();
        int popularThreshold = popularityProps.getPopularThreshold();
        int superThreshold   = popularityProps.getSuperThreshold();

        if (count >= superThreshold) {
            popularRoomStatePort.setRoomPopularity(roomId, ChatRoomPopularity.SUPER);
        } else if (count >= popularThreshold) {
            popularRoomStatePort.setRoomPopularity(roomId, ChatRoomPopularity.POPULAR);
        } else {
            popularRoomStatePort.setRoomPopularity(roomId, ChatRoomPopularity.NORMAL);
        }
        return saved;
    }

    @Override
    public List<ChatParticipant> getParticipants(Long roomId) {
        return participantPersistencePort.findByRoomId(roomId);
    }
}
