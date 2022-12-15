package com.example.bot1.controller;

import com.example.bot1.entity.Journal;
import com.example.bot1.keyboard.InlineKeyboard;
import com.example.bot1.service.JournalService;
import com.example.bot1.service.TimeService;
import com.example.bot1.util.MyCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.example.bot1.data.CommandNames.*;

@Component
public class CallbackQueryController {
    private final InlineKeyboard inlineKeyboard;
    private final TimeService timeService;
    private final JournalService journalService;

    public CallbackQueryController(@Autowired InlineKeyboard inlineKeyboard,
                                   @Autowired TimeService timeService,
                                   @Autowired JournalService journalService) {
        this.inlineKeyboard = inlineKeyboard;
        this.timeService = timeService;
        this.journalService = journalService;
    }

    public EditMessageText execute(Update update) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
        editMessage.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        String param = update.getCallbackQuery().getData().split(SEPARATOR)[1];
        int year, month, day;
        String time;

        switch (update.getCallbackQuery().getData().split(SEPARATOR)[0]) {
            case (GET_CALENDAR):
                year = Integer.parseInt(param.split(":")[0]);
                month = Integer.parseInt(param.split(":")[1]);
                editMessage.setText("Пожалуйста выберите дату записи на\n" +
                        MyCalendar.getInstance().getMonthName(month) + " " + year + " года:");
                editMessage.setReplyMarkup(inlineKeyboard.getCalendar(year, month));
                break;

            case (GET_DAY):
                year = Integer.parseInt(param.split(":")[0]);
                month = Integer.parseInt(param.split(":")[1]);
                day = Integer.parseInt(param.split(":")[2]);
                if (MyCalendar.getInstance().isPastDate(year, month, day)) {
                    editMessage.setText("Записи нет на выбранную дату:\n" +
                            day + " " + MyCalendar.getInstance().getMonthName(month) + " " + year + " год");
                    editMessage.setReplyMarkup(inlineKeyboard.backCalendar(year, month));
                } else {
                    if (true /* if not available dates */) {
                        editMessage.setText("Пожалуйста выберете время визита:");
                        List<String> availableTimes = timeService.getAllTimes();
                        editMessage.setReplyMarkup(inlineKeyboard.getAvailableTimes(year, month, day, availableTimes));
                        //todo if not available dates


                    } else {
                        //todo if available dates


                    }
                }
                break;

            case (SET_BOOK):
                year = Integer.parseInt(param.split(":")[0]);
                month = Integer.parseInt(param.split(":")[1]);
                day = Integer.parseInt(param.split(":")[2]);
                time = param.split(":")[3] + ":" + param.split(":")[4];

                journalService.saveJournal(new Journal(year + ":" + month + ":" + day, time,
                        update.getCallbackQuery().getMessage().getFrom().getId().toString()));

                editMessage.setText("Pressed button 3");
                break;

            case (APPROVE_BOOK):
                editMessage.setText("Pressed button 4");
                year = Integer.parseInt(param.split(":")[0]);
                month = Integer.parseInt(param.split(":")[1]);
                day = Integer.parseInt(param.split(":")[2]);
                time = param.split(":")[3] + ":" + param.split(":")[4];
                journalService.saveJournal(new Journal(year + ":" + month + ":" + day, time,
                        update.getCallbackQuery().getMessage().getFrom().getId().toString()));
                break;

            case (GET_MY_BOOKING):
                editMessage.setText("Pressed button 4");
                break;
        }
        return editMessage;
    }
}
