package com.example.mobimarket.service.impl;

import com.example.mobimarket.dto.request.SendSmsRequest;
import com.example.mobimarket.security.config.TwilioConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

@Service
@Slf4j
public class SmsSendService {
    private final TwilioConfig twilioConfiguration;

    @Autowired
    public SmsSendService(TwilioConfig twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    public String sendSms(SendSmsRequest request, String message) {
        if (isPhoneNumberValid(request.getPhone())) {
            PhoneNumber to = new PhoneNumber(request.getPhone());
            PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
            MessageCreator creator = Message.creator(to, from, message);
            creator.create();
            log.info("Send sms {}", message);
            return "success";
        } else {
            throw new IllegalArgumentException(
                    "Phone number [" + request.getPhone() + "] is not a valid number"
            );
        }

    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        return !phoneNumber.isEmpty();
    }
}
