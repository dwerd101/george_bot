
package ru.mephi.bot.cache;

import ru.mephi.config.BotState;

public interface DataCache {
    void setUsersCurrentBotState(Long userId, BotState botState);

    BotState getUsersCurrentBotState(Long userId);
}

