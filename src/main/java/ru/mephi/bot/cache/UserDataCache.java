
package ru.mephi.bot.cache;

import org.springframework.stereotype.Component;
import ru.mephi.config.BotState;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDataCache implements DataCache {

    private Map<Long, BotState> usersBotStates = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(Long userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotState getUsersCurrentBotState(Long userId) {
        BotState botState = usersBotStates.get(userId);
        if (botState == null) {
            botState = BotState.NEW_USER;
        }

        return botState;
    }
/*
    @Override
    public UserProfileData getUserProfileData(int userId) {
        return null;
    }

    @Override
    public void saveUserProfileData(int userId, UserProfileData userProfileData) {

    }*/
}

