package com.example.bot1.controller;

import com.example.bot1.data.CommandNames;
import com.example.bot1.keyboard.InlineKeyboard;
import com.example.bot1.util.MyCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MessageController {
    private final InlineKeyboard inlineKeyboard;

    public MessageController(@Autowired InlineKeyboard inlineKeyboard) {
        this.inlineKeyboard = inlineKeyboard;
    }

    public SendMessage execute(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());

        if (update.getMessage().hasContact()) {
            // todo
        }

        switch (update.getMessage().getText()) {
            case (CommandNames.CHECK_IN):
                int year = MyCalendar.getInstance().getCurrentYear();
                int month = MyCalendar.getInstance().getCurrentMonth();
                sendMessage.setText("Пожалуйста выберите дату записи на\n" +
                        MyCalendar.getInstance().getMonthName(month) + " " + year + " года:");
                sendMessage.setReplyMarkup(inlineKeyboard.getCalendar(year, month));
                break;

            case (CommandNames.LOOK_BOOKINGS):
                sendMessage.setText("Ваши текущие записи:\n" +
                        "У вас нет активных записей \uD83E\uDD72");
                break;

            default:
                sendMessage.setText("Введенная вами команда не поддерживается. " +
                        "Пожалуйста, пользуйтесь кнопками ниже \uD83D\uDC47");
        }
        return sendMessage;
    }
}
