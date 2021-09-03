
package ru.mephi.bot.cache;

import ru.mephi.config.bot.BotState;

public interface DataCache {
    void setUsersCurrentBotState(int userId, BotState botState);

    BotState getUsersCurrentBotState(int userId);
}

