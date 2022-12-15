package com.example.bot1.controller;

import com.example.bot1.config.BotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class BotController extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final MessageController messageController;
    private final CallbackQueryController callbackQueryController;
    private final CommandController commandController;

    public BotController(@Autowired BotConfig botConfig,
                         @Autowired MessageController messageController,
                         @Autowired CallbackQueryController callbackQueryController,
                         @Autowired CommandController commandController) {
        this.botConfig = botConfig;
        this.messageController = messageController;
        this.callbackQueryController = callbackQueryController;
        this.commandController = commandController;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                if (update.getMessage().isCommand()) {
                    execute(commandController.execute(update));
                } else {
                    execute(messageController.execute(update));
                }
            }
            if (update.hasCallbackQuery()) {
                execute(callbackQueryController.execute(update));
            }
        } catch (TelegramApiException exception) {
            exception.printStackTrace();
        }
    }
}
