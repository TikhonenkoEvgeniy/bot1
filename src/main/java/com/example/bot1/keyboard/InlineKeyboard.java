package com.example.bot1.keyboard;

import com.example.bot1.util.MyCalendar;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.bot1.data.CommandNames.*;

@Component
public class InlineKeyboard {

    public InlineKeyboardMarkup getStartKeyboard() {

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton("Button 1");
        inlineKeyboardButton1.setCallbackData("button1");

        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton("Button 2");
        inlineKeyboardButton2.setCallbackData("button2");

        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton("Button 3");
        inlineKeyboardButton3.setCallbackData("button3");

        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton("Button 4");
        inlineKeyboardButton4.setCallbackData("button4");

        return new InlineKeyboardMarkup(List.of(
                List.of(inlineKeyboardButton1, inlineKeyboardButton2),
                List.of(inlineKeyboardButton3, inlineKeyboardButton4)));
    }

    public InlineKeyboardMarkup getButton1Keyboard() {

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton("Button 5");
        inlineKeyboardButton1.setCallbackData("button5");

        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton("Button 6");
        inlineKeyboardButton3.setCallbackData("button6");

        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton("Button 7");
        inlineKeyboardButton4.setCallbackData("button7");

        return new InlineKeyboardMarkup(List.of(
                List.of(inlineKeyboardButton1),
                List.of(inlineKeyboardButton3, inlineKeyboardButton4)));
    }

    public InlineKeyboardMarkup getCalendar(int year, int month) {
        MyCalendar myCalendar = MyCalendar.getInstance();
        List<List<InlineKeyboardButton>> allButtons = new ArrayList<>();
        boolean isLess = true;
        boolean isMore = false;

        // Fill days
        List<int[]> numbers = myCalendar.getCalendarOfMonth(year, month);
        for (int[] number : numbers) {
            List<InlineKeyboardButton> numberButtons = new ArrayList<>();
            for (int j : number) {
                InlineKeyboardButton tempButton = new InlineKeyboardButton();
                tempButton.setText(String.valueOf(j));
                if (j == 1 && !isLess) { isMore = true; }
                if (j == 1) { isLess = false; }
                if (isLess) {
                    int[] param = myCalendar.getPrevious(year, month);
                    tempButton.setCallbackData(GET_DAY + SEPARATOR + param[0] + ":" + param[1] + ":" + j);
                } else if (isMore) {
                    int[] param = myCalendar.getNext(year, month);
                    tempButton.setCallbackData(GET_DAY + SEPARATOR + param[0] + ":" + param[1] + ":" + j);
                } else {
                    tempButton.setCallbackData(GET_DAY + SEPARATOR + year + ":" + month + ":" + j);
                }
                numberButtons.add(tempButton);
            }
            allButtons.add(numberButtons);
        }

        // Fill navigation buttons
        List<InlineKeyboardButton> navigationButtons = new ArrayList<>();
        if (month > myCalendar.getCurrentMonth() || year > myCalendar.getCurrentYear()) {
            int[] previous = myCalendar.getPrevious(year, month);
            InlineKeyboardButton buttonPrevious = new InlineKeyboardButton();
            buttonPrevious.setText("⬅️ Назад");
            buttonPrevious.setCallbackData(GET_CALENDAR + SEPARATOR + previous[0] + ":" + previous[1]);
            navigationButtons.add(buttonPrevious);
        }
        int[] next = myCalendar.getNext(year, month);
        InlineKeyboardButton buttonNext = new InlineKeyboardButton();
        buttonNext.setText("Далее ➡️");
        buttonNext.setCallbackData(GET_CALENDAR + SEPARATOR + next[0] + ":" + next[1]);
        navigationButtons.add(buttonNext);
        allButtons.add(navigationButtons);
        return new InlineKeyboardMarkup(allButtons);
    }

    public InlineKeyboardMarkup backCalendar(int year, int month) {
        List<List<InlineKeyboardButton>> allButtons = new ArrayList<>();
        List<InlineKeyboardButton> navigationButtons = new ArrayList<>();
        InlineKeyboardButton buttonBack = new InlineKeyboardButton();
        buttonBack.setText("◀️ Возврат в календарь");
        if (MyCalendar.getInstance().isPastDate(year, month, 1)) {
            buttonBack.setCallbackData(GET_CALENDAR + SEPARATOR +
                    MyCalendar.getInstance().getCurrentYear() + ":" +
                    MyCalendar.getInstance().getCurrentMonth());
        } else {
            buttonBack.setCallbackData(GET_CALENDAR + SEPARATOR + year + ":" + month);
        }
        navigationButtons.add(buttonBack);
        allButtons.add(navigationButtons);
        return new InlineKeyboardMarkup(allButtons);
    }

    public InlineKeyboardMarkup selectDayKeyboard(int year, int month, int day) {

        return null;
    }

    public InlineKeyboardMarkup getAvailableTimes(int year, int month, int day, List<String> availableTimes) {
        List<List<InlineKeyboardButton>> allButtons = new ArrayList<>();
        for (String object : availableTimes) {
            InlineKeyboardButton button = new InlineKeyboardButton(object);
            button.setCallbackData(SET_BOOK + SEPARATOR + year + ":" + month + ":" + day + ":" + object);
            allButtons.add(List.of(button));
        }
        InlineKeyboardButton button = new InlineKeyboardButton("◀️ Возврат в календарь");
        button.setCallbackData(GET_CALENDAR + SEPARATOR + year + ":" + month);
        allButtons.add(List.of(button));
        return new InlineKeyboardMarkup(allButtons);
    }
}
