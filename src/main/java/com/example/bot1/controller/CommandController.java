package com.example.bot1.controller;

import com.example.bot1.entity.User;
import com.example.bot1.keyboard.InlineKeyboard;
import com.example.bot1.keyboard.MainKeyboard;
import com.example.bot1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

@Component
public class CommandController {
    private final MainKeyboard mainKeyboard;
    private final InlineKeyboard inlineKeyboard;
    private final UserService userService;

    public CommandController(@Autowired MainKeyboard mainKeyboard,
                             @Autowired UserService userService,
                             @Autowired InlineKeyboard inlineKeyboard) {
        this.mainKeyboard = mainKeyboard;
        this.inlineKeyboard = inlineKeyboard;
        this.userService = userService;
    }

    public SendMessage execute(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        switch (update.getMessage().getText().toLowerCase()) {
            case ("/start"):
                userService.saveUser(new User(update.getMessage().getFrom().getId(),
                        update.getMessage().getFrom().getFirstName(),
                        update.getMessage().getFrom().getLastName(),
                        update.getMessage().getFrom().getUserName(), ""));
                sendMessage.setText(update.getMessage().getFrom().getFirstName() +
                        ", приветствую в нашем чудесном барбершопе\n" +
                                    "Только у нас самые опытные мастера и мастерицы\n" +
                                    "----------------------------------------------\n" +
                                    "Подстрижем так, что мама родная не узнает! \uD83D\uDE09 \n" +
                                    "Работаем для вас без обедов, выходных и праздников\n" +
                                    "Время работы с 9:00 до 21:00");
                sendMessage.setReplyMarkup(mainKeyboard.getMainCustomerKeyboard());
                break;
            case ("/stop"):
                userService.deleteById(update.getMessage().getFrom().getId());
                sendMessage.setText("Досвидания, друг!");
                sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
                break;
            default:
                sendMessage.setText("Введенная вами комманда не поддерживается!\n" +
                                    "Пожалуйста, пользуйтесь кнопками ниже \uD83D\uDC47");
                break;
        }
        return sendMessage;
    }
}
