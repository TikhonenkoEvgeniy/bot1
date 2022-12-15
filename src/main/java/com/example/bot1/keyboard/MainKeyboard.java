package com.example.bot1.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

import static com.example.bot1.data.CommandNames.*;

@Component
public class MainKeyboard {
    public ReplyKeyboardMarkup getStartKeyboard() {
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText(PHONE_APPROVE);
        keyboardButton.setRequestContact(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(
                List.of(new KeyboardRow(List.of(keyboardButton))));
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getMainCustomerKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(
                List.of(new KeyboardRow(List.of(new KeyboardButton(CHECK_IN))),
                        new KeyboardRow(List.of(new KeyboardButton(LOOK_BOOKINGS)))));
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }
}
